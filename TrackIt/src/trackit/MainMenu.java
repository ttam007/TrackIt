package trackit;

import java.util.*;
import trackit.UI.*;

public class MainMenu {
    private final ArrayList<Dashboard> dashboards = new ArrayList<>();

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

    public ArrayList<Dashboard> getDashboards() {
        this.refreshDashboards();
        return this.dashboards;
    }

    public void logout() {
        Login login = new Login();
        login.startLogout();
        //TODO:  close the Main Menu UI; done in MainMenuUI
    }

    public void exit() {
        //TODO:  close all other windows?
    }
    
    public void start(){
        MainMenuUI dlgMain = new MainMenuUI();
        dlgMain.display();
    }
}
