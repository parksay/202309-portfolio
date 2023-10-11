package simple.myboard.myprac;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import simple.myboard.myprac.dao.ArticleDao;
import simple.myboard.myprac.dao.MemberDao;
import simple.myboard.myprac.service.MemberService;
import simple.myboard.myprac.vo.MemberVO;

public class TestUtil {

    public static void deleteAllArticleAndMember() {
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        MemberDao memberDao = context.getBean("memberDao", MemberDao.class);
        ArticleDao articleDao = context.getBean("articleDao", ArticleDao.class);
        articleDao.deleteAllArticle();
        memberDao.deleteAllMember();
    }

    public static int getLastIndexMember() {
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        MemberService memberService = context.getBean("memberService", MemberService.class);
        memberService.addMember(new MemberVO("testId", "testPsw", "testName"));
        return memberService.getLastIndexMember();
    }
}
