package simple.myboard.myprac.service;

import simple.myboard.myprac.dao.MemberDao;
import simple.myboard.myprac.domain.Member;

public interface MemberService {

    public void setMemberDao(MemberDao memberDao);
    public abstract Member getMemberBySeq(Long memberSeq);
    public abstract void addMember(Member member);
    public abstract void updateMember(Member member);
    public abstract void deleteMemberBySeq(Long memberSeq);
    public abstract Long getLastIndexMember();
    
}
