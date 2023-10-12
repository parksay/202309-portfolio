package simple.myboard.myprac;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import simple.myboard.myprac.dao.MemberDaoJdbc;
import simple.myboard.myprac.vo.MemberVO;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

// TODO : rollback transaction 다 넣고 deleteAll 지우기
public class MemberDaoJdbcTest {

    private MemberDaoJdbc memberDao;
    private List<MemberVO> memberList;

    @BeforeEach
    public void setUp() {
        //
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        this.memberDao = context.getBean("memberDao", MemberDaoJdbc.class); // 테스트하려는 특정 클래스
        //
        TestUtil.clearTestData();
        //
        LocalDateTime newTime = LocalDateTime.of(2023, 10, 6, 14, 26, 47);
        memberList = Arrays.asList(
                new MemberVO("testid01", "testpsw01", "testname01", 0, newTime, newTime),
                new MemberVO("testid02", "testpsw02", "testname02", 0, newTime, newTime),
                new MemberVO("testid03", "testpsw03", "testname03", 0, newTime, newTime),
                new MemberVO("testid04", "testpsw04", "testname04", 0, newTime, newTime),
                new MemberVO("testid05", "testpsw05", "testname05", 0, newTime, newTime)
        );
    }

    @AfterEach
    public void endEach() {
        TestUtil.clearTestData();
    }
    @Test
    public void addAndGetMemberTest() {
        //
        MemberVO member0 = this.memberList.get(0);
        MemberVO member1 = this.memberList.get(1);
        MemberVO member2 = this.memberList.get(2);
        this.memberDao.insertMember(member0);
        Assertions.assertEquals(1, this.memberDao.getCountAllMember());
        this.memberDao.insertMember(member1);
        Assertions.assertEquals(2, this.memberDao.getCountAllMember());
        this.memberDao.insertMember(member2);
        Assertions.assertEquals(3, this.memberDao.getCountAllMember());
        //
        int lastIndex = this.memberDao.getLastIndexMember();
        checkSameMember(member0, this.memberDao.getMemberBySeq(lastIndex-2));
        checkSameMember(member1, this.memberDao.getMemberBySeq(lastIndex-1));
        checkSameMember(member2, this.memberDao.getMemberBySeq(lastIndex));
    }


    private void checkSameMember(MemberVO member1, MemberVO member2) {
        Assertions.assertEquals(member1.getUserId(), member2.getUserId());
        Assertions.assertEquals(member1.getUserPsw(), member2.getUserPsw());
        Assertions.assertEquals(member1.getUserName(), member2.getUserName());
        Assertions.assertEquals(member1.getIsDel(), member2.getIsDel());
        Assertions.assertEquals(member1.getCreateTime(), member2.getCreateTime());
        Assertions.assertEquals(member1.getUpdateTime(), member2.getUpdateTime());
    }

    @Test
    public void deleteAllMemberTest() {
        //
        this.memberDao.insertMember(this.memberList.get(0));
        Assertions.assertNotEquals(0, this.memberDao.getCountAllMember());
        //
        this.memberDao.deleteAllMember();
        Assertions.assertEquals(0, this.memberDao.getCountAllMember());
    }

    @Test
    public void updateMemberTest() {
        //
        MemberVO member0 = this.memberList.get(0);
        this.memberDao.insertMember(member0);
        int lastIndex1 = this.memberDao.getLastIndexMember();
        member0.setMemberSeq(lastIndex1);
        MemberVO member1 = this.memberDao.getMemberBySeq(lastIndex1);
        this.checkSameMember(member0, member1);
        //
        member1.setUserId("newId");
        member1.setUserPsw("newPsw");
        member1.setUserName("newName");
        member1.setIsDel(1);
        LocalDateTime newTime = LocalDateTime.of(2023, 3, 13, 16, 52, 22);
        member1.setCreateTime(newTime);
        member1.setUpdateTime(newTime);
        this.memberDao.updateMember(member1);
        //
        int lastIndex2 = this.memberDao.getLastIndexMember();
        MemberVO member2 = this.memberDao.getMemberBySeq(lastIndex2);
        checkSameMember(member1, member2);
    }

    @Test
    public void deleteMemberBySeqTest() {
        //
        this.memberDao.insertMember(this.memberList.get(0));
        this.memberDao.insertMember(this.memberList.get(1));
        this.memberDao.insertMember(this.memberList.get(2));
        //
        Assertions.assertEquals(3, this.memberDao.getCountAllMember());
        this.memberDao.deleteMemberBySeq(this.memberDao.getLastIndexMember());
        Assertions.assertEquals(2, this.memberDao.getCountAllMember());
        this.memberDao.deleteMemberBySeq(this.memberDao.getLastIndexMember());
        Assertions.assertEquals(1, this.memberDao.getCountAllMember());
        this.memberDao.deleteMemberBySeq(this.memberDao.getLastIndexMember());
        Assertions.assertEquals(0, this.memberDao.getCountAllMember());

    }

}
