package simple.myboard.myprac.serviceimpl;

import org.springframework.dao.EmptyResultDataAccessException;
import simple.myboard.myprac.dao.CommentDao;
import simple.myboard.myprac.service.CommentService;
import simple.myboard.myprac.domain.Comment;

import java.util.List;

public class CommentServiceImpl implements CommentService {

    private CommentDao commentDao;

    @Override
    public void setCommentDao(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    @Override
    public void addComment(Comment comment) {
        this.commentDao.insertComment(comment);
    }

    @Override
    public Comment getCommentBySeq(int commentSeq) {
        try {
            return this.commentDao.getCommentBySeq(commentSeq);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public void updateComment(Comment comment) {
        this.commentDao.updateComment(comment);
    }

    @Override
    public void deleteCommentBySeq(int commentSeq) {
        this.commentDao.deleteCommentBySeq(commentSeq);
    }

    @Override
    public int getLastIndexComment() {
        return this.commentDao.getLastIndexComment();
    }

    @Override
    public List<Comment> getCommentListByMemberSeq(Long memberSeq) {
        List<Comment> commentList = this.commentDao.getCommentListByMemberSeq(memberSeq);
        if(commentList.size() < 1) {
            return null;
        }
        return commentList;
    }

    @Override
    public List<Comment> getCommentListByArticleSeq(Long articleSeq) {
        List<Comment> commentList = this.commentDao.getCommentListByArticleSeq(articleSeq);
        if(commentList.size() < 1) {
            return null;
        }
        return commentList;
    }

    @Override
    public void deleteAllComment() {
        this.commentDao.deleteAllComment();
    }

    @Override
    public void deleteAllCommentByMemberSeq(Long memberSeq) {
        this.commentDao.deleteAllCommentByMemberSeq(memberSeq);
    }

    @Override
    public void deleteAllCommentByArticleSeq(Long articleSeq) {
        this.commentDao.deleteAllCommentByArticleSeq(articleSeq);
    }

    @Override
    public int getCountAllComment() {
        return this.commentDao.getCountAllComment();
    }

    @Override
    public int getCountAllCommentByMemberSeq(Long memberSeq) {
        return this.commentDao.getCountAllCommentByMemberSeq(memberSeq);
    }

    @Override
    public int getCountAllCommentByArticleSeq(Long articleSeq) {
        return this.commentDao.getCountAllCommentByArticleSeq(articleSeq);
    }
}
