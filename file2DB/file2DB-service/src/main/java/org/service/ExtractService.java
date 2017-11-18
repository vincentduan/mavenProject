package org.service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vincent
 * Date: 2017/11/17
 * Time: 11:20
 */
public interface ExtractService {
    String tblcf1c18(List<String[]> list);
    String tblcf1c19(List<String[]> list);

    String tblcf1c18ForOne(String[] cf1c18);

    String tblcf1c19ForOne(String[] cf1c19);

    String tblcf1c28ForOne(String[] cf1c28);

    String tblcf1c32ForOne(String[] cf1c32);

    String tblcf1c39ForOne(String[] cf1c39);

    String tblcf1c50ForOne(String[] cf1c50);
}
