package simple.myboard.myprac.dao;

import simple.myboard.myprac.domain.Article;

public interface ArticleDao {

    public abstract void insertArticle(Article article);
    public abstract void updateArticle(Article article);
    public abstract Article getArticleBySeq(int articleSeq);
    public abstract void deleteArticleBySeq(int articleSeq);
    public abstract int getCountAllArticle();
    public abstract void deleteAllArticle();
    public abstract void deleteAllArticleByMemberSeq(int memberSeq);
    public abstract int getLastIndexArticle();
}
