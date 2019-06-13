package com.fault.collect.center.dao;

import com.fault.collect.center.CenterApplication;
import com.fault.collect.center.entity.FaultLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@SpringBootTest(classes = CenterApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class FaultLogDaoTest {

    @Autowired
    FaultLogDao faultLogDao;

    @Test
    public void getFaultLogById() {
        FaultLog faultLog = faultLogDao.getFaultLogById(3101);
        System.out.println(faultLog.getHost());
        System.out.println(faultLog.getNode());
        System.out.println(faultLog.getService());
    }

}