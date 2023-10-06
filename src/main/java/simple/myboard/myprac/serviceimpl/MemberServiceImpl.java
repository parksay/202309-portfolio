package simple.myboard.myprac.serviceimpl;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import simple.myboard.myprac.dao.MemberDao;
import simple.myboard.myprac.service.MemberService;
import simple.myboard.myprac.vo.MemberVO;

import java.time.LocalDateTime;

public class MemberServiceImpl implements MemberService {
    private MemberDao memberDao;

    public void setMemberDao(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    public MemberVO getMemberBySeq(int memberSeq) {
        try {
            return this.memberDao.getMemberBySeq(memberSeq);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void addMember(MemberVO member) throws DataIntegrityViolationException {
        // null check
        if(member.getUserId() == null || member.getUserPsw() == null || member.getUserName() == null) {
            throw new DataIntegrityViolationException("cannot be null: user_id, user_psw, user_name");
        }
        // 최초 등록 시 0
        member.setIsDel(0);
        // 날짜 넣어주기
        LocalDateTime nowTime = LocalDateTime.now();
        member.setCreateTime(nowTime);
        member.setUpdateTime(nowTime);
        this.memberDao.insertMember(member);
    }

    public void updateMember(MemberVO member) {
        member.setUpdateTime(LocalDateTime.now());
        this.memberDao.updateMember(member);
    }

    public void deleteMemberBySeq(int memberSeq) {
        this.memberDao.deleteMemberBySeq(memberSeq);
    }

}
