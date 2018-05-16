package com.sixi.oaservice.userservice.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.sixi.feignservice.user.model.param.ManageRoleUpdateAssign;
import com.sixi.oaservice.userservice.UserServiceApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@SpringBootTest(classes = UserServiceApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class ManageRoleRelationControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void updateDifferentRole() throws Exception {
        List<Integer> roleIds = Collections.singletonList(1);

        ManageRoleUpdateAssign manageRoleUpdateAssign = ManageRoleUpdateAssign.builder().manageId(230).newRoleIds(roleIds).build();

        String s = JSONObject.toJSONString(manageRoleUpdateAssign);

        mockMvc.perform(
                post("/manageRole/assignRole")
                .contentType(MediaType.APPLICATION_JSON)
                .content(s)).andExpect(status().isOk());

    }
}