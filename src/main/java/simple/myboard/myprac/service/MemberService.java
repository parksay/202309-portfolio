package simple.myboard.myprac.service;

import simple.myboard.myprac.vo.MemberVO;

public interface MemberService {

    public abstract MemberVO getMemberBySeq(int memberSeq);
    public abstract void addMember(MemberVO member);
    public abstract void updateMember(MemberVO member);
    public abstract void deleteMember(int memberSeq);
    
}
