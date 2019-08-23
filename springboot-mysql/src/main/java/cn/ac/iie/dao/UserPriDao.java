package cn.ac.iie.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class UserPriDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<String> getPriByUserId(String userId) {
        String sql = "select priId from userPri where userId=?";
        List<Map<String, Object>> maps = jdbcTemplate.queryForList(sql, new Object[]{userId});
        List<String> priIds = new ArrayList();
        for (Map<String, Object> map : maps) {
            priIds.add(map.get("priId").toString());
        }
        return priIds;
    }

}
