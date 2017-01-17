package music_sp;

import java.io.IOException;

/**
 * Abstraction of the remote system
 */
@SuppressWarnings("unused")
public class MusicSp {
    /**
     * Connection with the legacy system
     */
    private Connection connection;

    /**
     * Constructor of the class
     *
     * @param host Host to connect to, in the format "ip:port"
     */
    public MusicSp(String host) {
        this.connection = new Connection(host);
    }

    /**
     * Connect to the remote terminal
     *
     * @return true in case the connection was succesful, false otherwise
     * @throws IOException In case there was an error during the communication with the server
     */
    public boolean connect() throws IOException {
        return this.connection.connect();
    }

    /**
     * Closes the connection with the server
     *
     * @throws IOException In case there was an error during the communication with the server
     */
    public void close() throws IOException {
        this.connection.quit();
    }

    /**
     * Returns the low level connection with the remote terminal
     *
     * @return Low level Connection object
     */
    public Connection getConnection() {
        return this.connection;
    }

    /**
     * From the welcome screen, logs into the remote system
     *
     * @param user     Username
     * @param password Password
     * @return true in case the login was correct, false if either the username or the password is incorrect
     * @throws IOException In case there was an error during the communication with the server
     */
    public boolean login(String user, String password) throws IOException {
        if (!(user.contains("grupo_") && password.contains("secreto"))) {return false;}
        this.connection.enter();
        this.connection.writeString(user);
        this.connection.enter();
        // Check incorrect username
        String[] result = this.connection.getScreen();
        if (result[6].contains("Userid is not authorized")) {
            // Incorrect user
            return false;
        }
        this.connection.writeString(password);
        this.connection.enter();
        // Check incorrect password
        result = this.connection.getScreen();
        if (result[6].contains("Password incorrect!")) {
            // Incorrect password
            return false;
        }
        this.connection.enter();
        return true;
    }

    /**
     * From the main menu, opens TAREAS.C
     *
     * @throws IOException In case there was an error during the communication with the server
     */
    public void tasks() throws IOException {
        this.connection.writeString("TAREAS.C");
        this.connection.enter();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {

        }
    }
}
