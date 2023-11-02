package simple.myboard.myprac.dao;

import simple.myboard.myprac.domain.Member;

public interface MemberDao {

    public abstract void insertMember(Member member);
    public abstract Member getMemberBySeq(int memberSeq);
    public abstract void updateMember(Member member);
    public abstract void deleteMemberBySeq(int memberSeq);
    public abstract int getCountAllMember();
    public abstract void deleteAllMember();
    public abstract int getLastIndexMember();
}
