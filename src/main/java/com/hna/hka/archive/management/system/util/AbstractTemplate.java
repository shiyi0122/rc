package com.hna.hka.archive.management.system.util;

import com.google.common.collect.Maps;
import com.hna.hka.archive.management.system.util.HtmlUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-09-17 17:51
 **/
public abstract class AbstractTemplate {
    // 使用thymeleaf模版引擎
    private TemplateEngine engine;
    // 模版名称
    private String templateName;

    private AbstractTemplate() {}

    public AbstractTemplate(TemplateEngine engine,String templateName) {
        this.engine = engine;
        this.templateName=templateName;
    }

    /**
     * 模版名称
     *
     * @return
     */
    protected String templateName(){
        return this.templateName;
    }

    /**
     * 所有的参数数据
     *
     * @return
     */
    private Map<String, Object> variables(){
        Map<String, Object> variables = Maps.newHashMap();
        // 对应html模版中的template变量，取值的时候就按照“${template.字段名}”格式，可自行修改
        variables.put("template", this);
        return variables;
    };

    /**
     * 解析模版，生成html
     *
     * @return
     */
    public String process() {
        Context ctx = new Context();
        // 设置model
        ctx.setVariables(variables());
        // 根据model解析成html字符串
        return engine.process(templateName(), ctx);
    }

    public void parse2Pdf(String targetPdfFilePath) throws IOException {
        String html = process();
        // 通过html转换成pdf
        HtmlUtil.html2Pdf(html, targetPdfFilePath);
    }

    public void downloadPdf(String targetFileName , HttpServletResponse response) throws IOException{
        String html = process();
        HtmlUtil.html2servlet(targetFileName ,html , response);
    }
}
