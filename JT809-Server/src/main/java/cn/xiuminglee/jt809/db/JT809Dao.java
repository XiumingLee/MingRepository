package cn.xiuminglee.jt809.db;

import cn.xiuminglee.jt809.packet.JT809Packet0x1202;
import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @Author: Xiuming Lee
 * @Date: 2019/9/26 18:48
 * @Version 1.0
 * @Describe:
 */
public class JT809Dao {
    private static Logger log = LoggerFactory.getLogger(JT809Dao.class);

    public static int insert0x1202(JT809Packet0x1202 packet) {
        QueryRunner runner = new QueryRunner();
        Connection conn = DataSourceConnectionFactory.getDbConnection();
        String sql = "insert into test.bus_gps(id,vehicleno,lon,lat,sendtime,cjsj,vec1,vec2,vec3,direction,altitude,alarm)values(?,?,?,?,?,?,?,?,?,?,?,?)";
        int insert = 0;
        try {
            insert = runner.update(conn, sql, UUID.randomUUID().toString().replace("-",""), packet.getVehicleNo().replaceAll("\u0000", ""), String.format("%.6f",packet.getLon() * 1e-6), String.format("%.6f",packet.getLat() * 1e-6),LocalDateTime.of(packet.getDate(), packet.getTime()),LocalDateTime.now(),packet.getVec1(),packet.getVec2(),packet.getVec3(),packet.getDirection(),packet.getAltitude(),packet.getAlarm());
        } catch (SQLException e) {
            log.info("数据存储错误：{}",e.getMessage());
        }
        return insert;
    }

    public static int delYesterdayData()  {
        QueryRunner runner = new QueryRunner();
        Connection conn = DataSourceConnectionFactory.getDbConnection();
        String sql = "DELETE FROM test.bus_gps WHERE sendtime < CURRENT_DATE";
        int del = 0;
        try {
            del = runner.update(conn, sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return del;
    }
}
