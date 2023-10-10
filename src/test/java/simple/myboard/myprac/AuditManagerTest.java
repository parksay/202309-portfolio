package simple.myboard.myprac;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import simple.myboard.myprac.dao.ArticleDao;
import simple.myboard.myprac.dao.MemberDao;
import simple.myboard.myprac.service.ArticleService;
import simple.myboard.myprac.service.MemberService;
import simple.myboard.myprac.vo.ArticleVO;
import simple.myboard.myprac.vo.MemberVO;

public class AuditManagerTest {

    private MemberService memberService;
    private MemberDao memberDao;
    private ArticleService articleService;
    private ArticleDao articleDao;
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
    }

}
