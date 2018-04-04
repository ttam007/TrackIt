package trackit;

public class Utilities
{
  public static final String PROGRAM_NAME = "TrackIt HITS";
  
  public Utilities() {}
  
  public static String getWindowCaption(String windowName)
  {
    return String.format("{0} - {1}", new Object[] { "TrackIt HITS", windowName });
  }
}
