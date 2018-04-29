package trackit;

/**
 *
 * @author Bond
 */
public class MainMenu {

    /**
     * Default Constructor.
     */
    public MainMenu() {
    }

    /**
     * Logs out of the database and brings up the login window.
     */
    public void logout() {
        Login login = new Login();
        login.startLogout();
    }
}
