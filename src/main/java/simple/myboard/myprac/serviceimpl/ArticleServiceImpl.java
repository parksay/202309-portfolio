package simple.myboard.myprac.serviceimpl;

import org.springframework.dao.EmptyResultDataAccessException;
import simple.myboard.myprac.dao.ArticleDao;
import simple.myboard.myprac.service.ArticleService;
import simple.myboard.myprac.service.CommentService;
import simple.myboard.myprac.domain.Article;

public class ArticleServiceImpl implements ArticleService {

    private ArticleDao articleDao;
    private CommentService commentService;

    public void setArticleDao(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    public void addArticle(Article article) {
        this.articleDao.insertArticle(article);
    }

    public void updateArticle(Article article) {
        this.articleDao.updateArticle(article);
    }

    public Article getArticleBySeq(Long seq) {
        try {
            return this.articleDao.getArticleBySeq(seq);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void deleteArticleBySeq(Long articleSeq) {
        this.commentService.deleteAllCommentByArticleSeq(articleSeq);
        this.articleDao.deleteArticleBySeq(articleSeq);
    }

    public void deleteAllArticleByMemberSeq(Long memberSeq) {
        this.commentService.deleteAllCommentByMemberSeq(memberSeq);
        this.articleDao.deleteAllArticleByMemberSeq(memberSeq);
    }

    public Long getLastIndexArticle() {
        return this.articleDao.getLastIndexArticle();
    }

    public int getCountAllArticle() {
        return this.articleDao.getCountAllArticle();
    }
}
