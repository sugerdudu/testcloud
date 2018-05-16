package com.sixi.oaservice.userservice.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.sixi.feignservice.user.model.param.RoleInsertDto;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@SpringBootTest(classes = UserServiceApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class RoleControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void roleTreeList() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get("/role/roleTree")).andExpect(status().isOk()).andReturn().getResponse();

        response.setCharacterEncoding("utf-8");
        String contentAsString = response.getContentAsString();

        log.info("树结构内容："+contentAsString);
    }

    @Test
    public void roleTreeListById() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get("/role/roleTree/1")).andExpect(status().isOk()).andReturn().getResponse();

        String contentAsString = response.getContentAsString();
        log.info("树结构内容："+contentAsString);
    }

    @Test
    public void roleTreeListByName() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get("/role/roleTree/根")).andExpect(status().isOk()).andReturn().getResponse();

        String contentAsString = response.getContentAsString();
        log.info("树结构内容："+contentAsString);
    }

    @Test
    public void insertRole() throws Exception {
        RoleInsertDto roleInsertDto = RoleInsertDto.builder().name("总监组").code("role:chief").description("总监组的基础角色组名").build();

        String s = JSONObject.toJSONString(roleInsertDto);

        mockMvc.perform(
                post("/role/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(s)).andExpect(status().isOk());
    }

    @Test
    public void getRoleInfo() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get("/role/info").param("id", "2")).andExpect(status().isOk()).andReturn().getResponse();

        String contentAsString = response.getContentAsString();
        log.info("角色返回信息："+contentAsString);
    }

    @Test
    public void updateRole() throws Exception {
        RoleInsertDto roleInsertDto = RoleInsertDto.builder().id(2).code("role:chief").name("总监组").description("总监组的基础角色组名").build();

        String s = JSONObject.toJSONString(roleInsertDto);

        mockMvc.perform(
                post("/role/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(s)).andExpect(status().isOk());
    }

    @Test
    public void deleteRole() {
    }
}