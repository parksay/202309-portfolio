package simple.myboard.myprac.service;

import simple.myboard.myprac.dao.ArticleDao;
import simple.myboard.myprac.domain.Article;

public interface ArticleService {


    public abstract void setArticleDao(ArticleDao articleDao);

    public abstract void addArticle(Article article);

    public abstract void updateArticle(Article article);

    public abstract Article getArticleBySeq(Long seq);

    public abstract void deleteArticleBySeq(Long articleSeq);

    public abstract void deleteAllArticleByMemberSeq(Long articleSeq);

    public abstract Long getLastIndexArticle();

    public abstract int getCountAllArticle();

}
