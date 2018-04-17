package trackit;

import trackit.DAL.*;

/**
 * The TrackIt program starts here!
 */
public class TrackIt {

    /**
     * Processes command line parameters before launching the UI.
     *
     * @param args The command line parameters used to set the connection
     * string.
     */
    public static void main(String[] args) {
        SQLConnector conn = SQLConnector.getInstance();
        try {
            switch (args.length) {
                case 5:
                    conn.setConnectionString(args[0], Integer.parseInt(args[1]), args[2], args[3], args[4]);
                    break;
                case 4:
                    conn.setConnectionString(args[0], Integer.parseInt(args[1]), args[2], args[3]);
                    break;
                case 3:
                    conn.setConnectionString(args[0], Integer.parseInt(args[1]), args[2]);
                    break;
                case 2:
                    conn.setConnectionString(args[0], args[1]);
                    break;
                case 1:
                    conn.setConnectionString(args[0]);
                    break;
                default:
                    break;
            }
        } catch (NumberFormatException exNF) {
            System.out.println("Invalid command line parameter " + args[1]);
            System.exit(1); //First error.
        }

        Login login = new Login();
        if (!login.startLogin()) {
            login.startLogout();
        }
    }
}
