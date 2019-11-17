import java.util.Comparator;
public class TeamSeedComparator implements Comparator<Team> {
    public int compare(Team a, Team b) {
        if (a.averageSeed > b.averageSeed) {
            return 1;
        } else {
            return -1;
        }
        
    }
}