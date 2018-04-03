package implementation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class TextReader{
    public static void main(String args[]) {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://" +
                "192.168.1.5:3306/gupiao", "root", "root");
            String sql = "Insert into ACT_cdKeytempsss (cdKey, ActivityId) VALUES (?,3577)";

            String sqlupdate = "update ACT_cdKey set status=1 where CdKey = ?";


            con.prepareStatement(sql).executeUpdate() ;
            PreparedStatement ps = con.prepareStatement(sql) ;

            PreparedStatement ps1 = con.prepareStatement(sqlupdate);

            con.setAutoCommit(false);  // 关闭事务自动提交


            String pathname = "C:/Users/Administrator/Desktop/result8.txt";
            File fileName = new File(pathname);

            InputStreamReader reader = new InputStreamReader(new FileInputStream(fileName));
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            line = br.readLine();


            while (line != null) {
                ps.setString(1, line);
                ps1.setString(1, line);
                line = br.readLine();
                ps.addBatch();
                ps1.addBatch();
            }

            ps.executeBatch();
            ps1.executeBatch();
            con.commit();
            ps.close();
            ps1.close();
            con.close();
            br.close();
        }
        catch (Exception e) {
            e.printStackTrace();// TODO: handle exception
        }

    }
}
