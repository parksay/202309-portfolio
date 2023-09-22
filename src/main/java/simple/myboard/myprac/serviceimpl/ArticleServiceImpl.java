package simple.myboard.myprac.serviceimpl;

import simple.myboard.myprac.dao.ArticleDao;
import simple.myboard.myprac.service.ArticleService;
import simple.myboard.myprac.vo.ArticleVO;

import java.util.Date;

public class ArticleServiceImpl implements ArticleService {

    private ArticleDao articleDao;

    public void setArticleDao(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    public void addArticle(ArticleVO article) {
        Date now = new Date();
        if(article.getIsDel() == 0) article.setIsDel(0);
        if(article.getCreateTime() == null) article.setCreateTime(now);
        if(article.getUpdateTime() == null) article.setUpdateTime(now);
        this.articleDao.insertArticle(article);
    }

    public void updateArticle(ArticleVO article) {
        article.setUpdateTime(new Date());
        this.articleDao.updateArticle(article);
    }

    public ArticleVO getArticleBySeq(int seq) {
        return this.articleDao.getArticle(seq);
    }

    public void delteArticle(int articleSeq) {
        this.articleDao.deleteArticle(articleSeq);
    }
}
