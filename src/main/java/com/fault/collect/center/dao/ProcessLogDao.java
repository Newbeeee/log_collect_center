package com.fault.collect.center.dao;


import com.fault.collect.center.entity.ProcessLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;


public interface ProcessLogDao {
    //插入日志
    @Insert("insert into process_logs(date,content) values(#{date,jdbcType=TIMESTAMP},#{content})")
    @Results(value = {
            @Result(property = "date", column = "date", jdbcType = JdbcType.TIMESTAMP)
    })
    void insertProcessLog(ProcessLog processLog);

    //根据mission_id, 时间查询在某时间之后的数据
    @Select("select * from process_logs where date > #{time} order by date")
    List<ProcessLog> getProcessLogAfter(String time);

    @Select("select * from process_logs where id > #{id} order by id")
    List<ProcessLog> getProcessLogAfterById(int id);
}
