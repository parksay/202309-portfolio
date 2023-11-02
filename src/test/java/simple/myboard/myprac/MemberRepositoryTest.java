package simple.myboard.myprac;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import simple.myboard.myprac.repository.MemberRepository;
import simple.myboard.myprac.domain.Member;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class MemberRepositoryTest {

    private MemberRepository memberRepo;
    private List<Member> memberList;

    @BeforeEach
    public void setUp() {
        //
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        this.memberRepo = context.getBean("memberRepository", MemberRepository.class); // 테스트하려는 특정 클래스
        //
        TestUtil.clearTestData();
        //
        LocalDateTime newTime = LocalDateTime.of(2023, 10, 6, 14, 26, 47);
        memberList = Arrays.asList(
                new Member("testid01", "testpsw01", "testname01", 0, newTime, newTime),
                new Member("testid02", "testpsw02", "testname02", 0, newTime, newTime),
                new Member("testid03", "testpsw03", "testname03", 0, newTime, newTime),
                new Member("testid04", "testpsw04", "testname04", 0, newTime, newTime),
                new Member("testid05", "testpsw05", "testname05", 0, newTime, newTime)
        );
    }

    @AfterEach
    public void endEach() {
        TestUtil.clearTestData();
    }
    @Test
    public void addAndGetMemberTest() {
        //
        Member member0 = this.memberList.get(0);
        Member member1 = this.memberList.get(1);
        Member member2 = this.memberList.get(2);
        this.memberRepo.save(member0);
        this.memberRepo.flush();
        Assertions.assertEquals(1, this.memberRepo.count());
        this.memberRepo.save(member1);
        this.memberRepo.flush();
        Assertions.assertEquals(2, this.memberRepo.count());
        this.memberRepo.save(member2);
        this.memberRepo.flush();
        Assertions.assertEquals(3, this.memberRepo.count());
        //
        Long lastIndex = this.memberRepo.getLastIndexMember();
        checkSameMember(member0, this.memberRepo.findById(lastIndex-2).get());
        checkSameMember(member1, this.memberRepo.findById(lastIndex-1).get());
        checkSameMember(member2, this.memberRepo.findById(lastIndex).get());
    }


    private void checkSameMember(Member member1, Member member2) {
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
        this.memberRepo.save(this.memberList.get(0));
        Assertions.assertNotEquals(0, this.memberRepo.count());
        //
        this.memberRepo.deleteAll();
        Assertions.assertEquals(0, this.memberRepo.count());
    }

    @Test
    public void updateMemberTest() {
        //
        Member member0 = this.memberList.get(0);
        this.memberRepo.save(member0);
        Long lastIndex1 = this.memberRepo.getLastIndexMember();
        member0.setMemberSeq(lastIndex1);
        Member member1 = this.memberRepo.findById(lastIndex1).get();
        this.checkSameMember(member0, member1);
        //
        member1.setUserId("newId");
        member1.setUserPsw("newPsw");
        member1.setUserName("newName");
        member1.setIsDel(1);
        LocalDateTime newTime = LocalDateTime.of(2023, 3, 13, 16, 52, 22);
        member1.setCreateTime(newTime);
        member1.setUpdateTime(newTime);
        this.memberRepo.save(member1);
        //
        Long lastIndex2 = this.memberRepo.getLastIndexMember();
        Member member2 = this.memberRepo.findById(lastIndex2).get();
        checkSameMember(member1, member2);
    }

    @Test
    public void deleteMemberBySeqTest() {
        //
        this.memberRepo.save(this.memberList.get(0));
        this.memberRepo.save(this.memberList.get(1));
        this.memberRepo.save(this.memberList.get(2));
        //
        Assertions.assertEquals(3, this.memberRepo.count());
        this.memberRepo.deleteById(this.memberRepo.getLastIndexMember());
        Assertions.assertEquals(2, this.memberRepo.count());
        this.memberRepo.deleteById(this.memberRepo.getLastIndexMember());
        Assertions.assertEquals(1, this.memberRepo.count());
        this.memberRepo.deleteById(this.memberRepo.getLastIndexMember());
        Assertions.assertEquals(0, this.memberRepo.count());

    }

}
