import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class HBaseOperation {

  public static void insterRow(Connection conn, String tableName,
      String rowkey, String colFamily, String col, String val)
      throws IOException {
    Table table = conn.getTable(TableName.valueOf(tableName));
    Put put = new Put(Bytes.toBytes(rowkey));
    put.addColumn(Bytes.toBytes(colFamily), Bytes.toBytes(col), Bytes.toBytes(val));
    table.put(put);

    //批量插入
       /* List<Put> putList = new ArrayList<Put>();
        putList.add(put);
        table.put(putList);*/
    table.close();
  }

  //删除数据
  public static void deleRow(Connection conn, String tableName, String rowkey,
      String colFamily, String col) throws IOException {
    Table table = conn.getTable(TableName.valueOf(tableName));
    Delete delete = new Delete(Bytes.toBytes(rowkey));
    table.delete(delete);
    table.close();
  }

  //根据rowkey查找数据
  public static void getDataByRowkey(Connection conn, String tableName, String rowkey
      ) throws IOException {
    Table table = conn.getTable(TableName.valueOf(tableName));
    Get get = new Get(Bytes.toBytes(rowkey));
    Result result = table.get(get);

    showCell(result);
    table.close();
  }
  public static void getDataByCol(Connection conn, String tableName, String rowkey,
      String colFamily, String col) throws IOException {
    Table table = conn.getTable(TableName.valueOf(tableName));
    Get get = new Get(Bytes.toBytes(rowkey));
    //获取指定列族数据
    get.addFamily(Bytes.toBytes(colFamily));
    //获取指定列数据
    get.addColumn(Bytes.toBytes(colFamily),Bytes.toBytes(col));
    Result result = table.get(get);

    showCell(result);
    table.close();
  }

  //批量查找数据
  public static void scanData(Connection conn,String tableName,
      String startRow, String stopRow) throws IOException {
    Table table = conn.getTable(TableName.valueOf(tableName));
    Scan scan = new Scan();
    scan.setStartRow(Bytes.toBytes(startRow));
    scan.setStopRow(Bytes.toBytes(stopRow));
    ResultScanner resultScanner = table.getScanner(scan);
    for (Result result : resultScanner) {
      showCell(result);
    }
    resultScanner.close();
    table.close();
  }

  //格式化输出
  public static void showCell(Result result) {
    Cell[] cells = result.rawCells();
    for (Cell cell : cells) {
      System.out.print("RowName:" + new String(CellUtil.cloneRow(cell)) + " ");
      System.out.print("Timestamp:" + cell.getTimestamp() + " ");
      System.out.print(
          "column Family:" + new String(CellUtil.cloneFamily(cell)) + " ");
      System.out.print(
          "column Name:" + new String(CellUtil.cloneQualifier(cell)) + " ");
      System.out.println("value:" + new String(CellUtil.cloneValue(cell)) + " ");
    }
  }
}
