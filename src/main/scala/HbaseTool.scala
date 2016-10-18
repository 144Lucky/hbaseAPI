/**
  * Created by kojo on 2016/9/18.
  */

import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{TableName, HColumnDescriptor, HTableDescriptor, HBaseConfiguration}
import org.apache.hadoop.hbase.client._

object HbaseTool {

  val hbaseConf = HBaseConfiguration.create()
  val conn = ConnectionFactory.createConnection(hbaseConf)
  val admin = conn.getAdmin



  def createTable(){

    val tName = TableName.valueOf("")

    if (admin.isTableAvailable(tName)){
      admin.disableTable(tName)
      admin.deleteTable(tName)
    }

    if (!admin.isTableAvailable(tName)) {

      val tableDesc = new HTableDescriptor(tName)
      val cf = new HColumnDescriptor("cfs".getBytes())
      tableDesc.addFamily(new HColumnDescriptor(cf))

      val startKey = Bytes.toBytes("aaaaaaa")
      val endKey = Bytes.toBytes("zzzzzzz")
      val splitKeys = Bytes.split(startKey, endKey, 3)

      admin.createTable(tableDesc, splitKeys)

      val r = conn.getRegionLocator(tName).getAllRegionLocations
      while(r == null || r.size() == 0) {
        System.out.print(s"region not allocated")
        Thread.sleep(1000)
      }
      System.out.print(s"region allocated $r")
    }

    admin.close()
    conn.close()
  }


}
