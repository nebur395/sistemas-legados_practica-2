package music_sp;

import java.io.*;

@SuppressWarnings("unused")
public class Connection {
    private Process connection;
    private String hostDirection;
    public BufferedWriter stdin;
    public BufferedReader stdout;
    private boolean lastCommandExitCode = false;

    private String[] lc;
    private String lc_keyboardState;
    private boolean lc_success;


    public Connection(String host) {
        this.hostDirection = host;
    }

    public boolean connect() throws IOException {
        // Create process
        ProcessBuilder builder = new ProcessBuilder(Const.EXECUTABLE_NAME, this.hostDirection);
        builder.redirectErrorStream(true); // Redirect stderr to stdout
        this.connection = builder.start();
        // Get IO
        this.stdin = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
        this.stdout = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        try {
            Thread.sleep(1500); //Wait until the connection is done to prevent the next instruction from finding a blocked keyboard
        } catch (InterruptedException e) {
            // NOBODY CARES ABOUT YOUR EXCEPTIONS
        }
        return true;
    }

    public void send(String s) throws IOException {
        if (s.endsWith("\n")) {
            this.stdin.write(s);
            this.stdin.flush();
        } else {
            this.stdin.write(s + "\n");
            this.stdin.flush();
        }
    }

    public boolean parseResult(String res1, String res2) {
        //The status message consists of 12 blank - separated fields.
        //1 Keyboard State: If the keyboard is unlocked, the letter U. If the keyboard is locked waiting for a response from the host, or if not connected to a host, the letter L. If the keyboard is locked because of an operator error (field overflow, protected field, etc.), the letter E.
        //2 Screen Formatting: If the screen is formatted, the letter F. If unformatted or in NVT mode, the letter U.
        //3 Field Protection: If the field containing the cursor is protected, the letter P. If unprotected or unformatted, the letter U.
        //4 Connection State: If connected to a host, the string C(hostname). Otherwise, the letter N.
        //5 Emulator Mode: If connected in 3270 mode, the letter I. If connected in NVT line mode, the letter L. If connected in NVT character mode, the letter C. If connected in unnegotiated mode (no BIND active from the host), the letter P. If not connected, the letter N.
        //6 Model Number (2-5)
        //7 Number of Rows: The current number of rows defined on the screen. The host can request that the emulator use a 24x80 screen, so this number may be smaller than the maximum number of rows possible with the current model.
        //8 Number of Columns: The current number of columns defined on the screen, subject to the same difference for rows, above.
        //9 Cursor Row: The current cursor row (zero-origin).
        //10 Cursor Column: The current cursor column (zero-origin).
        //11 Window ID: The X window identifier for the main x3270 window, in hexadecimal preceded by 0x. For s3270 and c3270, this is zero.
        //12 Command Execution Time: The time that it took for the host to respond to the previous commnd, in seconds with milliseconds after the decimal. If the previous command did not require a host response, this is a dash.
        //System.out.println(res2);
        //System.out.println(res1);
        String[] result = res1.split("\\s+");
        this.lc_keyboardState = result[0];
        this.lc = result;
        return (this.lc_success = res2.equals("ok"));
    }

    public boolean parseResult() {
        try {
            String res1 = this.stdout.readLine();
            String res2 = this.stdout.readLine();
            return this.parseResult(res1, res2);
        } catch (IOException e) {
            return false;
        }
    }

    public boolean writeString(String s) throws IOException {
        this.send("string(\"" + s + "\")");
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
        }
        return this.parseResult();
    }

    public boolean enter() throws IOException {
        this.send("enter");
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
        }
        return this.parseResult();
    }

    public boolean quit() throws IOException {
        this.send("quit");
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
        }
        this.connection.destroy();
        return true;
    }

    public String[] getScreen() throws IOException {
        this.writeString("ascii");
        int i = 0;
        String line;
        String result[] = new String[Const.SCREEN_HEIGHT];
        boolean keepLoop = true;
        while (keepLoop) {
            line = this.stdout.readLine();
            System.out.println(line);
            if(line.startsWith("data:")) {
                result[i] = line.substring(6);
            }
            i++;
            if (line.startsWith("ok") || line.startsWith("error")) {
                keepLoop = false;
            }
        }
        return result;
    }
    public boolean printScreen() throws IOException {
        this.send("ascii");
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
        }
        String line;
        String res1 = "";
        String res2 = "error";
        boolean keepLoop = true;
        while (keepLoop) {
            line = this.stdout.readLine();
            if (line.startsWith("data:")) {
                System.out.println(line.substring(5));
            } else if (line.startsWith("ok") || line.startsWith("error")) {
                keepLoop = false;
                res2 = line;
            } else {
                res1 = line;
            }
        }
        return parseResult(res1, res2);
    }
}
