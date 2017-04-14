package tp.paw.khet.persistence;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import tp.paw.khet.Comment;
import tp.paw.khet.CommentAndCommenter;
import tp.paw.khet.persistence.rowmapper.CommentAndCommenterRowMapper;

@Repository
public class CommentJdbcDao implements CommentDao {	
	@Autowired
	private CommentAndCommenterRowMapper commentAndCommenterRowMapper;
	
	private JdbcTemplate jdbcTemplate;
	private final SimpleJdbcInsert jdbcInsert;
	
	@Autowired
	public CommentJdbcDao(final DataSource ds) {
		jdbcTemplate = new JdbcTemplate(ds);
		jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
					.withTableName("comments")
					.usingGeneratedKeyColumns("commentid");		
	}

	@Override
	public List<CommentAndCommenter> getCommentsByProductId(int id) {
		List<CommentAndCommenter> comments = jdbcTemplate.query("SELECT * FROM comments NATURAL JOIN users WHERE productId = ? ORDER BY parentId NULLS FIRST, commentDate ASC", 
				commentAndCommenterRowMapper, id);
		return comments;	
	}
	
	@Override
	public Comment createComment(String content, LocalDateTime date, Integer parentId, int productId, int userId) {
		final Map<String, Object> args = new HashMap<String, Object>();
		args.put("commentContent", content);
		args.put("commentDate", Timestamp.valueOf(date));
		args.put("userId", userId);
		args.put("productId", productId);
		args.put("parentId", parentId);
		
		final Number commentId = jdbcInsert.executeAndReturnKey(args);

		return new Comment(commentId.intValue(), parentId, content, date);
	}

}
