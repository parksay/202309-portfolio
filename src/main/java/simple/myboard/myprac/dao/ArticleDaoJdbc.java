package simple.myboard.myprac.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import simple.myboard.myprac.vo.ArticleVO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ArticleDaoJdbc implements ArticleDao {

    private DataSource dataSource;
    private static final Logger logger = LoggerFactory.getLogger(ArticleDaoJdbc.class);


    @Override
    public void insertArticle(ArticleVO article) {
        try {
            //
            Connection c = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/myprac",
                    "root",
                    "0000");
            //
            PreparedStatement ps = c.prepareStatement("INSERT INTO TB_ARTICLE (" +
                    " member_seq," +
                    " title," +
                    " contents ) VALUES (" +
                    "?, ?, ?");
            //
            ps.setInt(1, article.getMemberSeq());
            ps.setString(2, article.getTitle());
            ps.setString(3, article.getContents());
            //
            ps.executeUpdate();
            ps.close();
            c.close();
        } catch (SQLException e) {
            logger.info("#### insertArticle > ", e);
            System.exit(0);
        }
    }

    @Override
    public void updateArticle(ArticleVO article) {

    }

    @Override
    public ArticleVO getArticle(int articleSeq) {

        return null;
    }

    @Override
    public void deleteArticle(int articleSeq) {

    }

    @Override
    public int getCountArticle() {
        return 0;
    }

    @Override
    public void deleteAllArticle() {

    }
}
