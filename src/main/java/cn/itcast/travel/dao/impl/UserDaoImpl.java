package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findByUsername(String username) {
        //1.定义sql
        User user = null;
        try {
            String sql = "select * from tab_user where username = ?";
            user = template.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<User>(User.class),
                    username);
        } catch (DataAccessException e) {
//            e.printStackTrace();
        }

        return user;
    }

    @Override
    public User findByCode(String code) {
        User user = null;
        try {
            String sql = "select * from tab_user where code = ?";
            user = template.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<User>(User.class),
                    code);
        } catch (DataAccessException e) {
//            e.printStackTrace();
        }

        return user;
    }

    public User findByUsernameAndPassword(User user) {
        User u = null;
        String sql = "select uid,name,status from tab_user where username=? and password=?";
        try {
            u = template.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<User>(User.class),
                    user.getUsername(),user.getPassword());
        } catch (DataAccessException e) {
//            e.printStackTrace();
        }

        return u;

    }

    @Override
    public void save(User user) {
        //1.定义sql
        String sql = "insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code)" +
                "value(?,?,?,?,?,?,?,?,?)";
        template.update(sql,user.getUsername(),user.getPassword(),user.getName(),user.getBirthday(),user.getSex(),
                user.getTelephone(),user.getEmail(),user.getStatus(),user.getCode());
    }

    @Override
    public void updateStatus(User user) {
        //1.定义sql
        String sql = "update tab_user set status = 'Y' where uid = ?";
        template.update(sql,user.getUid());
    }

}
