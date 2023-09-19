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

import java.util.Arrays;
import java.util.List;


@ExtendWith(SpringExtension.class)
//@ContextConfiguration(locations = {"file:src/main/resources/applicationContext.xml"})
public class ArticleDaoJDBCTest {

    private static final Logger logger = LoggerFactory.getLogger(ArticleDaoJDBCTest.class);

    private ArticleDao dao;
    List<ArticleVO> articleList;

    @BeforeEach
    public void setUp() {
        //
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        this.dao = context.getBean("articleDao", ArticleDao.class);
        //
        articleList = Arrays.asList(
                new ArticleVO(1, "testTitle01", "testContents01"),
                new ArticleVO(1, "testTitle02", "testContents02"),
                new ArticleVO(2, "testTitle03", "testContents03"),
                new ArticleVO(2, "testTitle04", "testContents04"),
                new ArticleVO(3, "testTitle05", "testContents05")
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
        Assertions.assertEquals(this.articleList.get(0).getContents(), this.dao.getArticle(1).getContents());
        Assertions.assertEquals(this.articleList.get(1).getContents(), this.dao.getArticle(2).getContents());
        Assertions.assertEquals(this.articleList.get(2).getContents(), this.dao.getArticle(3).getContents());
    }

    @Test
    public void updateArticleTest() {

    }

    @Test
    public void getArticleTest() {

    }

    @Test
    public void deleteArticleTest() {

    }

    @Test
    public void getCountArticleTest() {

    }

    @Test
    public void deleteAllArticleTest() {
        this.dao.deleteAllArticle();
        Assertions.assertEquals(0, this.dao.getCountArticle());
    }

}
