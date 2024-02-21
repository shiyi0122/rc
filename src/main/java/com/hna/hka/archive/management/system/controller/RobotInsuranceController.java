package com.hna.hka.archive.management.system.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hna.hka.archive.management.system.model.*;
import com.hna.hka.archive.management.system.util.*;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hna.hka.archive.management.system.service.SysRobotInsuranceService;
import com.hna.hka.archive.management.system.service.SysRobotService;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;

/**
 * 
* @ClassName: RobotInsuranceController
* @Description: 机器人保险管理控制层
* @author 郭凯
* @date 2020年12月23日
* @version V1.0
 */

@RequestMapping("/system/robotInsurance")
@Controller
public class RobotInsuranceController extends PublicUtil{
	
	@Autowired
	private SysRobotInsuranceService sysRobotInsuranceService;
	
	@Autowired
	private SysRobotService sysRobotService;
	
	@Autowired
    private HttpServletRequest request;

    @Value("${filePathPadPaht}")
    private String filePathPadPaht;
	
	
	/**
	* @Author 郭凯
	* @Description: 机器人保险列表查询
	* @Title: getRobotInsuranceList
	* @date  2020年12月23日 上午11:07:19 
	* @param @param pageNum
	* @param @param pageSize
	* @param @param sysRobotInsurance
	* @param @return
	* @return PageDataResult
	* @throws
	 */
	@RequestMapping(value = "/getRobotInsuranceList")
    @ResponseBody
    public PageDataResult getRobotInsuranceList(@RequestParam("pageNum") Integer pageNum,
                                            @RequestParam("pageSize") Integer pageSize, SysRobotInsurance sysRobotInsurance) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("userId",this.getSysUser().getUserId().toString());
            search.put("robotCode",sysRobotInsurance.getRobotCode());
            search.put("scenicSpotId",sysRobotInsurance.getScenicSpotId());
            search.put("insureState", sysRobotInsurance.getInsureState());
            pageDataResult = sysRobotInsuranceService.getRobotInsuranceList(pageNum,pageSize,search);
        } catch (Exception e) {
            logger.error("机器人保险列表查询异常！", e);
        }
        return pageDataResult;
    }
	
	/**
	 * 
	* @Author 郭凯
	* @Description: 编辑机器人保险单
	* @Title: addRobotInsurance
	* @date  2020年12月23日 下午4:31:35 
	* @param @param file
	* @param @param sysRobotInsurance
	* @param @return
	* @return ReturnModel
	* @throws
	 */
	@RequestMapping("/editRobotInsurance")
	@ResponseBody
	public ReturnModel editRobotInsurance(@RequestParam MultipartFile file, SysRobotInsurance sysRobotInsurance) {
        ReturnModel dataModel = new ReturnModel();
        try {
        	if (ToolUtil.isEmpty(sysRobotInsurance.getInsuranceId())) {
				if (!file.isEmpty()) {
					//添加机器人保险单
					int i = sysRobotInsuranceService.addRobotInsurance(file,sysRobotInsurance);
					if (i == 1) {
						dataModel.setData("");
			            dataModel.setMsg("保险新增成功！");
			            dataModel.setState(Constant.STATE_SUCCESS);
			            return dataModel;
					}else if (i == 2) {
						dataModel.setData("");
			            dataModel.setMsg("文件格式不正确！（只支持PDF文件）");
			            dataModel.setState(Constant.STATE_FAILURE);
			            return dataModel;
					}else {
						dataModel.setData("");
			            dataModel.setMsg("保险新增失败！");
			            dataModel.setState(Constant.STATE_FAILURE);
			            return dataModel;
					}
				}else {
					dataModel.setData("");
		            dataModel.setMsg("请上传PDF文档！");
		            dataModel.setState(Constant.STATE_FAILURE);
		            return dataModel;
				}
			}else {
				//修改机器人保险单
				int i = sysRobotInsuranceService.updateRobotInsurance(file,sysRobotInsurance);
				if (i == 1) {
					dataModel.setData("");
		            dataModel.setMsg("保险修改成功！");
		            dataModel.setState(Constant.STATE_SUCCESS);
		            return dataModel;
				}else if (i == 2) {
					dataModel.setData("");
		            dataModel.setMsg("文件格式不正确！（只支持PDF文件）");
		            dataModel.setState(Constant.STATE_FAILURE);
		            return dataModel;
				}else {
					dataModel.setData("");
		            dataModel.setMsg("保险修改失败！");
		            dataModel.setState(Constant.STATE_FAILURE);
		            return dataModel;
				}
			}
        } catch (Exception e) {
            dataModel.setData("");
            dataModel.setMsg("机器人保险单编辑失败！（请联系管理员）");
            dataModel.setState(Constant.STATE_FAILURE);
            logger.error("addRobotInsurance", e);
            return dataModel;
        }
    }
	
	/**
	* @Author 郭凯
	* @Description: 下载PDF
	* @Title: download
	* @date  2020年12月24日 上午10:20:55 
	* @param @param response
	* @param @throws IOException
	* @return void
	* @throws
	 */
	@RequestMapping("/download")
    public void download(HttpServletResponse response) throws IOException {
        String fileName = request.getParameter("fileName").toString();
        //获得第一个点的位置
        int index=fileName.indexOf("/");
        //根据第一个点的位置 获得第二个点的位置
        index=fileName.indexOf("/", index+1);
        //根据第二个点的位置，截取 字符串。得到结果 result
        String result=fileName.substring(index+1);
        File file = new File(filePathPadPaht  + result);
        if(file.exists()){
            response.setContentType("application/octet-stream");
            response.setHeader("content-type", "application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName,"utf8"));
            byte[] buffer = new byte[1024];
            //输出流
            OutputStream os = null;
            try(FileInputStream fis= new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fis);) {
                os = response.getOutputStream();
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            response.setContentType("text/html; charset=UTF-8"); //转码
            PrintWriter out = response.getWriter();
            out.flush();
            out.println("<script defer='defer' type='text/javascript'>");
            out.println("alert('文件不存在或已经被删除！');");
            out.println("window.location.href='/page/system/robotInsurance/html/robotInsuranceList.html';");
            out.println("</script>");
        }
    }
	
	/**
	* @Author 郭凯
	* @Description: 导入机器人保险Excel表
	* @Title: upload
	* @date  2020年12月24日 下午2:13:13 
	* @param @param multipartFile
	* @param @return
	* @param @throws Exception
	* @return ReturnModel
	* @throws
	 */
	@RequestMapping("/upload")
    @ResponseBody
    public ReturnModel upload(@RequestParam("file") MultipartFile multipartFile) throws Exception {
        ReturnModel returnModel = new ReturnModel();
        try {
            ImportParams params = new ImportParams();
            params.setTitleRows(1);
            params.setHeadRows(1);
            List<SysRobotInsurance> result = ExcelImportUtil.importExcel(multipartFile.getInputStream(),SysRobotInsurance.class, params);
            String robotCode = "";
            for (SysRobotInsurance sysRobotInsurance:result){
            	if (ToolUtil.isNotEmpty(sysRobotInsurance.getInsureStartTime())) {
            		if (!DateUtil.isValidDate(sysRobotInsurance.getInsureEndTime())) {
                		sysRobotInsurance.setInsureEndTime(DateUtil.checkDate(sysRobotInsurance.getInsureEndTime()));
    				}
				}
            	SysRobot robot = sysRobotService.getRobotCodeBy(sysRobotInsurance.getRobotCode());
            	if (ToolUtil.isNotEmpty(robot)) {
            		SysRobotInsurance robotInsurance = sysRobotInsuranceService.getRobotInsuranceById(robot.getRobotId());
                    if (ToolUtil.isEmpty(robotInsurance)){
                    	sysRobotInsurance.setRobotId(robot.getRobotId());
                    	sysRobotInsuranceService.insertRobotInsurance(sysRobotInsurance);
                    }else {
                    	sysRobotInsurance.setInsuranceId(robotInsurance.getInsuranceId());
                    	sysRobotInsuranceService.updateRobotInsurance(sysRobotInsurance);
                    }
				}else {
					robotCode += sysRobotInsurance.getRobotCode() + "，";
				}
            }
            if (robotCode.length() > 0) {
            	//sendDocNum.length() - 1 的原因：从零开始的，含头不含尾。
            	robotCode = robotCode.substring(0, robotCode.length() - 1);
            }else {
            	robotCode = "无";
            }
            returnModel.setData("");
            returnModel.setMsg("导入成功"+robotCode+"编号错误");
            returnModel.setState(Constant.STATE_SUCCESS);
            return returnModel;
        }catch (Exception e){
        	logger.info("upload", e);
            returnModel.setData("");
            returnModel.setMsg("导入失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }
	
	/**
	* @Author 郭凯
	* @Description: 导入PDF
	* @Title: uploadPdf
	* @date  2021年1月4日 下午3:35:58 
	* @param @param file
	* @param @param scenicSpotId
	* @param @return
	* @return ReturnModel
	* @throws
	 */
	@RequestMapping("/uploadPdf")
	@ResponseBody
	public ReturnModel uploadPdf(@RequestParam("file") MultipartFile file,Long scenicSpotId,String ids) {
		ReturnModel returnModel = new ReturnModel();
		try {
			if (ToolUtil.isEmpty(ids)) {
				returnModel.setData("");
	            returnModel.setMsg("机器人编号获取失败");
	            returnModel.setState(Constant.STATE_FAILURE);
	            return returnModel;
			}
			if (!file.isEmpty()) {
				int i = sysRobotInsuranceService.uploadPdf(file , ids);
				if (i == 1) {
					returnModel.setData("");
		            returnModel.setMsg("导入成功");
		            returnModel.setState(Constant.STATE_SUCCESS);
		            return returnModel;
				}else {
					returnModel.setData("");
		            returnModel.setMsg("导入失败！");
		            returnModel.setState(Constant.STATE_FAILURE);
		            return returnModel;
				}
			}else {
				returnModel.setData("");
	            returnModel.setMsg("导入失败！文件不能为空");
	            returnModel.setState(Constant.STATE_FAILURE);
	            return returnModel;
			}
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("uploadPdf", e);
            returnModel.setData("");
            returnModel.setMsg("导入失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
		}
	}

	/**
	 * @Author zhang
	 * @Description: 下载机器人保险Excel表
	 * @Title: uploadScenicSpotCapPriceExcel
	 * @date
	 * @return void
	 * @throws
	 */

	@RequestMapping(value = "/uploadRobotInsuranceExcel")
	public void  uploadRobotInsuranceExcel(HttpServletResponse response, SysRobotInsurance sysRobotInsurance) throws Exception {
		//List<SysScenicSpotCapPrice> scenicSpotCapPriceByExample = null;
		List<SysRobotInsurance> sysRobotInsurancesExample = null;
		Map<String,String> search = new HashMap<>();
		SysUsers user = this.getSysUser();
		search.put("userId",user.getUserId().toString());
		search.put("robotCode",sysRobotInsurance.getRobotCode());
		search.put("scenicSpotId",sysRobotInsurance.getScenicSpotId());
		sysRobotInsurancesExample = sysRobotInsuranceService.uploadRobotInsuranceExcel(search);
		String dateTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
		//     FileUtil.exportExcel(FileUtil.getWorkbook("封顶价格", "封顶价格", SysScenicSpotCapPriceLog.class, scenicSpotCapPriceLogByExample),"封顶价格"+ dateTime +".xls",response);
		FileUtil.exportExcel(FileUtil.getWorkbook("机器人保险","机器人保险",SysRobotInsurance.class,sysRobotInsurancesExample),"机器人保险"+ dateTime +".xls",response);
	}

}
