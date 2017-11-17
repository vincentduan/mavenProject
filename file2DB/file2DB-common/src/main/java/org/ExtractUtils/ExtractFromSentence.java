package org.ExtractUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 抽取c18,c19,c28,c32,c39,c50
 * Created with IntelliJ IDEA.
 * User: vincent
 * Date: 2017/11/17
 * Time: 9:44
 */
public class ExtractFromSentence {

    public static String sentence;

    public static void main(String[] args) {
        sentence = "{ \"index\" : { \"_index\" : \"test\", \"_type\" : \"t3\", \"_id\" : \"10000000\" } }\n" +
                "{\"cf1\":\"0000008207\", \"cf2\":\"20170808023304\", \"cf3\":{\"c42\":{\"c4\":\"1100008207\"}, \"d1\":{\"c4\":\"1000004751\"}, \"c43\":\"chat\", \"d2\":\"c++\"}, \"cf4\":{\"c18\":\"466187329633641\", \"c19\":\"231899539124898\", \"c32\":\"92541820110\", \"c28\":\"202118428730\", \"c20\":\"1.5.2.8\", \"c50\":\"1100008207\"}, \"cf5\":\"5\", \"cf6\":\"t3\", \"cf7\":\"20170808\", \"cf8\":\"reg1\"}";
        String[] str = tblcf1c18();
        System.out.println("tblcf1c18:" + str[0] + "," + str[1]);
        str = tblcf1c19();
        System.out.println("tblcf1c19:" + str[0] + "," + str[1]);
        str = tblcf1c28();
        System.out.println("tblcf1c28:" + str[0] + "," + str[1]);
        str = tblcf1c32();
        System.out.println("tblcf1c32:" + str[0] + "," + str[1]);
        str = tblcf1c39();
        System.out.println("tblcf1c39:" + str[0] + "," + str[1]);
        str = tblcf1c50();
        System.out.println("tblcf1c50:" + str[0] + "," + str[1]);
    }

    public static String matchCf1(String temp){
        String reg = "\"cf1\":\"(\\d+)\"";
        Pattern p2 = Pattern.compile(reg);
        Matcher m2 = p2.matcher(temp);
        if (m2.find()) {
            String cf1 = m2.group(1);
            return cf1;
        }else{
            return "";
        }
    }

    public static String[] tblcf1c18(){
        String str = sentence;
        String[] result = new String[2];
        String reg = "\"c18\":\"(\\d+)\"";
        Pattern p1 = Pattern.compile(reg);
        Matcher m1 = p1.matcher(str);
        if (m1.find()) {
            String c18 = m1.group(1);
            String cf1 = matchCf1(str);
            if (cf1 != ""){
                result[0] = cf1;
                result[1] = c18;
            }
        }
        return result;
    }

    public static String[] tblcf1c19(){
        String str = sentence;
        String[] result = new String[2];
        String reg = "\"c19\":\"(\\d+)\"";
        Pattern p1 = Pattern.compile(reg);
        Matcher m1 = p1.matcher(str);
        if (m1.find()) {
            String c19 = m1.group(1);
            String cf1 = matchCf1(str);
            if (cf1 != ""){
                result[0] = cf1;
                result[1] = c19;
            }
        }
        return result;
    }

    public static String[] tblcf1c28(){
        String str = sentence;
        String[] result = new String[2];
        String reg = "\"c28\":\"(\\d+)\"";
        Pattern p1 = Pattern.compile(reg);
        Matcher m1 = p1.matcher(str);
        if (m1.find()) {
            String c28 = m1.group(1);
            String cf1 = matchCf1(str);
            if (cf1 != ""){
                result[0] = cf1;
                result[1] = c28;
            }
        }
        return result;
    }
    public static String[] tblcf1c32(){
        String str = sentence;
        String[] result = new String[2];
        String reg = "\"c32\":\"(\\d+)\"";
        Pattern p1 = Pattern.compile(reg);
        Matcher m1 = p1.matcher(str);
        if(m1.find()){
            String c32 = m1.group(1);
            String cf1 = matchCf1(str);
            if(cf1 != ""){
                result[0] = cf1;
                result[1] = c32;
            }
        }
        return result;
    }
    public static String[] tblcf1c39(){
        String str = sentence;
        String[] result = new String[2];
        String reg = "\"c39\":\"(\\d+)\"";
        Pattern p1 = Pattern.compile(reg);
        Matcher m1 = p1.matcher(str);
        if(m1.find()){
            String c39 = m1.group(1);
            String cf1 = matchCf1(str);
            if(cf1 != ""){
                result[0] = cf1;
                result[1] = c39;
            }
        }
        return result;
    }
    public static String[] tblcf1c50(){
        String str = sentence;
        String[] result = new String[2];
        String reg = "\"c50\":\"(\\d+)\"";
        Pattern p1 = Pattern.compile(reg);
        Matcher m1 = p1.matcher(str);
        if(m1.find()){
            String c50 = m1.group(1);
            String cf1 = matchCf1(str);
            if(cf1 != ""){
                result[0] = cf1;
                result[1] = c50;
            }
        }
        return result;
    }

}
