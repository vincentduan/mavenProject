package org.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: vincent
 * Date: 2017/11/17
 * Time: 11:21
 */
public interface ExtractDao {
    void updateMysqlPerCf1C18(@Param("list")List<String[]> list);
    void updateMysqlPerCf1C19(@Param("list")List<String[]> list);
    void updateMysqlPerCf1C28(@Param("list")List<String[]> list);
    void updateMysqlPerCf1C32(@Param("list")List<String[]> list);
    void updateMysqlPerCf1C39(@Param("list")List<String[]> list);
    void updateMysqlPerCf1C50(@Param("list")List<String[]> list);

    void updateMysqlPerCf1C18ForOne(@Param("item")String[] cf1c18);

    void updateMysqlPerCf1C19ForOne(@Param("item")String[] cf1c19);

    void updateMysqlPerCf1C50ForOne(@Param("item")String[] cf1c50);

    void updateMysqlPerCf1C39ForOne(@Param("item")String[] cf1c39);

    void updateMysqlPerCf1C32ForOne(@Param("item")String[] cf1c32);

    void updateMysqlPerCf1C28ForOne(@Param("item")String[] cf1c28);
}
