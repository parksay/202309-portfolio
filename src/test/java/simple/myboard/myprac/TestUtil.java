package simple.myboard.myprac;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import simple.myboard.myprac.dao.ArticleDao;
import simple.myboard.myprac.dao.CommentDao;
import simple.myboard.myprac.dao.MemberDao;
import simple.myboard.myprac.service.ArticleService;
import simple.myboard.myprac.service.MemberService;
import simple.myboard.myprac.domain.Article;
import simple.myboard.myprac.domain.Member;

public class TestUtil {

    private static ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");

    public static void clearTestData() {
        MemberDao memberDao = TestUtil.context.getBean("memberDao", MemberDao.class);
        ArticleDao articleDao = TestUtil.context.getBean("articleDao", ArticleDao.class);
        CommentDao commentDao = TestUtil.context.getBean("commentDao", CommentDao.class);
        commentDao.deleteAllComment();
        articleDao.deleteAllArticle();
        memberDao.deleteAllMember();
    }

    public static Long getLastIndexMember() {
        MemberService memberService = TestUtil.context.getBean("memberService", MemberService.class);
        Long lastIndex = memberService.getLastIndexMember();
        memberService.addMember(new Member("test"+lastIndex, "testPsw", "testName"));
        return memberService.getLastIndexMember();
    }

    public static Long getLastIndexArticle() {
        MemberService memberService = TestUtil.context.getBean("memberService", MemberService.class);
        ArticleService articleService = TestUtil.context.getBean("articleService", ArticleService.class);
        Long lastIndexMember = memberService.getLastIndexMember();
        articleService.addArticle(new Article(lastIndexMember, "testTitle", "testContents"));
        return articleService.getLastIndexArticle();
    }
}
