package simple.myboard.myprac.domain;

import java.time.LocalDateTime;

public class Comment extends AuditProps {

    private Long commentSeq;
    private Long articleSeq;
    private Long memberSeq;
    private String contents;

    public Comment() {
    }

    public Comment(Long memberSeq, Long articleSeq, String contents) {
        this.memberSeq = memberSeq;
        this.articleSeq = articleSeq;
        this.contents = contents;
    }


    public Comment(Long memberSeq, Long articleSeq, String contents, int isDel, LocalDateTime createTime, LocalDateTime updateTime) {
        this.memberSeq = memberSeq;
        this.articleSeq = articleSeq;
        this.contents = contents;
        super.setIsDel(isDel);
        super.setCreateTime(createTime);
        super.setUpdateTime(updateTime);
    }

    public Long getCommentSeq() {
        return commentSeq;
    }

    public void setCommentSeq(Long commentSeq) {
        this.commentSeq = commentSeq;
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

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
    
}
