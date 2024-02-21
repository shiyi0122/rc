package com.hna.hka.archive.management.assetsSystem.controller;

import com.hna.hka.archive.management.assetsSystem.service.PeopleCountingService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.ReturnModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @Author zhang
 * @Date 2023/2/11 9:59
 * 人流统计页
 */
@Api(tags = "人流统计页")
@RequestMapping("/system/peopleCounting")
@RestController
public class PeopleCountingController {

    @Autowired
    PeopleCountingService peopleCountingService;

    @GetMapping("/getPeopleCountingList")
    @ApiOperation("列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "spotId", value = "景区ID"),
            @ApiImplicitParam(name = "beginDate", value = "起始时间"),
            @ApiImplicitParam(name = "endDate", value = "截止时间"),
    })
    public ReturnModel getPeopleCountingList( Long spotId, String beginDate , String endDate ) {

        ReturnModel returnModel = new ReturnModel();

        Map<String,Object> map =  peopleCountingService.getPeopleCountingList(spotId,beginDate,endDate);

        returnModel.setData(map);
        returnModel.setState(Constant.STATE_SUCCESS);
        returnModel.setMsg("查询成功");

        return returnModel;
    }


}
