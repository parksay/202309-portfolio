package simple.myboard.myprac.service;

import simple.myboard.myprac.dao.ArticleDao;
import simple.myboard.myprac.vo.ArticleVO;

import java.util.Date;

public interface ArticleService {


    public abstract void setArticleDao(ArticleDao articleDao);

    public abstract void addArticle(ArticleVO article);

    public abstract void updateArticle(ArticleVO article);

    public abstract ArticleVO getArticleBySeq(int seq);

    public abstract void delteArticle(int articleSeq);

}
