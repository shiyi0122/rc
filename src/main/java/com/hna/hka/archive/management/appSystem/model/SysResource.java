package com.hna.hka.archive.management.appSystem.model;

import lombok.Data;

import java.util.Date;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.appSystem.model
 * @ClassName: SysResource
 * @Author: 郭凯
 * @Description:
 * @Date: 2020/11/23 14:41
 * @Version: 1.0
 */

@Data
public class SysResource {
    private static final long serialVersionUID = 6969075492873840476L;

    public static Long DEFAULT_ROOT_RESOURCE_ID = 0L;
    public static int DEFAULT_RESOURCE_MAX_LEVEL = 5;

    /** 资源类型函数 **/
    public static String RESOURCE_TYPE_FUNCTION = "10";
    public static String RESOURCE_TYPE_METHOD = "20";
    public static String RESOURCE_TYPE_STATIC = "30";

    private Long resId;// 系统资源主键
    private String resIdentity;// 系统资源标示
    private String resName;// 系统资源名称
    private String resContent;// 系统资源内容
    private String resIcon;// 系统资源图标
    private String resDesc;// 系统资源描述
    private Long resParentId;// 系统资源父ID
    private String resType;// 系统资源类型
    private Integer resSeq;// 系统资源顺序
    private String resStatus;// 系统资源状态
    private Date createDate;// 系统资源创建时间
    private Date updateDate;// 系统资源更新时间


}
