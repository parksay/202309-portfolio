package simple.myboard.myprac;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import simple.myboard.myprac.dao.ArticleDaoJdbc;
import simple.myboard.myprac.vo.ArticleVO;

import javax.sql.DataSource;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(locations    = {"file:src/main/resources/applicationContext.xml"})
public class ArticleDaoJdbcTest {

    private static final Logger logger = LoggerFactory.getLogger(ArticleDaoJdbcTest.class);

    private ArticleDaoJdbc articleDao;
    List<ArticleVO> articleList;

    @BeforeEach
    public void setUp() throws ParseException {
        //
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        DataSource dataSource = context.getBean("dataSource", DataSource.class);
        this.articleDao = new ArticleDaoJdbc();
        this.articleDao.setDataSource(dataSource);
        TestUtil.clearTestData();
        int lastIndexMember = TestUtil.getLastIndexMember();
        //
        String pattern = "yyyy-MM-dd HH:mm:ss"; // LocalDateTime 을 formatter 로 변환해 보기
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        //
        articleList = Arrays.asList(
                new ArticleVO(lastIndexMember, "testTitle01", "testContents01", 0, LocalDateTime.parse("2023-09-20 14:08:11", formatter), LocalDateTime.parse("2023-09-20 14:08:11", formatter)),
                new ArticleVO(lastIndexMember, "testTitle02", "testContents02", 0, LocalDateTime.parse("2023-09-20 14:08:22", formatter), LocalDateTime.parse("2023-09-20 14:08:22", formatter)),
                new ArticleVO(lastIndexMember, "testTitle03", "testContents03", 0, LocalDateTime.parse("2023-09-20 14:08:33", formatter), LocalDateTime.parse("2023-09-20 14:08:33", formatter)),
                new ArticleVO(lastIndexMember, "testTitle04", "testContents04", 0, LocalDateTime.parse("2023-09-20 14:08:44", formatter), LocalDateTime.parse("2023-09-20 14:08:44", formatter)),
                new ArticleVO(lastIndexMember, "testTitle05", "testContents05", 0, LocalDateTime.parse("2023-09-20 14:08:55", formatter), LocalDateTime.parse("2023-09-20 14:08:55", formatter))
        );
    }

    @AfterEach
    public void endEach() {
        TestUtil.clearTestData();
    }

    @Test
    public void insertArticleTest() {
        //
        this.articleDao.insertArticle(this.articleList.get(0));
        Assertions.assertEquals(1, this.articleDao.getCountAllArticle());
        this.articleDao.insertArticle(this.articleList.get(1));
        Assertions.assertEquals(2, this.articleDao.getCountAllArticle());
        this.articleDao.insertArticle(this.articleList.get(2));
        Assertions.assertEquals(3, this.articleDao.getCountAllArticle());
        //
        int lastIndex = this.articleDao.getLastIndexArticle();
        Assertions.assertEquals(this.articleList.get(0).getContents(), this.articleDao.getArticleBySeq(lastIndex-2).getContents());
        Assertions.assertEquals(this.articleList.get(1).getContents(), this.articleDao.getArticleBySeq(lastIndex-1).getContents());
        Assertions.assertEquals(this.articleList.get(2).getContents(), this.articleDao.getArticleBySeq(lastIndex).getContents());
    }

    @Test
    public void updateArticleTest() {
        //
        ArticleVO article0 = this.articleList.get(0);
        this.articleDao.insertArticle(article0);
        int lastIndex1 = this.articleDao.getLastIndexArticle();
        article0.setArticleSeq(lastIndex1);
        ArticleVO article1 = this.articleDao.getArticleBySeq(lastIndex1);
        this.checkSameArticle(article0, article1);
        //
        String newTitle = "this is new title";
        String newContents = "this is new contents";
        LocalDateTime newTime = LocalDateTime.of(2023,10,6,13,26,17);
        article1.setTitle(newTitle);
        article1.setContents(newContents);
        article1.setCreateTime(newTime);
        article1.setUpdateTime(newTime);
        //
        this.articleDao.updateArticle(article1);
        int lastIndex2 = this.articleDao.getLastIndexArticle();
        ArticleVO article2 = this.articleDao.getArticleBySeq(lastIndex2);

        //
        this.checkSameArticle(article1, article2);


    }

    @Test
    public void getArticleTest() {
        //
        ArticleVO article1 = this.articleList.get(1);
        ArticleVO article2 = this.articleList.get(2);
        this.articleDao.insertArticle(article1);
        this.articleDao.insertArticle(article2);
        //
        int lastIndex = this.articleDao.getLastIndexArticle();
        ArticleVO article3 = this.articleDao.getArticleBySeq(lastIndex-1);
        ArticleVO article4 = this.articleDao.getArticleBySeq(lastIndex);
        // check same
        article1.setArticleSeq(lastIndex-1);
        article2.setArticleSeq(lastIndex);
        this.checkSameArticle(article1, article3);
        this.checkSameArticle(article2, article4);

    }

    @Test
    public void deleteArticleTest() {
        //
        ArticleVO article1 = this.articleList.get(1);
        ArticleVO article2 = this.articleList.get(2);
        ArticleVO article3 = this.articleList.get(3);
        ArticleVO article4 = this.articleList.get(4);
        //
        this.articleDao.insertArticle(article1);
        this.articleDao.insertArticle(article2);
        this.articleDao.insertArticle(article3);
        this.articleDao.insertArticle(article4);
        //
        int lastIndex = this.articleDao.getLastIndexArticle();
        //
        this.articleDao.deleteArticleBySeq(lastIndex);
        Assertions.assertEquals(lastIndex-1, this.articleDao.getLastIndexArticle());
        this.articleDao.deleteArticleBySeq(lastIndex-1);
        Assertions.assertEquals(lastIndex-2, this.articleDao.getLastIndexArticle());
        this.articleDao.deleteArticleBySeq(lastIndex-2);
        Assertions.assertEquals(lastIndex-3, this.articleDao.getLastIndexArticle());
        this.articleDao.deleteArticleBySeq(lastIndex-3);
    }

    @Test
    public void deleteAllArticleTest() {
        this.articleDao.deleteAllArticle();
        Assertions.assertEquals(0, this.articleDao.getCountAllArticle());
    }

    private void checkSameArticle(ArticleVO article1, ArticleVO article2) {
        Assertions.assertEquals(article1.getArticleSeq(), article2.getArticleSeq());
        Assertions.assertEquals(article1.getMemberSeq(), article2.getMemberSeq());
        Assertions.assertEquals(article1.getTitle(), article2.getTitle());
        Assertions.assertEquals(article1.getContents(), article2.getContents());
        Assertions.assertEquals(article1.getIsDel(), article2.getIsDel());
        Assertions.assertEquals(article1.getCreateTime(), article2.getCreateTime());
        Assertions.assertEquals(article1.getUpdateTime(), article2.getUpdateTime());
    }
}
