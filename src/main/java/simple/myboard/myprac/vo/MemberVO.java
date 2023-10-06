package simple.myboard.myprac.vo;

import java.time.LocalDateTime;

public class MemberVO {

    private int memberSeq;
    private String userId;
    private String userPsw;
    private String userName;
    private int isDel;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;


    public MemberVO() {
    }

    public MemberVO(String userId, String userPsw, String userName) {
        this.userId = userId;
        this.userPsw = userPsw;
        this.userName = userName;
    }


    public MemberVO(String userId, String userPsw, String userName, int isDel, LocalDateTime createTime, LocalDateTime updateTime) {
        this.userId = userId;
        this.userPsw = userPsw;
        this.userName = userName;
        this.isDel = isDel;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public int getMemberSeq() {
        return memberSeq;
    }

    public void setMemberSeq(int memberSeq) {
        this.memberSeq = memberSeq;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPsw() {
        return userPsw;
    }

    public void setUserPsw(String userPsw) {
        this.userPsw = userPsw;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}
