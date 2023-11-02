package simple.myboard.myprac.daojdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import simple.myboard.myprac.dao.ArticleDao;
import simple.myboard.myprac.domain.Article;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ArticleDaoJdbc implements ArticleDao {

    private JdbcTemplate jdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(ArticleDaoJdbc.class);

    private RowMapper<Article> rowMapper = new RowMapper<Article>() {
        @Override
        public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
            Article article = new Article();
            article.setArticleSeq(rs.getInt("article_seq"));
            article.setMemberSeq(rs.getInt("member_seq"));
            article.setTitle(rs.getString("title"));
            article.setContents(rs.getString("contents"));
            article.setIsDel(rs.getInt("is_del"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            article.setCreateTime(LocalDateTime.parse(rs.getString("create_time"), formatter));
            article.setUpdateTime(LocalDateTime.parse(rs.getString("update_time"), formatter));
            return article;
        }
    };

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate();
        this.jdbcTemplate.setDataSource(dataSource);
    }


    @Override
    public void insertArticle(Article article) {
        this.jdbcTemplate.update("INSERT INTO TB_ARTICLE (" +
                        " member_seq, title, contents, is_del, create_time, update_time " +
                        ") VALUES ( ?, ?, ?, ?, ?, ?)",
                article.getMemberSeq(), article.getTitle(), article.getContents(), article.getIsDel(), article.getCreateTime(), article.getUpdateTime());
    }

    @Override
    public void updateArticle(Article article) {
        this.jdbcTemplate.update(
                "UPDATE TB_ARTICLE SET " +
                        " member_seq = ?, " +
                        " title = ?, " +
                        " contents = ?, " +
                        " is_del = ?, " +
                        " create_time = ?, " +
                        " update_time = ? " +
                    "WHERE article_seq = ? ",
                article.getMemberSeq(),
                article.getTitle(),
                article.getContents(),
                article.getIsDel(),
                article.getCreateTime(),
                article.getUpdateTime(),
                article.getArticleSeq());
    }

    @Override
    public Article getArticleBySeq(int articleSeq) {
        return this.jdbcTemplate.queryForObject("SELECT * FROM TB_ARTICLE WHERE article_seq = ?", this.rowMapper, new Object[] {articleSeq});
    }

    @Override
    public void deleteArticleBySeq(int articleSeq) {
        this.jdbcTemplate.update("DELETE FROM TB_ARTICLE WHERE article_seq = ?", articleSeq);
    }

    @Override
    public void deleteAllArticleByMemberSeq(int memberSeq) {
        this.jdbcTemplate.update("DELETE FROM TB_ARTICLE WHERE member_seq = ?", memberSeq);
    }

    @Override
    public int getCountAllArticle() {
        return this.jdbcTemplate.query(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        return con.prepareStatement("SELECT COUNT(*) FROM TB_ARTICLE");
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

    @Override
    public int getLastIndexArticle() {
        return this.jdbcTemplate.query(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        return con.prepareStatement("SELECT MAX(ARTICLE_SEQ) FROM TB_ARTICLE");
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
}
