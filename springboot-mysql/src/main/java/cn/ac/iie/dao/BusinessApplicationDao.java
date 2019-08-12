package cn.ac.iie.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author vincent
 * @time 2019-08-06 17:23
 */
@Repository
public class BusinessApplicationDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void batch() {
        String sql = "insert into businessapplication (id, userId, orgName, businessdirection, dutydesc) values (?,?,?,?,?)";
        List<Object[]> batchArgs = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            batchArgs.add(new Object[]{i, "001" + (i % 10), "org_" + (i % 5), "direction_" + (i % 10), "duty_" + (i % 20)});
        }
        jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    public List<String> getUserByDirection(String direction) {
        String sql = "select distinct(userId) from businessapplication where businessDirection=? ;";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, new Object[]{direction});
        List<String> uids = new ArrayList();
        for (Map<String, Object> map : maps) {
            uids.add(map.get("userId").toString());
        }
        return uids;
    }

    public List<String> getUserByOrgName(String orgName) {
        String sql = "select distinct(userId) from businessapplication where orgName=? ;";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, new Object[]{orgName});
        List<String> uids = new ArrayList();
        for (Map<String, Object> map : maps) {
            uids.add(map.get("userId").toString());
        }
        return uids;
    }

    public List<String> getUserByDutyDesc(String dutyDesc) {
        String sql = "select distinct(userId) from businessapplication where dutydesc=? ;";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, new Object[]{dutyDesc});
        List<String> uids = new ArrayList();
        for (Map<String, Object> map : maps) {
            uids.add(map.get("userId").toString());
        }
        return uids;
    }
}
