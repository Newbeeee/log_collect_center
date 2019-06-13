package com.fault.collect.center.dao;


import com.fault.collect.center.entity.Match;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MatchDao {

    //取出全部匹配
    @Select("select * from matchs")
    List<Match> getAll();

    //根据id取出匹配
    @Select("select * from matchs where id=#{id}")
    Match getMatchById(int id);

    //表matchs建立了 log_id template_id 和 mission_id 的联合唯一约束
    //插入匹配，避免重复记录 方法1
    //违反联合唯一约束不返回错误，只以警告的形式返回，所以使用ignore请确保语句
    //本身没有问题，否则也会被忽略掉
//    @Insert("insert ignore into matchs(log_id,template_id,mission_id,rate) values(#{log_id},#{template_id},#{mission_id},#{rate})")
//    void insertMatch(Match match);

    //插入匹配，避免重复记录 方法2
    //错误不会被忽略掉，当触发联合唯一约束，就更新一下rate值
    @Insert("insert into matchs(log_id,template_id,mission_id,rate) values(#{log_id},#{template_id},#{mission_id},#{rate}) on duplicate key update rate=#{rate}")
    void insertMatch(Match match);


    //根据log_id取出匹配
    @Select("select * from matchs where log_id=#{log_id}")
    List<Match> getMatchsByLogId(int log_id);

//    //插入匹配，存在log_id,template_id,mission_id相同的则不插入
//    @Insert("insert into matchs(log_id,template_id,mission_id,rate) select #{log_id},#{template_id},#{mission_id},#{rate} from dual where not exists (select * from matchs where log_id=#{log_id} and template_id=#{template_id} and mission_id=#{mission_id})")
//    void insertMatch(Match match);
}
