package example;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

public class PostgreSQLBean {

    public Connection connect() {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/sample", "postgres", "password");
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void insert(Map<String, Object> data) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String query = String.format("insert into test (name, color, timestamp) values ('%s', '%s', '%s')",
                    (String) data.get("name"), (String) data.get("color"), sdf.format(date));
            int row_count = stmt.executeUpdate(query);
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<HashMap<String, String>> select() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            String query = "select * from test";
            ResultSet rs = stmt.executeQuery(query);
            ArrayList list = new ArrayList();
            while (rs.next()) {
                HashMap map = new HashMap();
                map.clear();
                map.put("name", rs.getString("name"));
                map.put("color", rs.getString("color"));
                map.put("timestamp", rs.getString("timestamp"));
                list.add(map);
            }
            rs.close();
            stmt.close();
            conn.close();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
