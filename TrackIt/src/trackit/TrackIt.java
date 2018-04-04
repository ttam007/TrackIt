package trackit;

import trackit.DAL.SQLConnector;

public class TrackIt {

    public TrackIt() {
    }

    public static void main(String[] args) {
        SQLConnector conn = SQLConnector.getInstance();
        switch (args.length) {
            case 5:
                conn.setConnectionString(args[0], args[1], args[2], args[3], args[4]);
                break;
            case 4:
                conn.setConnectionString(args[0], args[1], args[2], args[3]);
                break;
            case 3:
                conn.setConnectionString(args[0], args[1], args[2]);
                break;
            case 2:
                conn.setConnectionString(args[0], args[1]);
                break;
            case 1:
                conn.setConnectionString(args[0]);
                break;
        }

        Login login = new Login();
        login.startLogin();
    }
}
