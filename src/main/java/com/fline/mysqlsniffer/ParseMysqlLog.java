/*
package com.fline.mysqlsniffer;

import com.fline.aic.audit.service.BinlogService;
import com.fline.aic.audit.util.BeanUtil;
import com.fline.aic.audit.util.LocalDateUtil;
import com.fline.aic.audit.util.PropertiesUtil;
import com.jcraft.jsch.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.time.Duration;
import java.time.LocalDateTime;

public class ParseMysqlLog {
    private Logger logger = LoggerFactory.getLogger(ParseMysqlLog.class);


    private static  final String FILTER_SQL = "sql.filter.begin";
    private static final String HOST="mysql.host";
    private static final String USERNAME="mysql.username";
    private static final String PASSWORD="mysql.password";
    private static final String PORT="mysql.port";



    private  BinlogService binlogService;

    public ParseMysqlLog() {
        binlogService = (BinlogService) BeanUtil.getBean("binlogService");
    }

    private Session session = null;

    */
/**配置连接
     * @param user
     * @param passwd
     * @param host
     * @param post
     * @throws Exception
     *//*

    public void connect(String user, String passwd, String host,int post) throws Exception {
        JSch jsch = new JSch();
        session = jsch.getSession(user, host, post);
        if (session == null) {
            throw new Exception("session is null");
        }
        session.setPassword(passwd);
        java.util.Properties config = new java.util.Properties();
        //第一次登陆
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        try {
            session.connect(30000);
        } catch (Exception e) {
            throw new Exception("连接远程端口无效或用户名密码错误");
        }

    }


    */
/**
     * @param command shell 命令
     * @param user 用户名
     * @param passwd 密码
     * @param host ip地址
     * @param port 端口号
     * @throws Exception
     *//*

    public  void execCmd(String command, String user, String passwd, String host,int port) throws Exception {
        connect(user, passwd, host,port);
        BufferedReader reader = null;
        Channel channel = null;
        try {
            channel = session.openChannel("exec");
            ((ChannelExec) channel).setCommand(command);

            channel.setInputStream(null);
            ((ChannelExec) channel).setErrStream(System.err);

            channel.connect();
            InputStream in = channel.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in));
            parseData(reader);

        } catch (IOException | JSchException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (channel != null) {
                channel.disconnect();
            }
            session.disconnect();
        }
    }

    */
/**
     * 解析读到的数据
     *       时间                 用户  ip              数据库   耗时  返回结果数         sql语句
     * 格式：2019-06-25 15:52:28	 NULL 125.120.145.141	NULL   0ms	 5	SELECT * FROM `canal_test`.`user` LIMIT 0,1000
     * @param reader
     * @throws IOException
     *//*

    private  void parseData(BufferedReader reader) throws Exception {
        String buf;

        //从solr中取最大的时间比较，只读取以前没有读过的数据
        String max_time_sql = "select audit_time from accessLog order by audit_time desc";
        ResultSet rs = binlogService.dataInSolr(max_time_sql);
        String solrDate = "2000-01-01 00:00:00";
        if (rs.next()){
            solrDate = rs.getString("audit_time");
        }
        LocalDateTime solrTime = LocalDateUtil.strToLocalDate(solrDate);

        while ((buf = reader.readLine()) != null) {
            String[] arr = buf.split("\t");
            //如果长度不大于7，认为这条数据不符合规则，不需要了。
            if (arr.length >= 7)  {
                LocalDateTime fileTime = LocalDateUtil.strToLocalDate(arr[0]);
                long duration = Duration.between(solrTime, fileTime).getSeconds();

                //当读到的文件的时间比solr中的时间还小，说明这条数据已经被读过了。
                if (duration < 0) {
                    break;
                }

                    if (duration == 0) {
                    //当读的文件内容的时间和solr中的最大时间一样大。
                    //拿当前这一行的所有的值去当条件去solr中去查，如果能查到，说明这条已被存储。否则，说明没有被存储过。
                    String select_sql = "select * from accessLog where audit_user='" + arr[1].trim() + "' "
                            + "and audit_ip ='" + arr[2].trim() + "' and audit_sql='" + arr[6].trim() + "' "
                            + "and audit_db ='" + arr[3].trim() + "' and audit_exec_time='" + arr[4].trim() + "' "
                            + "and audit_time ='" + arr[0].trim() + "'";
                    ResultSet set = binlogService.dataInSolr(select_sql);
                    if (set.next()) {
                        continue;
                    }
                }


                //过滤不符合条件的sql语句
                String filterSql = PropertiesUtil.getInstance().getProperties(FILTER_SQL);
                if (StringUtils.isNotBlank(filterSql)) {
                    boolean isFiltered = false;
                    if (filterSql.indexOf(",") > 0) {
                        String[] begins = filterSql.split(",");
                        for (String begin : begins) {
                            if (arr[6].toLowerCase().trim().startsWith(begin)) {
                                isFiltered = true;
                                break;
                            }
                        }
                    } else {
                        if (arr[6].toLowerCase().trim().startsWith(filterSql)) {
                            isFiltered = true;
                        }
                    }

                    if (isFiltered) {
                        continue;
                    }
                }

                AuditLogBuilder auditLogBuilder = new AuditLogBuilder();
                AuditLog auditLog = auditLogBuilder.builderDateTime(arr[0].trim())
                        .builderUserName(arr[1].trim())
                        .builderHost(arr[2].trim())
                        .builderDataBase(arr[3].trim())
                        .builderConsumingTime(arr[4].trim())
                        .builderResultCount(arr[5].trim())
                        .builderSql(arr[6].trim())
                        .build();

                binlogService.indexData(auditLog);

            }


        }
    }

    public void run() throws Exception {
        PropertiesUtil instance = PropertiesUtil.getInstance();
        execCmd("tac /tmp/mysql.log",instance.getProperties(USERNAME),
                instance.getProperties(PASSWORD),
                instance.getProperties(HOST),
                Integer.valueOf(instance.getProperties(PORT)));
    }

    public static void main(String[] args) throws Exception {
//        ParseMysqlLog p =new ParseMysqlLog();
//        p.execCmd("tac /tmp/mysql.log","root","ccDmm123456","47.106.108.208",22);

        */
/*String solrDate = "";
        String arr = "2019-06-08 10:00:19";
        LocalDateTime solrTime = LocalDateUtil.strToLocalDate(solrDate);
        LocalDateTime fileTime = LocalDateUtil.strToLocalDate(arr);
        Duration duration = Duration.between(solrTime, fileTime);
        System.out.println(duration.getSeconds());*//*


        String s = " use canal_test";
        System.out.println(s.toLowerCase().trim().startsWith("use"));

    }
}
*/
