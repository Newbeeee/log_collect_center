package com.fault.collect.center.dao;


import com.fault.collect.center.entity.FaultLog;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface FaultLogDao {

    //取出全部故障日志
    @Select("select * from fault_logs")
    @Results({
            @Result(property = "logPath", column = "log_path"),
            @Result(property = "storagePath", column = "storage_path"),
            @Result(property = "missionId", column = "mission_id")
    })
    List<FaultLog> getAll();

    //取出带RedisId的log
    @Select("select * from fault_logs")
    @Results({
            @Result(property = "logPath", column = "log_path"),
            @Result(property = "storagePath", column = "storage_path"),
            @Result(property = "missionId", column = "mission_id"),
            @Result(property = "redisId",column = "redis_id")
    })
    List<FaultLog> getAllWithRedis();

    //根据id取出故障日志
    @Select("select * from fault_logs where id = #{id}")
    @Results({
            @Result(property = "logPath", column = "log_path"),
            @Result(property = "storagePath", column = "storage_path"),
            @Result(property = "missionId", column = "mission_id")
    })
    FaultLog getFaultLogById(int id);

    //插入故障日志
    @Insert("insert into fault_logs(node,service,log_path,storage_path,mission_id,host,not_empty) values(#{node},#{service},#{logPath},#{storagePath},#{missionId},#{host},#{notEmpty})")
    void insertFaultLog(FaultLog faultLog);

    //更新故障日志
    @Update("update fault_logs set node=#{node},service=#{service},log_path=#{logPath},storage_path=#{storagePath},mission_id=#{missionId},host=#{host} where id=#{id}")
    void updateFaultLog(FaultLog faultLog);

    //删除故障日志
    @Delete("delete from fault_logs where id=#{id}")
    void deleteFaultLog(int id);

    //根据任务id取出日志
    @Select("select * from fault_logs where mission_id=#{missionId}")
    @Results({
            @Result(property = "logPath", column = "log_path"),
            @Result(property = "storagePath", column = "storage_path"),
            @Result(property = "missionId", column = "mission_id")
    })
    List<FaultLog> getFaultLogsByMissionId(String missionId);

    @Update("update fault_logs set redis_id=#{redisId} where id=#{id}")
    void updateFaultLogWithRedisId(FaultLog faultLog);

    @Select("select * from fault_logs where mission_id=#{missionId} and not_empty=1")
    @Results({
            @Result(property = "logPath", column = "log_path"),
            @Result(property = "storagePath", column = "storage_path"),
            @Result(property = "missionId", column = "mission_id"),
            @Result(property = "redisId",column = "redis_id")
    })
    List<FaultLog> getNotNullFaultLogs(@Param("missionId") String missionId);

    @Update("update fault_logs set not_empty=1 where id=#{id}")
    void updateFaultLogNotEmpty(FaultLog faultLog);
}
