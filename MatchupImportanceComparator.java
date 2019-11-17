import java.util.Comparator;
import java.lang.Math.*;
public class MatchupImportanceComparator implements Comparator<Matchup> {
    public int compare(Matchup a, Matchup b) {
        if (Math.abs(a.importance) - Math.abs(b.importance) > 0) {
            return 1;
        } else {
            return -1;
        }
    }
}