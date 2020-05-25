package nm;

import com.alibaba.fastjson.annotation.JSONField;

public class Member_org {
    @JSONField(name="position")
    private String position;
    @JSONField(name="desc")
    private String desc;
    @JSONField(name="idcard")
    private String idcard;
    @JSONField(name="orgName")
    private String orgName;
    @JSONField(name="remarks")
    private String remarks;

    @Override
    public String toString() {
        return "Member_org{" +
                "position='" + position + '\'' +
                ", desc='" + desc + '\'' +
                ", idcard='" + idcard + '\'' +
                ", orgName='" + orgName + '\'' +
                ", remarks='" + remarks + '\'' +
                '}';
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public static Member_org generate() {
        return null;
    }

}
