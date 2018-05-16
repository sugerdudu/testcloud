package com.sixi.oaservice.userservice.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.sixi.feignservice.user.model.param.RoleFunctionAssignDto;
import com.sixi.feignservice.user.model.param.RoleFunctionUpdateAssign;
import com.sixi.oaservice.userservice.UserServiceApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@SpringBootTest(classes = UserServiceApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class RoleFunctionRelationControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void getRoleFunctionList() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get("/roleFunc/info").param("roleId", "2"))
                .andExpect(status().isOk()).andReturn().getResponse();

        response.setCharacterEncoding("utf-8");
        String contentAsString = response.getContentAsString();

        log.info("当前角色对应的功能树："+contentAsString);
    }

    @Test
    public void applyFunction() throws Exception {
        List<Integer> funcIds = new ArrayList<>();
        //funcIds.add(3);
        funcIds.add(7);

        RoleFunctionAssignDto roleFunctionAssignDto = RoleFunctionAssignDto.builder().roleId(2).functionIds(funcIds).build();
        String s = JSONObject.toJSONString(roleFunctionAssignDto);

        mockMvc.perform(
            post("/roleFunc/apply")
            .contentType(MediaType.APPLICATION_JSON)
            .content(s)).andExpect(status().isOk());
    }

    @Test
    public void updateDifferentFunction() throws Exception {
        List<Integer> ids = new ArrayList<>();
        ids.add(3);

        List<Integer> oldIds = new ArrayList<>();
        oldIds.add(7);

        RoleFunctionUpdateAssign roleFunctionUpdateAssign = RoleFunctionUpdateAssign.builder().oldFunctionIds(oldIds).newFunctionIds(ids).roleId(2).build();

        String s = JSONObject.toJSONString(roleFunctionUpdateAssign);

        mockMvc.perform(
                post("/roleFunc/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(s)).andExpect(status().isOk());
    }
}