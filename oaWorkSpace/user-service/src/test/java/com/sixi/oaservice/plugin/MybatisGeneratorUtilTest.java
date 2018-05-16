package com.sixi.oaservice.plugin;

import com.sixi.oaservice.config.db.DBConfigInfo;
import com.sixi.oaservice.config.db.DBInfo;
import com.sixi.oaservice.userservice.UserServiceApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = UserServiceApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class MybatisGeneratorUtilTest {

    @Autowired
    private DBConfigInfo dbConfigInfo;

    @Test
    public void test() throws Exception {
        DBInfo dbInfo = dbConfigInfo.getOapg();

        MybatisGeneratorUtil.generator(dbInfo.getDriver(),dbInfo.getUrl(),dbInfo.getUsername(),dbInfo.getPassword(),"sync_manage","user-service","com.sixi.oaservice.userservice","oapg","oaPg","","user");
    }

}