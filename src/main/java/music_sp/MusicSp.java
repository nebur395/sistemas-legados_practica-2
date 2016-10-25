package music_sp;

import java.io.IOException;

@SuppressWarnings("unused")
public class MusicSp {
    private Connection connection;

    public MusicSp(String host) {
        this.connection = new Connection(host);
    }

    public boolean connect() throws IOException {
        return this.connection.connect();
    }

    public void close() throws IOException {
        this.connection.quit();
    }

    public Connection getConnection() {
        return this.connection;
    }

    public void login(String user, String password) throws IOException {
        this.connection.enter();
        this.connection.writeString(user);
        this.connection.enter();
        this.connection.writeString(password);
        this.connection.enter();
        this.connection.enter();
    }

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
