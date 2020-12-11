package discordBot;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class Filter {
    String JDBC_Driver = "org.mariadb.jdbc.Driver";
    String DB_URL = "jdbc:mariadb://localhost:3306/userdb";
    String username = "root";
    String userPass = "soe2020";

    private List<String> list = new ArrayList<String>();


    public Filter() {
        list.add("asperger");
        list.add("hurensohn");
        list.add("nutte");
        list.add("penis");
        list.add("florian");
        list.add(":(");
    }

    public boolean findInsult(String s) {
        for (String insults : list) {
            s = s.toLowerCase();
            if (s.contains(insults)) {
                return true;
            }
        }
        return false;
    }

    public boolean addInsults(String s) {
        for (String insults : list) {
            s = s.toLowerCase();
            if (s.equals(insults)) {
                return false;
            }
        }
        list.add(s);
        return true;
    }

    public void conTest() throws SQLException {
        try {
            Class.forName(JDBC_Driver);

            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stmt = conn.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery("SELECT Id FROM users");
            while (rs.next()) {
                String lastName = rs.getString("Id");
                System.out.println(lastName);
            }
            conn.close();
        }catch (Exception e) {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
}

