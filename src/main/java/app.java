
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.HConstants;

import java.io.IOException;

/**
 * Created by kojo on 2016/8/10.
 */

public class app {
  public static void main(String[] args) throws IOException {
    Connection conn;
    Admin admin;

    HBaseConnection hbc = new HBaseConnection();
    conn = hbc.getConnection();
    admin = hbc.getAdmin();

   HBaseTable.listTabs(admin);
   // HBaseTable.getRegion(conn,"t1");
    // HBaseOperation.scanData(conn,"t1","r1","r3");
    byte[] byte1 = ("????_2016_?????").getBytes();
    byte[] byte2 = {1,1,1,1,0,0,0,0,0,0,1,1,1,1,1};

    HBaseFilter.scanByFuzzy(conn, "t1", byte1, byte2);
  }
}
