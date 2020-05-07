package projectrobot;

import com.alibaba.fastjson.annotation.JSONField;
import com.github.javafaker.Faker;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CellPhone {
    @JSONField(name="imei")
    private String imei;
    @JSONField(name="MacIS")
    private String MacIS;
    @JSONField(name="dgraph.type")
    private String type = "Cellphone";
    @JSONField(name="uid")
    private String uid;
    @JSONField(name="IOSdevidIs")
    private String IOSdevidIs;
    @JSONField(name="AndroidIdIs")
    private String AndroidIdIs;
    @JSONField(name="IdfaIs")
    private String IdfaIs;
    @JSONField(name="own_by")
    private List<Person> own_by;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getMacIS() {
        return MacIS;
    }

    public void setMacIS(String macIS) {
        MacIS = macIS;
    }

    public String getIOSdevidIs() {
        return IOSdevidIs;
    }

    public void setIOSdevidIs(String IOSdevidIs) {
        this.IOSdevidIs = IOSdevidIs;
    }

    public String getAndroidIdIs() {
        return AndroidIdIs;
    }

    public void setAndroidIdIs(String androidIdIs) {
        AndroidIdIs = androidIdIs;
    }

    public String getIdfaIs() {
        return IdfaIs;
    }

    public void setIdfaIs(String idfaIs) {
        IdfaIs = idfaIs;
    }

    public List<Person> getOwn_by() {
        return own_by;
    }

    public void setOwn_by(List<Person> own_by) {
        this.own_by = own_by;
    }

    @Override
    public String toString() {
        return "Phone{" +
                "imei='" + imei + '\'' +
                ", MacIS='" + MacIS + '\'' +
                ", IOSdevidIs='" + IOSdevidIs + '\'' +
                ", AndroidIdIs='" + AndroidIdIs + '\'' +
                ", IdfaIs='" + IdfaIs + '\'' +
                ", own_by=" + own_by +
                '}';
    }

    public CellPhone(String imei, String macIS, String IOSdevidIs, String androidIdIs, String idfaIs, List<Person> own_by) {
        this.imei = imei;
        this.uid = "_:" + imei;
        MacIS = macIS;
        this.IOSdevidIs = IOSdevidIs;
        AndroidIdIs = androidIdIs;
        IdfaIs = idfaIs;
        this.own_by = own_by;
    }

    public static List<CellPhone> getRandomPhone(int number) {
        Faker faker = new Faker(Locale.CHINA);
        return Stream.generate(() -> new CellPhone(faker.code().imei(), faker.internet().macAddress(),faker.number().numberBetween(0,1) + "",faker.number().numberBetween(0,1) + "",faker.number().numberBetween(0,1) + "", Stream.generate(()->new Person(Utils.getRandomIdcard())).limit(faker.number().numberBetween(0,3)).collect(Collectors.toList()))).limit(number).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        System.out.println(getRandomPhone(3));
    }

}
