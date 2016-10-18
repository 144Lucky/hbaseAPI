/**
 * Created by kojo on 2016/8/9.
 */

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HBaseConnection {
  //private static final Logger logger = LoggerFactory.getLogger(HBaseConnection.class);

  private static Configuration conf;
  public  static Connection connection;
  public  static Admin admin;

  public  HBaseConnection(){
    if (conf == null) {
      conf = newHBaseConfiguration();
    }

    if ((connection == null) || (connection.isClosed())) {
      //logger.info("connection is null or closed, creating a new one");
      try {
        connection = ConnectionFactory.createConnection(conf);
        admin = connection.getAdmin();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private static Configuration newHBaseConfiguration() {
    Configuration configuration = HBaseConfiguration.create();

    configuration.set("hbase.client.pause", "3000");
    configuration.set("hbase.client.retries.number", "5");
    configuration.set("hbase.client.operation.timeout", "60000");
    configuration.set("hbase.zookeeper.quorum", "172.16.0.30");
    configuration.set("hbase.zookeeper.property.clientPort", "2181");
    configuration.set("zookeeper.znode.parent", "/hbase");

    return configuration;
  }

  public  Connection getConnection() {
    return connection;
  }

  public  Admin getAdmin() {
    return admin;
  }

  public static void close() {
    try {
      if (null != admin)
        admin.close();
      if (null != connection)
        connection.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}

