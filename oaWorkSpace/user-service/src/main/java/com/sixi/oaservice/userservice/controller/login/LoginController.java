package com.sixi.oaservice.userservice.controller.login;

import com.sixi.oaservice.userservice.domain.dto.EmployeeResultDto;
import com.sixi.oaservice.userservice.service.ManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 系统用户控制层
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/sys")
public class LoginController {

    @Autowired
    private ManageService manageService;

    @RequestMapping("/login")
    public EmployeeResultDto checkOutNameAndPassword(String username, String password){
        return manageService.login(username, password);
    }

}
