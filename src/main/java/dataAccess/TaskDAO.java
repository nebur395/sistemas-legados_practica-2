package dataAccess;

import models.TaskVO;
import music_sp.MusicSp;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Class that offers a collection of methods to manage Tasks in a remote terminal
 */
public class TaskDAO {
    /**
     * Inserts a new task into the given system
     *
     * @param t   Task to insert
     * @param msp System to insert the Task into
     * @throws IOException In case there was an error during the communication with the server
     */
    public static void newTask(TaskVO t, MusicSp msp) throws IOException {
        msp.getConnection().writeString("1");
        doubleEnter(msp);
        if (t.isGeneral()) {
            msp.getConnection().writeString("1");
            doubleEnter(msp);
        } else {
            msp.getConnection().writeString("2");
            doubleEnter(msp);
        }
        msp.getConnection().writeString(t.getDate());
        doubleEnter(msp);
        if (!t.isGeneral()) {
            msp.getConnection().writeString(t.getAssignee());
            doubleEnter(msp);
        }
        msp.getConnection().writeString(t.getDescription());
        doubleEnter(msp);
        msp.getConnection().writeString("3");
        doubleEnter(msp);
    }

    /**
     * Check if we have to do a double enter (in case we have reached the end of the screen) and does it.
     *
     * @param msp MusicSp to do the double enter
     * @throws IOException In case there was an error during the communication with the server
     */
    private static void doubleEnter(MusicSp msp) throws IOException {
        msp.getConnection().enter();
        String[] result = msp.getConnection().getScreen();
        if (!result[41].equals("                                                                                ")) {
            msp.getConnection().enter();
        }
    }

    /**
     * Gets all the tasks in the defined MusicSp
     *
     * @param msp      System to connect to
     * @param taskType Type of the task. true if its a general Task, false otherwise
     * @return List of all the tasks in the system
     */
    public static ArrayList<TaskVO> getTasks(MusicSp msp, boolean taskType) throws IOException {
        // 2, enter, taskType?1:2, enter, READ, 3
        // 2 work modes. If the task list crosses the screen border, an if not
        msp.getConnection().writeString("2");
        doubleEnter(msp);
        if (taskType) {
            // General Tasks
            msp.getConnection().writeString("1");
            doubleEnter(msp);
        } else {
            // Specific Tasks
            msp.getConnection().writeString("2");
            doubleEnter(msp);
        }

        ArrayList<TaskVO> taskList = new ArrayList<TaskVO>();
        String[] result = msp.getConnection().getScreen();
        for (int i = result.length - 1; i != 0; i--) {
            // Start from bottom to top, until we see our own command (stop of list)
            if (result[i].startsWith("1    ") || result[i].startsWith("2    ")) {
                break; // Stop iterating, end of last task list
            }
            if (result[i].startsWith("TASK ")) {
                TaskVO t = recordToTask(result[i]);
                taskList.add(t);
            }
        }
        //If the last line is not empty, the task list CROSSES the border
        while (!result[39].equals("                                                                                ")) {
            msp.getConnection().enter(); // Advance screen
            result = msp.getConnection().getScreen();

            for (String i : result) {
                if (i.startsWith("TOTAL TASK")) {
                    break;
                }
                if (i.startsWith("TASK ")) {
                    TaskVO t = recordToTask(i);
                    taskList.add(t);
                }

            }

        }
        return taskList;
    }

    private static TaskVO recordToTask(String record) {
        String[] splitted_record = record.split(" ");
        // splitted_record content:
        // 0: "TASK" constant
        // 1: task number, followed by ":" ("1:")
        // 2: task type: "GENERAL" or "SPECIFIC"
        // 3: date
        // 4: assignee ("-----" in general tasks)
        // 5: description
        TaskVO t;
        if (splitted_record[2].equals("GENERAL")) {
            // General Task
            t = new TaskVO(splitted_record[5], splitted_record[3]);
        } else {
            // Specific Task
            t = new TaskVO(splitted_record[5], splitted_record[3], splitted_record[4]);
        }
        return t;
    }
}
