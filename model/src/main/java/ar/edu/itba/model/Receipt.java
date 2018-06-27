package ar.edu.itba.model;

import javax.persistence.*;

@Entity
@Table(name = "receipt")
public class Receipt {

    public enum Type {
        SOLDPLAYER, BOUGHTPLAYER, EXPANDEDSTADIUM, MATCHINCOME, TOURNAMENTPRIZE, PLAYERSSALARIES
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "receipt_receiptid_seq")
    @SequenceGenerator(sequenceName = "receipt_receiptid_seq", name = "receipt_receiptid_seq", allocationSize = 1)
    @Column(name = "receiptid")
    private long id;

    @Column(nullable = false)
    private int amount;

    @Enumerated(value = EnumType.STRING)
    private Type type;

    @ManyToOne
    @JoinColumn(name = "team")
    private Team team;

    public Receipt(){}

    public Receipt(int amount, Type type, Team team) {
        this.amount = amount;
        this.type = type;
        this.team = team;
    }

    public Receipt(long id, final int amount, final Type type){
        this.amount = amount;
        this.type = type;
        this.id = id;
    }

    public Team getTeam(){
        return team;
    }

    public int getAmount(){
        return amount;
    }

    public long getId() {
        return id;
    }

    public Type getType(){
        return type;
    }
}
