package dataAccess;

import java.io.IOException;
import java.util.ArrayList;
import music_sp.*;
import models.TaskVO;

/**
 * Class that offers a collection of methods to manage Tasks in a remote terminal
 */
public class TaskDAO {
    /**
     * Inserts a new task into the given system
     *
     * @param t Task to insert
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
     * @param msp System to connect to
     * @param taskType Type of the task. true if its a general Task, false otherwise
     * @return List of all the tasks in the system
     */
    public static ArrayList<TaskVO> getTasks(MusicSp msp, boolean taskType) {
        return null;
    }

}
