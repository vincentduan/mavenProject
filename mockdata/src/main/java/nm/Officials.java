package nm;

import com.alibaba.fastjson.annotation.JSONField;

public class Officials {
    @JSONField(name="address_of_birth")
    private String address_of_birth;
    @JSONField(name="address_of_birth_city")
    private String address_of_birth_city;
    @JSONField(name="start_working_time_1")
    private String start_working_time_1;
    @JSONField(name="start_working_time_2")
    private String start_working_time_2;
    @JSONField(name="start_working_time_3")
    private String start_working_time_3;
    @JSONField(name="end_working_time_1")
    private String end_working_time_1;
    @JSONField(name="end_working_time_2")
    private String end_working_time_2;
    @JSONField(name="end_working_time_3")
    private String end_working_time_3;
    @JSONField(name="name")
    private String name;
    @JSONField(name="org")
    private String org;
    @JSONField(name="reside_address_town")
    private String reside_address_town;
    @JSONField(name="reside_address_BAG")
    private String reside_address_BAG;
    @JSONField(name="reside_address_city")
    private String reside_address_city;
    @JSONField(name="reside_address_province")
    private String reside_address_province;
    @JSONField(name="reside_address_country")
    private String reside_address_country;
    @JSONField(name="TYDICID")
    private String TYDICID;
    @JSONField(name="education")
    private String education;
    @JSONField(name="register_date")
    private String register_date;
    @JSONField(name="department")
    private String department;
    @JSONField(name="idcard")
    private String idcard;
    @JSONField(name="family_telephone")
    private String family_telephone;
    @JSONField(name="region_address_province")
    private String region_address_province;
    @JSONField(name="region_address_city")
    private String region_address_city;
    @JSONField(name="region_address_country")
    private String region_address_country;
    @JSONField(name="org_1")
    private String org_1;
    @JSONField(name="org_2")
    private String org_2;
    @JSONField(name="org_3")
    private String org_3;
    @JSONField(name="password")
    private String password;
    @JSONField(name="position_name")
    private String position_name;
    @JSONField(name="year")
    private String year;
    @JSONField(name="telephone")
    private String telephone;
    @JSONField(name="position")
    private String position;
    @JSONField(name="level")
    private String level;
    @JSONField(name="firstName")
    private String firstName;
    @JSONField(name="mobile")
    private String mobile;
    @JSONField(name="place_of_birth")
    private String place_of_birth;
    @JSONField(name="speciality")
    private String speciality;
    @JSONField(name="family_name")
    private String family_name;
    @JSONField(name="is_abroad")
    private String is_abroad;

    public static Officials generate() {
        return null;
    }

    @Override
    public String toString() {
        return "Officials{" +
                "address_of_birth='" + address_of_birth + '\'' +
                ", address_of_birth_city='" + address_of_birth_city + '\'' +
                ", start_working_time_1='" + start_working_time_1 + '\'' +
                ", start_working_time_2='" + start_working_time_2 + '\'' +
                ", start_working_time_3='" + start_working_time_3 + '\'' +
                ", end_working_time_1='" + end_working_time_1 + '\'' +
                ", end_working_time_2='" + end_working_time_2 + '\'' +
                ", end_working_time_3='" + end_working_time_3 + '\'' +
                ", name='" + name + '\'' +
                ", org='" + org + '\'' +
                ", reside_address_town='" + reside_address_town + '\'' +
                ", reside_address_BAG='" + reside_address_BAG + '\'' +
                ", reside_address_city='" + reside_address_city + '\'' +
                ", reside_address_province='" + reside_address_province + '\'' +
                ", reside_address_country='" + reside_address_country + '\'' +
                ", TYDICID='" + TYDICID + '\'' +
                ", education='" + education + '\'' +
                ", register_date='" + register_date + '\'' +
                ", department='" + department + '\'' +
                ", idcard='" + idcard + '\'' +
                ", family_telephone='" + family_telephone + '\'' +
                ", region_address_province='" + region_address_province + '\'' +
                ", region_address_city='" + region_address_city + '\'' +
                ", region_address_country='" + region_address_country + '\'' +
                ", org_1='" + org_1 + '\'' +
                ", org_2='" + org_2 + '\'' +
                ", org_3='" + org_3 + '\'' +
                ", password='" + password + '\'' +
                ", position_name='" + position_name + '\'' +
                ", year='" + year + '\'' +
                ", telephone='" + telephone + '\'' +
                ", position='" + position + '\'' +
                ", level='" + level + '\'' +
                ", firstName='" + firstName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", place_of_birth='" + place_of_birth + '\'' +
                ", speciality='" + speciality + '\'' +
                ", family_name='" + family_name + '\'' +
                ", is_abroad='" + is_abroad + '\'' +
                '}';
    }

    public String getAddress_of_birth() {
        return address_of_birth;
    }

    public void setAddress_of_birth(String address_of_birth) {
        this.address_of_birth = address_of_birth;
    }

    public String getAddress_of_birth_city() {
        return address_of_birth_city;
    }

    public void setAddress_of_birth_city(String address_of_birth_city) {
        this.address_of_birth_city = address_of_birth_city;
    }

    public String getStart_working_time_1() {
        return start_working_time_1;
    }

    public void setStart_working_time_1(String start_working_time_1) {
        this.start_working_time_1 = start_working_time_1;
    }

    public String getStart_working_time_2() {
        return start_working_time_2;
    }

    public void setStart_working_time_2(String start_working_time_2) {
        this.start_working_time_2 = start_working_time_2;
    }

    public String getStart_working_time_3() {
        return start_working_time_3;
    }

    public void setStart_working_time_3(String start_working_time_3) {
        this.start_working_time_3 = start_working_time_3;
    }

    public String getEnd_working_time_1() {
        return end_working_time_1;
    }

    public void setEnd_working_time_1(String end_working_time_1) {
        this.end_working_time_1 = end_working_time_1;
    }

    public String getEnd_working_time_2() {
        return end_working_time_2;
    }

    public void setEnd_working_time_2(String end_working_time_2) {
        this.end_working_time_2 = end_working_time_2;
    }

    public String getEnd_working_time_3() {
        return end_working_time_3;
    }

    public void setEnd_working_time_3(String end_working_time_3) {
        this.end_working_time_3 = end_working_time_3;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getReside_address_town() {
        return reside_address_town;
    }

    public void setReside_address_town(String reside_address_town) {
        this.reside_address_town = reside_address_town;
    }

    public String getReside_address_BAG() {
        return reside_address_BAG;
    }

    public void setReside_address_BAG(String reside_address_BAG) {
        this.reside_address_BAG = reside_address_BAG;
    }

    public String getReside_address_city() {
        return reside_address_city;
    }

    public void setReside_address_city(String reside_address_city) {
        this.reside_address_city = reside_address_city;
    }

    public String getReside_address_province() {
        return reside_address_province;
    }

    public void setReside_address_province(String reside_address_province) {
        this.reside_address_province = reside_address_province;
    }

    public String getReside_address_country() {
        return reside_address_country;
    }

    public void setReside_address_country(String reside_address_country) {
        this.reside_address_country = reside_address_country;
    }

    public String getTYDICID() {
        return TYDICID;
    }

    public void setTYDICID(String TYDICID) {
        this.TYDICID = TYDICID;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getRegister_date() {
        return register_date;
    }

    public void setRegister_date(String register_date) {
        this.register_date = register_date;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getFamily_telephone() {
        return family_telephone;
    }

    public void setFamily_telephone(String family_telephone) {
        this.family_telephone = family_telephone;
    }

    public String getRegion_address_province() {
        return region_address_province;
    }

    public void setRegion_address_province(String region_address_province) {
        this.region_address_province = region_address_province;
    }

    public String getRegion_address_city() {
        return region_address_city;
    }

    public void setRegion_address_city(String region_address_city) {
        this.region_address_city = region_address_city;
    }

    public String getRegion_address_country() {
        return region_address_country;
    }

    public void setRegion_address_country(String region_address_country) {
        this.region_address_country = region_address_country;
    }

    public String getOrg_1() {
        return org_1;
    }

    public void setOrg_1(String org_1) {
        this.org_1 = org_1;
    }

    public String getOrg_2() {
        return org_2;
    }

    public void setOrg_2(String org_2) {
        this.org_2 = org_2;
    }

    public String getOrg_3() {
        return org_3;
    }

    public void setOrg_3(String org_3) {
        this.org_3 = org_3;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPosition_name() {
        return position_name;
    }

    public void setPosition_name(String position_name) {
        this.position_name = position_name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public void setPlace_of_birth(String place_of_birth) {
        this.place_of_birth = place_of_birth;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String getIs_abroad() {
        return is_abroad;
    }

    public void setIs_abroad(String is_abroad) {
        this.is_abroad = is_abroad;
    }
}
