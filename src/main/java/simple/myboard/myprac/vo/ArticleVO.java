package simple.myboard.myprac.vo;

import java.util.Date;

public class ArticleVO {

    private int articleSeq;
    private int memberSeq;
    private String title;
    private String contents;
    private int isDel;
    private Date createTime;
    private Date updateTime;


    public ArticleVO() {
    }

    public ArticleVO(int memberSeq, String title, String contents) {
        this.memberSeq = memberSeq;
        this.title = title;
        this.contents = contents;
    }

    public ArticleVO(int memberSeq, String title, String contents, int isDel, Date createTime, Date updateTime) {
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
