package simple.myboard.myprac;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import simple.myboard.myprac.dao.MemberDaoJdbc;
import simple.myboard.myprac.vo.MemberVO;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MemberDaoJdbcTest {

    private MemberDaoJdbc memberDao;
    private List<MemberVO> memberList;

    @BeforeEach
    public void setUp() {
        ApplicationContext context = new GenericXmlApplicationContext("applicationContext.xml");
        this.memberDao = context.getBean("memberDao", MemberDaoJdbc.class);
        memberList = Arrays.asList(
                new MemberVO("testid01", "testpsw01", "testname01", 0, new Date(), new Date()),
                new MemberVO("testid02", "testpsw02", "testname02", 0, new Date(), new Date()),
                new MemberVO("testid03", "testpsw03", "testname03", 0, new Date(), new Date()),
                new MemberVO("testid04", "testpsw04", "testname04", 0, new Date(), new Date()),
                new MemberVO("testid05", "testpsw05", "testname05", 0, new Date(), new Date())
        );
    }

    public void addAndGetMemberTest() {
        //
        this.memberDao.deleteAllMember();
        Assertions.assertEquals(0, this.memberDao.getCountMember());
        //
        MemberVO member0 = this.memberList.get(0);
        MemberVO member1 = this.memberList.get(1);
        MemberVO member2 = this.memberList.get(2);
        this.memberDao.insertMember(member0);
        Assertions.assertEquals(1, this.memberDao.getCountMember());
        this.memberDao.insertMember(member1);
        Assertions.assertEquals(2, this.memberDao.getCountMember());
        this.memberDao.insertMember(member2);
        Assertions.assertEquals(3, this.memberDao.getCountMember());
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
}
