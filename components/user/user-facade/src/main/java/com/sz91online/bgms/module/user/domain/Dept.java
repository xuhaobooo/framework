package com.sz91online.bgms.module.user.domain;

import com.sz91online.common.db.entity.RootBean;

/**
 *
 * 此类对应数据库表: t_u_dept
 */
public class Dept extends RootBean {
    /**
     *
     * 此属性对应数据库字段: t_u_dept.id
     */
    private Long id;

    /**
     *
     * 此属性对应数据库字段: t_u_dept.dept_name
     */
    private String deptName;

    /**
     *
     * 此属性对应数据库字段: t_u_dept.parent_dept_code
     */
    private String parentDeptCode;

    /**
     *
     * 此属性对应数据库字段: t_u_dept.dept_desc
     */
    private String deptDesc;

    /**
     *
     * 此属性对应数据库字段: t_u_dept.dept_code
     */
    private String deptCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getParentDeptCode() {
        return parentDeptCode;
    }

    public void setParentDeptCode(String parentDeptCode) {
        this.parentDeptCode = parentDeptCode;
    }

    public String getDeptDesc() {
        return deptDesc;
    }

    public void setDeptDesc(String deptDesc) {
        this.deptDesc = deptDesc;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }
}