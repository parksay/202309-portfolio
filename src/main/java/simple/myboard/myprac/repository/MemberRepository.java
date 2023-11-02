package simple.myboard.myprac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import simple.myboard.myprac.entity.MemberEntity;

import java.util.List;


@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    public List<MemberEntity> findAllByName(String name);
}
