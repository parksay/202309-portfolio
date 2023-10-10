package simple.myboard.myprac.serviceimpl;

import simple.myboard.myprac.dao.ArticleDao;
import simple.myboard.myprac.service.ArticleService;
import simple.myboard.myprac.vo.ArticleVO;

public class ArticleServiceImpl implements ArticleService {

    private ArticleDao articleDao;

    public void setArticleDao(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    public void addArticle(ArticleVO article) {
        // null check 여기서 하기
        this.articleDao.insertArticle(article);
    }

    public void updateArticle(ArticleVO article) {
        this.articleDao.updateArticle(article);
    }

    public ArticleVO getArticleBySeq(int seq) {
        return this.articleDao.getArticleBySeq(seq);
    }

    public void deleteArticleBySeq(int articleSeq) {
        this.articleDao.deleteArticleBySeq(articleSeq);
    }
}
