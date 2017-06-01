package tp.paw.khet.model;

import static org.junit.Assert.assertEquals;
import static tp.paw.khet.model.UserTestUtils.*;
import static tp.paw.khet.model.ProductTestUtils.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tp.paw.khet.model.Comment;

public final class CommentTestUtils {

	private CommentTestUtils() {
	}
	
	public static Comment dummyParentComment(int commentId, int authorId, int productId) {
		LocalDateTime ldt = LocalDateTime.now().plusSeconds(commentId);
		return new Comment(dummyUser(authorId), dummyProduct(productId), "Content " + commentId, Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant())).setCommentId(commentId);
	}
	
	public static Comment dummyComment(int commentId, Comment parent, int authorId, int productId) {
		LocalDateTime ldt = LocalDateTime.now().plusSeconds(commentId);
		return new Comment(parent, dummyUser(authorId), dummyProduct(productId), "Content " + commentId, Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant())).setCommentId(commentId);
	}
	
	public static List<Comment> dummyParentCommentList(int size, int initialId, int authorId, int productId) {
		List<Comment> list = new ArrayList<>(size);
		
		for (int i = 0; i < size; i++)
			list.add(dummyParentComment(initialId + i, authorId, productId));

		return list;
	}

	public static List<Comment> dummyCommentList(int size, int initialId, Comment parent, int authorId, int productId) {
		List<Comment> list = new ArrayList<>(size);
		
		for (int i = 0; i < size; i++)
			list.add(dummyComment(initialId + i, parent, authorId, productId));

		return list;
	}
	
	public static void assertEqualsComments(Comment expected, Comment actual) {
		assertEqualsUsers(expected.getAuthor(), actual.getAuthor());
		assertEquals(expected.hasParent(), actual.hasParent());
		if (expected.hasParent())
			assertEquals(expected.getParent(), actual.getParent());
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getContent(), actual.getContent());
		assertEquals(expected, actual);
	}	
}
