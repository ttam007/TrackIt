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

    /**
     * @return description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     *
     * @return A String that will contain the content of each string
     */
    @Override
    public String toString() {

        return this.getDescription();
    }
}
