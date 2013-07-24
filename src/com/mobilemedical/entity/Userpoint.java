package com.mobilemedical.entity;

public class Userpoint {
    private String pointId;

    private Integer userinfoId;

    private String pointinfo;

    private Integer createtime;

    private Integer deleted;

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId == null ? null : pointId.trim();
    }

    public Integer getUserinfoId() {
        return userinfoId;
    }

    public void setUserinfoId(Integer userinfoId) {
        this.userinfoId = userinfoId;
    }

    public String getPointinfo() {
        return pointinfo;
    }

    public void setPointinfo(String pointinfo) {
        this.pointinfo = pointinfo == null ? null : pointinfo.trim();
    }

    public Integer getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Integer createtime) {
        this.createtime = createtime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }
}