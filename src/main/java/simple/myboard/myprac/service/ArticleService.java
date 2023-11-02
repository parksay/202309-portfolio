package simple.myboard.myprac.service;

import simple.myboard.myprac.dao.ArticleDao;
import simple.myboard.myprac.domain.Article;

public interface ArticleService {


    public abstract void setArticleDao(ArticleDao articleDao);

    public abstract void addArticle(Article article);

    public abstract void updateArticle(Article article);

    public abstract Article getArticleBySeq(int seq);

    public abstract void deleteArticleBySeq(int articleSeq);

    public abstract void deleteAllArticleByMemberSeq(int articleSeq);

    public abstract int getLastIndexArticle();

    public abstract int getCountAllArticle();

}
