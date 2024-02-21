package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.service.CommonService;
import com.hna.hka.archive.management.system.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @program: rc
 * @description: 共用接口
 * @author: zhaoxianglong
 * @create: 2021-10-19 09:51
 **/
@Api(tags = "公用接口")
@RestController
@RequestMapping("/system/common")
public class CommonController {

    @Value("${common_path}")
    private String COMMON_PATH;

    @Autowired
    CommonService service;
    @Autowired
    FileUploadUtil fileUploadUtil;

    @ApiOperation("上传文件")
    @PostMapping("/uploadFile")
    public ReturnModel uploadFile(@RequestParam("file") MultipartFile[] files) {
        try {
            if (files != null && files.length > 0) {
                StringBuilder builder = new StringBuilder();
                for (MultipartFile file : files) {
                    String flieName = System.currentTimeMillis() + file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
                    String path = COMMON_PATH + flieName;// 存放位置
                    File destFile = new File(path);
                    try {
                        //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                        FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
//                        String upload = fileUploadUtil.upload(file, COMMON_PATH + flieName);
//                        System.out.println(upload);
                        builder.append(path).append(",");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }//复制文件到指定目录
                }
                return new ReturnModel(builder.toString().substring(0 , builder.length()-1), "success", Constant.STATE_SUCCESS, null);
            } else {
                throw new RuntimeException("文件不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ReturnModel(e.getMessage(), "error", "500", null);
        }
    }

    @ApiOperation("获取单号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type" , value = "订单类型:1.入库单;2.出库单;3.备件申请")
    })
    @GetMapping("getOrderNumber")
    public String getOrderNumber(Integer type){
        return service.getOrderNumber(type);
    }
}
