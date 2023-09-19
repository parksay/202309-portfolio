package simple.myboard.myprac.vo;

public class ArticleVO {

    private int articleSeq;
    private int memberSeq;
    private String title;
    private String contents;
    private int isDel;
    private String createTime;
    private String changeTime;


    public ArticleVO() {
    }

    public ArticleVO(int memberSeq, String title, String contents) {
        this.memberSeq = memberSeq;
        this.title = title;
        this.contents = contents;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }
}
