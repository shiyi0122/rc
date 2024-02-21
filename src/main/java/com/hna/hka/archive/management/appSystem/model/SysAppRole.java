package com.hna.hka.archive.management.appSystem.model;

import lombok.Data;

/**
 * @ProjectName: IDEA-CODE
 * @Package: com.hna.hka.archive.management.appSystem.model
 * @ClassName: SysAppRole
 * @Author: 郭凯
 * @Description:
 * @Date: 2020/11/23 14:43
 * @Version: 1.0
 */

@Data
public class SysAppRole {
    private Long roleId;

    private String roleIdentity;

    private String roleName;

    private Long roleParentId;

    private String roleDesc;

    private String roleStatus;

    private String createDate;

    private String updateDate;

    private String roleType;

    /**
     * 	角色状态
     */
    private String roleStatusName;


}
