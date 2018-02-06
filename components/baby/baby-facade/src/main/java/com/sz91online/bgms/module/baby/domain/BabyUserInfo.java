package com.sz91online.bgms.module.baby.domain;

import com.sz91online.common.db.entity.RootBean;

/**
 *
 * 此类对应数据库表: t_baby_user_info
 */
public class BabyUserInfo extends RootBean {
    /**
     *
     * 此属性对应数据库字段: t_baby_user_info.id
     */
    private Long id;

    /**
     *
     * 此属性对应数据库字段: t_baby_user_info.addr_name
     */
    private String addrName;

    /**
     *
     * 此属性对应数据库字段: t_baby_user_info.addr_pos_x
     */
    private Double addrPosX;

    /**
     *
     * 此属性对应数据库字段: t_baby_user_info.addr_pos_y
     */
    private Double addrPosY;

    /**
     *   DD:爸爸
            MM:妈妈
            NN:奶奶
            YY:爷爷
            WG:外公
            WP:外婆
     *
     * 此属性对应数据库字段: t_baby_user_info.user_role
     */
    private String userRole;

    /**
     *
     * 此属性对应数据库字段: t_baby_user_info.tel
     */
    private String tel;

    /**
     *
     * 此属性对应数据库字段: t_baby_user_info.note
     */
    private String note;

    /**
     *   关联T_U_USER中的用户编号
     *
     * 此属性对应数据库字段: t_baby_user_info.user_code
     */
    private String userCode;

    /**
     *
     * 此属性对应数据库字段: t_baby_user_info.user_name
     */
    private String userName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddrName() {
        return addrName;
    }

    public void setAddrName(String addrName) {
        this.addrName = addrName;
    }

    public Double getAddrPosX() {
        return addrPosX;
    }

    public void setAddrPosX(Double addrPosX) {
        this.addrPosX = addrPosX;
    }

    public Double getAddrPosY() {
        return addrPosY;
    }

    public void setAddrPosY(Double addrPosY) {
        this.addrPosY = addrPosY;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}