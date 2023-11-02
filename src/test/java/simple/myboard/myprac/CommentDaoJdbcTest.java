package simple.myboard.myprac;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import simple.myboard.myprac.dao.CommentDao;
import simple.myboard.myprac.domain.Comment;

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

    private CommentDao commentDao;
    List<Comment> commentList;

    @BeforeEach
    public void setUp() throws ParseException {
        //
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        this.commentDao = context.getBean("commentDao", CommentDao.class);
        //
        TestUtil.clearTestData();
        Long lastIndexMember1 = TestUtil.getLastIndexMember();
        Long lastIndexMember2 = TestUtil.getLastIndexMember();
        Long lastIndexArticle1 = TestUtil.getLastIndexArticle();
        Long lastIndexArticle2 = TestUtil.getLastIndexArticle();
        LocalDateTime newTime = LocalDateTime.of(2023, 10, 16, 10, 51, 36);
        this.commentList = Arrays.asList(
                new Comment(lastIndexMember1, lastIndexArticle1, "testContents1", 0, newTime, newTime),
                new Comment(lastIndexMember1, lastIndexArticle1, "testContents2", 0, newTime, newTime),
                new Comment(lastIndexMember1, lastIndexArticle2, "testContents3", 0, newTime, newTime),
                new Comment(lastIndexMember2, lastIndexArticle2, "testContents4", 0, newTime, newTime),
                new Comment(lastIndexMember2, lastIndexArticle2, "testContents5", 0, newTime, newTime),
                new Comment(lastIndexMember2, lastIndexArticle1, "testContents5", 0, newTime, newTime),
                new Comment(lastIndexMember2, lastIndexArticle1, "testContents5", 0, newTime, newTime)
        );
    }

    @AfterEach
    public void endEach() {
        TestUtil.clearTestData();
    }

    @Test
    public void insertAndGetCommentTest() {
        //
        Comment comment0 = this.commentList.get(0);
        Comment comment2 = this.commentList.get(2);
        Comment comment3 = this.commentList.get(3);
        Comment comment5 = this.commentList.get(5);
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

    private void checkSameComment(Comment comment1, Comment comment2) {
        Assertions.assertEquals(comment1.getArticleSeq(), comment2.getArticleSeq());
        Assertions.assertEquals(comment1.getMemberSeq(), comment2.getMemberSeq());
        Assertions.assertEquals(comment1.getContents(), comment2.getContents());
        Assertions.assertEquals(comment1.getIsDel(), comment2.getIsDel());
        Assertions.assertEquals(comment1.getCreateTime(), comment2.getCreateTime());
        Assertions.assertEquals(comment1.getUpdateTime(), comment2.getUpdateTime());
    }

    private void checkSameCommentList(List<Comment> commentList1, List<Comment> commentList2) {
        //
        int length1 = commentList1.size();
        int length2 = commentList2.size();
        Assertions.assertEquals(length1, length2);
        //
        for(int i=0; i<length1; i++) {
            this.checkSameComment(commentList1.get(i), commentList2.get(i));
        }
    }


    @Test
    public void updateCommentTest() {
        // 새로 넣은 comment 와 불러온 comment 가 같은지 확인
        Comment comment0 = this.commentList.get(0);
        this.commentDao.insertComment(comment0);
        int lastIndexComment = this.commentDao.getLastIndexComment();
        Comment comment1 = this.commentDao.getCommentBySeq(lastIndexComment);
        this.checkSameComment(comment0, comment1);
        // update - 로컬
        LocalDateTime updateTime = LocalDateTime.of(2023, 10, 13, 21, 17, 53);
        comment1.setMemberSeq(this.commentList.get(4).getMemberSeq());
        comment1.setArticleSeq(this.commentList.get(4).getArticleSeq());
        comment1.setContents("newContents");
        comment1.setIsDel(1);
        comment1.setCreateTime(updateTime);
        comment1.setUpdateTime(updateTime);
        // update - db
        this.commentDao.updateComment(comment1);
        int lastIndexComment2 = this.commentDao.getLastIndexComment();
        Assertions.assertEquals(lastIndexComment, lastIndexComment2);
        Comment comment2 = this.commentDao.getCommentBySeq(lastIndexComment2);
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
        Assertions.assertThrows(EmptyResultDataAccessException.class, ()->{this.commentDao.getCommentBySeq(lastIndexComment-3);});
        // 3번만 삭제되고 나머지는 그대로인지?
        this.commentDao.deleteCommentBySeq(lastIndexComment-2);
        Assertions.assertEquals(2, this.commentDao.getCountAllComment());
        Assertions.assertNotNull(this.commentDao.getCommentBySeq(lastIndexComment));
        Assertions.assertNotNull(this.commentDao.getCommentBySeq(lastIndexComment-1));
        Assertions.assertThrows(EmptyResultDataAccessException.class, ()->{this.commentDao.getCommentBySeq(lastIndexComment-2);});
        // 2번만 삭제되고 나머지는 그대로인지?
        this.commentDao.deleteCommentBySeq(lastIndexComment-1);
        Assertions.assertEquals(1, this.commentDao.getCountAllComment());
        Assertions.assertNotNull(this.commentDao.getCommentBySeq(lastIndexComment));
        Assertions.assertThrows(EmptyResultDataAccessException.class, ()->{this.commentDao.getCommentBySeq(lastIndexComment-1);});
        // 1번만 삭제되고 나머지는 그대로인지?
        this.commentDao.deleteCommentBySeq(lastIndexComment);
        Assertions.assertEquals(0, this.commentDao.getCountAllComment());
        Assertions.assertThrows(EmptyResultDataAccessException.class, ()->{this.commentDao.getCommentBySeq(lastIndexComment);});
    }

    @Test
    public void getCommentListByMemberSeqTest() {
        // 픽스처 comment 모두 등록
        Iterator<Comment> iterLocal = this.commentList.iterator();
        while(iterLocal.hasNext()) {
            this.commentDao.insertComment(iterLocal.next());
        }
        Assertions.assertEquals(this.commentList.size(), this.commentDao.getCountAllComment());
        // 픽스처 list 에 filter 돌려서 조건에 맞는 item 만으로 구성된 list 새로 만들기
        Long targetMemberIndex = this.commentList.get(0).getMemberSeq();
        List<Comment> commentListLocal = this.commentList.stream().filter(e -> e.getMemberSeq() == targetMemberIndex).collect(Collectors.toList());
        // 등록된 comment 중에 member_seq 가 특정 값인 comment 만 list 로 받아오기
        List<Comment> commentListGet = this.commentDao.getCommentListByMemberSeq(targetMemberIndex);
        // 픽스처 list 와 받아온  list 비교해 보기
        Assertions.assertEquals(commentListGet.size(), commentListLocal.size());
        Iterator<Comment> iterGet = commentListGet.iterator();
        while (iterGet.hasNext()) {
            Comment item = iterGet.next();
            Assertions.assertEquals(item.getMemberSeq(), targetMemberIndex);
        }
    }


    @Test
    public void getCommentListByArticleSeqTest() {
        //
        // 일단 테스트 데이터 다 넣음
        Iterator<Comment> iter = this.commentList.iterator();
        while(iter.hasNext()) {
            this.commentDao.insertComment(iter.next());
        }
        //
        // this.commentList 를 articleSeq1 로 필터링한 리스트와 DB 에서 articleSeq1 꺼내온 리스트가 같은지
        Long articleSeq1 = this.commentList.get(1).getArticleSeq();
        List<Comment> listGet1 = this.commentDao.getCommentListByArticleSeq(articleSeq1);
        List<Comment> listLocal1 = this.commentList.stream().filter((ele)->ele.getArticleSeq() == articleSeq1).collect(Collectors.toList());
        this.checkSameCommentList(listGet1, listLocal1);
        //
        // this.commentList 를 articleSeq2 로 필터링한 리스트와 DB 에서 articleSeq2 꺼내온 리스트가 같은지
        Long articleSeq2 = this.commentList.get(2).getArticleSeq();
        List<Comment> listGet2 = this.commentDao.getCommentListByArticleSeq(articleSeq2);
        List<Comment> listLocal2 = this.commentList.stream().filter((ele)->ele.getArticleSeq() == articleSeq2).collect(Collectors.toList());
        this.checkSameCommentList(listGet2, listLocal2);

    }


    @Test
    public void deleteAllCommentTest() {
        this.commentDao.deleteAllComment();
        Assertions.assertEquals(0, this.commentDao.getCountAllComment());
    }

    @Test
    public void deleteAllCommentByArticleSeqTest() {
        //
        this.commentDao.insertComment(this.commentList.get(1));
        this.commentDao.insertComment(this.commentList.get(2));
        Long articleSeq1 = this.commentList.get(1).getArticleSeq();
        Long articleSeq2 = this.commentList.get(2).getArticleSeq();
        Assertions.assertNotEquals(0, this.commentDao.getCommentListByArticleSeq(articleSeq1).size());
        Assertions.assertNotEquals(0, this.commentDao.getCommentListByArticleSeq(articleSeq2).size());
        //
        this.commentDao.deleteAllCommentByArticleSeq(articleSeq1);
        //
        Assertions.assertEquals(0, this.commentDao.getCommentListByArticleSeq(articleSeq1).size());
        Assertions.assertNotEquals(0, this.commentDao.getCommentListByArticleSeq(articleSeq2).size());

    }

    @Test
    public void deleteAllCommentByMemberSeqTest() {
        //
        this.commentDao.insertComment(this.commentList.get(2));
        this.commentDao.insertComment(this.commentList.get(3));
        Long memberSeq1 = this.commentList.get(2).getMemberSeq();
        Long memberSeq2 = this.commentList.get(3).getMemberSeq();
        Assertions.assertNotEquals(0, this.commentDao.getCommentListByMemberSeq(memberSeq1).size());
        Assertions.assertNotEquals(0, this.commentDao.getCommentListByMemberSeq(memberSeq2).size());
        //
        this.commentDao.deleteAllCommentByMemberSeq(memberSeq1);
        //
        Assertions.assertEquals(0, this.commentDao.getCommentListByMemberSeq(memberSeq1).size());
        Assertions.assertNotEquals(0, this.commentDao.getCommentListByMemberSeq(memberSeq2).size());
    }


    @Test
    public void getCountAllCommentByMemberSeqTest() {
        //
        Long memberSeq2 = this.commentList.get(2).getMemberSeq();
        Long memberSeq3 = this.commentList.get(3).getMemberSeq();
        Assertions.assertEquals(0, this.commentDao.getCountAllCommentByMemberSeq(memberSeq2));
        Assertions.assertEquals(0, this.commentDao.getCountAllCommentByMemberSeq(memberSeq3));
        //
        this.commentDao.insertComment(this.commentList.get(3));
        Assertions.assertEquals(1, this.commentDao.getCountAllCommentByMemberSeq(memberSeq3));
        this.commentDao.insertComment(this.commentList.get(4));
        Assertions.assertEquals(2, this.commentDao.getCountAllCommentByMemberSeq(memberSeq3));
        this.commentDao.insertComment(this.commentList.get(5));
        Assertions.assertEquals(3, this.commentDao.getCountAllCommentByMemberSeq(memberSeq3));
        Assertions.assertEquals(0, this.commentDao.getCountAllCommentByMemberSeq(memberSeq2));
    }

    @Test
    public void getCountAllCommentByArticleSeqTest() {
        //
        Long articleSeq1 = this.commentList.get(1).getArticleSeq();
        Long articleSeq2 = this.commentList.get(2).getArticleSeq();
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
