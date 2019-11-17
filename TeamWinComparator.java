import java.util.Comparator;
public class TeamWinComparator implements Comparator<Team> {
    public int compare(Team a, Team b) {
        return a.wins - b.wins;
    }
}