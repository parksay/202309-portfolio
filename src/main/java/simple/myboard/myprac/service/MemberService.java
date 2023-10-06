package simple.myboard.myprac.service;

import simple.myboard.myprac.dao.MemberDao;
import simple.myboard.myprac.vo.MemberVO;

public interface MemberService {

    public void setMemberDao(MemberDao memberDao);
    public abstract MemberVO getMemberBySeq(int memberSeq);
    public abstract void addMember(MemberVO member);
    public abstract void updateMember(MemberVO member);
    public abstract void deleteMemberBySeq(int memberSeq);
    
}
