package trackit;

/**
 * BLL Layer: Handles all things dealing with the Main Menu.
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
