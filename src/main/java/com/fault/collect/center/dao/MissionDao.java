package com.fault.collect.center.dao;

import com.fault.collect.center.entity.Mission;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface MissionDao {
    //取出全部任务
    @Select("select * from missions")
    @Results({
            @Result(property = "missionId", column = "mission_id")
    })
    List<Mission> getAll();

    //根据id取出任务
    @Select("select * from missions where id=#{id}")
    @Results({
            @Result(property = "missionId", column = "mission_id")
    })
    Mission getMissionById(int id);

    //插入任务
    @Insert("insert into missions(status,mission_id) values(#{status},#{missionId})")
    void insertMission(Mission mission);

    //更新任务
    @Update("update missions set status=#{status},mission_id=#{missionId} where id=#{id}")
    void updateMission(Mission mission);

    //删除任务
    @Delete("delete from missions where id=#{id}")
    void deleteMission(int id);

    //根据状态取出正在运行的任务
    @Select("select * from missions where status='running'")
    @Results({
            @Result(property = "missionId", column = "mission_id")
    })
    Mission getRunningMission();

    //设置任务运行状态
    @Update("update missions set status=#{status} where id=#{id}")
    void setMissionStatus(Mission mission);
}
