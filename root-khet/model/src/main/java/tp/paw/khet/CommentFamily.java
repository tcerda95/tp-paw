package tp.paw.khet;

import java.util.Collections;
import java.util.List;

import tp.paw.khet.structures.ParentNode;

public class CommentFamily {

	private final ParentNode<Comment> parentNode;
	private final int parentId;
	
	public CommentFamily(Comment parentComment) {
		this.parentNode = new ParentNode<Comment>(parentComment);
		this.parentId = parentComment.getId();
	}
	
	public Comment getParentComment() {
		return parentNode.getParent();
	}
	
	public List<Comment> getChildComments() {
		return Collections.unmodifiableList(parentNode.getChildren());
	}
	
	public void addChildComment(Comment child) {
		if (child.getParentId() != parentId)
			throw new IllegalArgumentException("Child's ID: " + child.getId() + " should be equals to parent's ID: " + parentId);
		parentNode.addChild(child);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof CommentFamily))
			return false;
		
		CommentFamily other = (CommentFamily) obj;
		
		return other.parentId == this.parentId && other.parentNode.equals(parentNode);
	}
	
	@Override
	public int hashCode() {
		return parentNode.hashCode();
	}
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder(parentNode.getParent().toString());
		for (Comment childComment : parentNode.getChildren())
			stringBuilder.append('\t').append(childComment.toString()).append('\n');
		return stringBuilder.toString();
	}
}
