import java.util.ArrayList;
public class Team {
    public String name;
    public Team[] schedule;
    public int currentWins;
    public int wins;
    public int pointsFor;

    public ArrayList<Integer> seeds;
    public Double averageSeed;
    public ArrayList<Integer> madePlayoffs;
    public Double playoffOdds;
    public ArrayList<Integer> gotBye;
    public Double byeOdds;

    public int madePlayoffsCount = 0;
    public int gotByeCount = 0;

    public Team(String name, int wins, int pointsFor) {
        this.name = name;
        this.currentWins = wins;
        this.seeds = new ArrayList<Integer>();
        this.madePlayoffs = new ArrayList<Integer>();
        this.pointsFor = pointsFor;
        this.gotBye = new ArrayList<Integer>();
    }
}