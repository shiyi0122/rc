package com.hna.hka.archive.management.appSystem.model;

import java.util.Date;
import lombok.Data;

/**
 * 系统权限
 * @author Mars
 *
 */
@Data
public class SysPermission {

	private static final long serialVersionUID = -8355023076717762878L;

	private Long prmsId;// 主键
	private String prmsName;// 权限名称
	private String prmsIdentity;// 权限标识
	private String prmsDesc;// 权限描述
	private String prmsStatus;// 权限状态
	private Date createDate;// 创建时间
	private String updateDate;// 更新时间

}
