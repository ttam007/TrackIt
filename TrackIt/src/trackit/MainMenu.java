package trackit;

import java.util.*;

/**
 *
 * @author Bryan
 */
public class MainMenu {

    private final ArrayList<Dashboard> dashboards = new ArrayList<>();

    /**
     * Create dashboard
     */
    public MainMenu() {
        this.createDashboards();
    }

    /**
     * Creates all the dashboard objects and adds them to this.dashboards.
     */
    private void createDashboards() {
        //TODO:  
        this.refreshDashboards();
    }

    /**
     * Refreshes the data on all dashboards with current information.
     */
    private void refreshDashboards() {
    }

    /**
     *
     * @return
     */
    public ArrayList<Dashboard> getDashboards() {
        this.refreshDashboards();
        return this.dashboards;
    }

    /**
     * logout
     */
    public void logout() {
        Login login = new Login();
        login.startLogout();
        //TODO:  close the Main Menu UI; done in MainMenuFrame
    }
}
