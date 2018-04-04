package trackit;

import trackit.DAL.SQLConnector;

public class TrackIt {

    public TrackIt() {
    }

    public static void main(String[] args) {
        SQLConnector conn = SQLConnector.getInstance();
        if (args.length >= 5) {
            conn.setConnectionString(args[0], args[1], args[2], args[3], args[4]);
        }
        
        Login login = new Login();
        login.startLogin();
    }
}
