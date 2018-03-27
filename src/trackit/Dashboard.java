package trackit;

/**
 * BAL layer for handling all things about a single dashboard.
 */
public class Dashboard {

    private final DashboardType type;
    private String title;
    private Integer count;
    private String description;

    public Dashboard(DashboardType type) {
        this.type = type;
    }

    public DashboardType getType() {
        return this.type;
    }

    /**
     * Refreshes all non-final private fields from database.
     */
    public void refresh(){
        
    }
    
    @Override
    public String toString() {
        return "";
    }
}
