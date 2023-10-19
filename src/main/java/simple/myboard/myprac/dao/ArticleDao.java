package simple.myboard.myprac.dao;

import simple.myboard.myprac.vo.ArticleVO;

public interface ArticleDao {

    public abstract void insertArticle(ArticleVO article);
    public abstract void updateArticle(ArticleVO article);
    public abstract ArticleVO getArticleBySeq(int articleSeq);
    public abstract void deleteArticleBySeq(int articleSeq);
    public abstract int getCountAllArticle();
    public abstract void deleteAllArticle();
    public abstract void deleteAllArticleByMemberSeq(int memberSeq);
    public abstract int getLastIndexArticle();
}
