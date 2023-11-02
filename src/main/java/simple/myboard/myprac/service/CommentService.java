package simple.myboard.myprac.service;

import simple.myboard.myprac.dao.CommentDao;
import simple.myboard.myprac.domain.Comment;

import java.util.List;

public interface CommentService {

    public abstract void setCommentDao(CommentDao commentDao);
    public abstract void addComment(Comment comment);
    public abstract Comment getCommentBySeq(int commentSeq);
    public abstract void updateComment(Comment comment);
    public abstract void deleteCommentBySeq(int commentSeq);
    public abstract int getLastIndexComment();
    public abstract List<Comment> getCommentListByMemberSeq(Long memberSeq);
    public abstract List<Comment> getCommentListByArticleSeq(Long articleSeq);
    public abstract void deleteAllComment();
    public abstract void deleteAllCommentByMemberSeq(Long memberSeq);
    public abstract void deleteAllCommentByArticleSeq(Long articleSeq);
    public abstract int getCountAllComment();
    public abstract int getCountAllCommentByMemberSeq(Long memberSeq);
    public abstract int getCountAllCommentByArticleSeq(Long articleSeq);
}
