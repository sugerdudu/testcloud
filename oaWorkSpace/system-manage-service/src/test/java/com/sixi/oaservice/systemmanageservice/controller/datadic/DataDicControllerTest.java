package com.sixi.oaservice.systemmanageservice.controller.datadic;

import com.alibaba.fastjson.JSONObject;
import com.sixi.feignservice.system.model.param.DataDicInsertDto;
import com.sixi.oaservice.systemmanageservice.SystemManageServiceApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

@SpringBootTest(classes = SystemManageServiceApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class DataDicControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void insertDataDic() throws Exception {
        DataDicInsertDto dataDicInsertDto = DataDicInsertDto.builder().name("拜访客户").code("personnel:system::attendance::application_form::business_trip::reason::visiting_clients").engName("BusinessTrip_Reason_VisitingClients").sort(10).parent(18).build();
        String s = JSONObject.toJSONString(dataDicInsertDto);

        MvcResult mvcResult = mockMvc.perform(post("/datadic/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(s))
                .andReturn();

        MockHttpServletResponse response = mvcResult.getResponse();
        response.setCharacterEncoding("utf-8");
        String contentAsString = response.getContentAsString();
        log.info("返回内容："+contentAsString+"~~~~~状态码："+response.getStatus());
    }

    @Test
    public void deleteDataDic() {
    }

    @Test
    public void dataDicTreeList() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(get("/datadic/dataDicTree")).andReturn().getResponse();
        response.setCharacterEncoding("utf-8");
        String contentAsString = response.getContentAsString();

        log.info("返回值："+contentAsString);
    }

    @Test
    public void listDataDic() {
    }

    @Test
    public void getDataDicInfo() {
    }

    @Test
    public void getDataDicInfoList() {
    }
}