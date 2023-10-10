package simple.myboard.myprac.vo;

import java.time.LocalDateTime;

public class AuditProps {

    private int isDel;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

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

    public void renewCreateTime() {
        this.createTime = LocalDateTime.now();
    }

    public void renewUpdateTime() {
        this.updateTime = LocalDateTime.now();
    }

}
