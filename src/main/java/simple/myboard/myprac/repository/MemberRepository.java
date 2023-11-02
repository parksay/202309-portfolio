package simple.myboard.myprac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import simple.myboard.myprac.domain.Member;
import simple.myboard.myprac.entity.MemberEntity;

import java.util.List;


public interface MemberRepository extends JpaRepository<Member, Long> {

    public List<MemberEntity> findAllByName(String name);
    @Query("SELECT MAX(m.member_seq) FROM member")
    public Long getLastIndexMember();
}
