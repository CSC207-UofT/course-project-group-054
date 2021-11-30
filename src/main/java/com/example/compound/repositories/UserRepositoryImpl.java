package com.example.compound.repositories;

import com.example.compound.entities.User;
import com.example.compound.exceptions.UserAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;


@Repository
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public UserRepositoryImpl() {
        this.jdbcTemplate = new JdbcTemplate();
    }

//    private static final String SQL_CREATE_USER = "INSERT INTO users(USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD) VALUES(NEXTVAL('ET_USERS_SEQ'), ?, ?, ?, ?)";
//    private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM ET_USERS WHERE EMAIL = ?";
//    private static final String SQL_FIND_BY_ID = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD " +
//            "FROM users WHERE USER_ID = ?";
//    private static final String SQL_FIND_BY_EMAIL = "SELECT USER_ID, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD " +
//            "FROM ET_USERS WHERE EMAIL = ?";


    private static final String SQL_CREATE_USER = "INSERT INTO users(uuid, name, email, username, password, expenses, " +
            "balance) VALUES(NEXTVAL('users_seq'), ?, ?, ?, ?, '{}', 0)";
    private static final String SQL_GET_USER_BY_UUID = "SELECT * FROM users WHERE uuid = ?";



    @Override
    public Integer create(String name, String email, String username, String password) throws UserAuthException {
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE_USER, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, username);
                ps.setString(4, password);
                return ps;
            }, keyHolder);
            return (Integer) keyHolder.getKeys().get("uuid");
        } catch (Exception ignored) { }
        return 0;
    }

    @Override
    public User findByEmailAndPassword(String email, String password) throws UserAuthException {
        return null;
    }

    public List<Map<String, Object>> listAllUsers() {
        return jdbcTemplate.queryForList("SELECT * FROM users");
    }

    @Override
    public Integer getCountByEmail(String email) {
        return null;
    }

    @Override
    public User findById(int id) {
        System.out.println("I'm here" + id);
        // changed from: return jdbcTemplate.queryForObject(SQL_GET_USER_BY_UUID, new Object[]{id}, userRowMapper);
        return jdbcTemplate.queryForObject(SQL_GET_USER_BY_UUID, userRowMapper, id); // TODO: Is this right?
    }

    private final RowMapper<User> userRowMapper = ((rs, rowNum) -> new User(
            rs.getInt("uuid"),
            rs.getString("name"),
            rs.getString("email"),
            rs.getString("username"),
            rs.getDouble("balance"),
            rs.getString("password")));
}
