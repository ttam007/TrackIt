/*
 * Decompiled with CFR 0_123.
 * 
 * Could not load the following classes:
 *  trackit.DAL.SQLConnector
 *  trackit.UI.LoginUI
 *  trackit.UI.MainMenuUI
 */
package trackit;

import trackit.DAL.SQLConnector;
import trackit.UI.LoginUI;
import trackit.UI.MainMenuUI;

public class Login {
    public void startLogin() {
        if (!SQLConnector.getInstance().isValidConnection()) {
            LoginUI dlgLogin = new LoginUI();
            dlgLogin.display();
        } else {
            MainMenuUI dlgMain = new MainMenuUI();
            dlgMain.display();
        }
    }

    public void startLogout() {
        LoginUI dlgLogin = new LoginUI();
        dlgLogin.display();
    }
}
