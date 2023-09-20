package simple.myboard.myprac;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import simple.myboard.myprac.dao.ArticleDao;
import simple.myboard.myprac.vo.ArticleVO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@ExtendWith(SpringExtension.class)
//@ContextConfiguration(locations = {"file:src/main/resources/applicationContext.xml"})
public class ArticleDaoJDBCTest {

    private static final Logger logger = LoggerFactory.getLogger(ArticleDaoJDBCTest.class);

    private ArticleDao dao;
    List<ArticleVO> articleList;

    @BeforeEach
    public void setUp() throws ParseException {
        //
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        this.dao = context.getBean("articleDao", ArticleDao.class);
        //
        String pattern = "yyyyMMddhhmmss";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        //
        articleList = Arrays.asList(
                new ArticleVO(1, "testTitle01", "testContents01", 0, formatter.parse("20230920140811"), formatter.parse("20230920140811")),
                new ArticleVO(1, "testTitle02", "testContents02", 0, formatter.parse("20230920140822"), formatter.parse("20230920140822")),
                new ArticleVO(2, "testTitle03", "testContents03", 0, formatter.parse("20230920140833"), formatter.parse("20230920140833")),
                new ArticleVO(2, "testTitle04", "testContents04", 0, formatter.parse("20230920140844"), formatter.parse("20230920140844")),
                new ArticleVO(3, "testTitle05", "testContents05", 0, formatter.parse("20230920140855"), formatter.parse("20230920140855"))
        );
        //
    }

    @Test
    public void insertArticleTest() {
        //
        this.dao.deleteAllArticle();
        Assertions.assertEquals(0, this.dao.getCountArticle());
        //
        this.dao.insertArticle(this.articleList.get(0));
        Assertions.assertEquals(1, this.dao.getCountArticle());
        this.dao.insertArticle(this.articleList.get(1));
        Assertions.assertEquals(2, this.dao.getCountArticle());
        this.dao.insertArticle(this.articleList.get(2));
        Assertions.assertEquals(3, this.dao.getCountArticle());
        //
        int lastIndex = this.dao.getLastIndexArticle();
        Assertions.assertEquals(this.articleList.get(0).getContents(), this.dao.getArticle(lastIndex-2).getContents());
        Assertions.assertEquals(this.articleList.get(1).getContents(), this.dao.getArticle(lastIndex-1).getContents());
        Assertions.assertEquals(this.articleList.get(2).getContents(), this.dao.getArticle(lastIndex).getContents());
    }

    @Test
    public void updateArticleTest() {

    }

    @Test
    public void getArticleTest() {
        //
        ArticleVO article1 = this.articleList.get(1);
        ArticleVO article2 = this.articleList.get(2);
        this.dao.insertArticle(article1);
        this.dao.insertArticle(article2);
        //
        int lastIndex = this.dao.getLastIndexArticle();
        ArticleVO article3 = this.dao.getArticle(lastIndex-1);
        ArticleVO article4 = this.dao.getArticle(lastIndex);
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
        this.dao.insertArticle(article1);
        this.dao.insertArticle(article2);
        this.dao.insertArticle(article3);
        this.dao.insertArticle(article4);
        //
        int lastIndex = this.dao.getLastIndexArticle();
        //
        this.dao.deleteArticle(lastIndex);
        Assertions.assertEquals(lastIndex-1, this.dao.getLastIndexArticle());
        this.dao.deleteArticle(lastIndex-1);
        Assertions.assertEquals(lastIndex-2, this.dao.getLastIndexArticle());
        this.dao.deleteArticle(lastIndex-2);
        Assertions.assertEquals(lastIndex-3, this.dao.getLastIndexArticle());
    }

    @Test
    public void deleteAllArticleTest() {
        this.dao.deleteAllArticle();
        Assertions.assertEquals(0, this.dao.getCountArticle());
    }

    @Test
    public void dateTest() throws ParseException {
        String pattern = "yyyy-MM-dd hh:mm:ss";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        Date result1 = formatter.parse("2023-09-20 14:08:22");
        Date result2 = formatter.parse("2023-09-20 14:08:22");
        Date result3 = formatter.parse("2023-09-20 14:08:23");
        Assertions.assertEquals(result1, result2);
        Assertions.assertNotEquals(result1, result3);

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
