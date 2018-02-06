package com.sz91online.bgms.module.baby.domain;

import com.sz91online.common.db.entity.RootBean;
import java.util.Date;

/**
 *
 * 此类对应数据库表: t_baby_info
 */
public class BabyInfo extends RootBean {
    /**
     *
     * 此属性对应数据库字段: t_baby_info.id
     */
    private Long id;

    /**
     *
     * 此属性对应数据库字段: t_baby_info.baby_name
     */
    private String babyName;

    /**
     *
     * 此属性对应数据库字段: t_baby_info.baby_birthday
     */
    private Date babyBirthday;

    /**
     *   F为女，M为男
     *
     * 此属性对应数据库字段: t_baby_info.baby_sex
     */
    private String babySex;

    /**
     *
     * 此属性对应数据库字段: t_baby_info.create_time
     */
    private Date createTime;

    /**
     *
     * 此属性对应数据库字段: t_baby_info.baby_code
     */
    private String babyCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBabyName() {
        return babyName;
    }

    public void setBabyName(String babyName) {
        this.babyName = babyName;
    }

    public Date getBabyBirthday() {
        return babyBirthday;
    }

    public void setBabyBirthday(Date babyBirthday) {
        this.babyBirthday = babyBirthday;
    }

    public String getBabySex() {
        return babySex;
    }

    public void setBabySex(String babySex) {
        this.babySex = babySex;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getBabyCode() {
        return babyCode;
    }

    public void setBabyCode(String babyCode) {
        this.babyCode = babyCode;
    }
}