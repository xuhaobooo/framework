package com.sz91online.bgms.module.user.domain;

import com.sz91online.common.db.entity.RootBean;

/**
 *
 * 此类对应数据库表: t_u_role
 */
public class Role extends RootBean {
    /**
     *
     * 此属性对应数据库字段: t_u_role.id
     */
    private Long id;

    /**
     *
     * 此属性对应数据库字段: t_u_role.role_name
     */
    private String roleName;

    /**
     *   N：正常使用状态
            S：状态为禁用状态，需要管理员恢复
     *
     * 此属性对应数据库字段: t_u_role.status
     */
    private String status;

    /**
     *
     * 此属性对应数据库字段: t_u_role.role_desc
     */
    private String roleDesc;

    /**
     *
     * 此属性对应数据库字段: t_u_role.role_code
     */
    private String roleCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
}