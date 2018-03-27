package trackit;

import trackit.DAL.*;

/**
 * Handles launching the program.
 */
public class TrackIt {

    /**
     * Initializes the program and launches the login processor.
     *
     * @param args [0] = database location; [1] = database port; [2] = database
     * name; [3] = database username; [4] = database password;
     */
    public static void main(String[] args) {
        SQLConnector conn = SQLConnector.getInstance();
        conn.setConnectionString(args[0], args[1], args[2], args[3], args[4]);

        Login login = new Login();
        login.startLogin();
    }
}
