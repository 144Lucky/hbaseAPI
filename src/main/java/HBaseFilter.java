import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Pair;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FuzzyRowFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kojo on 2016/8/11.
 */
public class HBaseFilter {

  public static void scanByFuzzy(Connection conn, String tableName,
       byte[] byte1, byte[] byte2) throws IOException {
    Table table = conn.getTable(TableName.valueOf(tableName));
    Scan scan = new Scan();
    List<Pair<byte[], byte[]>> pairList = new ArrayList<Pair<byte[], byte[]>>();


    Pair<byte[], byte[]> pair1 = Pair.newPair(byte1,byte2);
    pairList.add(pair1);
    Filter filter1 = new FuzzyRowFilter(pairList);
    scan.setFilter(filter1);

    ResultScanner resultScanner = table.getScanner(scan);
    for (Result result : resultScanner) {
      showCell(result);
    }
    resultScanner.close();
    table.close();
  }

  public static void showCell(Result result) {
    Cell[] cells = result.rawCells();
    for (Cell cell : cells) {
      System.out.print("RowName:" + new String(CellUtil.cloneRow(cell)) + " ");
      System.out.print("Timetamp:" + cell.getTimestamp() + " ");
      System.out.print(
          "column Family:" + new String(CellUtil.cloneFamily(cell)) + " ");
      System.out.print("row Name:" + new String(CellUtil.cloneQualifier(cell)) + " ");
      System.out.println("value:" + new String(CellUtil.cloneValue(cell)) + " ");
    }
  }
}
