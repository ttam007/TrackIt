package trackit;

import trackit.DAL.SQLConnector;
import trackit.UI.*;

/**
 * BAL Layer that handles all aspects of logging in and out.
 */
public class Login {

    /**
     * Starts the login process. Shows a UI if necessary.
     */
    public void startLogin() {
        if (!SQLConnector.getInstance().isValidConnection()) {
            LoginUI dlgLogin = new LoginUI();
            dlgLogin.display();
        } else {
            MainMenuUI dlgMain = new MainMenuUI();
            dlgMain.display();
        }
    }

    /**
     * Starts the logout process. Shows the Login UI once done.
     */
    public void startLogout() {
        //TODO
        
        LoginUI dlgLogin = new LoginUI();
        dlgLogin.display();
    }
}
