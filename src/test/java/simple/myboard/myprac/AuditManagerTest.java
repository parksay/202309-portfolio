package simple.myboard.myprac;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import simple.myboard.myprac.service.ArticleService;
import simple.myboard.myprac.service.CommentService;
import simple.myboard.myprac.service.MemberService;
import simple.myboard.myprac.domain.Article;
import simple.myboard.myprac.domain.Comment;
import simple.myboard.myprac.domain.Member;

import java.time.LocalDateTime;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations={"file:src/main/resources/applicationContext.xml"})
@Transactional
public class AuditManagerTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CommentService commentService;

    private Logger logger = LoggerFactory.getLogger(AuditManagerTest.class);

    @BeforeEach
    public void setUp() {
        TestUtil.clearTestData();
    }

    @Test
    public void addTest() {
        //
        this.memberService.addMember(new Member("testId", "testPsw", "testName"));
        int lastIndexMember = this.memberService.getLastIndexMember();
        Member member = this.memberService.getMemberBySeq(lastIndexMember);
        Assertions.assertTrue(member.getCreateTime().plusSeconds(1).isAfter(LocalDateTime.now()));
        Assertions.assertTrue(member.getUpdateTime().plusSeconds(1).isAfter(LocalDateTime.now()));
        //
        this.articleService.addArticle(new Article(lastIndexMember, "testTitle", "testContents"));
        int lastIndexArticle = this.articleService.getLastIndexArticle();
        Article article = this.articleService.getArticleBySeq(lastIndexArticle);
        Assertions.assertTrue(article.getCreateTime().plusSeconds(1).isAfter(LocalDateTime.now()));
        Assertions.assertTrue(article.getUpdateTime().plusSeconds(1).isAfter(LocalDateTime.now()));
        //
        this.commentService.addComment(new Comment(lastIndexMember, lastIndexArticle, "testContents"));
        Comment comment = this.commentService.getCommentBySeq(this.commentService.getLastIndexComment());
        Assertions.assertTrue(comment.getCreateTime().plusSeconds(1).isAfter(LocalDateTime.now()));
        Assertions.assertTrue(comment.getUpdateTime().plusSeconds(1).isAfter(LocalDateTime.now()));

    }

    @Test
    public void updateTest() {
        // add 하고 3초 있다가 update 날림
        // add 했던 개체와 update 한 개체가 같은 개체인지?
        // update 시간이 create 시간보다 나중인지?
        // update 시간이 현재 시간이랑 같은지? (1초 이하로 차이나는지?)
        //
        this.memberService.addMember(new Member("testId", "testPsw", "testName"));
        int lastIndexMember = this.memberService.getLastIndexMember();
        this.articleService.addArticle(new Article(lastIndexMember, "testTitle", "testContents"));
        int lastIndexArticle = this.articleService.getLastIndexArticle();
        this.commentService.addComment(new Comment(lastIndexMember, lastIndexArticle, "testContents"));
        try {
            Thread.sleep(3000L);
        } catch (Exception e) {
            throw new RuntimeException("Failed to run: Thread.sleep");
        }
        //
        Member memberBefore = this.memberService.getMemberBySeq(lastIndexMember);
        memberBefore.setUserId("newId");
        memberBefore.setUserPsw("newPsw");
        memberBefore.setUserName("newName");
        this.memberService.updateMember(memberBefore);
        Member memberAfter = this.memberService.getMemberBySeq(lastIndexMember);
        Assertions.assertEquals(memberBefore.getMemberSeq(), memberAfter.getMemberSeq());
        Assertions.assertTrue(memberAfter.getUpdateTime().isAfter(memberAfter.getCreateTime()));
        Assertions.assertTrue(memberAfter.getUpdateTime().plusSeconds(1).isAfter(LocalDateTime.now()));
        //
        Article articleBefore = this.articleService.getArticleBySeq(lastIndexArticle);
        articleBefore.setTitle("newTitle");
        articleBefore.setContents("newContents");
        this.articleService.updateArticle(articleBefore);
        Article articleAfter = this.articleService.getArticleBySeq(lastIndexArticle);
        Assertions.assertEquals(articleBefore.getArticleSeq(), articleAfter.getArticleSeq());
        Assertions.assertTrue(articleAfter.getUpdateTime().isAfter(articleAfter.getCreateTime()));
        Assertions.assertTrue(articleAfter.getUpdateTime().plusSeconds(1).isAfter(LocalDateTime.now()));

        //
        Comment commentBefore = this.commentService.getCommentBySeq(this.commentService.getLastIndexComment());
        commentBefore.setContents("newContents");
        this.commentService.updateComment(commentBefore);
        Comment commentAfter = this.commentService.getCommentBySeq(this.commentService.getLastIndexComment());
        Assertions.assertEquals(commentBefore.getCommentSeq(), commentAfter.getCommentSeq());
        Assertions.assertTrue(commentAfter.getUpdateTime().isAfter(commentAfter.getCreateTime()));
        Assertions.assertTrue(commentAfter.getUpdateTime().plusSeconds(1).isAfter(LocalDateTime.now()));

    }

}
