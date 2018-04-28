package trackit;

import trackit.DAL.*;
import trackit.UI.*;

/**
 * BLL Layer: Handles all things dealing with logging in and out of the
 * database/program.
 *
 * @author Bryan, Bond
 */
public class Login {

    private static final int MAX_LOGIN_ATTEMPTS = 3;
    private int loginAttempts = 0;

    /**
     * Default Constructor.
     */
    public Login() {
    }

    /**
     * Starts the login process, modifying the existing connection string with
     * the specified user name and password.
     *
     * @param userName
     * @param password
     * @return True = successful login; False = unsuccessful login.
     */
    public boolean startLogin(String userName, String password) {
        SQLConnector.getInstance().setConnectionString(userName, password);
        return startLogin();
    }

    /**
     * Starts the login process using the existing connection string.
     *
     * @return True = successful login; False = unsuccessful login.
     */
    public boolean startLogin() {
        boolean returnValue = false;

        String maxAttemptsError = "Maximum number of login attempts has been exceeded.";

        if (!SQLConnector.getInstance().isValidConnection()) {
            loginAttempts++;
            String errorMessage = Utilities.getErrorMessage();
            if (this.isTooManyLoginAttempts()) {
                errorMessage += "\r\n\r\n" + maxAttemptsError;
            }
            Utilities.setErrorMessage(errorMessage);
        } else {
            MainMenuFrame mainMenu = new MainMenuFrame();
            mainMenu.display();
            returnValue = true;
        }
        return returnValue;
    }

    /**
     * Starts the logout process.
     */
    public void startLogout() {
        LoginFrame dlgLogin = new LoginFrame(SQLConnector.getInstance().getUserName());
        dlgLogin.display();
    }

    /**
     * Has User attempted to log in too many times?
     *
     * @return True = login attempts are greater than the maximum allowed; False
     * = login attempts are less than the maximum allowed.
     */
    public boolean isTooManyLoginAttempts() {
        return loginAttempts >= MAX_LOGIN_ATTEMPTS;
    }
}
