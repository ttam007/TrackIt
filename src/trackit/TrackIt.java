package trackit;

/**
 * Handles all aspects of starting the program and initializing global classes.
 */
public class TrackIt {

    /**
     * @param args The command line arguments, which this program as none.
     */
    public static void main(String[] args) {
        /*Create instances of global classes here.  Then pass those instances 
        into Login & MainMenu via constructors.
        For example:
        
        GlobalItems global = new GlobalItems();
        LoginUI login = new LoginUI(global);
        login.display();
        
        */
        MainMenu mm = new MainMenu();
        mm.display();
    }
}
