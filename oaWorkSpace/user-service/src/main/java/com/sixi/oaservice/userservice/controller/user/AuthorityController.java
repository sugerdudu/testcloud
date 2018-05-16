package com.sixi.oaservice.userservice.controller.user;

import com.sixi.feignservice.system.feign.DataDicService;
import com.sixi.feignservice.system.model.param.DataDicIdListDto;
import com.sixi.feignservice.system.model.result.DataDicDetailInfoDto;
import com.sixi.feignservice.user.model.param.AuthorityInsertDto;
import com.sixi.feignservice.user.model.result.AuthorityInfoDto;
import com.sixi.oaplatform.common.utils.BeanUtils;
import com.sixi.oaplatform.common.utils.Fn;
import com.sixi.oaservice.userservice.domain.model.oapg.Authority;
import com.sixi.oaservice.valid.group.GroupFirst;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import com.sixi.oaservice.userservice.service.user.AuthorityService;
import lombok.extern.slf4j.Slf4j;

import javax.validation.groups.Default;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Authority控制层
 * @author Administrator create on 2018/4/17
 *
 */
@RestController
@RequestMapping("/authority")
@Api(value = "按钮权限Authority控制器" , description = "按钮权限Authority管理")
@Slf4j
public class AuthorityController{

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private DataDicService dataDicService;

    @ApiOperation(value = "由功能id得到权限集合",httpMethod = "get")
    @RequestMapping(value = "/funcList",method = RequestMethod.GET)
    public List<AuthorityInfoDto> getAuthorityByFunId(Integer funcId){
        List<Authority> authorities = authorityService.selectListByFuncId(funcId);
        List<AuthorityInfoDto> authorityInfoDtos = BeanUtils.mapList(authorities, AuthorityInfoDto.class);

        Set<Integer> authTypeIds = authorities.stream().map(Authority::getAuthType).collect(Collectors.toSet());
        Set<Integer> methodIds = authorities.stream().map(Authority::getMethod).collect(Collectors.toSet());

        //查询数据字典信息
        List<Integer> dataDicIds = new ArrayList<>();
        dataDicIds.addAll(authTypeIds);
        dataDicIds.addAll(methodIds);
        DataDicIdListDto dataDicIdListDto = DataDicIdListDto.builder().ids(dataDicIds).build();
        List<DataDicDetailInfoDto> dataDicInfoList = dataDicService.getDataDicInfoList(dataDicIdListDto);

        //数据字典中午赋值
        for (AuthorityInfoDto authorityInfoDto : authorityInfoDtos) {
            //设置权限类型
            Optional<DataDicDetailInfoDto> optional = dataDicInfoList.stream().filter(data -> authorityInfoDto.getAuthType().equals(data.getId())).findFirst();

            optional.ifPresent(dataDicDetailInfoDto -> {
                authorityInfoDto.setAuthTypeName(dataDicDetailInfoDto.getName());
                dataDicInfoList.remove(dataDicDetailInfoDto);
            });

            //设置请求类型
            Optional<DataDicDetailInfoDto> optional1 = dataDicInfoList.stream().filter(data -> authorityInfoDto.getMethod().equals(data.getId())).findFirst();

            optional1.ifPresent(dataDicDetailInfoDto -> {
                authorityInfoDto.setMethodName(dataDicDetailInfoDto.getName());
                dataDicInfoList.remove(dataDicDetailInfoDto);
            });
        }

        return authorityInfoDtos;
    }

    @ApiOperation(value = "新增一条权限",httpMethod = "post")
    @RequestMapping(value = "/add",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public void insertAuthority(@Validated @RequestBody AuthorityInsertDto authorityInsertDto){
        Authority authority = authorityService.selectByCodeOrUrl(authorityInsertDto.getCode(), authorityInsertDto.getUrl());

        //抛出异常 已经存在一条权限
        if (authority != null){

        }

        authorityService.insertAuthority(authorityInsertDto,0);
    }

    @ApiOperation(value = "由id得到权限信息",httpMethod = "get")
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public AuthorityInfoDto getAuthorityInfo(Integer id){
        id = Fn.toInt(id);

        Authority authority = authorityService.selectByPrimary(id);
        AuthorityInfoDto authorityInfoDto = BeanUtils.mapObject(authority, AuthorityInfoDto.class);

        DataDicDetailInfoDto authType = dataDicService.getDataDicInfo(authority.getAuthType());
        DataDicDetailInfoDto method = dataDicService.getDataDicInfo(authority.getMethod());

        authorityInfoDto.setMethodName(authType.getName());
        authorityInfoDto.setAuthTypeName(method.getName());

        return authorityInfoDto;
    }

    @ApiOperation(value = "更新权限信息",httpMethod = "post")
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public void updateAuthority(@Validated(value = {Default.class,GroupFirst.class}) @RequestBody AuthorityInsertDto authorityInsertDto){
        authorityService.updateAuthority(authorityInsertDto,0);
    }

    @ApiOperation(value = "删除一条权限信息",httpMethod = "get")
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public void deleteAuthority(Integer id){
        authorityService.deleteAuthority(id,0);
    }

}