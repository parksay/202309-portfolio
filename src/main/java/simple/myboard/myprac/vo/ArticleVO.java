package simple.myboard.myprac.vo;

import java.time.LocalDateTime;

public class ArticleVO {

    private int articleSeq;
    private int memberSeq;
    private String title;
    private String contents;
    private int isDel;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // TODO : audit 항목 공틍 인터페이스 만들고 service 단에서도 add 나 update 시에 공통 필터 거치도록 만들기 > 데코레이터 패턴으로 되려나

    public ArticleVO() {
    }

    public ArticleVO(int memberSeq, String title, String contents) {
        this.memberSeq = memberSeq;
        this.title = title;
        this.contents = contents;
    }

    public ArticleVO(int memberSeq, String title, String contents, int isDel, LocalDateTime createTime, LocalDateTime updateTime) {
        this.articleSeq = articleSeq;
        this.memberSeq = memberSeq;
        this.title = title;
        this.contents = contents;
        this.isDel = isDel;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public int getArticleSeq() {
        return articleSeq;
    }

    public void setArticleSeq(int articleSeq) {
        this.articleSeq = articleSeq;
    }

    public int getMemberSeq() {
        return memberSeq;
    }

    public void setMemberSeq(int memberSeq) {
        this.memberSeq = memberSeq;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
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
