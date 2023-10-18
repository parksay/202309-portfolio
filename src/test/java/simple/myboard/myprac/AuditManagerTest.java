package simple.myboard.myprac;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import simple.myboard.myprac.dao.ArticleDao;
import simple.myboard.myprac.dao.CommentDao;
import simple.myboard.myprac.dao.MemberDao;
import simple.myboard.myprac.service.ArticleService;
import simple.myboard.myprac.service.CommentService;
import simple.myboard.myprac.service.MemberService;
import simple.myboard.myprac.vo.ArticleVO;
import simple.myboard.myprac.vo.CommentVO;
import simple.myboard.myprac.vo.MemberVO;



@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations={"file:src/main/resources/applicationContext.xml"})
public class AuditManagerTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentDao commentDao;
    private MemberVO testMember;
    private ArticleVO testArticle;

    @BeforeEach
    public void setUp() {
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        this.memberDao = context.getBean("memberDao", MemberDao.class);
        this.memberService = context.getBean("memberService", MemberService.class);
        this.articleDao = context.getBean("articleDao", ArticleDao.class);
        this.articleService = context.getBean("articleService", ArticleService.class);
        this.articleDao.deleteAllArticle();
        this.memberDao.deleteAllMember();
        this.testMember = new MemberVO("testId", "testPsw", "testName");
        this.memberService.addMember(this.testMember);
        int lastIndexMember = this.memberDao.getLastIndexMember();
        this.testArticle = new ArticleVO(lastIndexMember, "testTitle", "testContents");
        this.articleService.addArticle(this.testArticle);
    }

    @Test
    public void addTest() {
        System.out.println("this.testMember = " + this.testMember);
    }

    @Test
    public void updateTest() {
        //
        MemberVO member = this.memberService.getMemberBySeq(this.memberDao.getLastIndexMember());
        member.setUserId("newId");
        member.setUserPsw("newPsw");
        member.setUserName("newName");
        this.memberService.updateMember(member);
        //
        ArticleVO article = this.articleService.getArticleBySeq(this.articleDao.getLastIndexArticle());
        article.setTitle("newTitle");
        article.setContents("newContents");
        this.articleService.updateArticle(article);
        //
        CommentVO comment = this.commentService.getCommentBySeq(this.commentDao.getLastIndexComment());
        comment.setContents("newContents");
        this.commentService.updateComment(comment);
    }

}
