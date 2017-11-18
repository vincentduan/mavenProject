package org.service.impl;

import org.dao.ExtractDao;
import org.service.ExtractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vincent
 * Date: 2017/11/17
 * Time: 11:24
 */
@Service
public class ExtractServiceImpl implements ExtractService {

    @Autowired
    ExtractDao extractDao;

    public String tblcf1c18(List<String[]> list){

        extractDao.updateMysqlPerCf1C18(list);
        System.out.println("tblcf1c18 ");
        return "";
    }

    @Override
    public String tblcf1c19(List<String[]> list) {
        extractDao.updateMysqlPerCf1C19(list);
        System.out.println("tblcf1c19 ");
        return null;
    }

}
