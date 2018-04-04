package trackit;


public class Dashboard
{
  private final DashboardType type;
  
  private String title;
  private Integer count;
  private String description;
  
  public Dashboard(DashboardType type)
  {
    this.type = type;
  }
  
  public DashboardType getType() {
    return type;
  }
  



  public void refresh() {}
  


  public String toString()
  {
    return "";
  }
}