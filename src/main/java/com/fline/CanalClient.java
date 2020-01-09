package com.fline;

import java.net.InetSocketAddress;
import java.sql.SQLOutput;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;


import com.alibaba.otter.canal.protocol.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import com.googlecode.protobuf.format.JsonFormat;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.otter.canal.client.*;

public class CanalClient{

    public static void main(String args[]) {

        System.out.println("===========启动====================");
        testmain();

    }
    public static void testmain() {                          //172.16.3.154:2181,172.16.3.9:2181,172.16.3.15:2181  172.16.3.150:2181,172.16.3.154:2181,172.16.3.155:2181
        System.out.println("准备获取连接");

        System.out.println("===============================");
//		ChannelHandlerContext
//		CanalConnector connector =null;
//			connector= CanalConnectors.newClusterConnector("172.16.1.201:2181,172.16.1.202:2181,172.16.1.203:2181",
//					"example", "", ""); //2号实例是主主切换加上挂载zookeeper的，这是集群版连接
        //连接canal 本地单机版，example是实例文件夹名称,canal安装地址，端口号一般未更改是11111
        CanalConnector connector;
        connector = CanalConnectors.newSingleConnector(new
                InetSocketAddress("47.106.108.208", 11111), "example", "", "");

        System.out.println("获取连接完成");
        int batchSize = 1; //这里指定数量是1000
        int emptyCount = 0;
        long batchId=0;
        while(true) {
            //一直尝试重新连接
            try {
                connector.connect();  //说明
                connector.subscribe(".*\\..*");  //过滤
                connector.rollback();    //回滚
                while (true) {

                    Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
                    batchId = message.getId();
                    int size = message.getEntries().size();
                    if (batchId == -1 || size == 0) {
                        emptyCount++;
                        //没有新信息的时候打印一下计数
                        System.out.println("empty count : " + emptyCount);

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("---------获取到变更信息，开始打印message--------------");
                        processEntry(message.getEntries());  //处理事件信息
//	            	  System.out.println(message.toString()); //查看全部信息，数据变更事件信息只是其中的一小部分
                        System.out.println("---------打印message    结束--------------");
                    }
                    connector.ack(batchId); // 信息处理无误后，提交确认消费信息
                    // connector.rollback(batchId); // 处理失败, 回滚数据
                }
            }catch (Exception e) {
                System.out.println("处理失败");
                System.out.println(e.getMessage());
                System.out.println("=========");
                e.printStackTrace();
//	        	   connector.rollback(batchId);
            } finally {
                connector.disconnect();
            }

        }

    }


    private static void processEntry( List<Entry> entrys) {
        for (Entry entry : entrys) {

            //开始          跳过开头和结尾                                                                                                                                 //结束
            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                continue;
            }

            //这段代码和连接消息系统很相似。变更事件会批量发送过来，待处理完毕后我们可以 ACK 这一批次，从而避免消息丢失。
            RowChange rowChage = null;
            try {

                //。变更事件会批量发送过来，待处理完毕后我们可以 ACK 这一批次，从而避免消息丢失。

                rowChage = RowChange.parseFrom(entry.getStoreValue());
                String sql = rowChage.getSql();
                System.out.println("ddl sql语句："+sql);
                //protobuf数据转 为json
                String rowChagejson=JsonFormat.printToString(rowChage);
                System.out.println("数据："+rowChagejson);
                //kafka生产者发送出去

            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }
            EventType eventType = rowChage.getEventType();//获取变更事件的类型
            System.out.println(String.format("================> binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(), eventType));

            Instant instant = Instant.ofEpochMilli(entry.getHeader().getExecuteTime());
            ZoneId zone = ZoneId.systemDefault();
            System.out.println("执行时间："+LocalDateTime.ofInstant(instant, zone));

            String baseName = entry.getHeader().getSchemaName();//获取到数据库库名
            String tableName=entry.getHeader().getTableName();//从事件信息里获取到变更数据的表名

//            if(eventType.equals(EventType.INSERT)) {
//                InsertSql(entry);
//            }else if(eventType.equals(EventType.UPDATE)){
//                UpdateSql(entry);
//            }else if(eventType.equals(EventType.DELETE)){
//                DeleteSql(entry);
//            }

            //等等还有很多

          /*  for (RowData rowData : rowChage.getRowDatasList()) {
                //rowData为一行变更的数据信息
                List<Column> afcolumns= rowData.getAfterColumnsList();//数据变更后字段内容
                rowData.getBeforeColumnsList();//数据变更前字段内
                showColumn(afcolumns, "UPDATE", tableName); //此时传入的是变更后的字段内容
            }*/
        }
    }
    private static void showColumn(List<Column> columns, String eventType, String tableName) {

        JSONObject json = new JSONObject();

        // 插入变更类型
        json.put("tableName", tableName);
        json.put("eventType", eventType);
        for (Column column : columns) { //遍历每个字段
            column.getIsKey();//查看字段是不是主键
            column.getName();//字段名
            column.getMysqlType();//字段的mysql类型
            //等等还有很多属性
            json.put(column.getName(), column.getValue());

        }
        // Testhbase.useHbase(json);
        if (columns.size() > 0) {
            //
            System.out.println("key:" + columns.get(0).getValue() + "---------" + json.toJSONString());
        }
    }


    /**
     * 保存更新语句
     *
     * @param entry
     */
    private static void UpdateSql(Entry entry) {
        try {
            RowChange rowChange = RowChange.parseFrom(entry.getStoreValue());
            List<RowData> rowDatasList = rowChange.getRowDatasList();
            for (RowData rowData : rowDatasList) {
                List<Column> newColumnList = rowData.getAfterColumnsList();
                StringBuffer sql = new StringBuffer("update " + entry.getHeader().getSchemaName() + "." + entry.getHeader().getTableName() + " set ");
                for (int i = 0; i < newColumnList.size(); i++) {
                    sql.append(" " + newColumnList.get(i).getName()
                            + " = '" + newColumnList.get(i).getValue() + "'");
                    if (i != newColumnList.size() - 1) {
                        sql.append(",");
                    }
                }
                sql.append(" where ");
                List<Column> oldColumnList = rowData.getBeforeColumnsList();
                for (Column column : oldColumnList) {
                    if (column.getIsKey()) {
                        //暂时只支持单一主键
                        sql.append(column.getName() + "=" + column.getValue());
                        break;
                    }
                }
                System.out.println(sql.toString());
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存删除语句
     *
     * @param entry
     */
    private static void DeleteSql(Entry entry) {
        try {
            RowChange rowChange = RowChange.parseFrom(entry.getStoreValue());
            List<RowData> rowDatasList = rowChange.getRowDatasList();
            for (RowData rowData : rowDatasList) {
                List<Column> columnList = rowData.getBeforeColumnsList();
                StringBuffer sql = new StringBuffer("delete from " + entry.getHeader().getSchemaName() + "." + entry.getHeader().getTableName() + " where ");
                for (Column column : columnList) {
                    if (column.getIsKey()) {
                        //暂时只支持单一主键
                        sql.append(column.getName() + "=" + column.getValue());
                        break;
                    }
                }
                System.out.println(sql.toString());
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存插入语句
     *
     * @param entry
     */
    private static void InsertSql(Entry entry) {
        try {
            RowChange rowChange = RowChange.parseFrom(entry.getStoreValue());
            List<RowData> rowDatasList = rowChange.getRowDatasList();
            for (RowData rowData : rowDatasList) {
                List<Column> columnList = rowData.getAfterColumnsList();
                StringBuffer sql = new StringBuffer("insert into " + entry.getHeader().getSchemaName() + "." + entry.getHeader().getTableName() + " (");
                for (int i = 0; i < columnList.size(); i++) {
                    sql.append(columnList.get(i).getName());
                    if (i != columnList.size() - 1) {
                        sql.append(",");
                    }
                }
                sql.append(") VALUES (");
                for (int i = 0; i < columnList.size(); i++) {
                    sql.append("'" + columnList.get(i).getValue() + "'");
                    if (i != columnList.size() - 1) {
                        sql.append(",");
                    }
                }
                sql.append(")");
                System.out.println(sql.toString());
            }
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }


}
