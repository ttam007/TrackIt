package trackit;

/**
 * Super class for all dashboard widgets.
 *
 * @author Bryan, Bond
 */
public abstract class Dashboard {

    // <editor-fold defaultstate="collapsed" desc="Constants">
    protected final static String PREFIX = "- ";
    protected final DashboardType type;
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Private Fields">
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
     * Refreshes the data for the dashboard.
     *
     * @return True = successfully refreshed; False = there was an error.
     */
    protected abstract boolean refreshData();
    // </editor-fold>
}
