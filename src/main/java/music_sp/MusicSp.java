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
     * @throws IOException In case there was an error during the communication with the server
     */
    public void login(String user, String password) throws IOException {
        this.connection.enter();
        this.connection.writeString(user);
        this.connection.enter();
        this.connection.writeString(password);
        this.connection.enter();
        this.connection.enter();
    }

    /**
     * From the main menu, opens TAREAS.C
     *
     * @throws IOException In case there was an error during the communication with the server
     */
    public void tasks() throws IOException {
        this.connection.writeString("FLIB");
        this.connection.enter();
        this.connection.writeString("TAREAS.C");
        this.connection.enter();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {

        }
    }
}
