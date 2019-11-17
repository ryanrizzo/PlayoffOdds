import java.util.Comparator;
import java.lang.Math.*;
public class MatchupByeImportanceComparator implements Comparator<Matchup> {
    public int compare(Matchup a, Matchup b) {
        if (Math.abs(a.byeimportance) - Math.abs(b.byeimportance) > 0) {
            return 1;
        } else {
            return -1;
        }
    }
}