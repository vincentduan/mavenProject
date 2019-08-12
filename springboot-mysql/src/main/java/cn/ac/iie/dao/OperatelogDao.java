package cn.ac.iie.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author vincent
 * @time 2019-08-03 15:55
 */
@Repository
public class OperatelogDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addOperatelog(int id) {
        String sql = "insert into operatelog (id) values (" + id + ")";
        jdbcTemplate.execute(sql);
    }

    public void addBatch() {
        String sql = "insert into operatelog (id, userId, operateDesc) values (?,?,?)";
        List<Object[]> batchArgs = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            batchArgs.add(new Object[]{3 + i, "001" + (i % 10), "test_1" + (i % 5)});
        }
        jdbcTemplate.batchUpdate(sql, batchArgs);
    }

    public List<String> queryDirection() {
        String sql = "select operateDesc from operatelog group by operateDesc";
        List<String> directions = jdbcTemplate.queryForList(sql, String.class);
        return directions;
    }

    public List<String> getUserIdByDirection(String direction) {
        String sql = "select distinct(userId) from operatelog where operateDesc=? group by userId;";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, new Object[]{direction});
        List<String> uids = new ArrayList();
        for (Map<String, Object> map : maps) {
            uids.add(map.get("userId").toString());
        }
        return uids;
    }
}
