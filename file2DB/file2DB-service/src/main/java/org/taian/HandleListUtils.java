package org.taian;

import org.ExtractUtils.ExtractFromSentence;
import org.service.ExtractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: vincent
 * Date: 2017/11/27
 * Time: 11:12
 */
public class HandleListUtils {

    private final  Logger logger = LoggerFactory.getLogger(HandleListUtils.class);

    public void handleList(List<String> list, ExtractService extractService){
        List<String[]> cf1c18_list = new LinkedList<>();
        List<String[]> cf1c19_list = new LinkedList<>();
        List<String[]> cf1c28_list = new LinkedList<>();
        List<String[]> cf1c32_list = new LinkedList<>();
        List<String[]> cf1c39_list = new LinkedList<>();
        List<String[]> cf1c50_list = new LinkedList<>();
        for(String str: list){
            ExtractFromSentence extractFromSentence = new ExtractFromSentence(str);
            String[] cf1c18 = extractFromSentence.tblcf1c18();
            String[] cf1c19 = extractFromSentence.tblcf1c19();
            String[] cf1c28 = extractFromSentence.tblcf1c28();
            String[] cf1c32 = extractFromSentence.tblcf1c32();
            String[] cf1c39 = extractFromSentence.tblcf1c39();
            String[] cf1c50 = extractFromSentence.tblcf1c50();
            if (cf1c18[0] != null) {
                cf1c18_list.add(extractFromSentence.tblcf1c18());

            }
            if (cf1c19[0] != null) {
                cf1c19_list.add(extractFromSentence.tblcf1c19());

            }
            if (cf1c28[0] != null) {
                cf1c28_list.add(extractFromSentence.tblcf1c28());

            }
            if (cf1c32[0] != null) {
                cf1c32_list.add(extractFromSentence.tblcf1c32());

            }
            if (cf1c39[0] != null) {
                cf1c39_list.add(extractFromSentence.tblcf1c39());

            }
            if (cf1c50[0] != null) {
                cf1c50_list.add(extractFromSentence.tblcf1c50());

            }
        }
        if(cf1c18_list.size()>0){
            Collections.sort(cf1c18_list, Comparator.comparing(o -> o[0]));
            cf1c18_list.forEach(l->logger.info(l[0]+":"+l[1]));
            extractService.tblcf1c18(cf1c18_list);
            //logger.info("c18 1000===============");
        }
        if(cf1c19_list.size()>0){
            Collections.sort(cf1c19_list, Comparator.comparing(o -> o[0]));
            extractService.tblcf1c19(cf1c19_list);
            //logger.info("c19 1000===============");
        }
        if(cf1c28_list.size()>0){
            Collections.sort(cf1c28_list, Comparator.comparing(o -> o[0]));
            extractService.tblcf1c28(cf1c28_list);
            //logger.info("c28 1000===============");
        }
        if(cf1c32_list.size()>0){
            Collections.sort(cf1c32_list, Comparator.comparing(o -> o[0]));
            extractService.tblcf1c32(cf1c32_list);
            //logger.info("c32 1000===============");
        }
        if(cf1c39_list.size()>0){
            Collections.sort(cf1c39_list, Comparator.comparing(o -> o[0]));
            extractService.tblcf1c39(cf1c39_list);
            //logger.info("c39 1000===============");
        }
        if(cf1c50_list.size()>0){
            Collections.sort(cf1c50_list, Comparator.comparing(o -> o[0]));
            extractService.tblcf1c50(cf1c50_list);
            //logger.info("c50 1000===============");
        }
    }

}
