import java.util.Comparator;
public class TeamPointsForComparator implements Comparator<Team> {
    public int compare(Team a, Team b) {
        return a.pointsFor - b.pointsFor;
    }
}