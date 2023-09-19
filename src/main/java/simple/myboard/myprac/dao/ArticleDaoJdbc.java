package simple.myboard.myprac.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import simple.myboard.myprac.vo.ArticleVO;

import javax.sql.DataSource;
import java.sql.*;

public class ArticleDaoJdbc implements ArticleDao {

    private JdbcTemplate jdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(ArticleDaoJdbc.class);

    private RowMapper<ArticleVO> rowMapper = new RowMapper<ArticleVO>() {
        @Override
        public ArticleVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            ArticleVO article = new ArticleVO();
            article.setArticleSeq(rs.getInt("article_seq"));
            article.setMemberSeq(rs.getInt("member_seq"));
            article.setTitle(rs.getString("title"));
            article.setContents(rs.getString("contents"));
            article.setIsDel(rs.getInt("is_del"));
            article.setCreateTime(rs.getString("create_time"));
            article.setChangeTime(rs.getString("change_time"));
            return article;
        }
    };

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate();
        this.jdbcTemplate.setDataSource(dataSource);
    }


    @Override
    public void insertArticle(ArticleVO article) {
        this.jdbcTemplate.update("INSERT INTO TB_ARTICLE (" +
                        " member_seq, title, contents " +
                        ") VALUES ( ?, ?, ?)",
                article.getMemberSeq(), article.getTitle(), article.getContents());
    }

    @Override
    public void updateArticle(ArticleVO article) {

    }

    @Override
    public ArticleVO getArticle(int articleSeq) {
        return this.jdbcTemplate.queryForObject("SELECT * FROM TB_ARTICLE WHERE article_seq = ?", this.rowMapper, new Object[] {articleSeq});
    }

    @Override
    public void deleteArticle(int articleSeq) {
        this.jdbcTemplate.update("DELETE FROM TB_ARTICLE WHERE article_seq = ?", articleSeq);
    }

    @Override
    public int getCountArticle() {
        return this.jdbcTemplate.query(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        return con.prepareStatement("SELECT count(*) FROM TB_ARTICLE");
                    }
                },
                new ResultSetExtractor<Integer>() {
                    @Override
                    public Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
                        rs.next();
                        return rs.getInt(1);
                    }
                }
        );
    }

    @Override
    public void deleteAllArticle() {
        this.jdbcTemplate.update("DELETE FROM TB_ARTICLE");
    }
}
