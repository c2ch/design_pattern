package com.fline.mysqlsniffer;

public class AuditLogBuilder {

    private AuditLog auditLog = new AuditLog();

    public AuditLog build(){
        return auditLog;
    }

    public AuditLogBuilder builderDateTime(String dataTime){
        auditLog.setDateTime(dataTime);
        return this;
    }

    public AuditLogBuilder builderUserName(String userName){
        auditLog.setUserName(userName);
        return this;
    }

    public AuditLogBuilder builderHost(String host){
        auditLog.setHost(host);
        return this;
    }

    public AuditLogBuilder builderDataBase(String dataBase){
        auditLog.setDataBase(dataBase);
        return this;
    }

    public AuditLogBuilder builderConsumingTime(String consumingTime){
        auditLog.setConsumingTime(consumingTime);
        return this;
    }

    public AuditLogBuilder builderResultCount(String resultCount){
        auditLog.setResultCount(resultCount);
        return this;
    }


    public AuditLogBuilder builderSql(String sql){
        auditLog.setSql(sql);
        return this;
    }



}
