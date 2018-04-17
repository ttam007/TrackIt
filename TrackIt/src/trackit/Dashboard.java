package trackit;

/**
 *
 * @author Bryan
 */
public class Dashboard {

    private final DashboardType type;

    private String title;
    private Integer count;
    private String description;

    /**
     *
     * @param type
     */
    public Dashboard(DashboardType type) {
        this.type = type;
    }

    /**
     *
     * @return
     */
    public DashboardType getType() {
        return type;
    }

    /**
     * refresh
     */
    public void refresh() {
    }

    @Override
    public String toString() {
        return "";
    }
}
