package trackit;

/**
 * Super class for all dashboard widgets.
 *
 * @author Bryan, Bond
 */
public abstract class Dashboard {

    // <editor-fold defaultstate="collapsed" desc="Constants">
    /**
     * Used as the preface before the data/text in each widget.
     */
    protected final static String PREFIX = "- ";

    /**
     * The type of dashboard widget. Set in child classes.
     */
    protected final DashboardType type;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Fields">
    /**
     * The standard text used by the widget for displaying its data.
     */
    protected String description;

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Constructors">
    /**
     *
     * @param type
     */
    protected Dashboard(DashboardType type) {
        this.type = type;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Methods">
    /**
     * Refreshes the data for the dashboard.
     *
     * @return True = successfully refreshed; False = there was an error.
     */
    protected abstract boolean refreshData();
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Public Methods">

    /**
     * Gets the type of Dashboard.
     *
     * @return
     */
    public DashboardType getType() {
        return type;
    }

    /**
     * Returns the string form of the widget's results.
     *
     * @return A string of the results. Will never be null.
     */
    public abstract String getData();

    // </editor-fold>
}
