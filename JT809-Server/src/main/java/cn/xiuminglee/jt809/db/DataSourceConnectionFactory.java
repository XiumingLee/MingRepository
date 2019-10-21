package cn.xiuminglee.jt809.db;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/9/26 17:47
 * @Version 1.0
 * @Describe: 数据库连接工厂
 */
public class DataSourceConnectionFactory {
    private static Logger log = LoggerFactory.getLogger(DataSourceConnectionFactory.class);
    private static Connection dbConnection = null;
    static {
        Properties properties = new Properties();
        DataSource dataSource = null;
        try {
            properties.load(new InputStreamReader(DataSourceConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties"),"UTF-8"));
            dataSource = DruidDataSourceFactory.createDataSource(properties);
            dbConnection = dataSource.getConnection();
        } catch (Exception e) {
            log.error("数据源初始化失败！错误信息：{}",e.getMessage());
        }
    }

    public static Connection getDbConnection(){
        return dbConnection;
    }

    public static void closeResource1(Connection conn, Statement ps, ResultSet rs){
        DbUtils.closeQuietly(conn);
        DbUtils.closeQuietly(ps);
        DbUtils.closeQuietly(rs);
    }

}
