package simple.myboard.myprac.daojdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import simple.myboard.myprac.dao.CommentDao;
import simple.myboard.myprac.domain.Comment;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CommentDaoJdbc implements CommentDao {

    public static final Logger logger = LoggerFactory.getLogger(CommentDaoJdbc.class);
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate();
        this.jdbcTemplate.setDataSource(dataSource);
    }

    private RowMapper<Comment> rowMapper = new RowMapper<Comment>() {
        @Override
        public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
            Comment comment = new Comment();
            comment.setCommentSeq(rs.getLong("comment_seq"));
            comment.setArticleSeq(rs.getLong("article_seq"));
            comment.setMemberSeq(rs.getLong("member_seq"));
            comment.setContents(rs.getString("contents"));
            comment.setIsDel(rs.getInt("is_del"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            comment.setCreateTime(LocalDateTime.parse(rs.getString("create_time"), formatter));
            comment.setUpdateTime(LocalDateTime.parse(rs.getString("update_time"), formatter));
            return comment;
        }
    };

    @Override
    public void insertComment(Comment comment) {
        this.jdbcTemplate.update("INSERT INTO TB_COMMENT  (" +
                        "member_seq, article_seq, contents, is_del, create_time, update_time " +
                        ") VALUES ( ?, ?, ?, ?, ?, ? )",
                comment.getMemberSeq(), comment.getArticleSeq(),comment.getContents(), comment.getIsDel(), comment.getCreateTime(), comment.getUpdateTime());
    }

    @Override
    public Comment getCommentBySeq(int commentSeq) {
        return this.jdbcTemplate.queryForObject("SELECT * FROM TB_COMMENT WHERE COMMENT_SEQ = ?", this.rowMapper, new Object[] {commentSeq});
    }

    @Override
    public void updateComment(Comment comment) {
        this.jdbcTemplate.update("UPDATE TB_COMMENT SET " +
                        " member_seq = ?, " +
                        " article_seq = ?,  " +
                        " contents = ?, " +
                        " is_del = ?, " +
                        " create_time = ?, " +
                        " update_time = ? " +
                        " WHERE comment_seq = ?",
                comment.getMemberSeq(),
                comment.getArticleSeq(),
                comment.getContents(),
                comment.getIsDel(),
                comment.getCreateTime(),
                comment.getUpdateTime(),
                comment.getCommentSeq());
    }

    @Override
    public void deleteCommentBySeq(int commentSeq) {
        this.jdbcTemplate.update("DELETE FROM TB_COMMENT WHERE comment_seq = ?", commentSeq);

    }

    @Override
    public int getLastIndexComment() {
        return this.jdbcTemplate.query("SELECT MAX(COMMENT_SEQ) FROM TB_COMMENT", (rs)->{ rs.next(); return rs.getInt(1); });
    }

    @Override
    public List<Comment> getCommentListByMemberSeq(Long memberSeq) {
        return this.jdbcTemplate.query("SELECT * FROM TB_COMMENT WHERE member_seq = ?", this.rowMapper, new Object[] {memberSeq});
    }

    @Override
    public List<Comment> getCommentListByArticleSeq(Long articleSeq) {
        return this.jdbcTemplate.query("SELECT * FROM TB_COMMENT WHERE article_seq = ?", this.rowMapper, new Object[] {articleSeq});

    }

    @Override
    public void deleteAllComment() {
        this.jdbcTemplate.update("DELETE FROM TB_COMMENT");
    }

    @Override
    public void deleteAllCommentByMemberSeq(Long memberSeq) {
        this.jdbcTemplate.update("DELETE FROM TB_COMMENT WHERE member_seq = ?", new Object[] {memberSeq});
    }

    @Override
    public void deleteAllCommentByArticleSeq(Long articleSeq) {
        this.jdbcTemplate.update("DELETE FROM TB_COMMENT WHERE article_seq = ?", new Object[] {articleSeq});

    }

    @Override
    public int getCountAllComment() {
        return this.jdbcTemplate.query("SELECT COUNT(*) FROM TB_COMMENT", (rs)->{ rs.next(); return rs.getInt(1); });
    }

    @Override
    public int getCountAllCommentByMemberSeq(Long memberSeq) {
        return this.jdbcTemplate.query("SELECT COUNT(*) FROM TB_COMMENT WHERE member_seq = ?", (rs)->{ rs.next(); return rs.getInt(1); }, new Object[] {memberSeq});

    }

    @Override
    public int getCountAllCommentByArticleSeq(Long articleSeq) {
        return this.jdbcTemplate.query("SELECT COUNT(*) FROM TB_COMMENT WHERE article_seq = ?", (rs)->{ rs.next(); return rs.getInt(1); }, new Object[] {articleSeq});

    }

}
