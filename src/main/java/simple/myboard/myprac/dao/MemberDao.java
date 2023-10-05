package simple.myboard.myprac.dao;

import simple.myboard.myprac.vo.MemberVO;

public interface MemberDao {

    public abstract void insertMember(MemberVO member);
    public abstract MemberVO getMemberBySeq(int memberSeq);
    public abstract void updateMember(MemberVO member);
    public abstract void deleteMemberBySeq(int memberSeq);
    public abstract int getCountMember();
    public abstract void deleteAllMember();
    public abstract int getLastIndexMember();
}
