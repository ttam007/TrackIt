package trackit;

import java.util.*;
import trackit.UI.*;

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

    /**
     * exit app
     */
    public void exit() {
        //TODO:  close all other windows?
    }

    /**
     * start the application
     */
    public void start() {
        MainMenuFrame dlgMain = new MainMenuFrame();
        dlgMain.display();
    }
}
