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
import simple.myboard.myprac.dao.CommentDao;
import simple.myboard.myprac.vo.CommentVO;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(locations    = {"file:src/main/resources/applicationContext.xml"})
public class CommentDaoJdbcTest {

    private static final Logger logger = LoggerFactory.getLogger(CommentDaoJdbcTest.class);

    private ArticleDaoJdbc articleDao;
    private CommentDao commentDao;
    List<CommentVO> commentList;

    @BeforeEach
    public void setUp() throws ParseException {
        //
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        this.commentDao = context.getBean("commentDao", CommentDao.class);
        //
        TestUtil.clearTestData();
        int lastIndexMember1 = TestUtil.getLastIndexMember();
        int lastIndexMember2 = TestUtil.getLastIndexMember();
        int lastIndexArticle1 = TestUtil.getLastIndexArticle();
        int lastIndexArticle2 = TestUtil.getLastIndexArticle();
        LocalDateTime newTime = LocalDateTime.of(2023, 10, 16, 10, 51, 36);
        this.commentList = Arrays.asList(
                new CommentVO(lastIndexMember1, lastIndexArticle1, "testContents1", 0, newTime, newTime),
                new CommentVO(lastIndexMember1, lastIndexArticle1, "testContents2", 0, newTime, newTime),
                new CommentVO(lastIndexMember1, lastIndexArticle2, "testContents3", 0, newTime, newTime),
                new CommentVO(lastIndexMember2, lastIndexArticle2, "testContents4", 0, newTime, newTime),
                new CommentVO(lastIndexMember2, lastIndexArticle2, "testContents5", 0, newTime, newTime),
                new CommentVO(lastIndexMember2, lastIndexArticle1, "testContents5", 0, newTime, newTime),
                new CommentVO(lastIndexMember2, lastIndexArticle1, "testContents5", 0, newTime, newTime)
        );
    }

    @AfterEach
    public void endEach() {
        TestUtil.clearTestData();
    }

    @Test
    public void insertAndGetCommentTest() {
        //
        CommentVO comment0 = this.commentList.get(0);
        CommentVO comment2 = this.commentList.get(2);
        CommentVO comment3 = this.commentList.get(3);
        CommentVO comment5 = this.commentList.get(5);
        // 하나씩 등록되는 거 맞는지
        Assertions.assertEquals(0, this.commentDao.getCountAllComment());
        this.commentDao.insertComment(comment0);
        Assertions.assertEquals(1, this.commentDao.getCountAllComment());
        this.commentDao.insertComment(comment2);
        Assertions.assertEquals(2, this.commentDao.getCountAllComment());
        this.commentDao.insertComment(comment3);
        Assertions.assertEquals(3, this.commentDao.getCountAllComment());
        this.commentDao.insertComment(comment5);
        Assertions.assertEquals(4, this.commentDao.getCountAllComment());
        // 로컬 데이터랑 DB에 등록된 데이터랑 같은지 비교하기
        int lastIndexComment = this.commentDao.getLastIndexComment();
        this.checkSameComment(comment5, this.commentDao.getCommentBySeq(lastIndexComment));
        this.checkSameComment(comment3, this.commentDao.getCommentBySeq(lastIndexComment-1));
        this.checkSameComment(comment2, this.commentDao.getCommentBySeq(lastIndexComment-2));
        this.checkSameComment(comment0, this.commentDao.getCommentBySeq(lastIndexComment-3));
    }

    private void checkSameComment(CommentVO comment1, CommentVO comment2) {
        Assertions.assertEquals(comment1.getArticleSeq(), comment2.getArticleSeq());
        Assertions.assertEquals(comment1.getMemberSeq(), comment2.getMemberSeq());
        Assertions.assertEquals(comment1.getContents(), comment2.getContents());
        Assertions.assertEquals(comment1.getIsDel(), comment2.getIsDel());
        Assertions.assertEquals(comment1.getCreateTime(), comment2.getCreateTime());
        Assertions.assertEquals(comment1.getUpdateTime(), comment2.getUpdateTime());
    }


    @Test
    public void getCommentBySeqEmptyTest() {
        // 없으면 null 던지기
        int lastIndexComment = this.commentDao.getLastIndexComment();
        Assertions.assertNull(this.commentDao.getCommentBySeq(lastIndexComment+1));
    }

    @Test
    public void updateCommentTest() {
        // 새로 넣은 comment 와 불러온 comment 가 같은지 확인
        CommentVO comment0 = this.commentList.get(0);
        this.commentDao.insertComment(comment0);
        int lastIndexComment = this.commentDao.getLastIndexComment();
        CommentVO comment1 = this.commentDao.getCommentBySeq(lastIndexComment);
        this.checkSameComment(comment0, comment1);
        // update - 로컬
        LocalDateTime updateTime = LocalDateTime.of(2023, 10, 13, 21, 17, 53);
        comment1.setMemberSeq(1);
        comment1.setArticleSeq(1);
        comment1.setContents("newContents");
        comment1.setIsDel(1);
        comment1.setCreateTime(updateTime);
        comment1.setUpdateTime(updateTime);
        // update - db
        this.commentDao.updateComment(comment1);
        int lastIndexComment2 = this.commentDao.getLastIndexComment();
        Assertions.assertEquals(lastIndexComment, lastIndexComment2);
        CommentVO comment2 = this.commentDao.getCommentBySeq(lastIndexComment2);
        checkSameComment(comment1, comment2);

    }

    @Test
    public void deleteCommentBySeqTest() {
        // 테스트 데이터 넣기
        this.commentDao.insertComment(this.commentList.get(0));
        this.commentDao.insertComment(this.commentList.get(1));
        this.commentDao.insertComment(this.commentList.get(2));
        this.commentDao.insertComment(this.commentList.get(3));
        Assertions.assertEquals(4, this.commentDao.getCountAllComment());
        // 4번만 삭제되고 나머지는 그대로인지?
        int lastIndexComment = this.commentDao.getLastIndexComment();
        this.commentDao.deleteCommentBySeq(lastIndexComment-3);
        Assertions.assertEquals(3, this.commentDao.getCountAllComment());
        Assertions.assertNotNull(this.commentDao.getCommentBySeq(lastIndexComment));
        Assertions.assertNotNull(this.commentDao.getCommentBySeq(lastIndexComment-1));
        Assertions.assertNotNull(this.commentDao.getCommentBySeq(lastIndexComment-2));
        Assertions.assertNull(this.commentDao.getCommentBySeq(lastIndexComment-3));
        // 3번만 삭제되고 나머지는 그대로인지?
        this.commentDao.deleteCommentBySeq(lastIndexComment-2);
        Assertions.assertEquals(2, this.commentDao.getCountAllComment());
        Assertions.assertNotNull(this.commentDao.getCommentBySeq(lastIndexComment));
        Assertions.assertNotNull(this.commentDao.getCommentBySeq(lastIndexComment-1));
        Assertions.assertNull(this.commentDao.getCommentBySeq(lastIndexComment-2));
        // 2번만 삭제되고 나머지는 그대로인지?
        this.commentDao.deleteCommentBySeq(lastIndexComment-1);
        Assertions.assertEquals(1, this.commentDao.getCountAllComment());
        Assertions.assertNotNull(this.commentDao.getCommentBySeq(lastIndexComment));
        Assertions.assertNull(this.commentDao.getCommentBySeq(lastIndexComment-1));
        // 1번만 삭제되고 나머지는 그대로인지?
        this.commentDao.deleteCommentBySeq(lastIndexComment);
        Assertions.assertEquals(0, this.commentDao.getCountAllComment());
        Assertions.assertNull(this.commentDao.getCommentBySeq(lastIndexComment));
    }

    @Test
    public void getCommentListByMemberSeqTest() {
        // 픽스처 comment 모두 등록
        Iterator<CommentVO> iterLocal = this.commentList.iterator();
        while(iterLocal.hasNext()) {
            this.commentDao.insertComment(iterLocal.next());
        }
        Assertions.assertEquals(this.commentList.size(), this.commentDao.getCountAllComment());
        // 픽스처 list 에 filter 돌려서 조건에 맞는 item 만으로 구성된 list 새로 만들기
        int targetMemberIndex = this.commentList.get(0).getMemberSeq();
        List<CommentVO> commentListLocal = this.commentList.stream().filter(e -> e.getMemberSeq() == targetMemberIndex).collect(Collectors.toList());
        // 등록된 comment 중에 member_seq 가 특정 값인 comment 만 list 로 받아오기
        List<CommentVO> commentListGet = this.commentDao.getCommentListByMemberSeq(targetMemberIndex);
        // 픽스처 list 와 받아온  list 비교해 보기
        Assertions.assertEquals(commentListGet.size(), commentListLocal.size());
        Iterator<CommentVO> iterGet = commentListGet.iterator();
        while (iterGet.hasNext()) {
            CommentVO item = iterGet.next();
            Assertions.assertEquals(item.getMemberSeq(), targetMemberIndex);
        }
    }

    @Test
    public void getCommentListByMemberSeqEmptyTest() {
        // 없으면 null 던지기
        int lasIndexMember = TestUtil.getLastIndexMember();
        Assertions.assertNull(this.commentDao.getCommentListByMemberSeq(lasIndexMember));
    }

    @Test
    public void getCommentListByArticleSeqTest() {
        //
        // 일단 테스트 데이터 다 넣음
        this.commentDao.insertComment(this.commentList.get(0));
        this.commentDao.insertComment(this.commentList.get(1));
        this.commentDao.insertComment(this.commentList.get(2));
        this.commentDao.insertComment(this.commentList.get(3));
        this.commentDao.insertComment(this.commentList.get(4));
        this.commentDao.insertComment(this.commentList.get(5));
        this.commentDao.insertComment(this.commentList.get(6));
        //
        // this.commentList 를 articleSeq1 로 필터링한 리스트와 DB 에서 articleSeq1 꺼내온 리스트가 같은지
        int articleSeq1 = this.commentList.get(1).getArticleSeq();
        List<CommentVO> listGet1 = this.commentDao.getCommentListByArticleSeq(articleSeq1);
        List<CommentVO> listLocal1 = this.commentList.stream().filter((ele)->ele.getArticleSeq() == articleSeq1).collect(Collectors.toList());
        Assertions.assertTrue(listGet1.containsAll(listLocal1));
        Assertions.assertTrue(listLocal1.containsAll(listGet1));
        //
        // this.commentList 를 articleSeq2 로 필터링한 리스트와 DB 에서 articleSeq2 꺼내온 리스트가 같은지
        int articleSeq2 = this.commentList.get(2).getArticleSeq();
        List<CommentVO> listGet2 = this.commentDao.getCommentListByArticleSeq(articleSeq2);
        List<CommentVO> listLocal2 = this.commentList.stream().filter((ele)->ele.getArticleSeq() == articleSeq2).collect(Collectors.toList());
        Assertions.assertTrue(listGet2.containsAll(listLocal2));
        Assertions.assertTrue(listLocal2.containsAll(listGet2));

    }

    @Test
    public void getCommentListByArticleSeqEmptyTest() {
        //
        int lasIndexArticle = TestUtil.getLastIndexArticle();
        Assertions.assertNull(this.commentDao.getCommentListByArticleSeq(lasIndexArticle));
    }

    @Test
    public void deleteAllCommentTest() {
        this.commentDao.deleteAllComment();
        Assertions.assertEquals(0, this.commentDao.getCountAllComment());
    }

    @Test
    public void deleteAllCommentByMemberSeqTest() {
        //
        this.commentDao.insertComment(this.commentList.get(1));
        this.commentDao.insertComment(this.commentList.get(2));
        int articleSeq1 = this.commentList.get(1).getArticleSeq();
        int articleSeq2 = this.commentList.get(2).getArticleSeq();
        Assertions.assertNotEquals(0, this.commentDao.getCommentListByArticleSeq(articleSeq1).size());
        Assertions.assertNotEquals(0, this.commentDao.getCommentListByArticleSeq(articleSeq2).size());
        //
        this.commentDao.deleteAllCommentByArticleSeq(articleSeq1);
        //
        Assertions.assertEquals(0, this.commentDao.getCommentListByArticleSeq(articleSeq1).size());
        Assertions.assertNotEquals(0, this.commentDao.getCommentListByArticleSeq(articleSeq2).size());

    }

    @Test
    public void deleteAllCommentByArticleSeqTest() {
        //
        this.commentDao.insertComment(this.commentList.get(2));
        this.commentDao.insertComment(this.commentList.get(3));
        int memberSeq1 = this.commentList.get(1).getMemberSeq();
        int memberSeq2 = this.commentList.get(2).getMemberSeq();
        Assertions.assertNotEquals(0, this.commentDao.getCommentListByMemberSeq(memberSeq1).size());
        Assertions.assertNotEquals(0, this.commentDao.getCommentListByMemberSeq(memberSeq2).size());
        //
        this.commentDao.deleteAllCommentByArticleSeq(memberSeq1);
        //
        Assertions.assertEquals(0, this.commentDao.getCommentListByMemberSeq(memberSeq1).size());
        Assertions.assertNotEquals(0, this.commentDao.getCommentListByMemberSeq(memberSeq2).size());
    }


    @Test
    public void getCountAllCommentByMemberSeqTest() {
        //
        int memberSeq1 = this.commentList.get(1).getMemberSeq();
        int memberSeq2 = this.commentList.get(2).getMemberSeq();
        Assertions.assertEquals(0, this.commentDao.getCountAllCommentByMemberSeq(memberSeq1));
        Assertions.assertEquals(0, this.commentDao.getCountAllCommentByMemberSeq(memberSeq2));
        //
        this.commentDao.insertComment(this.commentList.get(3));
        Assertions.assertEquals(1, this.commentDao.getCountAllCommentByMemberSeq(memberSeq2));
        this.commentDao.insertComment(this.commentList.get(4));
        Assertions.assertEquals(2, this.commentDao.getCountAllCommentByMemberSeq(memberSeq2));
        this.commentDao.insertComment(this.commentList.get(5));
        Assertions.assertEquals(3, this.commentDao.getCountAllCommentByMemberSeq(memberSeq2));
        Assertions.assertEquals(0, this.commentDao.getCountAllCommentByMemberSeq(memberSeq1));
    }

    @Test
    public void getCountAllCommentByArticleSeqTest() {
        //
        int articleSeq1 = this.commentList.get(1).getArticleSeq();
        int articleSeq2 = this.commentList.get(2).getArticleSeq();
        Assertions.assertEquals(0, this.commentDao.getCountAllCommentByArticleSeq(articleSeq1));
        Assertions.assertEquals(0, this.commentDao.getCountAllCommentByArticleSeq(articleSeq2));
        //
        this.commentDao.insertComment(this.commentList.get(3));
        Assertions.assertEquals(1, this.commentDao.getCountAllCommentByArticleSeq(articleSeq2));
        this.commentDao.insertComment(this.commentList.get(4));
        Assertions.assertEquals(2, this.commentDao.getCountAllCommentByArticleSeq(articleSeq2));
        Assertions.assertEquals(0, this.commentDao.getCountAllCommentByArticleSeq(articleSeq1));
    }


}
