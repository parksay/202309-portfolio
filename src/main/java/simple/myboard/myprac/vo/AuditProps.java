package simple.myboard.myprac.vo;

import java.time.LocalDateTime;

public class AuditProps {

    private int isDel;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public int getIsDel() {
        return isDel;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public void renewCreateTime() {
        this.createTime = LocalDateTime.now();
    }

    public void renewUpdateTime() {
        this.updateTime = LocalDateTime.now();
    }

}
