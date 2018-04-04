/*
 * Decompiled with CFR 0_123.
 */
package trackit;

import java.util.ArrayList;
import trackit.Dashboard;
import trackit.Login;

public class MainMenu {
    private final ArrayList<Dashboard> dashboards = new ArrayList<>();

    public MainMenu() {
        this.createDashboards();
    }

    private void createDashboards() {
        this.refreshDashboards();
    }

    private void refreshDashboards() {
    }

    public ArrayList<Dashboard> getDashboards() {
        this.refreshDashboards();
        return this.dashboards;
    }

    public void logout() {
        Login login = new Login();
        login.startLogout();
    }

    public void exit() {
    }
}
