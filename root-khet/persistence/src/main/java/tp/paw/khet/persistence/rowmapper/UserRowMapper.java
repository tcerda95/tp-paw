package tp.paw.khet.persistence.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import tp.paw.khet.model.User;

@Component
public class UserRowMapper implements RowMapper<User> {
	public User mapRow(final ResultSet rs, final int rowNum) throws SQLException {
		return new User(rs.getString("userName"), rs.getString("email"), rs.getString("password"), rs.getBytes("profilePicture"));
	}
}
