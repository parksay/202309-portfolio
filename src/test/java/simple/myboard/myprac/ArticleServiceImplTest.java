package simple.myboard.myprac;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.TransientDataAccessResourceException;
import simple.myboard.myprac.dao.ArticleDao;
import simple.myboard.myprac.daojdbc.ArticleDaoJdbc;
import simple.myboard.myprac.service.ArticleService;
import simple.myboard.myprac.service.CommentService;
import simple.myboard.myprac.serviceimpl.ArticleServiceImpl;
import simple.myboard.myprac.vo.ArticleVO;
import simple.myboard.myprac.vo.CommentVO;

import javax.sql.DataSource;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(locations = {"file:src/main/resources/applicationContext.xml"})
public class ArticleServiceImplTest {

    private static final Logger logger = LoggerFactory.getLogger(ArticleDaoJdbcTest.class);

    List<ArticleVO> articleList;
    private ArticleDao articleDao;
    private ArticleServiceImpl articleService;
    private CommentService commentService;


    @BeforeEach
    public void setUp() {
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        this.articleDao = context.getBean("articleDao", ArticleDao.class);
        this.articleService = context.getBean("articleService", ArticleServiceImpl.class);  // 내가 테스트하려는 특정 클래스는 구현 클래스로 받기
        this.commentService = context.getBean("commentService", CommentService.class);  // 테스트에 쓰이는 의존 클래스는 인터페이스로 받음
        //
        TestUtil.clearTestData();
        int lastIndexMember = TestUtil.getLastIndexMember();
        this.articleList = Arrays.asList(
                new ArticleVO(lastIndexMember, "testTitle01", "testContents01"),
                new ArticleVO(lastIndexMember, "testTitle02", "testContents02"),
                new ArticleVO(lastIndexMember, "testTitle03", "testContents03"),
                new ArticleVO(lastIndexMember, "testTitle04", "testContents04"),
                new ArticleVO(lastIndexMember, "testTitle05", "testContents05")
        );

    }

    @AfterEach
    public void endEach() {
        TestUtil.clearTestData();
    }

    @Test
    public void addArticleAndGetArticleTest() {
        //
        ArticleVO article1 = this.articleList.get(0);
        ArticleVO article2 = this.articleList.get(1);
        this.articleService.addArticle(article1);
        this.articleService.addArticle(article2);
        //
        int lastIndex = this.articleService.getLastIndexArticle();
        ArticleVO article3 = this.articleService.getArticleBySeq(lastIndex-1);
        ArticleVO article4 = this.articleService.getArticleBySeq(lastIndex);
        //
        this.checkSameArticle(article1, article3);
        this.checkSameArticle(article2, article4);
    }


    @Test
    public void getArticleEmptyTest() {
        int overIndex = this.articleService.getLastIndexArticle();
        Assertions.assertNull(this.articleService.getArticleBySeq(overIndex));

    }

    @Test
    public void deleteArticleTest() {
        //
        this.articleService.addArticle(this.articleList.get(0));
        this.articleService.addArticle(this.articleList.get(1));
        this.articleService.addArticle(this.articleList.get(2));
        this.articleService.addArticle(this.articleList.get(3));
        int lastIndex = this.articleService.getLastIndexArticle();
        //
        Assertions.assertEquals(4, this.articleDao.getCountAllArticle());
        this.articleService.deleteArticleBySeq(lastIndex);
        Assertions.assertEquals(3, this.articleDao.getCountAllArticle());
        this.articleService.deleteArticleBySeq(lastIndex-1);
        Assertions.assertEquals(2, this.articleDao.getCountAllArticle());
        this.articleService.deleteArticleBySeq(lastIndex-2);
        Assertions.assertEquals(1, this.articleDao.getCountAllArticle());
        this.articleService.deleteArticleBySeq(lastIndex-3);
        Assertions.assertEquals(0, this.articleDao.getCountAllArticle());
    }

    @Test
    public void deleteArticleAndCommentTest() {
        // article1 과 article2 를 참조하는 comment 를 각각 3개씩 등록함.
        // article2 만 삭제 후에 lastIndex 와 countAll 이 맞는지 확인
        ArticleVO article1 = this.articleList.get(0);
        this.articleService.addArticle(article1);
        int lastIndexArticle1 = this.articleService.getLastIndexArticle();
        ArticleVO article2 = this.articleList.get(1);
        this.articleService.addArticle(article2);
        int lastIndexArticle2 = this.articleService.getLastIndexArticle();
        List<CommentVO> commentList1 = Arrays.asList(
             new CommentVO(article1.getMemberSeq(), lastIndexArticle1, "test1"),
             new CommentVO(article1.getMemberSeq(), lastIndexArticle1, "test2"),
             new CommentVO(article1.getMemberSeq(), lastIndexArticle1, "test3")
        );
        List<CommentVO> commentList2 = Arrays.asList(
                new CommentVO(article2.getMemberSeq(), lastIndexArticle2, "test4"),
                new CommentVO(article2.getMemberSeq(), lastIndexArticle2, "test5"),
                new CommentVO(article2.getMemberSeq(), lastIndexArticle2, "test6")
        );
        Iterator<CommentVO> iter1 = commentList1.iterator();
        while(iter1.hasNext()) {
            this.commentService.addComment(iter1.next());
        }
        int lastIndexCommentBefore = this.commentService.getLastIndexComment();
        Iterator<CommentVO> iter2 = commentList2.iterator();
        while(iter2.hasNext()) {
            this.commentService.addComment(iter2.next());
        }
        int lastIndexCommentAfter = this.commentService.getLastIndexComment();
        //
        Assertions.assertTrue(lastIndexCommentBefore < lastIndexCommentAfter);
        this.articleService.deleteArticleBySeq(lastIndexArticle2);
        Assertions.assertEquals(lastIndexArticle1, this.articleService.getLastIndexArticle());
        Assertions.assertEquals(lastIndexCommentBefore, this.commentService.getLastIndexComment());
    }

    @Test
    public void deleteArticleAndCommentTransactionTest() {
        // 3개를 등록 후에 삭제하다가 예외 발생 - 그대로인지?
        ArticleVO article = this.articleList.get(0);
        this.articleService.addArticle(article);
        int lastIndexArticle = this.articleService.getLastIndexArticle();
        List<CommentVO> commnetList = Arrays.asList(
                new CommentVO(article.getMemberSeq(), lastIndexArticle, "test1"),
                new CommentVO(article.getMemberSeq(), lastIndexArticle, "test2"),
                new CommentVO(article.getMemberSeq(), lastIndexArticle, "test3")
        );
        Iterator<CommentVO> iter = commnetList.iterator();
        while(iter.hasNext()) {
            this.commentService.addComment(iter.next());
        }
        int lastIndexCommentBefore = this.commentService.getLastIndexComment();
        //
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        ArticleServiceImpl testArticleService = context.getBean("articleService", ArticleServiceImpl.class);
        DataSource testDataSource = context.getBean("dataSource", DataSource.class);
        ArticleTestDao testArticleDao = new ArticleTestDao();
        testArticleDao.setDataSource(testDataSource);
        testArticleService.setArticleDao(testArticleDao);
        //
        Assertions.assertThrows(ArticleTestException.class, ()->{testArticleService.deleteArticleBySeq(lastIndexArticle);});
        Assertions.assertEquals(commnetList.size(), this.commentService.getCountAllComment());
        Assertions.assertEquals(lastIndexCommentBefore, this.commentService.getLastIndexComment());

    }


    @Test
    public void updateArticleTest() {
        //
        ArticleVO article0 = this.articleList.get(0);
        this.articleService.addArticle(article0);
        int lastIndex = this.articleService.getLastIndexArticle();
        ArticleVO article1 = this.articleService.getArticleBySeq(lastIndex);
        this.checkSameArticle(article0, article1);
        //
        ArticleVO article2 = this.articleList.get(4);
        article2.setArticleSeq(lastIndex);
        article2.setCreateTime(article1.getCreateTime());
        this.articleService.updateArticle(article2);
        ArticleVO article3 = this.articleService.getArticleBySeq(lastIndex);
        //
        this.checkSameArticle(article2, article3);

    }
    private void checkSameArticle(ArticleVO article1, ArticleVO article2) {
        Assertions.assertEquals(article1.getMemberSeq(), article2.getMemberSeq());
        Assertions.assertEquals(article1.getTitle(), article2.getTitle());
        Assertions.assertEquals(article1.getContents(), article2.getContents());
        Assertions.assertEquals(article1.getIsDel(), article2.getIsDel());
        Assertions.assertEquals(article1.getCreateTime().truncatedTo(ChronoUnit.MINUTES), article2.getCreateTime().truncatedTo(ChronoUnit.MINUTES));
        Assertions.assertEquals(article1.getUpdateTime().truncatedTo(ChronoUnit.MINUTES), article2.getUpdateTime().truncatedTo(ChronoUnit.MINUTES));
    }




    @Test
    public void getArticleReadOnlyTransactionTest() {
        //
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        DataSource dataSourceTest = context.getBean("dataSource", DataSource.class);
        ArticleDaoJdbc articleDaoTest = new ArticleTestDao();
        articleDaoTest.setDataSource(dataSourceTest);
        ArticleService articleServiceTest = context.getBean("articleService", ArticleService.class);
        articleServiceTest.setArticleDao(articleDaoTest);
        //
        Assertions.assertThrows(TransientDataAccessResourceException.class,
                ()->{ articleServiceTest.getArticleBySeq(0); });
    }

    public static class ArticleTestDao extends ArticleDaoJdbc {
        @Override
        public ArticleVO getArticleBySeq(int articleSeq) {
            super.insertArticle(new ArticleVO(1, "testTitle01", "testContents01"));
            return null;
        }
        @Override
        public void deleteArticleBySeq(int articleSeq) {
            super.deleteArticleBySeq(articleSeq);
            throw new ArticleTestException();
        }
    }

    public static class ArticleTestException extends RuntimeException {
        // empty class
    }

}
