package simple.myboard.myprac.serviceimpl;

import simple.myboard.myprac.dao.ArticleDao;
import simple.myboard.myprac.service.ArticleService;
import simple.myboard.myprac.vo.ArticleVO;

import java.time.LocalDateTime;

public class ArticleServiceImpl implements ArticleService {

    private ArticleDao articleDao;

    public void setArticleDao(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    public void addArticle(ArticleVO article) {
        article.setIsDel(0);
        LocalDateTime now = LocalDateTime.now();
        article.setCreateTime(now);
        article.setUpdateTime(now);
        this.articleDao.insertArticle(article);
    }

    public void updateArticle(ArticleVO article) {
        article.setUpdateTime(LocalDateTime.now());
        this.articleDao.updateArticle(article);
    }

    public ArticleVO getArticleBySeq(int seq) {
        return this.articleDao.getArticleBySeq(seq);
    }

    public void deleteArticleBySeq(int articleSeq) {
        this.articleDao.deleteArticleBySeq(articleSeq);
    }
}
