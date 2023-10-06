package simple.myboard.myprac.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import simple.myboard.myprac.vo.MemberVO;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MemberDaoJdbc implements MemberDao {

    public static final Logger logger = LoggerFactory.getLogger(MemberDaoJdbc.class);
    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate();
        this.jdbcTemplate.setDataSource(dataSource);
    }

    private RowMapper<MemberVO> rowMapper = new RowMapper<MemberVO>() {
        @Override
        public MemberVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            MemberVO member = new MemberVO();
            member.setMemberSeq(rs.getInt("member_seq"));
            member.setUserId(rs.getString("user_id"));
            member.setUserPsw(rs.getString("user_psw"));
            member.setUserName(rs.getString("user_name"));
            member.setIsDel(rs.getInt("is_del"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            member.setCreateTime(LocalDateTime.parse(rs.getString("create_time"), formatter));
            member.setUpdateTime(LocalDateTime.parse(rs.getString("update_time"), formatter));
            return member;
        }
    };

    @Override
    public void insertMember(MemberVO member) {
        this.jdbcTemplate.update("INSERT INTO TB_MEMBER  (" +
                    "user_id, user_psw, user_name, is_del, create_time, update_time " +
                    ") VALUES ( ?, ?, ?, ?, ?, ? )",
                member.getUserId(), member.getUserPsw(),member.getUserName(), member.getIsDel(), member.getCreateTime(), member.getUpdateTime());
    }

    @Override
    public MemberVO getMemberBySeq(int memberSeq) {
        return this.jdbcTemplate.queryForObject("SELECT * FROM TB_MEMBER WHERE MEMBER_SEQ = ?", this.rowMapper, new Object[] {memberSeq});
    }

    @Override
    public void updateMember(MemberVO member) {
        this.jdbcTemplate.update("UPDATE TB_MEMBER SET " +
                " user_id = ?, " +
                " user_psw = ?,  " +
                " user_name = ?, " +
                " is_del = ?, " +
                " create_time = ?, " +
                " update_time = ? " +
                " WHERE member_seq = ?",
                member.getUserId(),
                member.getUserPsw(),
                member.getUserName(),
                member.getIsDel(),
                member.getCreateTime(),
                member.getUpdateTime(),
                member.getMemberSeq());
    }

    @Override
    public void deleteMemberBySeq(int memberSeq) {
        this.jdbcTemplate.update("DELETE FROM TB_MEMBER WHERE member_seq = ?", memberSeq);

    }

    @Override
    public int getCountMember() {
        return this.jdbcTemplate.query("SELECT COUNT(*) FROM TB_MEMBER", (rs)->{ rs.next(); return rs.getInt(1); });
    }

    @Override
    public void deleteAllMember() {
        this.jdbcTemplate.update("DELETE FROM TB_MEMBER");
    }

    @Override
    public int getLastIndexMember() {
        return this.jdbcTemplate.query("SELECT MAX(MEMBER_SEQ) FROM TB_MEMBER", (rs)->{ rs.next(); return rs.getInt(1); });
    }
}
