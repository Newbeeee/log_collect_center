package com.fault.collect.center.dao;



import com.fault.collect.center.entity.Template;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface TemplateDao {

    //取出全部故障模板
    @Select("select * from templates")
    @Results({
            @Result(property = "logPath", column = "log_path"),
            @Result(property = "impactFactor", column = "impact_factor")
    })
    List<Template> getAll();

    //根据id取出故障模板
    @Select("select * from templates where id = #{id}")
    @Results({
            @Result(property = "logPath", column = "log_path"),
            @Result(property = "impactFactor", column = "impact_factor")
    })
    Template getTemplateById(int id);

    //插入故障模板
    @Insert("insert into templates(node,service,log_path,content,fault,reason,suggest,impact_factor,accepts) values(#{node},#{service},#{logPath},#{content},#{fault},#{reason},#{suggest},#{impactFactor},#{accepts})")
    void insertTemplate(Template template);

    //更新故障模板
    @Update("update templates set node=#{node},service=#{service},log_path=#{logPath},content=#{content},fault=#{fault},reason=#{reason},suggest=#{suggest},impact_factor=#{impactFactor},accepts=#{accepts} where id=#{id}")
    void updateTemplate(Template template);

    //删除故障模板
    @Delete("delete from templates where id=#{id}")
    void deleteTemplate(int id);

    //根据service查询故障模板
    @Select("select * from templates where service=#{service}")
    @Results({
            @Result(property = "logPath", column = "log_path"),
            @Result(property = "impactFactor", column = "impact_factor")
    })
    List<Template> getTemplatesByService(String service);

    //根据故障模板id标记故障模板有效
    @Update("update templates set accepts=accepts+1 where id=#{id}")
    void setTemplateValidById(int id);
}
