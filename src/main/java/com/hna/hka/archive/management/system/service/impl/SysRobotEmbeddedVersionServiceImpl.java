package com.hna.hka.archive.management.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hna.hka.archive.management.system.dao.SysRobotEmbeddedVersionMapper;
import com.hna.hka.archive.management.system.model.SysRobotEmbeddedVersion;
import com.hna.hka.archive.management.system.service.SysRobotEmbeddedVersionService;
import com.hna.hka.archive.management.system.util.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.system.service.impl
 * @ClassName: SysRobotEmbeddedVersionServiceImpl
 * @Author: 郭凯
 * @Description: 嵌入式软件管理业务层（实现）
 * @Date: 2020/9/8 13:45
 * @Version: 1.0
 */
@Service
@Transactional
public class SysRobotEmbeddedVersionServiceImpl implements SysRobotEmbeddedVersionService {

    @Autowired
    private SysRobotEmbeddedVersionMapper sysRobotEmbeddedVersionMapper;

    @Value("${getEmbeddedPaht}")
    private String getEmbeddedPaht;

    @Value("${getEmbeddedUrl}")
    private String getEmbeddedUrl;

//    @Autowired
//	private FileUploadUtil fileUploadUtil;


    /**
     * @Author 郭凯
     * @Description 嵌入式软件管理列表查询
     * @Date 13:46 2020/9/8
     * @Param [pageNum, pageSize, search]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @Override
    public PageDataResult getRobotEmbeddedVersionList(Integer pageNum, Integer pageSize, Map<String, String> search) {
        PageDataResult pageDataResult = new PageDataResult();
        PageHelper.startPage(pageNum, pageSize);
        List<SysRobotEmbeddedVersion> sysRobotEmbeddedVersionList = sysRobotEmbeddedVersionMapper.getRobotEmbeddedVersionList(search);
        if (sysRobotEmbeddedVersionList.size() != 0){
            PageInfo<SysRobotEmbeddedVersion> pageInfo = new PageInfo<>(sysRobotEmbeddedVersionList);
            pageDataResult.setList(pageInfo.getList());
            pageDataResult.setTotals((int) pageInfo.getTotal());
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 新增嵌入式软件
     * @Date 15:08 2020/9/8
     * @Param [sysRobotEmbeddedVersion, file]
     * @return int
    **/
    @Override
    public int addRobotEmbeddedVersion(SysRobotEmbeddedVersion sysRobotEmbeddedVersion, MultipartFile file) {
        String type = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));// 取文件格式后缀名
        String originalFilename = file.getOriginalFilename();
        String softwareVersion = originalFilename.substring(0, originalFilename.indexOf("."));
        if (softwareVersion.length() != 14) {
        	return 4;
		}
        if (sysRobotEmbeddedVersion.getEmbeddedType().equals("1")) {
			if (!"ZK".equals(originalFilename.substring(0, 2))) {
				return 3;
			}
			String substring = originalFilename.substring(0, originalFilename.indexOf("V"));
	    	if (substring.substring(substring.indexOf("K") + 1).length() != 8) {
	    		return 5;
			}
	    	if (softwareVersion.substring(softwareVersion.indexOf("V") + 1).length() != 3) {
	    		return 6;
			}
		}else if(sysRobotEmbeddedVersion.getEmbeddedType().equals("2")) {
			if (!"CS".equals(originalFilename.substring(0, 2))) {
				return 3;
			}
			String substring = originalFilename.substring(0, originalFilename.indexOf("V"));
	    	if (substring.substring(substring.indexOf("S") + 1).length() != 8) {
	    		return 5;
			}
	    	if (softwareVersion.substring(softwareVersion.indexOf("V") + 1).length() != 3) {
	    		return 6;
			}
		}
    	sysRobotEmbeddedVersion.setEmbeddedVersion(softwareVersion);
    	if (sysRobotEmbeddedVersion.getEmbeddedType().equals("1")) {
    		String version = softwareVersion.substring(softwareVersion.indexOf("V") + 1);
    		List<String> list = new ArrayList<String>();
    		String str = "";
    		list.add(version.substring(0,1));
    		list.add(version.substring(1,2));
    		list.add(version.substring(2,3));
    		for (String num : list) {
    			if(num !=null){
    				str += num.toString() + ".";
    			}
    		}
    		if (str.length() > 0) {
    			//sendDocNum.length() - 1 的原因：从零开始的，含头不含尾。
    			str = str.substring(0, str.length() - 1);
    		}
    		sysRobotEmbeddedVersion.setRobotVersion(str);
			sysRobotEmbeddedVersion.setSoftwareVersion(version);
		}else if(sysRobotEmbeddedVersion.getEmbeddedType().equals("2")) {
			String version = softwareVersion.substring(softwareVersion.indexOf("V") + 1);
			sysRobotEmbeddedVersion.setSoftwareVersion(version);
			if ("101".equals(version)) {
				sysRobotEmbeddedVersion.setRobotVersion("3.0,3.1");
			}else if("201".equals(version)) {
				sysRobotEmbeddedVersion.setRobotVersion("3.2,3.3");
			}
		}
        if (type.equals(".bin")) {
            String filename = System.currentTimeMillis() + type;// 取当前时间戳作为文件名
            String path = getEmbeddedPaht + filename;// 存放位置
            File destFile = new File(path);
            try {
                //FileUtils.copyInputStreamToFile();这个方法里对IO进行了自动操作，不需要额外的再去关闭IO流
                FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);

//				String upload = fileUploadUtil.upload(file, getEmbeddedPaht.substring(1) + filename);

			} catch (IOException e) {
                e.printStackTrace();
            }//复制文件到指定目录
          //查询是否存在相同版本号
            SysRobotEmbeddedVersion embeddedVersion = sysRobotEmbeddedVersionMapper.getRobotEmbeddedVersionByVersion(sysRobotEmbeddedVersion.getSoftwareVersion());
            if (embeddedVersion != null) {
            	return 7;
    		}
            sysRobotEmbeddedVersion.setEmbeddedId(IdUtils.getSeqId());
            sysRobotEmbeddedVersion.setEmbeddedUrl(getEmbeddedUrl + filename);
            sysRobotEmbeddedVersion.setCreateDate(DateUtil.currentDateTime());
            sysRobotEmbeddedVersion.setUpdateDate(DateUtil.currentDateTime());
            sysRobotEmbeddedVersion.setFileSize(String.valueOf(file.getSize()));
            return sysRobotEmbeddedVersionMapper.insertSelective(sysRobotEmbeddedVersion);
        }else {
            return 2;
        }
    }

    /**
     * @Author 郭凯
     * @Description 嵌入式软件删除
     * @Date 15:51 2020/9/8
     * @Param [embeddedId]
     * @return int
    **/
    @Override
    public int delRobotEmbeddedVersion(Long embeddedId) {
    	SysRobotEmbeddedVersion embeddedVersion = sysRobotEmbeddedVersionMapper.selectByPrimaryKey(embeddedId);
    	int i = sysRobotEmbeddedVersionMapper.deleteByPrimaryKey(embeddedId);
    	if (i == 1) {
    		String embeddedUrl = embeddedVersion.getEmbeddedUrl();
        	String result = embeddedUrl.substring(embeddedUrl.lastIndexOf('/') + 1).trim();
    		FileUtilOne.deleteServerFile(getEmbeddedPaht + result);
		}
        return 1;
    }

    /**
     * @Author 郭凯
     * @Description 嵌入式软件修改
     * @Date 16:24 2020/9/8
     * @Param [sysRobotEmbeddedVersion, file]
     * @return int
    **/
    @Override
    public int editRobotEmbeddedVersion(SysRobotEmbeddedVersion sysRobotEmbeddedVersion, MultipartFile file) {
        return sysRobotEmbeddedVersionMapper.updateByPrimaryKeySelective(sysRobotEmbeddedVersion);
    }
}
