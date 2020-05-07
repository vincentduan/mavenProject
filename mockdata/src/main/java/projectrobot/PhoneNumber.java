package projectrobot;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.javafaker.Faker;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PhoneNumber {

    @JSONField(name="c_phone")
    private String c_phone;
    @JSONField(name="uid")
    private String uid;
    @JSONField(name="imsi")
    private String imsi;
    @JSONField(name="logins")
    private List<String> logins;
    @JSONField(name="InstallOn")
    private List<CellPhone> InstallOn;
    @JSONField(name="own_by")
    private List<Person> own_by;

    public PhoneNumber() {
    }

    public PhoneNumber(String c_phone, String imsi, List<String> logins, List<CellPhone> installOn, List<Person> own_by) {
        this.c_phone = c_phone;
        this.imsi = imsi;
        this.logins = logins;
        InstallOn = installOn;
        this.own_by = own_by;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getC_phone() {
        return c_phone;
    }

    public void setC_phone(String c_phone) {
        this.c_phone = c_phone;
        this.uid = "_:" + c_phone;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public List<String> getLogins() {
        return logins;
    }

    public void setLogins(List<String> logins) {
        this.logins = logins;
    }

    public List<CellPhone> getInstallOn() {
        return InstallOn;
    }

    public void setInstallOn(List<CellPhone> installOn) {
        InstallOn = installOn;
    }

    public List<Person> getOwn_by() {
        return own_by;
    }

    public void setOwn_by(List<Person> own_by) {
        this.own_by = own_by;
    }

    public static PhoneNumber generatePhoneNumber() {
        Faker faker = new Faker(Locale.CHINA);
        String c_phone = Utils.getRandomTelephone();
        String imsi = faker.code().imei();
        List<CellPhone> InstallOn = CellPhone.getRandomPhone(faker.number().numberBetween(1,3));
        List<Person> own_by = Stream.generate(() -> Person.generateOnePersonIdcard()).limit(faker.number().numberBetween(1, 5)).collect(Collectors.toList());
        PhoneNumber phoneNumber = new PhoneNumber();
        phoneNumber.setC_phone(c_phone);
        phoneNumber.setImsi(imsi);
        phoneNumber.setInstallOn(InstallOn);
        phoneNumber.setOwn_by(own_by);
        return phoneNumber;
    }
}
