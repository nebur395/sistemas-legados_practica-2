package servlets;

import music_sp.MusicSp;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public Login() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Client will send user and pass. The JSON will be read and it's data extracted.

        String user = "grupo_01";
        String pass = "whateverthepasswordis";
        // The object that wraps the legacy system is created
        MusicSp msp = new MusicSp("155.210.152.51:104");
        // The MusicSp object is appended to the request in order to save the state
        request.getSession().setAttribute("MusicSp", msp);
        // The connection to the LS is created.
        msp.connect();
        msp.login(user, pass);
        msp.tasks();
        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
