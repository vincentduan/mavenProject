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
}
