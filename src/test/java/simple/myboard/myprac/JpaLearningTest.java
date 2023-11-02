package simple.myboard.myprac;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import simple.myboard.myprac.domain.Member;
import simple.myboard.myprac.entity.TeamEntity;
import simple.myboard.myprac.repository.MemberRepository;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringConfig.class)
public class JpaLearningTest {

//    @Autowired
//    ApplicationContext context;
    @Autowired
    MemberRepository memberRepository;
//    MemberRepository memberRepository = null;

    @Test
    public void runTest() {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        MemberRepository memberRepository = context.getBean("memberRepository", MemberRepository.class);
        System.out.println("memberRepository = " + memberRepository);
    }

    @Test
    public void simpleJpa() {
        EntityManagerFactory emf =null;
        EntityManager em = null;
        EntityTransaction tx = null;


        try {
            emf = Persistence.createEntityManagerFactory("hello");
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            ////////////////////////////////////////
            Member member1 = new Member();
            member1.setUserName("hello");
            Member member2 = new Member();
            member2.setUserName("hello");
            Member member3 = new Member();
            member3.setUserName("hello1");
            em.persist(member1);
            em.persist(member2);
            em.persist(member3);
            ////////////////////////////////////////
            System.out.println("####################### commit ######################");
            tx.commit();
            em.clear();
            ////////////////////////////////////////
        } catch (Exception e) {
            if(tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            Assertions.fail();
        } finally {
            if(em != null) {
                em.close();
            }
            if(emf != null) {
                emf.close();
            }
        }
    }

    @Test
    public void springDataJpaTest() {
        try {
            Member member1 = new Member();
            member1.setUserName("hello");
            Member member2 = new Member();
            member2.setUserName("hello");
            Member member3 = new Member();
            member3.setUserName("hello1");
            this.memberRepository.save(member1);
            this.memberRepository.save(member2);
            this.memberRepository.save(member3);
            ////////////////////////////////////////
            this.memberRepository.flush();
            System.out.println("####################### commit ######################");
            ///////////////////////////////////////
//            List<Member> memberList = this.memberRepository.findAllByUsername("hello");
//            System.out.println("memberList = " + memberList);
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail();
        }
    }



}
