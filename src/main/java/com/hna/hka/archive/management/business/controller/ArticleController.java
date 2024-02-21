package com.hna.hka.archive.management.business.controller;

import com.alibaba.fastjson.JSONObject;
import com.hna.hka.archive.management.business.model.BusinessArticle;
import com.hna.hka.archive.management.business.service.BusinessArticleService;
import com.hna.hka.archive.management.system.util.Constant;
import com.hna.hka.archive.management.system.util.PageDataResult;
import com.hna.hka.archive.management.system.util.PublicUtil;
import com.hna.hka.archive.management.system.util.ReturnModel;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.business.controller
 * @ClassName: ArticleController
 * @Author: 郭凯
 * @Description: 文章管理控制层
 * @Date: 2020/8/5 10:24
 * @Version: 1.0
 */
@RequestMapping("/business/article")
@Controller
public class ArticleController extends PublicUtil {

    @Autowired
    private BusinessArticleService businessArticleService;

    /**
     * @Author 郭凯
     * @Description 文章管理列表查询
     * @Date 10:29 2020/8/5
     * @Param [pageNum, pageSize, businessArticle]
     * @return com.hna.hka.archive.management.system.util.PageDataResult
    **/
    @RequestMapping("/getArticleList")
    @ResponseBody
    public PageDataResult getArticleList(@RequestParam("pageNum") Integer pageNum,
                                        @RequestParam("pageSize") Integer pageSize, BusinessArticle businessArticle) {
        PageDataResult pageDataResult = new PageDataResult();
        Map<String,String> search = new HashMap<>();
        try {
            if(null == pageNum) {
                pageNum = 1;
            }
            if(null == pageSize) {
                pageSize = 10;
            }
            search.put("type",businessArticle.getType());
            pageDataResult = businessArticleService.getArticleList(pageNum,pageSize,search);
        }catch (Exception e){
            logger.info("文章管理列表查询失败",e);
        }
        return pageDataResult;
    }

    /**
     * @Author 郭凯
     * @Description 文章新增
     * @Date 14:44 2020/8/5
     * @Param [businessArticle]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/addArticle")
    @ResponseBody
    public ReturnModel addArticle(BusinessArticle businessArticle) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = businessArticleService.addArticle(businessArticle);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("文章新增成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("文章新增失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("addArticle", e);
            returnModel.setData("");
            returnModel.setMsg("文章新增失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 上传文件
     * 2020/8/5
     * @Param [file]
     * @return java.lang.Object
    **/
    @RequestMapping("/uploadApiFile")
    @ResponseBody
    public Object  uploadImage(MultipartFile file) {
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        //服务端要调用的外部接口
        String url ="https://oursjxzy.topsroboteer.ac.cn/common/upload_file";
        //httpclients构造post请求
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(url);
            //HttpMultipartMode.RFC6532参数的设定是为避免文件名为中文时乱码
            MultipartEntityBuilder builder = MultipartEntityBuilder.create().setMode(HttpMultipartMode.RFC6532);
            String originFileName = null;
            originFileName = file.getOriginalFilename();
            // 设置上传文件流(需要是输出流)，设置contentType的类型
            builder.addBinaryBody("file", file.getBytes(), ContentType.MULTIPART_FORM_DATA, originFileName);
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            // 执行提交
            HttpResponse response = httpClient.execute(httpPost);
            //接收调用外部接口返回的内容
            HttpEntity responseEntity = response.getEntity();
            if (response.getStatusLine().getStatusCode() == 200){
                //响应内容都存在content中
                InputStream content = responseEntity.getContent();
                in = new BufferedReader(
                        new InputStreamReader(content));
                String line;
                while ((line = in.readLine()) != null) {
                    result.append(line);
                }
                JSONObject jsonObject = JSONObject.parseObject(result.toString());
                String status = jsonObject.getString("status");
                Map<String,Object> map = new HashMap<String,Object>();
                Map<String,Object> map2 = new HashMap<String,Object>();
                if ("200".equals(status)){
                    String data1 = jsonObject.getString("data");
                    JSONObject data = JSONObject.parseObject(data1);
                    String path = data.getString("path");
                    map.put("code", 0);	//0表示上传成功
                    map.put("msg","上传成功"); //提示消息
                    map2.put("src", path);
                    map2.put("title", "");
                    map.put("data", map2);
                }
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {//处理结束后关闭httpclient的链接
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * @Author 郭凯
     * @Description 文章修改
     * @Date 15:18 2020/8/6
     * @Param [businessArticle]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/editArticle")
    @ResponseBody
    public ReturnModel editArticle(BusinessArticle businessArticle) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = businessArticleService.editArticle(businessArticle);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("文章修改成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("文章修改失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("editArticle", e);
            returnModel.setData("");
            returnModel.setMsg("文章修改失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

    /**
     * @Author 郭凯
     * @Description 文章删除
     * @Date 15:39 2020/8/6
     * @Param [id]
     * @return com.hna.hka.archive.management.system.util.ReturnModel
    **/
    @RequestMapping("/delArticle")
    @ResponseBody
    public ReturnModel delArticle(@RequestParam("id") Long id) {
        ReturnModel returnModel = new ReturnModel();
        try {
            int i = businessArticleService.delArticle(id);
            if (i == 1) {
                returnModel.setData("");
                returnModel.setMsg("文章删除成功！");
                returnModel.setState(Constant.STATE_SUCCESS);
                return returnModel;
            }else {
                returnModel.setData("");
                returnModel.setMsg("文章删除失败！");
                returnModel.setState(Constant.STATE_FAILURE);
                return returnModel;
            }
        } catch (Exception e) {
            logger.error("delArticle", e);
            returnModel.setData("");
            returnModel.setMsg("文章删除失败！（请联系管理员）");
            returnModel.setState(Constant.STATE_FAILURE);
            return returnModel;
        }
    }

}
