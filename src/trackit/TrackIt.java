package trackit;

/**
 * Handles launching the program.
 */
public class TrackIt {

    /**
     * Initializes the program and launches the UI.
     *
     * @param args No args are accepted.
     */
    public static void main(String[] args) {
        MainMenu mm = new MainMenu();
        mm.display();
    }
}
