package com.fault.collect.center.sender;

import com.fault.collect.center.CenterApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@SpringBootTest(classes = CenterApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class StartCollectSenderTest {

    @Autowired
    StartCollectSender startCollectSender;

    @Test
    public void startCollect() {
        startCollectSender.startCollect("WARNING");
    }
}