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
import simple.myboard.myprac.dao.ArticleDaoJdbc;
import simple.myboard.myprac.service.ArticleService;
import simple.myboard.myprac.serviceimpl.ArticleServiceImpl;
import simple.myboard.myprac.vo.ArticleVO;

import javax.sql.DataSource;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(locations = {"file:src/main/resources/applicationContext.xml"})
public class ArticleServiceImplTest {

    private static final Logger logger = LoggerFactory.getLogger(ArticleDaoJdbcTest.class);

    List<ArticleVO> articleList;
    private ArticleDao articleDao;
    private ArticleServiceImpl articleService;


    @BeforeEach
    public void setUp() {
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        this.articleDao = context.getBean("articleDao", ArticleDao.class);
        this.articleService = context.getBean("articleService", ArticleServiceImpl.class);  // 내가 테스트하려는 특정 클래스
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
    }

    // TODO: getEmptyFailTest
}
