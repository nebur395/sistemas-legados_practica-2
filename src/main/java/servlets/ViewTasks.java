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

import net.sf.json.JSONObject;
import net.sf.json.JSONArray;

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
        boolean isGeneral = true;
        if(request.getHeader("type").equals("specific")){
            isGeneral = false;
        }
        Iterator <TaskVO> iter = (TaskDAO.getTasks((MusicSp) request.getSession().getAttribute("MusicSp"), isGeneral)).iterator();
        JSONArray tasks = constructResponse(iter);
        JSONObject responseData = new JSONObject();
        responseData.element("tasks", tasks);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(responseData.toString());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private JSONArray constructResponse (Iterator<TaskVO> itr) {
        JSONArray ja = new JSONArray();

        while(itr.hasNext()){
            TaskVO tVo = itr.next();
            JSONObject task = new JSONObject();
            task.element("date", tVo.getDate());
            task.element("description", tVo.getDescription());
            task.element("assignee", tVo.getAssignee());
            ja.add(task);
        }
        return ja;
    }
}
