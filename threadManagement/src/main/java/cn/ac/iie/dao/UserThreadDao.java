package cn.ac.iie.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class UserThreadDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void add(String username, int threadId, String thread_status) {
        String sql = "insert into userthread (username, thread_id, thread_status) values (?,?,?)";
        jdbcTemplate.update(sql, new Object[]{username, threadId, thread_status});
        log.info("threadId:{}, jdbcTemplate:{}", threadId, jdbcTemplate);
    }

    public void update(String username, int threadId, String thread_status) {
        String sql = "update userthread set thread_status=? where username=? and thread_id=?";
        jdbcTemplate.update(sql, new Object[]{thread_status, username, threadId});
    }

}
