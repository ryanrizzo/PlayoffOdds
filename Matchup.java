public class Matchup {
    Team a;
    Team b;
    int aWinProbability;
    Double importance;
    Double byeimportance;
    int week;

    public Matchup(Team a, Team b, int aWinProbability, int week) {
        this.a = a;
        this.b = b;
        this.aWinProbability = aWinProbability;
        this.week = week;
    }
}