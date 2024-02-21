package com.hna.hka.archive.management.assetsSystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.assetsSystem.model.OperateState;
import com.hna.hka.archive.management.assetsSystem.service.SysOperateStateService;
import com.hna.hka.archive.management.system.dao.SysRobotMapper;
import com.hna.hka.archive.management.system.util.FileUtil;
import com.hna.hka.archive.management.system.util.PageDataResult;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: rc
 * @Package: com.hna.hka.archive.management.assetsSystem.service.impl
 * @ClassName: SysOperateStateServiceImpl
 * @Author: 郭凯
 * @Description: 运营状态业务层（实现）
 * @Date: 2021/7/11 22:53
 * @Version: 1.0
 */
@Service
@Transactional
public class SysOperateStateServiceImpl implements SysOperateStateService {

    @Autowired
    private SysRobotMapper sysRobotMapper;


    /**
     * @Method getOperateStateList
     * @Author 郭凯
     * @Version  1.0
     * @Description 运营状态根据机器人查询
     * @Return com.hna.hka.archive.management.system.util.PageDataResult
     * @Date 2021/7/11 22:54
     */
    @Override
    public PageDataResult getOperateStateList(Integer currPage, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(currPage, pageSize);
        List<OperateState> operateStateList = sysRobotMapper.getOperateStateList(search);
        for (OperateState operateState : operateStateList){
            operateState.setDate(search.get("startTime") + "，" + search.get("endTime"));
        }
        if (operateStateList.size() > 0){
            PageInfo<OperateState> pageInfo = new PageInfo<>(operateStateList);
            pageDataResult.setData(pageInfo.getList());
            pageDataResult.setCount((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    @Override
    public PageInfo<HashMap> getOperateStateSpotList(Long spotId, String beginDate, String endDate, Integer pageNum, Integer pageSize, String field) {
        PageHelper.startPage(pageNum , pageSize);
        List<HashMap> list = sysRobotMapper.getOperateStateSpotList(spotId , beginDate , endDate , field);
        PageInfo<HashMap> pageInfo = new PageInfo<>(list);
        pageInfo.getList().add(sysRobotMapper.getOperateStateSpotSum(spotId , beginDate , endDate));
        return pageInfo;
    }

    @Override
    public List getOperateStateSpotList(Long spotId, String beginDate, String endDate) {
        return sysRobotMapper.getOperateStateSpotList(spotId , beginDate , endDate , null);
    }

    @Override
    public PageInfo<HashMap> getOperateStateRobotList(Long spotId, String beginDate, String endDate, Integer page, Integer limit, Integer type) {
        PageHelper.startPage(page , limit);
        List<HashMap> list = sysRobotMapper.getOperateStateRobotList(spotId , beginDate , endDate , type);
        PageInfo<HashMap> pageInfo = new PageInfo<>(list);
        pageInfo.getList().add(sysRobotMapper.getOperateStateRobotSum(spotId , beginDate , endDate));
        return pageInfo;
    }

    @Override
    public List getDetailBySpot(Long spotId, Integer type, String beginDate, String endDate) {
        List<HashMap> list = sysRobotMapper.getOperateStateRobotList(spotId , beginDate , endDate , type);
        return list;
    }

    @Override
    public void exportExcel(HttpServletResponse response, Long spotId, Integer type, String beginDate, String endDate) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("机器人运营状态");
        XSSFDataFormat dataFormat = workbook.createDataFormat();
        XSSFCellStyle center = workbook.createCellStyle();
        center.setAlignment(HorizontalAlignment.CENTER);
        center.setBorderLeft(BorderStyle.THIN);
        center.setBorderBottom(BorderStyle.THIN);
        center.setBorderTop(BorderStyle.THIN);
        center.setBorderRight(BorderStyle.THIN);
        XSSFCellStyle centerInteger = workbook.createCellStyle();
        centerInteger.setAlignment(HorizontalAlignment.CENTER);
        centerInteger.setBorderLeft(BorderStyle.THIN);
        centerInteger.setBorderBottom(BorderStyle.THIN);
        centerInteger.setBorderTop(BorderStyle.THIN);
        centerInteger.setBorderRight(BorderStyle.THIN);
        centerInteger.setDataFormat(dataFormat.getFormat("#,##0"));
        if (type == 1){
            List<HashMap> list = sysRobotMapper.getOperateStateSpotList(spotId , beginDate , endDate, "yysbl desc");
            list.add(sysRobotMapper.getOperateStateSpotSum(spotId , beginDate , endDate));
            String[] strings = {"景区名称" , "有营收/台" , "有营收比例" ,"无营收/台" ,"无营收比例" ,"故障/台" ,"故障比例"};
            XSSFRow row = sheet.createRow(0);
            for (int i = 0; i < 7; i++) {
                row.createCell(i).setCellValue(strings[i]);
                row.getCell(i).setCellStyle(center);
            }
            for (int i = 0; i < list.size(); i++) {
                XSSFRow cells = sheet.createRow(i + 1);
                cells.createCell(0).setCellValue(list.get(i).get("spotName").toString());
                cells.createCell(1).setCellValue(list.get(i).get("yys").toString());
                cells.createCell(2).setCellValue(list.get(i).get("yysbl").toString() + "%");
                cells.createCell(3).setCellValue(list.get(i).get("wys").toString());
                cells.createCell(4).setCellValue(list.get(i).get("wysbl").toString() + "%");
                cells.createCell(5).setCellValue(list.get(i).get("gz").toString());
                cells.createCell(6).setCellValue(list.get(i).get("gzbl").toString() + "%");

                for (int j = 0; j < 7; j++) {
                    cells.getCell(j).setCellStyle(center);
                }
            }

            FileUtil.exportExcel(workbook, "机器人运营状态-按景区统计-" + System.currentTimeMillis(), response);
        } else if (type == 2){
            List<HashMap> list = sysRobotMapper.getOperateStateRobotList(spotId , beginDate , endDate , null);
            list.add(sysRobotMapper.getOperateStateRobotSum(spotId , beginDate , endDate));
            String[] strings = {"景区名称" , "机器人编码" , "交易金额" ,"运营时长"};
            XSSFRow row = sheet.createRow(0);
            for (int i = 0; i < 4; i++) {
                row.createCell(i).setCellValue(strings[i]);
                row.getCell(i).setCellStyle(center);
            }
            for (int i = 0; i < list.size(); i++) {
                XSSFRow cells = sheet.createRow(i + 1);
                cells.createCell(0).setCellValue(list.get(i).get("SCENIC_SPOT_NAME").toString());
                cells.createCell(1).setCellValue(list.get(i).get("ROBOT_CODE").toString());
                cells.createCell(2).setCellValue(list.get(i).get("ORDER_AMOUNT").toString());
                cells.createCell(3).setCellValue(list.get(i).get("TOTAL_TIME").toString());

                for (int j = 0; j < 4; j++) {
                    cells.getCell(j).setCellStyle(center);
                }
            }

            FileUtil.exportExcel(workbook, "机器人运营状态-按机器人统计-" + System.currentTimeMillis(), response);
        }
    }

}
