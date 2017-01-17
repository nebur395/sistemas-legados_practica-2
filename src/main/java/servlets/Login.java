package servlets;

import music_sp.MusicSp;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.eclipse.jetty.util.log.*;

public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private Logger log = null;

    /**
     * Default constructor.
     */
    public Login() {
        super();
        Log.setLog(new JavaUtilLog());
        log = Log.getLog();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String user = request.getHeader("user");
        String pass = request.getHeader("pass");
        log.info("**************************");
        log.info(user);
        log.info(pass);
        log.info("**************************");
        // The object that wraps the legacy system is created
        MusicSp msp = new MusicSp("155.210.152.51:104");
        // The MusicSp object is appended to the request in order to save the state
        request.getSession().setAttribute("MusicSp", msp);
        // The connection to the LS is created.
        msp.connect();
        if (msp.login(user, pass)) {
            msp.tasks();
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.getWriter().println("Contraseña o usuario incorrecto");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
