package com.sixi.oaservice.userservice.controller.user;

import com.alibaba.fastjson.JSONObject;
import com.sixi.feignservice.user.model.param.FunctionInsertDto;
import com.sixi.oaservice.userservice.UserServiceApplication;
import com.sixi.oaservice.userservice.domain.model.oapg.Function;
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

import java.net.URLEncoder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@SpringBootTest(classes = UserServiceApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class FunctionControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void functionTreeList() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get("/function/funcTree")).andReturn().getResponse();
        response.setCharacterEncoding("utf-8");
        String contentAsString = response.getContentAsString();

      log.info("功能块树结构:"+contentAsString);
    }

    @Test
    public void functionTreeListById() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get("/function/funcTree/1")).andReturn().getResponse();

        response.setCharacterEncoding("utf-8");
        String contentAsString = response.getContentAsString();

        log.info("功能块树结构："+contentAsString);
    }

    @Test
    public void functionTreeListByName() throws Exception {
        String name = URLEncoder.encode("主目录", "utf-8");
        MockHttpServletResponse response = mockMvc.perform(get("/function/funcTree/"+name)).andReturn().getResponse();
        response.setCharacterEncoding("utf-8");
        String contentAsString = response.getContentAsString();

        log.info("功能块树结构："+contentAsString);
    }

    @Test
    public void insertFunction() throws Exception {
            Function function = Function.builder().name("人事管理").code("personnel::management").url("/function").method(7).functionType(9).description("人事管理的目录，非菜单").parent(1).sort(10).build();
        String s = JSONObject.toJSONString(function);

        mockMvc.perform(post("/function/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(s))
                .andExpect(status().isOk());
    }

    @Test
    public void getFunctionDetailInfo() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get("/function/info?id="+1)).andExpect(status().isOk()).andReturn().getResponse();

        response.setCharacterEncoding("utf-8");
        String contentAsString = response.getContentAsString();

        log.info("功能块详细信息"+contentAsString);
    }

    @Test
    public void updateFunctionDetailInfo() throws Exception {
        FunctionInsertDto functionInsertDto = FunctionInsertDto.builder().id(1).name("主目录").code("root").url("/").method(7).icon("图标").functionType(9)
                .sort(1).description("主目录").parent(10).build();

        String s = JSONObject.toJSONString(functionInsertDto);

        mockMvc.perform
                (post("/function/update")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(s))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteFunction() throws Exception {
        mockMvc.perform(delete("/function/delete/6")).andExpect(status().isOk());
    }
}