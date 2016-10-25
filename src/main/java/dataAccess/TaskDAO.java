package music_sp;

import java.io.IOException;
import java.util.ArrayList;

public class TaskDAO {
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
            msp.getConnection().writeString(t.getAsignee());
            doubleEnter(msp);
        }
        msp.getConnection().writeString(t.getDescription());
        doubleEnter(msp);
        msp.getConnection().writeString("3");
        doubleEnter(msp);
    }

    private static void doubleEnter(MusicSp msp) throws IOException {
        msp.getConnection().enter();
        //String[] result = msp.getConnection().getScreen();
        //if (result[41].equals("                                                                                 ")) {
        //    msp.getConnection().enter();
        //}
    }

    public static ArrayList<TaskVO> getTasks(MusicSp msp) {
        return null;
    }

}
