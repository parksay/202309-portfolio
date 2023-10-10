package simple.myboard.myprac.vo;

import java.time.LocalDateTime;

public class ArticleVO extends AuditProps {

    private int articleSeq;
    private int memberSeq;
    private String title;
    private String contents;


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
        super.setIsDel(isDel);
        super.setCreateTime(createTime);
        super.setUpdateTime(updateTime);
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

}
