package by.bntu.smartlibrary.dao;

import by.bntu.smartlibrary.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<List<User>> findAll() {
        String sql = "SELECT *  FROM usr";
        return Optional.of(jdbcTemplate.query(sql, new UserRowMapper()));
    }

    @Override
    public Optional<User> update(User user) {
        String sql = "UPDATE usr SET username = ?, password = ?, email = ?, active = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getEmail(), user.isActive(), user.getId());
        return Optional.of(jdbcTemplate.queryForObject("SELECT * FROM usr WHERE id = ?", new Object[]{user.getId()},new UserRowMapper()));
    }

    @Override
    public Optional<User> save(User user) {
        String sql = "INSERT INTO usr (username, password, email) VALUES (?, ?, ?) RETURNING id";
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, user.getUsername());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getEmail());
                return preparedStatement;
            }
        }, holder);
        long primaryKey = holder.getKey().longValue();
        return findById(primaryKey);
    }

    @Override

    public Optional<User> findById(Long id) {
        String sql = "SELECT * FROM usr WHERE id = ?";
        return Optional.of(jdbcTemplate.queryForObject(sql, new Object[]{id},new UserRowMapper()));
    }


    private class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setUsername(resultSet.getString("username"));
            user.setPassword(resultSet.getString("password"));
            user.setId(resultSet.getLong("id"));
            user.setEmail(resultSet.getString("email"));
            user.setActive(resultSet.getBoolean("active"));
            return user;
        }
    }
}
