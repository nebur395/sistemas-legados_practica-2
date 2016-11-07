package servlets;

import dataAccess.TaskDAO;
import models.TaskVO;
import music_sp.MusicSp;

import java.io.IOException;
import java.io.BufferedReader;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

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
        JSONObject json = null;
        try{
            json = readJSON(request.getReader());
        }
        catch (IOException e){
            e.printStackTrace();
        }
        String date = json.getString("date");
        String description = json.getString("description");
        String assignee = json.getString("assignee");
        TaskVO task = null;
        //If it's empty, is a general task
        if(assignee.equals("")){
            if(description.length()<=12){
                task = new TaskVO(description, date);
                TaskDAO.newTask(task, (MusicSp) request.getSession().getAttribute("MusicSp"));
                response.setStatus(HttpServletResponse.SC_OK);
            }
            else{
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("La descripción es demasiado larga. Tamaño máximo: 12 caracteres");
            }
        }
        //If no, is a specific task
        else{
            int sum = assignee.length() + description.length();
            if(sum<=16){
                task = new TaskVO(description, date, assignee);
                TaskDAO.newTask(task, (MusicSp) request.getSession().getAttribute("MusicSp"));
                response.setStatus(HttpServletResponse.SC_OK);
            }
            else{
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println("La suma de los campos 'descripción' y 'encargado' no puede ser superior" +
                    "a 16 caracteres.");
            }
        }
    }

    private JSONObject readJSON (BufferedReader bf) throws IOException{
        StringBuffer jb = new StringBuffer();
        String line = null;
        while ((line = bf.readLine()) != null){
            jb.append(line);
        }
        return JSONObject.fromObject(jb.toString());
    }
}
