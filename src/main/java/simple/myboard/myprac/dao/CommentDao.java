package simple.myboard.myprac.dao;

import simple.myboard.myprac.vo.CommentVO;

import java.util.List;

public interface CommentDao {

    public abstract void insertComment(CommentVO comment);
    public abstract CommentVO getCommentBySeq(int commentSeq);

    public abstract void updateComment(CommentVO comment);
    public abstract void deleteCommentBySeq(int commentSeq);
    public abstract int getLastIndexComment();
    public abstract List<CommentVO> getCommentListByMemberSeq(int memberSeq);
    public abstract List<CommentVO> getCommentListByArticleSeq(int article);
    public abstract void deleteAllComment();
    public abstract void deleteAllCommentByMemberSeq(int memberSeq);
    public abstract void deleteAllCommentByArticleSeq(int articleSeq);
    public abstract int getCountAllComment();
    public abstract int getCountAllCommentByMemberSeq(int memberSeq);
    public abstract int getCountAllCommentByArticleSeq(int articleSeq);
}
