package simple.myboard.myprac;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import simple.myboard.myprac.dao.MemberDaoJdbc;
import simple.myboard.myprac.serviceimpl.MemberServiceImpl;
import simple.myboard.myprac.vo.MemberVO;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

public class MemberServiceImplTest {

    private MemberServiceImpl memberService;
    private MemberDaoJdbc memberDao;
    private List<MemberVO> memberList;

    // TODO : transaction 다 걸어놓고 deleteAll 지우기
    // TODO: dao 단 interface 에다가 setDataSource 만들기

    @BeforeEach
    public void setUp() {
        //
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        this.memberDao = context.getBean("memberDao", MemberDaoJdbc.class);
        this.memberService = new MemberServiceImpl();
        this.memberService.setMemberDao(this.memberDao);
        //
        memberList = Arrays.asList(
                new MemberVO("testid01", "testpsw01", "testname01"),
                new MemberVO("testid02", "testpsw02", "testname02"),
                new MemberVO("testid03", "testpsw03", "testname03"),
                new MemberVO("testid04", "testpsw04", "testname04"),
                new MemberVO("testid05", "testpsw05", "testname05")
        );
        this.memberDao.deleteAllMember();

    }


    @Test
    public void addAndGetMemberTest() {
        //
        this.memberDao.deleteAllMember();
        Assertions.assertEquals(0, this.memberDao.getCountMember());
        //
        MemberVO member0 = this.memberList.get(0);
        MemberVO member1 = this.memberList.get(1);
        MemberVO member2 = this.memberList.get(2);
        this.memberService.addMember(member0);
        Assertions.assertEquals(1, this.memberDao.getCountMember());
        this.memberService.addMember(member1);
        Assertions.assertEquals(2, this.memberDao.getCountMember());
        this.memberService.addMember(member2);
        Assertions.assertEquals(3, this.memberDao.getCountMember());
        //
        int lastIndex = this.memberDao.getLastIndexMember();
        MemberVO member4 = this.memberService.getMemberBySeq(lastIndex);
        Assertions.assertEquals(0, member4.getIsDel());
        Assertions.assertNotNull(member4.getCreateTime());
        Assertions.assertNotNull(member4.getUpdateTime());

    }

    @Test
    public void getMemberEmptyTest() {
        int lastIndex = this.memberDao.getLastIndexMember();
        MemberVO emptyMember = this.memberService.getMemberBySeq(lastIndex + 1);
        Assertions.assertNull(emptyMember);
    }


    @Test
    public void updateMemberTest() {
        //
        MemberVO member0 = this.memberList.get(0);
        this.memberService.addMember(member0);
        int lastIndex1 = this.memberDao.getLastIndexMember();
        MemberVO member1 = this.memberService.getMemberBySeq(lastIndex1);
        this.checkSameMember(member0, member1);

        //
        member1.setUserId("newId");
        member1.setUserPsw("newPsw");
        member1.setUserName("newName");
        member1.setIsDel(1);
        this.memberService.updateMember(member1);
        //
        int lastIndex2 = this.memberDao.getLastIndexMember();
        Assertions.assertEquals(lastIndex2, lastIndex1);
        MemberVO member2 = this.memberService.getMemberBySeq(lastIndex2);
        this.checkSameMember(member1, member2);
    }

    private void checkSameMember(MemberVO member1, MemberVO member2) {
        Assertions.assertEquals(member1.getUserId(), member2.getUserId());
        Assertions.assertEquals(member1.getUserPsw(), member2.getUserPsw());
        Assertions.assertEquals(member1.getUserName(), member2.getUserName());
        Assertions.assertEquals(member1.getIsDel(), member2.getIsDel());
        Assertions.assertEquals(member1.getCreateTime().truncatedTo(ChronoUnit.MINUTES), member2.getCreateTime().truncatedTo(ChronoUnit.MINUTES));
        Assertions.assertEquals(member1.getUpdateTime().truncatedTo(ChronoUnit.MINUTES), member2.getUpdateTime().truncatedTo(ChronoUnit.MINUTES));

    }


    // TODO: delete / deleteFailure(integrity) // transaction
}
