package simple.myboard.myprac;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import simple.myboard.myprac.service.CommentService;
import simple.myboard.myprac.vo.CommentVO;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(locations    = {"file:src/main/resources/applicationContext.xml"})
public class CommentServiceImplTest {

    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImplTest.class);
    private CommentService commentService;
    List<CommentVO> commentList;

    @BeforeEach
    public void setUp() throws ParseException {
        //
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        this.commentService = context.getBean("commentService", CommentService.class);
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
    public void addAndGetCommentTest() {
        //
        CommentVO comment0 = this.commentList.get(0);
        CommentVO comment2 = this.commentList.get(2);
        CommentVO comment3 = this.commentList.get(3);
        CommentVO comment5 = this.commentList.get(5);
        // 하나씩 등록되는 거 맞는지
        Assertions.assertEquals(0, this.commentService.getCountAllComment());
        this.commentService.addComment(comment0);
        Assertions.assertEquals(1, this.commentService.getCountAllComment());
        this.commentService.addComment(comment2);
        Assertions.assertEquals(2, this.commentService.getCountAllComment());
        this.commentService.addComment(comment3);
        Assertions.assertEquals(3, this.commentService.getCountAllComment());
        this.commentService.addComment(comment5);
        Assertions.assertEquals(4, this.commentService.getCountAllComment());
        // 로컬 데이터랑 DB에 등록된 데이터랑 같은지 비교하기
        int lastIndexComment = this.commentService.getLastIndexComment();
        this.checkSameComment(comment5, this.commentService.getCommentBySeq(lastIndexComment));
        this.checkSameComment(comment3, this.commentService.getCommentBySeq(lastIndexComment-1));
        this.checkSameComment(comment2, this.commentService.getCommentBySeq(lastIndexComment-2));
        this.checkSameComment(comment0, this.commentService.getCommentBySeq(lastIndexComment-3));
    }

    private void checkSameComment(CommentVO comment1, CommentVO comment2) {
        Assertions.assertEquals(comment1.getArticleSeq(), comment2.getArticleSeq());
        Assertions.assertEquals(comment1.getMemberSeq(), comment2.getMemberSeq());
        Assertions.assertEquals(comment1.getContents(), comment2.getContents());
        Assertions.assertEquals(comment1.getIsDel(), comment2.getIsDel());
        Assertions.assertEquals(comment1.getCreateTime(), comment2.getCreateTime());
        Assertions.assertEquals(comment1.getUpdateTime(), comment2.getUpdateTime());
    }

    private void checkSameCommentList(List<CommentVO> commentList1, List<CommentVO> commentList2) {
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
    public void getCommentBySeqEmptyTest() {
        // 없으면 null 던지기
        int lastIndexComment = this.commentService.getLastIndexComment();
        Assertions.assertNull(this.commentService.getCommentBySeq(lastIndexComment+1));
    }

    @Test
    public void updateCommentTest() {
        // 새로 넣은 comment 와 불러온 comment 가 같은지 확인
        CommentVO comment0 = this.commentList.get(0);
        this.commentService.addComment(comment0);
        int lastIndexComment = this.commentService.getLastIndexComment();
        CommentVO comment1 = this.commentService.getCommentBySeq(lastIndexComment);
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
        this.commentService.updateComment(comment1);
        int lastIndexComment2 = this.commentService.getLastIndexComment();
        Assertions.assertEquals(lastIndexComment, lastIndexComment2);
        CommentVO comment2 = this.commentService.getCommentBySeq(lastIndexComment2);
        this.checkSameComment(comment1, comment2);

    }

    @Test
    public void deleteCommentBySeqTest() {
        // 테스트 데이터 넣기
        this.commentService.addComment(this.commentList.get(0));
        this.commentService.addComment(this.commentList.get(1));
        this.commentService.addComment(this.commentList.get(2));
        this.commentService.addComment(this.commentList.get(3));
        Assertions.assertEquals(4, this.commentService.getCountAllComment());
        // 4번만 삭제되고 나머지는 그대로인지?
        int lastIndexComment = this.commentService.getLastIndexComment();
        this.commentService.deleteCommentBySeq(lastIndexComment-3);
        Assertions.assertEquals(3, this.commentService.getCountAllComment());
        Assertions.assertNotNull(this.commentService.getCommentBySeq(lastIndexComment));
        Assertions.assertNotNull(this.commentService.getCommentBySeq(lastIndexComment-1));
        Assertions.assertNotNull(this.commentService.getCommentBySeq(lastIndexComment-2));
        Assertions.assertNull(this.commentService.getCommentBySeq(lastIndexComment-3));
        // 3번만 삭제되고 나머지는 그대로인지?
        this.commentService.deleteCommentBySeq(lastIndexComment-2);
        Assertions.assertEquals(2, this.commentService.getCountAllComment());
        Assertions.assertNotNull(this.commentService.getCommentBySeq(lastIndexComment));
        Assertions.assertNotNull(this.commentService.getCommentBySeq(lastIndexComment-1));
        Assertions.assertNull(this.commentService.getCommentBySeq(lastIndexComment-2));
        // 2번만 삭제되고 나머지는 그대로인지?
        this.commentService.deleteCommentBySeq(lastIndexComment-1);
        Assertions.assertEquals(1, this.commentService.getCountAllComment());
        Assertions.assertNotNull(this.commentService.getCommentBySeq(lastIndexComment));
        Assertions.assertNull(this.commentService.getCommentBySeq(lastIndexComment-1));
        // 1번만 삭제되고 나머지는 그대로인지?
        this.commentService.deleteCommentBySeq(lastIndexComment);
        Assertions.assertEquals(0, this.commentService.getCountAllComment());
        Assertions.assertNull(this.commentService.getCommentBySeq(lastIndexComment));
    }

    @Test
    public void getCommentListByMemberSeqTest() {
        // 픽스처 comment 모두 등록
        Iterator<CommentVO> iterLocal = this.commentList.iterator();
        while(iterLocal.hasNext()) {
            this.commentService.addComment(iterLocal.next());
        }
        Assertions.assertEquals(this.commentList.size(), this.commentService.getCountAllComment());
        // 픽스처 list 에 filter 돌려서 조건에 맞는 item 만으로 구성된 list 새로 만들기
        int targetMemberIndex = this.commentList.get(0).getMemberSeq();
        List<CommentVO> commentListLocal = this.commentList.stream().filter(e -> e.getMemberSeq() == targetMemberIndex).collect(Collectors.toList());
        // 등록된 comment 중에 member_seq 가 특정 값인 comment 만 list 로 받아오기
        List<CommentVO> commentListGet = this.commentService.getCommentListByMemberSeq(targetMemberIndex);
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
        Assertions.assertNull(this.commentService.getCommentListByMemberSeq(lasIndexMember));
    }

    @Test
    public void getCommentListByArticleSeqTest() {
        //
        // 일단 테스트 데이터 다 넣음
        Iterator<CommentVO> iter = this.commentList.iterator();
        while(iter.hasNext()) {
            this.commentService.addComment(iter.next());
        }
        //
        // this.commentList 를 articleSeq1 로 필터링한 리스트와 DB 에서 articleSeq1 꺼내온 리스트가 같은지
        int articleSeq1 = this.commentList.get(1).getArticleSeq();
        List<CommentVO> listGet1 = this.commentService.getCommentListByArticleSeq(articleSeq1);
        List<CommentVO> listLocal1 = this.commentList.stream().filter((ele)->ele.getArticleSeq() == articleSeq1).collect(Collectors.toList());
        this.checkSameCommentList(listGet1, listLocal1);
        //
        // this.commentList 를 articleSeq2 로 필터링한 리스트와 DB 에서 articleSeq2 꺼내온 리스트가 같은지
        int articleSeq2 = this.commentList.get(2).getArticleSeq();
        List<CommentVO> listGet2 = this.commentService.getCommentListByArticleSeq(articleSeq2);
        List<CommentVO> listLocal2 = this.commentList.stream().filter((ele)->ele.getArticleSeq() == articleSeq2).collect(Collectors.toList());
        this.checkSameCommentList(listGet2, listLocal2);

    }

    @Test
    public void getCommentListByArticleSeqEmptyTest() {
        //
        int lasIndexArticle = TestUtil.getLastIndexArticle();
        Assertions.assertNull(this.commentService.getCommentListByArticleSeq(lasIndexArticle));
    }

    @Test
    public void deleteAllCommentTest() {
        this.commentService.deleteAllComment();
        Assertions.assertEquals(0, this.commentService.getCountAllComment());
    }

    @Test
    public void deleteAllCommentByArticleSeqTest() {
        //
        this.commentService.addComment(this.commentList.get(1));
        this.commentService.addComment(this.commentList.get(2));
        int articleSeq1 = this.commentList.get(1).getArticleSeq();
        int articleSeq2 = this.commentList.get(2).getArticleSeq();
        Assertions.assertNotEquals(0, this.commentService.getCommentListByArticleSeq(articleSeq1).size());
        Assertions.assertNotEquals(0, this.commentService.getCommentListByArticleSeq(articleSeq2).size());
        //
        this.commentService.deleteAllCommentByArticleSeq(articleSeq1);
        //
        Assertions.assertNull(this.commentService.getCommentListByArticleSeq(articleSeq1));
        Assertions.assertNotEquals(0, this.commentService.getCommentListByArticleSeq(articleSeq2).size());

    }

    @Test
    public void deleteAllCommentByMemberSeqTest() {
        //
        this.commentService.addComment(this.commentList.get(2));
        this.commentService.addComment(this.commentList.get(3));
        int memberSeq1 = this.commentList.get(2).getMemberSeq();
        int memberSeq2 = this.commentList.get(3).getMemberSeq();
        Assertions.assertNotEquals(0, this.commentService.getCommentListByMemberSeq(memberSeq1).size());
        Assertions.assertNotEquals(0, this.commentService.getCommentListByMemberSeq(memberSeq2).size());
        //
        this.commentService.deleteAllCommentByMemberSeq(memberSeq1);
        //
        Assertions.assertNull(this.commentService.getCommentListByMemberSeq(memberSeq1));
        Assertions.assertNotEquals(0, this.commentService.getCommentListByMemberSeq(memberSeq2).size());
    }


    @Test
    public void getCountAllCommentByMemberSeqTest() {
        //
        int memberSeq2 = this.commentList.get(2).getMemberSeq();
        int memberSeq3 = this.commentList.get(3).getMemberSeq();
        Assertions.assertEquals(0, this.commentService.getCountAllCommentByMemberSeq(memberSeq2));
        Assertions.assertEquals(0, this.commentService.getCountAllCommentByMemberSeq(memberSeq3));
        //
        this.commentService.addComment(this.commentList.get(3));
        Assertions.assertEquals(1, this.commentService.getCountAllCommentByMemberSeq(memberSeq3));
        this.commentService.addComment(this.commentList.get(4));
        Assertions.assertEquals(2, this.commentService.getCountAllCommentByMemberSeq(memberSeq3));
        this.commentService.addComment(this.commentList.get(5));
        Assertions.assertEquals(3, this.commentService.getCountAllCommentByMemberSeq(memberSeq3));
        Assertions.assertEquals(0, this.commentService.getCountAllCommentByMemberSeq(memberSeq2));
    }

    @Test
    public void getCountAllCommentByArticleSeqTest() {
        //
        int articleSeq1 = this.commentList.get(1).getArticleSeq();
        int articleSeq2 = this.commentList.get(2).getArticleSeq();
        Assertions.assertEquals(0, this.commentService.getCountAllCommentByArticleSeq(articleSeq1));
        Assertions.assertEquals(0, this.commentService.getCountAllCommentByArticleSeq(articleSeq2));
        //
        this.commentService.addComment(this.commentList.get(3));
        Assertions.assertEquals(1, this.commentService.getCountAllCommentByArticleSeq(articleSeq2));
        this.commentService.addComment(this.commentList.get(4));
        Assertions.assertEquals(2, this.commentService.getCountAllCommentByArticleSeq(articleSeq2));
        Assertions.assertEquals(0, this.commentService.getCountAllCommentByArticleSeq(articleSeq1));
    }


}