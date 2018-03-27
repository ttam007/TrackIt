package trackit;

import java.util.*;

/**
 * BAL layer that handles all aspects of the Main Menu.
 */
public class MainMenu {

    private final ArrayList<Dashboard> dashboards = new ArrayList<>();

    public MainMenu() {
        createDashboards();
    }

    /**
     * Populates ArrayList<Dashboard> dashboards.
     */
    private void createDashboards() {
        //Create objects, then ...
        refreshDashboards();
    }

    /**
     * Refreshes the data in the current dashboards.
     */
    private void refreshDashboards() {

    }

    public ArrayList<Dashboard> getDashboards() {
        refreshDashboards();
        return this.dashboards;
    }

    /**
     * Handles the Logout menu command.
     */
    public void logout() {
        Login login = new Login();
        login.startLogout();
    }

    /**
     * Handles the Exit menu command.
     */
    public void exit() {
    }
}
