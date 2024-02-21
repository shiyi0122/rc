package com.hna.hka.archive.management.system.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hna.hka.archive.management.system.util.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysRobotInsuranceMapper;
import com.hna.hka.archive.management.system.dao.SysRobotMapper;
import com.hna.hka.archive.management.system.model.SysRobot;
import com.hna.hka.archive.management.system.model.SysRobotInsurance;
import com.hna.hka.archive.management.system.service.SysRobotInsuranceService;

/**
 * 
* @ClassName: SysRobotInsuranceServiceImpl
* @Description: 机器人保险管理业务层（实现）
* @author 郭凯
* @date 2020年12月23日
* @version V1.0
 */
@Service
@Transactional
public class SysRobotInsuranceServiceImpl implements SysRobotInsuranceService {
	
	@Autowired
	private SysRobotInsuranceMapper sysRobotInsuranceMapper;
	
	@Autowired
	private SysRobotMapper sysRobotMapper;
	
	@Value("${filePathPadPaht}")
    private String filePathPadPaht;

    @Value("${filePathPadUrl}")
    private String filePathPadUrl;

    @Autowired
	private FileUploadUtil fileUploadUtil;

	/**
	* @Author 郭凯
	* @Description: 机器人保险列表查询
	* @Title: getRobotInsuranceList
	* @date  2020年12月23日 上午11:09:50 
	* @param @param pageNum
	* @param @param pageSize
	* @param @param search
	* @param @return
	* @throws
	 */
	@Override
	public PageDataResult getRobotInsuranceList(Integer pageNum, Integer pageSize, Map<String, String> search) {
		// TODO Auto-generated method stubX
		PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRobotInsurance> sysRobotInsuranceList = sysRobotInsuranceMapper.getRobotInsuranceList(search);
        for(SysRobotInsurance insurance : sysRobotInsuranceList) {
        	if (ToolUtil.isNotEmpty(insurance.getInsureEndTime()) && DateUtil.isValidDate(insurance.getInsureEndTime())) {
        		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
            	String format = df.format(new Date());
            	int totalTime = 0;
    			try {
    				totalTime = DateUtil.caculateTotalTime(format, insurance.getInsureEndTime());
    			} catch (ParseException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
            	insurance.setRemainingTime(String.valueOf(totalTime));
			}else {
				insurance.setRemainingTime("0");
			}
        }
        if (sysRobotInsuranceList.size() != 0){
            PageInfo<SysRobotInsurance> pageInfo = new PageInfo<>(sysRobotInsuranceList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
	}

	/**
	 * 
	* @Author 郭凯
	* @Description: 添加机器人保险单
	* @Title: addRobotInsurance
	* @date  2020年12月23日 下午5:30:46 
	* @param @param file
	* @param @param sysRobotInsurance
	* @param @return
	* @throws
	 */
	@Override
	public int addRobotInsurance(MultipartFile file, SysRobotInsurance sysRobotInsurance) {
		// TODO Auto-generated method stub
		String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
        if (type.equals(".pdf")) {
        	String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
            String path = filePathPadPaht + filename;// 存放位置
            File destFile = new File(path);
            try {
                //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
//				//阿里文件存储
//                 String upload = fileUploadUtil.upload(file, filePathPadPaht.substring(1) + filename);
//                System.out.println(upload);
            } catch (IOException e) {
                e.printStackTrace();
            }//复制文件到指定目录 
            sysRobotInsurance.setInsuranceId(IdUtils.getSeqId());
            sysRobotInsurance.setInsureUrl(filePathPadUrl + filename);
            sysRobotInsurance.setCreateDate(DateUtil.currentDateTime());
            sysRobotInsurance.setUpdateDate(DateUtil.currentDateTime());
            return sysRobotInsuranceMapper.insertSelective(sysRobotInsurance);
        }else {
        	return 2;
        }
	}

	/**
	 * 
	* @Author 郭凯
	* @Description: 修改机器人保险单
	* @Title: updateRobotInsurance
	* @date  2020年12月24日 上午9:47:46 
	* @param @param file
	* @param @param sysRobotInsurance
	* @param @return
	* @throws
	 */
	@Override
	public int updateRobotInsurance(MultipartFile file, SysRobotInsurance sysRobotInsurance) {
		// TODO Auto-generated method stub
		if (file.getSize() > 0) {
			String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
	        if (type.equals(".pdf")) {
	        	String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
	            String path = filePathPadPaht + filename;// 存放位置
	            File destFile = new File(path);
	            try {
	                //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
	                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
					//阿里文件存储
//					String upload = fileUploadUtil.upload(file, filePathPadPaht.substring(1) + filename);
//                	System.out.println(upload);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }//复制文件到指定目录 
	            sysRobotInsurance.setInsureUrl(filePathPadUrl + filename);
	        }else {
	        	return 2;
	        }
		}
        sysRobotInsurance.setUpdateDate(DateUtil.currentDateTime());
        return sysRobotInsuranceMapper.updateByPrimaryKeySelective(sysRobotInsurance);
	}

	/**
	 * 
	* @Author 郭凯
	* @Description: 根据ID查询机器人保险信息
	* @Title: getRobotInsuranceById
	* @date  2020年12月24日 下午2:14:35 
	* @param @param insuranceId
	* @param @return
	* @throws
	 */
	@Override
	public SysRobotInsurance getRobotInsuranceById(Long robotId) {
		// TODO Auto-generated method stub
		return sysRobotInsuranceMapper.getRobotInsuranceByRobotId(robotId);
	}

	/**
	 * 
	* @Author 郭凯
	* @Description: 新增机器人信息
	* @Title: insertRobotInsurance
	* @date  2020年12月24日 下午2:20:54 
	* @param @param sysRobotInsurance
	* @param @return
	* @throws
	 */
	@Override
	public int insertRobotInsurance(SysRobotInsurance sysRobotInsurance) {
		// TODO Auto-generated method stub
         sysRobotInsurance.setInsuranceId(IdUtils.getSeqId());
         sysRobotInsurance.setCreateDate(DateUtil.currentDateTime());
         sysRobotInsurance.setUpdateDate(DateUtil.currentDateTime());
         return sysRobotInsuranceMapper.insertSelective(sysRobotInsurance);
	}

	/**
	* @Author 郭凯
	* @Description: 导入PDF
	* @Title: uploadPdf
	* @date  2021年1月4日 下午3:37:24 
	* @param @param file
	* @param @param scenicSpotId
	* @param @return
	* @throws
	 */
	@Override
	public int uploadPdf(MultipartFile file, String ids) {
		// TODO Auto-generated method stub
		String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
        if (type.equals(".pdf")) {
        	String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
            String path = filePathPadPaht + filename;// 存放位置
            File destFile = new File(path);
            try {
                //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
				//阿里文件存储
//                 String upload = fileUploadUtil.upload(file, filePathPadPaht.substring(1) + filename);
//                System.out.println(upload);
            } catch (IOException e) {
                e.printStackTrace();
            }//复制文件到指定目录 
            String insureUrl = filePathPadUrl + filename;
            // 根据,(逗号)进行分割
        	String[] split = ids.split(",");
        	for (int i = 0; i < split.length; i++) {
        		System.out.println(split[i]);
        		SysRobot robot = sysRobotMapper.getRobotCodeBy(split[i]);
        		SysRobotInsurance sysRobotInsurance = sysRobotInsuranceMapper.getRobotInsuranceByRobotId(robot.getRobotId());
            	if (ToolUtil.isNotEmpty(sysRobotInsurance)) {
            		SysRobotInsurance robotInsurance =  new SysRobotInsurance();
            		robotInsurance.setInsuranceId(sysRobotInsurance.getInsuranceId());
            		robotInsurance.setRobotId(robot.getRobotId());
            		robotInsurance.setInsureUrl(insureUrl);
            		robotInsurance.setUpdateDate(DateUtil.currentDateTime());
            		sysRobotInsuranceMapper.updateByPrimaryKeySelective(robotInsurance);
				}else if(ToolUtil.isEmpty(sysRobotInsurance)){
					SysRobotInsurance robotInsurance =  new SysRobotInsurance();
					robotInsurance.setInsuranceId(IdUtils.getSeqId());
					robotInsurance.setRobotId(robot.getRobotId());
					robotInsurance.setInsureUrl(insureUrl);
            		robotInsurance.setCreateDate(DateUtil.currentDateTime());
            		robotInsurance.setUpdateDate(DateUtil.currentDateTime());
            		sysRobotInsuranceMapper.insertSelective(robotInsurance);
				}
        	}
            return 1;
        }else {
        	return 2;
        }
	}

	/**
	* @Author 郭凯
	* @Description: 修改机器人保险单
	* @Title: updateRobotInsurance
	* @date  2021年1月7日 下午4:52:19 
	* @param @param sysRobotInsurance
	* @param @return
	* @throws
	 */
	@Override
	public int updateRobotInsurance(SysRobotInsurance sysRobotInsurance) {
		// TODO Auto-generated method stub
		sysRobotInsurance.setUpdateDate(DateUtil.currentDateTime());
        return sysRobotInsuranceMapper.updateByPrimaryKeySelective(sysRobotInsurance);
	}

	//导chu机器人保险单
	@Override
	public List<SysRobotInsurance> uploadRobotInsuranceExcel(Map<String, String> search) {
		// TODO Auto-generated method stub
		List<SysRobotInsurance> sysRobotInsuranceList = sysRobotInsuranceMapper.uploadRobotInsuranceExcel(search);
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			for(SysRobotInsurance insurance : sysRobotInsuranceList) {
				if (ToolUtil.isNotEmpty(insurance.getInsureEndTime()) && DateUtil.isValidDate(insurance.getInsureEndTime())) {
					SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
					String format = df.format(new Date());
					int totalTime = 0;
					try {
						totalTime = DateUtil.caculateTotalTime(format, insurance.getInsureEndTime());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					insurance.setRemainingTime(String.valueOf(totalTime));
				}else {
					insurance.setRemainingTime("0");
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}


		return sysRobotInsuranceList;
	}


}
