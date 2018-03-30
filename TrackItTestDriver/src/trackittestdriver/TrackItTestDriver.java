package trackittestdriver;

import java.sql.*;
//import java.util.Calendar;

/**
 *
 */
public class TrackItTestDriver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            String myDriver = "com.mysql.jdbc.Driver";
            Class.forName(myDriver);
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/TrackItDB", "root", "Jade2627")) {
                /*
                Calendar calendar = Calendar.getInstance();
                java.sql.Date addDate = new java.sql.Date(calendar.getTime().getTime());
                 */
                String query = "insert into lookups (listName, listValue)" + "values (?, ?)";
                PreparedStatement prepStat = conn.prepareStatement(query);
                prepStat.setString(1, "toiletries");
                prepStat.setString(2, "soap");
                /*
                prepStat.setInt (2, 3);
                prepStat.setDate (3, addDate);
                 */
                prepStat.execute();
            }
        } catch (Exception e) {
            System.err.println("Exception detected!");
            System.err.println(e.getMessage());
        }
    }
}
