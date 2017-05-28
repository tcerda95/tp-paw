package tp.paw.khet.model;

import static org.apache.commons.lang3.Validate.isTrue;
import static org.apache.commons.lang3.Validate.notNull;
import static org.apache.commons.lang3.Validate.notBlank;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

public class Comment {
	private static final int NO_PARENT_ID = -1;
	
	private final int id;
	private final int parentId;
	private final String content;
	private final LocalDateTime commentDate;
	private final User author;
	
	public Comment(int id, User author, String content, LocalDateTime date) {
		this(id, NO_PARENT_ID, author, content, date, true);
	}
	
	public Comment(int id, int parentId, User author, String content, LocalDateTime date) {
		this(id, parentId, author, content, date, false);
	}
	
	private Comment(int id, int parentId, User author, String content, LocalDateTime date, boolean isParentId) {
		isTrue(id >= 0, "Comment ID must be non negative: %d", id);
		isTrue(parentId >= 0 || (parentId == NO_PARENT_ID && isParentId), "Parent comment ID must be non negative: %d", parentId);
		
		this.id = id;
		this.parentId = parentId;
		this.author = notNull(author, "Author of the comment cannot be null");
		this.content = notBlank(content, "Content must have at least one non blank character");
		this.commentDate = notNull(date, "Comment date cannot be null");		
	}
	
	public int getId() {
		return id;
	}
	
	public int getParentId() {
		if (!hasParent())
			throw new NoSuchElementException("Root comment has no parent");
		return parentId;
	}
	
	public String getContent() {
		return content;
	}
	
	public User getAuthor() {
		return author;
	}
	
	public LocalDateTime getCommentDate() {
		return commentDate;
	}
	
	public boolean hasParent() {
		return this.parentId != NO_PARENT_ID;
	}
	
	@Override
	public int hashCode() {
		return id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Comment))
			return false;
		
		Comment other = (Comment) obj;
		return getId() == other.getId();
	}

	@Override
	public String toString() {
		return content;
	}
}
