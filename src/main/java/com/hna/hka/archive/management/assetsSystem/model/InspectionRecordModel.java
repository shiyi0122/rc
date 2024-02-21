package com.hna.hka.archive.management.assetsSystem.model;

import com.hna.hka.archive.management.system.util.AbstractTemplate;
import lombok.Data;
import org.thymeleaf.TemplateEngine;

import java.util.List;

/**
 * @program: rc
 * @description: 导出模板类
 * @author: zhaoxianglong
 * @create: 2021-09-22 10:19
 **/
@Data
public class InspectionRecordModel extends AbstractTemplate {
    public InspectionRecordModel(TemplateEngine engine, String templateName) {
        super(engine, templateName);
    }

    private InspectionRecord record;

    private List<InspectionRecordDetail> details;
}
