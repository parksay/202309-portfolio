package simple.myboard.myprac.dao;

import simple.myboard.myprac.vo.ArticleVO;

public interface ArticleDao {

    public abstract void insertArticle(ArticleVO article);
    public abstract void updateArticle(ArticleVO article);
    public abstract ArticleVO getArticle(int articleSeq);
    public abstract void deleteArticle(int articleSeq);
    public abstract int getCountArticle();
    public abstract void deleteAllArticle();
}
