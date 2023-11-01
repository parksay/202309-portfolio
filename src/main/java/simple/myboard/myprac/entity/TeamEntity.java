package simple.myboard.myprac.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="team_entity")
@SequenceGenerator(name="TEAM_SEQ_GEN", sequenceName = "TEAM_SEQ",initialValue = 7, allocationSize = 5)
public class TeamEntity {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TEAM_SEQ_GEN")
    @Column(insertable = false, updatable = false)
    private Long seq;
    @Column(name = "team_name", length = 30, nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "team")
    private List<MemberEntity> memberList = new ArrayList<>();
    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSeq() {
        return seq;
    }

    public String getName() {
        return name;
    }

    public List<MemberEntity> getMemberList() {
        return memberList;
    }

    @Override
    public String toString() {
        return "TeamEntity{" +
                "seq=" + seq +
                ", name='" + name + '\'' +
                '}';
    }
}
