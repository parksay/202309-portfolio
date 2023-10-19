package simple.myboard.myprac.serviceimpl;

import org.springframework.dao.EmptyResultDataAccessException;
import simple.myboard.myprac.dao.ArticleDao;
import simple.myboard.myprac.service.ArticleService;
import simple.myboard.myprac.service.CommentService;
import simple.myboard.myprac.vo.ArticleVO;

public class ArticleServiceImpl implements ArticleService {

    private ArticleDao articleDao;
    private CommentService commentService;

    public void setArticleDao(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    public void addArticle(ArticleVO article) {
        // TODO - null check 여기서 하기
        this.articleDao.insertArticle(article);
    }

    public void updateArticle(ArticleVO article) {
        this.articleDao.updateArticle(article);
    }

    public ArticleVO getArticleBySeq(int seq) {
        try {
            return this.articleDao.getArticleBySeq(seq);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void deleteArticleBySeq(int articleSeq) {
        this.commentService.deleteAllCommentByArticleSeq(articleSeq);
        this.articleDao.deleteArticleBySeq(articleSeq);
    }

    public void deleteAllArticleByMemberSeq(int memberSeq) {
        this.commentService.deleteAllCommentByMemberSeq(memberSeq);
        this.articleDao.deleteAllArticleByMemberSeq(memberSeq);
    }

    public int getLastIndexArticle() {
        return this.articleDao.getLastIndexArticle();
    }

    public int getCountAllArticle() {
        return this.articleDao.getCountAllArticle();
    }
}
