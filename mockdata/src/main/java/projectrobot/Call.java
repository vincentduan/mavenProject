package projectrobot;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.github.javafaker.Faker;

import java.util.Date;
import java.util.Locale;

public class Call {
    @JSONField(name="start_time")
    private String start_time;
    @JSONField(name="end_time")
    private String end_time;
    @JSONField(name="from")
    private PhoneNumber from;
    @JSONField(name="to")
    private PhoneNumber to;

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public PhoneNumber getFrom() {
        return from;
    }

    public void setFrom(PhoneNumber from) {
        this.from = from;
    }

    public PhoneNumber getTo() {
        return to;
    }

    public void setTo(PhoneNumber to) {
        this.to = to;
    }

    public Call(String start_time, String end_time, PhoneNumber from, PhoneNumber to) {
        this.start_time = start_time;
        this.end_time = end_time;
        this.from = from;
        this.to = to;
    }

    public Call() {
    }



    public static Call generateCall() {
        Faker faker = new Faker(Locale.CHINA);
        Date startTime = faker.date().birthday();
        String endTime = Utils.timeAddSecond(startTime, faker.number().numberBetween(2, 100));
        PhoneNumber from = PhoneNumber.generatePhoneNumber();
        PhoneNumber to = PhoneNumber.generatePhoneNumber();
        Call call = new Call();
        call.setStart_time(Utils.date2String(startTime));
        call.setEnd_time(endTime);
        call.setFrom(from);
        call.setTo(to);
        return call;
    }

    public static void main(String[] args) {
        System.out.println(JSON.toJSONString(generateCall()));
    }


}
