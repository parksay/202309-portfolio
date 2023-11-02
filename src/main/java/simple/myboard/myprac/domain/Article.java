package simple.myboard.myprac.domain;

import java.time.LocalDateTime;

public class Article extends AuditProps {

    private Long articleSeq;
    private Long memberSeq;
    private String title;
    private String contents;


    public Article() {
    }

    public Article(Long memberSeq, String title, String contents) {
        this.memberSeq = memberSeq;
        this.title = title;
        this.contents = contents;
    }

    public Article(Long memberSeq, String title, String contents, int isDel, LocalDateTime createTime, LocalDateTime updateTime) {
        this.articleSeq = articleSeq;
        this.memberSeq = memberSeq;
        this.title = title;
        this.contents = contents;
        super.setIsDel(isDel);
        super.setCreateTime(createTime);
        super.setUpdateTime(updateTime);
    }

    public Long getArticleSeq() {
        return articleSeq;
    }

    public void setArticleSeq(Long articleSeq) {
        this.articleSeq = articleSeq;
    }

    public Long getMemberSeq() {
        return memberSeq;
    }

    public void setMemberSeq(Long memberSeq) {
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
