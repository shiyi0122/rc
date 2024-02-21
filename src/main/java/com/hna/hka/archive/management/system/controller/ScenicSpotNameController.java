package com.hna.hka.archive.management.system.controller;

import com.hna.hka.archive.management.system.model.SysScenicSpotName;
import com.hna.hka.archive.management.system.service.SysScenicSpotNameService;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.controller
 * @ClassName: ScenicSpotNameController
 * @Author: 郭凯
 * @Description: 景区名称管理控制层
 * @Date: 2020/12/3 13:57
 * @Version: 1.0
 */
@RequestMapping("/system/scenicSpotName")
@Controller
public class ScenicSpotNameController extends PublicUtil {

    @Autowired
    private SysScenicSpotNameService sysScenicSpotNameService;

    /**
     * @Author 郭凯
     * @Description 景区名称列表查询
     * @Date 14:01 2020/12/3
     * @Param [pageNum, pageSize, sysScenicSpotName]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getScenicSpotNameList")
    @ResponseBody
    public PageDataResult getScenicSpotNameList(@RequestParam("pageNum") Integer pageNum,
                                            @RequestParam("pageSize") Integer pageSize, SysScenicSpotName sysScenicSpotName) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,Object> search = new HashMap<>();
        search.put("scenicSpotName",sysScenicSpotName.getScenicSpotName());
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            pageDataResult = sysScenicSpotNameService.getScenicSpotNameList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("景区名称列表查询失败",e);
        }
        return pageDataResult;
    }

}
