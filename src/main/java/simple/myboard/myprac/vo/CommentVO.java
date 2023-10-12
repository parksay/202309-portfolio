package simple.myboard.myprac.vo;

import java.time.LocalDateTime;

public class CommentVO extends AuditProps {

    private int commentSeq;
    private int articleSeq;
    private int memberSeq;
    private String contents;

    public CommentVO() {
    }

    public CommentVO(int commentSeq, int articleSeq, int memberSeq, String contents) {
        this.commentSeq = commentSeq;
        this.articleSeq = articleSeq;
        this.memberSeq = memberSeq;
        this.contents = contents;
    }


    public CommentVO(int articleSeq, int memberSeq, String contents, int isDel, LocalDateTime createTime, LocalDateTime updateTime) {
        this.articleSeq = articleSeq;
        this.memberSeq = memberSeq;
        this.contents = contents;
        super.setIsDel(isDel);
        super.setCreateTime(createTime);
        super.setUpdateTime(updateTime);
    }

    public int getCommentSeq() {
        return commentSeq;
    }

    public void setCommentSeq(int commentSeq) {
        this.commentSeq = commentSeq;
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

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
    
}
