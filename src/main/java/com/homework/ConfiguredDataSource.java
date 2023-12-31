package com.homework;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public final class ConfiguredDataSource {
    private static HikariDataSource hikariDataSource;
    private ConfiguredDataSource(){}
    public static HikariDataSource getPool(){
        if(hikariDataSource==null){
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl(DbConfig.getDbUrl());
            hikariConfig.setUsername(DbConfig.getDbUser());
            hikariConfig.setPassword(DbConfig.getDbPassword());
            hikariDataSource = new HikariDataSource(hikariConfig);
        }
        return hikariDataSource;
    }
    public static void shutdown(){
        if(hikariDataSource!=null){
            hikariDataSource.close();
        }
    }
}
