package servlets;

import dataAccess.TaskDAO;
import models.TaskVO;
import music_sp.MusicSp;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NewTask extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public NewTask() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Client will send a JSON with the information to create a new task. The JSON will be read and it's data extracted.

        //This String will be read directly from the JSON, this variable will not exist.
        String asignee = "";
        TaskVO task = null;
        //If it's empty, is a general task
        if(asignee.equals("")){
            task = new TaskVO("descipcion", "fecha");
        }
        //If no, is a specific task
        else{
            task = new TaskVO("descipcion", "fecha", asignee);
        }
        TaskDAO.newTask(task, (MusicSp) request.getSession().getAttribute("MusicSp"));
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
