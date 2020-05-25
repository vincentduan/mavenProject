package nm;

import com.alibaba.fastjson.annotation.JSONField;

public class Info_org {
    @JSONField(name="brief")
    private String brief;
    @JSONField(name="email")
    private String email;
    @JSONField(name="name")
    private String name;
    @JSONField(name="org_id")
    private String org_id;
    @JSONField(name="fax")
    private String fax;
    @JSONField(name="website")
    private String website;
    @JSONField(name="zipCode")
    private String zipCode;
    @JSONField(name="telephone")
    private String telephone;


    @Override
    public String toString() {
        return "Info_org{" +
                "brief='" + brief + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", org_id='" + org_id + '\'' +
                ", fax='" + fax + '\'' +
                ", website='" + website + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrg_id() {
        return org_id;
    }

    public void setOrg_id(String org_id) {
        this.org_id = org_id;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public static Info_org generate() {
        return null;
    }

}
