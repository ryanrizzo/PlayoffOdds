import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.*;
import java.lang.Math.*;
public class League {
    ArrayList<Team> teams;
    Schedule schedule;
    public League(ArrayList<Team> teams, Schedule schedule) {
        this.teams = teams;
        this.schedule = schedule;
    }

    public void simSeason(int runs) {
        ArrayList<HashMap<Matchup, Boolean>> results = new ArrayList<HashMap<Matchup, Boolean>>();
        while (runs > 0) {
            HashMap<Matchup, Boolean> matchupWonByTeamA = new HashMap<Matchup, Boolean>();
            // System.out.println();
            for (Team team : this.teams) {
                team.wins = team.currentWins;
            }
            Random rand = new Random();
            int upsetCount = 0;
            for (Week week : schedule.weeks) {
                for (Matchup matchup : week.matchups) {
                    int  n = rand.nextInt(100) + 1;
                    while (n == matchup.aWinProbability) {
                        n = rand.nextInt(100) + 1;
                    }
                    // System.out.println("Rand n: " + n);
                    if (n < matchup.aWinProbability) {
                        matchupWonByTeamA.put(matchup, true);
                        //a wins
                        matchup.a.wins++;
                        if (matchup.aWinProbability < 50) {
                            // System.out.println("Week " + matchup.week + ": " + matchup.a.name + " (" + matchup.aWinProbability + ") beats " + matchup.b.name);
                            upsetCount++;
                        }
                    } else {
                        matchupWonByTeamA.put(matchup, false);
                        matchup.b.wins++;
                        if (matchup.aWinProbability > 50) {
                            // System.out.println("Week " + matchup.week + ": " + matchup.b.name + " ("+ (100 - matchup.aWinProbability) + ") beats " + matchup.a.name);
                            upsetCount++;
                        }
                    }
                }
            }
            // System.out.println();
            // System.out.println("# of Upsets: " + upsetCount);
            // System.out.println();

            Collections.sort(teams, new TeamWinComparator());
            Collections.reverse(teams);
            int i = 1;
            while(i <= 12) {
                Team firstTeam = teams.get(i-1);
                int winsInPlace = firstTeam.wins;
                ArrayList<Team> teamsInPlace = new ArrayList<Team>();
                teamsInPlace.add(firstTeam);
                for (Team team : teams) {
                    if (team != firstTeam && team.wins == winsInPlace) {
                        teamsInPlace.add(team);
                    } 
                }
                Collections.sort(teamsInPlace, new TeamPointsForComparator());
                Collections.reverse(teamsInPlace);
                for (Team team : teamsInPlace) {
                    if (i <= 6) {
                        team.madePlayoffs.add(1);
                        team.madePlayoffsCount ++;
                    } else {
                        team.madePlayoffs.add(0);
                    }
                    if (i <=2) {
                        team.gotBye.add(1);
                        team.gotByeCount ++;
                    } else {
                        team.gotBye.add(0);
                    }
                    team.seeds.add(i);
                    // System.out.println(i + ". " + team.name + " Wins: " + team.wins + " PF: " + team.pointsFor);
                    i++;
                }
                    
            }
            results.add(matchupWonByTeamA);
            runs--;
        }

        for (Team team : teams) {
            team.averageSeed = calculateAverage(team.seeds);
            team.playoffOdds = calculateAverage(team.madePlayoffs);
            team.byeOdds = calculateAverage(team.gotBye);
        }

        Collections.sort(teams, new TeamSeedComparator());
        for (Team team : teams) {
            System.out.println(Math.round(team.playoffOdds * 100 * 100.00) / 100.00 + "% playoff odds - " + Math.round(team.averageSeed * 100.00) / 100.00 + " ) " + team.name);
            System.out.println(Math.round(team.byeOdds * 100 * 100.00) / 100.00 + "% Bye odds");
            System.out.println(team.madePlayoffsCount + " times made playoffs");
            System.out.println(team.gotByeCount + " times got bye\n");
        }

        HashMap<Matchup, ArrayList<Integer>> ragsOddsIfMatchupWon = new HashMap<Matchup, ArrayList<Integer>>();
        HashMap<Matchup, ArrayList<Integer>> ragsOddsIfMatchupLost = new HashMap<Matchup, ArrayList<Integer>>();
        HashMap<Matchup, ArrayList<Integer>> ragsByeOddsIfMatchupWon = new HashMap<Matchup, ArrayList<Integer>>();
        HashMap<Matchup, ArrayList<Integer>> ragsByeOddsIfMatchupLost = new HashMap<Matchup, ArrayList<Integer>>();
        Team teamToAssess = null;
        for (Team team : teams) {
            if (team.name == "Rags") {
                teamToAssess = team;
            }
        }
        
        for (int i = 0; i < results.size(); i++) {
            HashMap<Matchup, Boolean> resultsForRun = results.get(i);
            Iterator it = resultsForRun.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                Matchup matchup = (Matchup)pair.getKey();
                Boolean aWon = (Boolean)pair.getValue();
                ArrayList<Integer> odds;
                if(aWon) {
                    if (ragsOddsIfMatchupWon.containsKey(matchup)) {
                        odds = ragsOddsIfMatchupWon.get(matchup);
                        odds.add(teamToAssess.madePlayoffs.get(i));
                    } else {
                        odds = new ArrayList<Integer>();
                        odds.add(teamToAssess.madePlayoffs.get(i));
                        ragsOddsIfMatchupWon.put(matchup, odds);
                    }
                    if (ragsByeOddsIfMatchupWon.containsKey(matchup)) {
                        odds = ragsByeOddsIfMatchupWon.get(matchup);
                        odds.add(teamToAssess.gotBye.get(i));
                    } else {
                        odds = new ArrayList<Integer>();
                        odds.add(teamToAssess.gotBye.get(i));
                        ragsByeOddsIfMatchupWon.put(matchup, odds);
                    }
                } else {
                    if (ragsOddsIfMatchupLost.containsKey(matchup)) {
                        odds = ragsOddsIfMatchupLost.get(matchup);
                        odds.add(teamToAssess.madePlayoffs.get(i));
                    } else {
                        odds = new ArrayList<Integer>();
                        odds.add(teamToAssess.madePlayoffs.get(i));
                        ragsOddsIfMatchupLost.put(matchup, odds);
                    }
                    if (ragsByeOddsIfMatchupLost.containsKey(matchup)) {
                        odds = ragsByeOddsIfMatchupLost.get(matchup);
                        odds.add(teamToAssess.gotBye.get(i));
                    } else {
                        odds = new ArrayList<Integer>();
                        odds.add(teamToAssess.gotBye.get(i));
                        ragsByeOddsIfMatchupLost.put(matchup, odds);
                    }
                }
            }
        }
        Iterator it = ragsOddsIfMatchupWon.entrySet().iterator();
        HashMap<Matchup, Double> ragsOddsAvgIfMatchupWon = new HashMap<Matchup, Double>();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Matchup matchup = (Matchup)pair.getKey();
            ArrayList<Integer> odds = (ArrayList)pair.getValue();
            Iterator<Integer> itr = odds.iterator(); // traversing over HashSet
            Integer sumOfOdds = 0;
            while(itr.hasNext()){
                sumOfOdds += itr.next();
            }
            ragsOddsAvgIfMatchupWon.put(matchup, sumOfOdds.doubleValue()/odds.size());
        }
        Iterator it2 = ragsByeOddsIfMatchupWon.entrySet().iterator();
        HashMap<Matchup, Double> ragsByeOddsAvgIfMatchupWon = new HashMap<Matchup, Double>();
        while (it2.hasNext()) {
            Map.Entry pair = (Map.Entry)it2.next();
            Matchup matchup = (Matchup)pair.getKey();
            ArrayList<Integer> odds = (ArrayList)pair.getValue();
            Iterator<Integer> itr = odds.iterator(); // traversing over HashSet
            Integer sumOfOdds = 0;
            while(itr.hasNext()){
                sumOfOdds += itr.next();
            }
            ragsByeOddsAvgIfMatchupWon.put(matchup, sumOfOdds.doubleValue()/odds.size());
        }

        Iterator it1 = ragsOddsIfMatchupLost.entrySet().iterator();
        HashMap<Matchup, Double> ragsOddsAvgIfMatchupLost = new HashMap<Matchup, Double>();
        while (it1.hasNext()) {
            Map.Entry pair = (Map.Entry)it1.next();
            Matchup matchup = (Matchup)pair.getKey();
            ArrayList<Integer> odds = (ArrayList)pair.getValue();
            Iterator<Integer> itr = odds.iterator(); // traversing over HashSet
            Integer sumOfOdds = 0;
            while(itr.hasNext()){
                sumOfOdds += itr.next();
            }
            ragsOddsAvgIfMatchupLost.put(matchup, sumOfOdds.doubleValue()/odds.size());
        }
        Iterator it3 = ragsByeOddsIfMatchupLost.entrySet().iterator();
        HashMap<Matchup, Double> ragsByeOddsAvgIfMatchupLost = new HashMap<Matchup, Double>();
        while (it3.hasNext()) {
            Map.Entry pair = (Map.Entry)it3.next();
            Matchup matchup = (Matchup)pair.getKey();
            ArrayList<Integer> odds = (ArrayList)pair.getValue();
            Iterator<Integer> itr = odds.iterator(); // traversing over HashSet
            Integer sumOfOdds = 0;
            while(itr.hasNext()){
                sumOfOdds += itr.next();
            }
            ragsByeOddsAvgIfMatchupLost.put(matchup, sumOfOdds.doubleValue()/odds.size());
        }
        
        HashMap<Matchup, Double> matchupImportance = new HashMap<Matchup, Double>();
        Iterator iterator = ragsOddsAvgIfMatchupWon.entrySet().iterator();
        ArrayList<Matchup> matchups = new ArrayList<Matchup>();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry)iterator.next();
            Matchup matchup = (Matchup)pair.getKey();
            Double odds = (Double)pair.getValue();
            Double diff;
            if (ragsOddsAvgIfMatchupLost.containsKey(matchup)) {
                diff = odds - ragsOddsAvgIfMatchupLost.get(matchup);
            } else {
                diff = 0.0;
            }
            matchup.importance = diff;
            matchups.add(matchup);
        }
        Iterator iterator2 = ragsByeOddsAvgIfMatchupWon.entrySet().iterator();
        ArrayList<Matchup> byematchups = new ArrayList<Matchup>();
        while (iterator2.hasNext()) {
            Map.Entry pair = (Map.Entry)iterator2.next();
            Matchup matchup = (Matchup)pair.getKey();
            Double odds = (Double)pair.getValue();
            Double diff;
            if (ragsByeOddsAvgIfMatchupLost.containsKey(matchup)) {
                diff = odds - ragsByeOddsAvgIfMatchupLost.get(matchup);
            } else {
                diff = 0.0;
            }
            matchup.byeimportance = diff;
            byematchups.add(matchup);
        }

        Collections.sort(matchups, new MatchupImportanceComparator());
        Collections.reverse(matchups);
        System.out.println();
        for (Matchup matchup : matchups) {
            if(matchup.importance > .00001) {
                System.out.println("Week " + matchup.week + ": " + matchup.a.name + " beating " + matchup.b.name + " swings " + teamToAssess.name + " playoff odds by: " + Math.round(100 * 100.00 * matchup.importance) / 100.00 + "%");
            } else if (matchup.importance < -.00001) {
                System.out.println("Week " + matchup.week + ": " + matchup.b.name + " beating " + matchup.a.name + " swings " + teamToAssess.name + " playoff odds by: " + Math.round(-100 * 100.00 * matchup.importance) / 100.00 + "%");
            }
        }
        Collections.sort(byematchups, new MatchupByeImportanceComparator());
        Collections.reverse(byematchups);
        System.out.println();
        for (Matchup matchup : byematchups) {
            if(matchup.byeimportance > .00001) {
                System.out.println("Week " + matchup.week + ": " + matchup.a.name + " beating " + matchup.b.name + " swings " + teamToAssess.name + " bye odds by: " + Math.round(100 * 100.00 * matchup.byeimportance) / 100.00 + "%");
            } else if (matchup.byeimportance < -.00001) {
                System.out.println("Week " + matchup.week + ": " + matchup.b.name + " beating " + matchup.a.name + " swings " + teamToAssess.name + " bye odds by: " + Math.round(-100 * 100.00 * matchup.byeimportance) / 100.00 + "%");
            }
        }

    }

    private double calculateAverage(ArrayList<Integer> seeds) {
        Integer sum = 0;
        if(!seeds.isEmpty()) {
            for (Integer seed : seeds) {
                sum += seed;
            }
            return sum.doubleValue() / seeds.size();
        }
        return sum;
    }
}