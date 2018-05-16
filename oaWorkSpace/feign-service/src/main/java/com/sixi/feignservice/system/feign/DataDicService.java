package com.sixi.feignservice.system.feign;

import com.sixi.feignservice.system.model.param.DataDicIdListDto;
import com.sixi.feignservice.system.model.param.DataDicInsertDto;
import com.sixi.feignservice.system.model.result.DataDicDetailInfoDto;
import com.sixi.feignservice.system.model.result.DataDicTree;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据字典feign
 *
 * @author Administrator
 */
@FeignClient("systemmanageservice")
@RequestMapping("/datadic")
public interface DataDicService {

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    void insertDataDic(@Validated @RequestBody DataDicInsertDto dataDicInsertDto);

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    void deleteDataDic(@PathVariable("id") Integer id);

    @RequestMapping(value = "/dataDicTree",method = RequestMethod.GET)
    List<DataDicTree> dataDicTreeList();

    @RequestMapping(value = "/dataDicTree/{id}",method = RequestMethod.GET)
    List<DataDicTree> listDataDic(@PathVariable("id") Integer id);

    @RequestMapping(value = "/info",method = RequestMethod.GET)
    DataDicDetailInfoDto getDataDicInfo(@RequestParam(value = "id",defaultValue = "0") Integer id);

    @RequestMapping(value = "/infoList",method = RequestMethod.POST)
    List<DataDicDetailInfoDto> getDataDicInfoList(@RequestBody DataDicIdListDto dataDicIdListDto);
}
