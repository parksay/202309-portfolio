package simple.myboard.myprac.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Member extends AuditProps {

    @Id @GeneratedValue
    private Long memberSeq;
    private String userId;
    private String userPsw;
    private String userName;



    public Member() {
    }

    public Member(String userId, String userPsw, String userName) {
        this.userId = userId;
        this.userPsw = userPsw;
        this.userName = userName;
    }


    public Member(String userId, String userPsw, String userName, int isDel, LocalDateTime createTime, LocalDateTime updateTime) {
        this.userId = userId;
        this.userPsw = userPsw;
        this.userName = userName;
        super.setIsDel(isDel);
        super.setCreateTime(createTime);
        super.setUpdateTime(updateTime);
    }

    public Long getMemberSeq() {
        return memberSeq;
    }

    public void setMemberSeq(Long memberSeq) {
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

}
