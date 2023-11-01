package simple.myboard.myprac.entity;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member_entity")
public class MemberEntity {
    @Id @GeneratedValue
    private Long seq;
    @Column(name = "member_name", columnDefinition = "varchar(17) default 'hi'")
    private String name;
    @ManyToOne
    @JoinColumn(name = "team_seq")
    private TeamEntity team;

    @ManyToMany
    @JoinTable(name = "orders_members_mapping_table")
    private List<OrderEntity> orderList = new ArrayList<>();

    public Long getSeq() {
        return seq;
    }

    public String getName() {
        return name;
    }

    public TeamEntity getTeam() {
        return team;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeam(TeamEntity team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "MemberEntity{" +
                "seq=" + seq +
                ", name='" + name + '\'' +
                ", team='" + team + '\'' +
                '}';
    }
}
