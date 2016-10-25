package servlets;

import dataAccess.TaskDAO;
import models.TaskVO;
import music_sp.MusicSp;

import java.io.IOException;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewTasks extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public ViewTasks() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Client will send a request to view either all general or specific tasks, depending on an attribute value on
        // the JSON. The JSON will be read and the attribute value extracted.

        // This boolean will be read directly from the JSON, this variable will not exist.
        boolean isGeneral = true;
        Iterator <TaskVO> iter = (TaskDAO.getTasks((MusicSp) request.getSession().getAttribute("MusicSp"), isGeneral)).iterator();
        // Here a JSON will be created with the list of tasks in the Array. After that, it will be
        // attached to the response.
        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
