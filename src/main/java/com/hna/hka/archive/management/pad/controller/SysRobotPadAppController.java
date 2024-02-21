package com.hna.hka.archive.management.pad.controller;

import com.hna.hka.archive.management.assetsSystem.model.BugStatus;
import com.hna.hka.archive.management.system.model.SysRobotPad;
import com.hna.hka.archive.management.system.service.SysRobotPadService;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.ReturnModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author zhang
 * @Date 2023/3/8 9:46
 * pad获取最新版本
 */


/**
 * 获取最新版本包
 */
@RequestMapping("/appSystem/robotPadApp")
@Controller
public class SysRobotPadAppController {

    @Autowired
    SysRobotPadService sysRobotPadService;

//    /**
//     * @Method getBugStatusList
//     * @Author
//     * @Version  1.0
//     * @Description 根据景区获取pad最新版本包
//     * @Return
//     * @Date
//     */
//    @RequestMapping("/getPadAppNewNumber")
//    @ResponseBody
//    public ReturnModel getPadAppNewNumber(String spotId) {
//
//        ReturnModel returnModel = new ReturnModel();
//
//
//
//        return returnModel;
//
//    }






}
