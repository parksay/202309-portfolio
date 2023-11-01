package simple.myboard.myprac;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import simple.myboard.myprac.entity.MemberEntity;
import simple.myboard.myprac.entity.TeamEntity;

public class JpaLearningTest {

    @Test
    public void runTest() {
        EntityManagerFactory emf =null;
        EntityManager em = null;
        EntityTransaction tx = null;


        try {
            emf = Persistence.createEntityManagerFactory("hello");
            em = emf.createEntityManager();
            tx = em.getTransaction();
            tx.begin();
            ////////////////////////////////////////
            TeamEntity team = new TeamEntity();
            team.setName("test");
            MemberEntity member = new MemberEntity();
            member.setTeam(team);
            System.out.println("######################## member before = " + member);
            em.persist(member);
            System.out.println("######################## member mid = " + member);
            em.persist(team);
            ////////////////////////////////////////
            tx.commit();
            em.clear();
            System.out.println("########################## clear #####################");
            System.out.println("######################## member after = " + member);
            TeamEntity teamResult = em.find(TeamEntity.class, 7);
            System.out.println("######################## teamResult = " + teamResult);
            System.out.println("teamResult. = " + teamResult.getMemberList().size());
            System.out.println("######################## teamResult.getMemberList() = " + teamResult.getMemberList());
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
}
