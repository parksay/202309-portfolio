package simple.myboard.myprac;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import simple.myboard.myprac.dao.ArticleDao;
import simple.myboard.myprac.serviceimpl.ArticleServiceImpl;
import simple.myboard.myprac.vo.ArticleVO;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(locations = {"file:src/main/resources/applicationContext.xml"})
public class ArticleServiceImplTest {

    private static final Logger logger = LoggerFactory.getLogger(ArticleDaoJDBCTest.class);

    List<ArticleVO> articleList;
    private ArticleDao dao;
    private ArticleServiceImpl service;



    @BeforeEach
    public void setUp() {
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        this.dao = context.getBean("articleDao", ArticleDao.class);
        this.service = new ArticleServiceImpl();
        this.service.setArticleDao(this.dao);
        this.articleList = Arrays.asList(
                new ArticleVO(1, "testTitle01", "testContents01"),
                new ArticleVO(1, "testTitle02", "testContents02"),
                new ArticleVO(2, "testTitle03", "testContents03"),
                new ArticleVO(2, "testTitle04", "testContents04"),
                new ArticleVO(3, "testTitle05", "testContents05")
        );

    }

    @Test
    public void addArticleAndGetArticleTest() {
        //
        ArticleVO article1 = this.articleList.get(0);
        ArticleVO article2 = this.articleList.get(1);
        this.service.addArticle(article1);
        this.service.addArticle(article2);
        //
        int lastIndex = this.dao.getLastIndexArticle();
        ArticleVO article3 = this.service.getArticleBySeq(lastIndex-1);
        ArticleVO article4 = this.service.getArticleBySeq(lastIndex);
        //
        this.checkSameArticle(article1, article3);
        this.checkSameArticle(article2, article4);
    }


    @Test
    public void deleteArticleTest() {
        //
        this.dao.deleteAllArticle();
        //
        this.service.addArticle(this.articleList.get(0));
        this.service.addArticle(this.articleList.get(1));
        this.service.addArticle(this.articleList.get(2));
        this.service.addArticle(this.articleList.get(3));
        int lastIndex = this.dao.getLastIndexArticle();
        //
        Assertions.assertEquals(4, this.dao.getCountArticle());
        this.service.delteArticle(lastIndex);
        Assertions.assertEquals(3, this.dao.getCountArticle());
        this.service.delteArticle(lastIndex-1);
        Assertions.assertEquals(2, this.dao.getCountArticle());
        this.service.delteArticle(lastIndex-2);
        Assertions.assertEquals(1, this.dao.getCountArticle());
        this.service.delteArticle(lastIndex-3);
        Assertions.assertEquals(0, this.dao.getCountArticle());


    }


    @Test
    public void updateArticleTest() {
        //
        this.dao.deleteAllArticle();
        ArticleVO article0 = this.articleList.get(0);
        this.service.addArticle(article0);
        int lastIndex = this.dao.getLastIndexArticle();
        ArticleVO article1 = this.service.getArticleBySeq(lastIndex);
        this.checkSameArticle(article0, article1);
        //
        ArticleVO article2 = this.articleList.get(4);
        article2.setArticleSeq(lastIndex);
        article2.setCreateTime(article1.getCreateTime());
        this.service.updateArticle(article2);
        ArticleVO article3 = this.service.getArticleBySeq(lastIndex);
        //
        this.checkSameArticle(article2, article3);

    }
    private void checkSameArticle(ArticleVO article1, ArticleVO article2) {
        //
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        //
        Assertions.assertEquals(article1.getMemberSeq(), article2.getMemberSeq());
        Assertions.assertEquals(article1.getTitle(), article2.getTitle());
        Assertions.assertEquals(article1.getContents(), article2.getContents());
        Assertions.assertEquals(article2.getIsDel(), 0);
        Assertions.assertEquals(formatter.format(article2.getCreateTime()), formatter.format(new Date()));
        Assertions.assertEquals(formatter.format(article2.getUpdateTime()), formatter.format(new Date()));
    }

}
