package simple.myboard.myprac.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order_entity")
@TableGenerator(name = "ORDER_seq_GEN",initialValue = 7, allocationSize = 5, pkColumnName = "MY_ORDER_SEQ")
public class OrderEntity {

    @Id @GeneratedValue(strategy = GenerationType.TABLE, generator = "ORDER_seq_GEN")
    private Long seq;
    private String name;

    @ManyToMany(mappedBy = "orderList")
    private List<MemberEntity> memberList = new ArrayList<>();
}
