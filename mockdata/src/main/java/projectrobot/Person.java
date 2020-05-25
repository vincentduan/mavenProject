package projectrobot;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.github.javafaker.Faker;

import java.io.*;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Person {
    @JSONField(name="idcard")
    private String idcard;
    @JSONField(name="dgraph.type")
    private String type = "Person";
    @JSONField(name="uid")
    private String uid;
    @JSONField(name="name")
    private List<String> name;
    @JSONField(name="age")
    private List<Integer> age;
    @JSONField(name="active_province")
    private List<String> active_province;
    @JSONField(name="active_city")
    private List<String> active_city;
    @JSONField(name="residenty_country")
    private List<String> residenty_country;
    @JSONField(name="workplace")
    private List<String> workplace;
    @JSONField(name="residence")
    private List<String> residence;
    @JSONField(name="lang")
    private List<String> lang;
    @JSONField(name="own_cellphone")
    private List<CellPhone> own_cellphone;
    @JSONField(name="own_phone_number")
    private List<PhoneNumber> own_phone_number;
    @JSONField(name="friends_relatives")
    private List<Person> friends_relatives;
    @JSONField(name="son")
    private List<Person> son;
    @JSONField(name="uncle")
    private List<Person> uncle;
    @JSONField(name="elder_brother")
    private List<Person> elder_brother;
    @JSONField(name="daughter")
    private List<Person> daughter;
    @JSONField(name="grandmother")
    private List<Person> grandmother;
    @JSONField(name="mother")
    private List<Person> mother;
    @JSONField(name="younger_sister")
    private List<Person> younger_sister;
    @JSONField(name="elder_sister")
    private List<Person> elder_sister;
    @JSONField(name="grandma")
    private List<Person> grandma;
    @JSONField(name="grandpa")
    private List<Person> grandpa;
    @JSONField(name="younger_brother")
    private List<Person> younger_brother;
    @JSONField(name="grandfather")
    private List<Person> grandfather;
    @JSONField(name="father")
    private List<Person> father;
    @JSONField(name="husband")
    private List<Person> husband;
    @JSONField(name="wife")
    private List<Person> wife;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public String getCSVFormat() {
        return idcard + "\t" + active_city+ "\t" + name + "\t" + age + "\t" + lang + "\n";
    }

    public void setUid(String uid) {
        this.uid = "_:" + idcard;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<Integer> getAge() {
        return age;
    }

    public void setAge(List<Integer> age) {
        this.age = age;
    }

    public List<String> getActive_province() {
        return active_province;
    }

    public void setActive_province(List<String> active_province) {
        this.active_province = active_province;
    }

    public List<String> getActive_city() {
        return active_city;
    }

    public void setActive_city(List<String> active_city) {
        this.active_city = active_city;
    }

    public List<String> getResidenty_country() {
        return residenty_country;
    }

    public void setResidenty_country(List<String> residenty_country) {
        this.residenty_country = residenty_country;
    }

    public List<String> getWorkplace() {
        return workplace;
    }

    public void setWorkplace(List<String> workplace) {
        this.workplace = workplace;
    }

    public List<String> getResidence() {
        return residence;
    }

    public void setResidence(List<String> residence) {
        this.residence = residence;
    }

    public List<String> getLang() {
        return lang;
    }

    public void setLang(List<String> lang) {
        this.lang = lang;
    }

    public List<CellPhone> getOwn_cellphone() {
        return own_cellphone;
    }

    public void setOwn_cellphone(List<CellPhone> own_cellphone) {
        this.own_cellphone = own_cellphone;
    }

    public List<PhoneNumber> getOwn_phone_number() {
        return own_phone_number;
    }

    public void setOwn_phone_number(List<PhoneNumber> own_phone_number) {
        this.own_phone_number = own_phone_number;
    }

    public List<Person> getFriends_relatives() {
        return friends_relatives;
    }

    public void setFriends_relatives(List<Person> friends_relatives) {
        this.friends_relatives = friends_relatives;
    }

    public List<Person> getSon() {
        return son;
    }

    public void setSon(List<Person> son) {
        this.son = son;
    }

    public List<Person> getUncle() {
        return uncle;
    }

    public void setUncle(List<Person> uncle) {
        this.uncle = uncle;
    }

    public List<Person> getElder_brother() {
        return elder_brother;
    }

    public void setElder_brother(List<Person> elder_brother) {
        this.elder_brother = elder_brother;
    }

    public List<Person> getDaughter() {
        return daughter;
    }

    public void setDaughter(List<Person> daughter) {
        this.daughter = daughter;
    }

    public List<Person> getGrandmother() {
        return grandmother;
    }

    public void setGrandmother(List<Person> grandmother) {
        this.grandmother = grandmother;
    }

    public List<Person> getMother() {
        return mother;
    }

    public void setMother(List<Person> mother) {
        this.mother = mother;
    }

    public List<Person> getYounger_sister() {
        return younger_sister;
    }

    public void setYounger_sister(List<Person> younger_sister) {
        this.younger_sister = younger_sister;
    }

    public List<Person> getElder_sister() {
        return elder_sister;
    }

    public void setElder_sister(List<Person> elder_sister) {
        this.elder_sister = elder_sister;
    }

    public List<Person> getGrandma() {
        return grandma;
    }

    public void setGrandma(List<Person> grandma) {
        this.grandma = grandma;
    }

    public List<Person> getGrandpa() {
        return grandpa;
    }

    public void setGrandpa(List<Person> grandpa) {
        this.grandpa = grandpa;
    }

    public List<Person> getYounger_brother() {
        return younger_brother;
    }

    public void setYounger_brother(List<Person> younger_brother) {
        this.younger_brother = younger_brother;
    }

    public List<Person> getGrandfather() {
        return grandfather;
    }

    public void setGrandfather(List<Person> grandfather) {
        this.grandfather = grandfather;
    }

    public List<Person> getFather() {
        return father;
    }

    public void setFather(List<Person> father) {
        this.father = father;
    }

    public List<Person> getHusband() {
        return husband;
    }

    public void setHusband(List<Person> husband) {
        this.husband = husband;
    }

    public List<Person> getWife() {
        return wife;
    }

    public void setWife(List<Person> wife) {
        this.wife = wife;
    }

    public Person() {
    }

    @Override
    public String toString() {
        return "Person{" +
                "idcard='" + idcard + '\'' +
                ", type='" + type + '\'' +
                ", uid='" + uid + '\'' +
                ", name=" + name +
                ", age=" + age +
                ", active_province=" + active_province +
                ", active_city=" + active_city +
                ", residenty_country=" + residenty_country +
                ", workplace=" + workplace +
                ", residence=" + residence +
                ", lang=" + lang +
                ", own_cellphone=" + own_cellphone +
                ", own_phone_number=" + own_phone_number +
                ", friends_relatives=" + friends_relatives +
                ", son=" + son +
                ", uncle=" + uncle +
                ", elder_brother=" + elder_brother +
                ", daughter=" + daughter +
                ", grandmother=" + grandmother +
                ", mother=" + mother +
                ", younger_sister=" + younger_sister +
                ", elder_sister=" + elder_sister +
                ", grandma=" + grandma +
                ", grandpa=" + grandpa +
                ", younger_brother=" + younger_brother +
                ", grandfather=" + grandfather +
                ", father=" + father +
                ", husband=" + husband +
                ", wife=" + wife +
                '}';
    }

    public Person(String idcard) {
        this.idcard = idcard;
        this.uid = "_:" + idcard;
    }

    public static Person generateOnePersonIdcard() {
        String idcard = Utils.getRandomIdcard();
        return new Person(idcard);
    }

    public Person generatePerson() {
        Faker faker = new Faker(Locale.CHINA);
        Person person = new Person();
        String idcard = faker.idNumber().valid();
        List<String> name = Stream.generate(() -> faker.name().name()).limit(faker.number().numberBetween(1, 2)).collect(Collectors.toList());
        List<Integer> age = Stream.generate(() -> faker.number().numberBetween(20,80)).limit(faker.number().numberBetween(1, 2)).collect(Collectors.toList());
        List<String> active_province = Stream.generate(() -> faker.address().state()).limit(faker.number().numberBetween(1,3)).collect(Collectors.toList());
        List<String> active_city = Stream.generate(() -> faker.address().cityName()).limit(faker.number().numberBetween(1,3)).collect(Collectors.toList());
        List<String> residenty_country = Stream.generate(() -> faker.address().country()).limit(faker.number().numberBetween(1,3)).collect(Collectors.toList());
        List<String> residence = Stream.generate(() -> faker.address().streetAddress()).limit(faker.number().numberBetween(1,3)).collect(Collectors.toList());
        List<String> workplace = Stream.generate(() -> faker.address().streetAddress()).limit(faker.number().numberBetween(1,3)).collect(Collectors.toList());
        List<String> lang = Stream.generate(() -> faker.nation().language()).limit(faker.number().numberBetween(1,3)).collect(Collectors.toList());
        List<CellPhone> own_cellphone = CellPhone.getRandomPhone(faker.number().numberBetween(1,3));
        // List<PhoneNumber> own_phone_number = Stream.generate(() -> Utils.getRandomTelephone()).limit(faker.number().numberBetween(1,3)).collect(Collectors.toList());
        List<PhoneNumber> own_phone_number = null;
        List<Person> friends_relatives = Stream.generate(() -> generateOnePersonIdcard()).limit(faker.number().numberBetween(2,5)).collect(Collectors.toList());
        List<Person> son = Stream.generate(() -> generateOnePersonIdcard()).limit(faker.number().numberBetween(2,5)).collect(Collectors.toList());
        List<Person> uncle = Stream.generate(() -> generateOnePersonIdcard()).limit(faker.number().numberBetween(2,5)).collect(Collectors.toList());
        List<Person> elder_brother = Stream.generate(() -> generateOnePersonIdcard()).limit(faker.number().numberBetween(2,5)).collect(Collectors.toList());
        List<Person> daughter = Stream.generate(() -> generateOnePersonIdcard()).limit(faker.number().numberBetween(2,5)).collect(Collectors.toList());
        List<Person> grandmother = Stream.generate(() -> generateOnePersonIdcard()).limit(faker.number().numberBetween(2,5)).collect(Collectors.toList());
        List<Person> mother = Stream.generate(() -> generateOnePersonIdcard()).limit(faker.number().numberBetween(2,5)).collect(Collectors.toList());
        List<Person> younger_sister = Stream.generate(() -> generateOnePersonIdcard()).limit(faker.number().numberBetween(2,5)).collect(Collectors.toList());
        List<Person> elder_sister = Stream.generate(() -> generateOnePersonIdcard()).limit(faker.number().numberBetween(2,5)).collect(Collectors.toList());
        List<Person> grandma = Stream.generate(() -> generateOnePersonIdcard()).limit(faker.number().numberBetween(2,5)).collect(Collectors.toList());
        List<Person> grandpa = Stream.generate(() -> generateOnePersonIdcard()).limit(faker.number().numberBetween(2,5)).collect(Collectors.toList());
        List<Person> younger_brother = Stream.generate(() -> generateOnePersonIdcard()).limit(faker.number().numberBetween(2,5)).collect(Collectors.toList());
        List<Person> grandfather = Stream.generate(() -> generateOnePersonIdcard()).limit(faker.number().numberBetween(2,5)).collect(Collectors.toList());
        List<Person> father = Stream.generate(() -> generateOnePersonIdcard()).limit(faker.number().numberBetween(2,5)).collect(Collectors.toList());
        List<Person> husband = Stream.generate(() -> generateOnePersonIdcard()).limit(faker.number().numberBetween(2,5)).collect(Collectors.toList());
        List<Person> wife = Stream.generate(() -> generateOnePersonIdcard()).limit(faker.number().numberBetween(2,5)).collect(Collectors.toList());
        person.setIdcard(idcard);
        person.setName(name);
        person.setAge(age);
        person.setActive_province(active_province);
        person.setActive_city(active_city);
        person.setResidenty_country(residenty_country);
        person.setWorkplace(workplace);
        person.setResidence(residence);
        person.setLang(lang);
        person.setOwn_cellphone(own_cellphone);
        person.setOwn_phone_number(own_phone_number);
        person.setFriends_relatives(friends_relatives);
        person.setSon(son);
        person.setUncle(uncle);
        person.setElder_brother(elder_brother);
        person.setDaughter(daughter);
        person.setGrandmother(grandmother);
        person.setMother(mother);
        person.setYounger_sister(younger_sister);
        person.setElder_sister(elder_sister);
        person.setGrandma(grandma);
        person.setGrandpa(grandpa);
        person.setYounger_brother(younger_brother);
        person.setGrandfather(grandfather);
        person.setFather(father);
        person.setHusband(husband);
        person.setWife(wife);
        return person;
    }

    public static List<Person> getPerson(int number) {
        Person person = new Person();
        return Stream.generate(() -> person.generatePerson()).limit(number).collect(Collectors.toList());
    }

    public static void main(String[] args) throws IOException {
        List<Person> person = getPerson(50);
        String string = JSON.toJSONString(person);
        File file = new File("E:/test/people.random");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(string.getBytes());
    }

}
