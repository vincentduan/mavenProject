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

    @Override
    public String tblcf1c18(List<String[]> list){
        extractDao.updateMysqlPerCf1C18(list);
        return "";
    }

    @Override
    public String tblcf1c19(List<String[]> list) {
        extractDao.updateMysqlPerCf1C19(list);
        return null;
    }

    @Override
    public String tblcf1c28(List<String[]> cf1c28_list) {
        extractDao.updateMysqlPerCf1C28(cf1c28_list);
        return null;
    }

    @Override
    public String tblcf1c32(List<String[]> cf1c32_list) {
        extractDao.updateMysqlPerCf1C32(cf1c32_list);
        return null;
    }

    @Override
    public String tblcf1c39(List<String[]> cf1c39_list) {
        extractDao.updateMysqlPerCf1C39(cf1c39_list);
        return null;
    }

    @Override
    public String tblcf1c50(List<String[]> cf1c50_list) {
        extractDao.updateMysqlPerCf1C50(cf1c50_list);
        return null;
    }

    @Override
    public String tblcf1c18ForOne(String[] cf1c18) {
        extractDao.updateMysqlPerCf1C18ForOne(cf1c18);
        return null;
    }

    @Override
    public String tblcf1c19ForOne(String[] cf1c19) {
        extractDao.updateMysqlPerCf1C19ForOne(cf1c19);
        return null;
    }

    @Override
    public String tblcf1c28ForOne(String[] cf1c28) {
        extractDao.updateMysqlPerCf1C28ForOne(cf1c28);
        return null;
    }

    @Override
    public String tblcf1c32ForOne(String[] cf1c32) {
        extractDao.updateMysqlPerCf1C32ForOne(cf1c32);
        return null;
    }

    @Override
    public String tblcf1c39ForOne(String[] cf1c39) {
        extractDao.updateMysqlPerCf1C39ForOne(cf1c39);
        return null;
    }

    @Override
    public String tblcf1c50ForOne(String[] cf1c50) {
        extractDao.updateMysqlPerCf1C50ForOne(cf1c50);
        return null;
    }

}
