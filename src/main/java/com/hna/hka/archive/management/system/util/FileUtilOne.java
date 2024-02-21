package com.hna.hka.archive.management.system.util;

import java.io.File;

/**
* @ClassName: FileUtils
* @Description: 文件操作工具类
* @author 郭凯
* @date 2021年1月15日
* @version V1.0
 */
public class FileUtilOne {
	
	/**
	* @Author 郭凯
	* @Description: 文件删除
	* @Title: deleteServerFile
	* @date  2021年1月15日 下午1:01:23 
	* @param @param filePath
	* @param @return
	* @return boolean
	* @throws
	 */
	public static boolean deleteServerFile(String filePath){  
        boolean delete_flag = false;  
        File file = new File(filePath);  
        if (file.exists() && file.isFile() && file.delete())  {
        	delete_flag = true;  
        }else {
        	delete_flag = false;  
        }
        return delete_flag;  
    }  

}
