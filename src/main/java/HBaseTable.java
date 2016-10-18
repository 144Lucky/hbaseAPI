/**
 * Created by kojo on 2016/8/9.
 */

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HRegionLocation;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.RegionLocator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HBaseTable {
//  private static Connection conn;
//  private static Admin admin;
//
//  public HBaseTable(){
//    HBaseConnection hbc = new HBaseConnection();
//    admin = hbc.getAdmin();
//    conn = hbc.getConnection();
//  }

  public static void listTabs(Admin admin) throws IOException {
    HTableDescriptor hTableDescriptors[] = admin.listTables();
    for (HTableDescriptor htd : hTableDescriptors) {
      System.out.println(htd.getNameAsString());
    }
  }

  public static void createTable(Admin admin, String tabName, String[] cols)
      throws IOException {

    TableName tn = TableName.valueOf(tabName);

    if (admin.tableExists(tn)) {
      System.out.println("talbe is exists!");
    } else {
      HTableDescriptor hTableDescriptor = new HTableDescriptor(tn);
      for (String col : cols) {
        HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(col);
        hTableDescriptor.addFamily(hColumnDescriptor);
      }
      admin.createTable(hTableDescriptor);
    }
  }

  //预分区建表
  public static void createTable(Admin admin, String tabName, String[] cols, byte[][] splitKeys)
      throws IOException {

    TableName tn = TableName.valueOf(tabName);

    if (admin.tableExists(tn)) {
      System.out.println("talbe is exists!");
    } else {
      HTableDescriptor hTableDescriptor = new HTableDescriptor(tn);
      for (String col : cols) {
        HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(col);
        hTableDescriptor.addFamily(hColumnDescriptor);
      }
      admin.createTable(hTableDescriptor,splitKeys);
    }
  }
  //指定rowkey预分区
  public static void createTable(Admin admin, String tabName, String[] cols,
      byte[] startKey, byte[] endKey, int numRegions) throws IOException {
    TableName tn = TableName.valueOf(tabName);

    if (admin.tableExists(tn)) {
      System.out.println("talbe is exists!");
    } else {
      HTableDescriptor hTableDescriptor = new HTableDescriptor(tn);
      for (String col : cols) {
        HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(col);
        hTableDescriptor.addFamily(hColumnDescriptor);
      }
      admin.createTable(hTableDescriptor,startKey,endKey,numRegions);
    }
  }

  public static void deleteTable(Admin admin, String tabName) throws IOException {
    TableName tn = TableName.valueOf(tabName);
    if (admin.tableExists(tn)) {
      admin.disableTable(tn);
      admin.deleteTable(tn);
    }else {
      System.out.println( "talbe"+ tabName +" is exists!");
    }
  }

  public static void getRegion(Connection conn, String tabName) throws IOException {

    RegionLocator rl = conn.getRegionLocator(TableName.valueOf(tabName));
    List<HRegionLocation> hrls = new ArrayList<HRegionLocation>();
    for (HRegionLocation hRegionLocation : hrls = rl.getAllRegionLocations()) {
      System.out.println("table"+ tabName + "region location hostname" + hRegionLocation.getHostname());
      System.out.println("table"+ tabName + "region location RegionInfo" + hRegionLocation.getRegionInfo());
      System.out.println("table"+ tabName + "region location ServerName" + hRegionLocation.getServerName());
    }
  }
}
