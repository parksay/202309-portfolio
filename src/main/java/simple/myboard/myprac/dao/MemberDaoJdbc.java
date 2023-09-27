package simple.myboard.myprac.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import simple.myboard.myprac.vo.MemberVO;

public class MemberDaoJdbc implements MemberDao {

    private JdbcTemplate jdbcTemplate;

    @Override
    public void insertMember(MemberVO member) {
        this.jdbcTemplate.update("INSERT INTO TB_MEMBER  (" +
                    "user_id, user_psw, user_name, is_del, create_time, update_time " +
                    ") VALUES ( ?, ?, ?, ?, ?, ? )",
                member.getUserId(), member.getUserPsw(),member.getUserName(), member.getIsDel(), member.getCreateTime(), member.getUpdateTime());
    }

    @Override
    public MemberVO getMemberBySeq(int memberSeq) {
        return null;
    }

    @Override
    public void updateMember(MemberVO member) {

    }

    @Override
    public void deleteMember(int memberSeq) {

    }

    @Override
    public int getCountMember() {
        return 0;
    }

    @Override
    public void deleteAllMember() {

    }

    @Override
    public int getLastIndexMember() {
        return 0;
    }
}
