package ar.edu.itba.model;

import javafx.geometry.Pos;

import javax.persistence.*;
import java.util.Date;

import static ar.edu.itba.model.Player.Position.FW;
import static ar.edu.itba.model.Player.Position.MID;

@Entity
@Table(name = "player")
public class Player {

    public enum Position{
        GK, DEF, MID, FW
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_playerid_seq")
    @SequenceGenerator(sequenceName = "player_playerid_seq", name = "player_playerid_seq", allocationSize = 1)
    @Column(name = "playerid")
    private long id;

    @Transient
    private long teamId;

    @ManyToOne
    @JoinColumn(name = "team")
    private Team team;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int age, value, potential, skillLevel, goalKeeping, finishing, defending, passing, fitness, salary;

    @Column(nullable = false)
    private Date contractExpiration;

    @Column(nullable = false)
    private boolean youth;

    public Player(){}

    public Player(Team team, String name, int age, int value, int potential, int skillLevel, int goalKeeping,
                  int finishing, int defending, int passing, int fitness, int salary, Date contractExpiration, boolean youth) {
        this.team = team;
        this.name = name;
        this.age = age;
        this.value = value;
        this.potential = potential;
        this.skillLevel = skillLevel;
        this.goalKeeping = goalKeeping;
        this.finishing = finishing;
        this.defending = defending;
        this.passing = passing;
        this.fitness = fitness;
        this.salary = salary;
        this.contractExpiration = contractExpiration;
        this.youth = youth;
    }

    public Player(long id, Team team, String name, int age, int value, int potential, int skillLevel, int goalKeeping,
                  int finishing, int defending, int passing, int fitness, int salary, Date contractExpiration, boolean youth) {
        this.id = id;
        this.team = team;
        this.name = name;
        this.age = age;
        this.value = value;
        this.potential = potential;
        this.skillLevel = skillLevel;
        this.goalKeeping = goalKeeping;
        this.finishing = finishing;
        this.defending = defending;
        this.passing = passing;
        this.fitness = fitness;
        this.salary = salary;
        this.contractExpiration = contractExpiration;
        this.youth = youth;
    }

    public Player(long id, long teamId, String name, int age, int value, int potential, int skillLevel, int goalkeeping,
                  int finishing, int defending, int passing, int fitness, int salary, Date contractExpiration, boolean youth) {
        this.id = id;
        this.teamId = teamId;
        this.name = name;
        this.age = age;
        this.value = value;
        this.potential = potential;
        this.skillLevel = skillLevel;
        this.goalKeeping = goalkeeping;
        this.finishing = finishing;
        this.defending = defending;
        this.passing = passing;
        this.fitness = fitness;
        this.salary = salary;
        this.contractExpiration = contractExpiration;
        this.youth = youth;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Player && id == ((Player) obj).id;
    }

    public long getTeamId() {
        if(team != null)
            return team.getId();
        return teamId;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGoalKeeping(int goalKeeping) {
        this.goalKeeping = goalKeeping;
    }

    public void setFinishing(int finishing) {
        this.finishing = finishing;
    }

    public void setDefending(int defending) {
        this.defending = defending;
    }

    public void setPassing(int passing) {
        this.passing = passing;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public void setContractExpiration(Date contractExpiration) {
        this.contractExpiration = contractExpiration;
    }

    public boolean isYouth() {
        return youth;
    }

    public void setYouth(boolean youth) {
        this.youth = youth;
    }

    public int getSalary() {
        return salary;
    }

    public Date getContractExpiration() {
        return contractExpiration;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getValue() {
        return value;
    }

    public int getPotential() {
        return potential;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public int getGoalKeeping() {
        return goalKeeping;
    }

    public int getFinishing() {
        return finishing;
    }

    public int getDefending() {
        return defending;
    }

    public int getPassing() {
        return passing;
    }

    public int getFitness() {
        return fitness;
    }

    public Team getTeam() {
        return team;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setPotential(int potential) {
        this.potential = potential;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    public long getId() {
        return id;
    }

    public void increaseAge() {
        age++;
    }

    public Position getPosition() {
        Position pos = Position.GK;
        int maxAttribute = 0;
        if(goalKeeping > maxAttribute) {
            maxAttribute = goalKeeping;
        }
        if(defending > maxAttribute) {
            maxAttribute = defending;
            pos = Position.DEF;
        }
        if(passing > maxAttribute) {
            maxAttribute = passing;
            pos = MID;
        }
        if(finishing > maxAttribute) {
            pos = FW;
        }
        return pos;
    }
}
