import java.util.ArrayList;
class PlayoffOdds {

    public static void main(String args[]) {
        Team ls = new Team("League Sources", 7, 12);
        Team chungus = new Team("Big Chungus", 6, 11);
        Team slotty = new Team("Slotty", 5, 8);
        Team ditka = new Team("Your Ditka Ertz My Butt", 2, 3);
        Team mcgee = new Team("Mcgee", 0, 1);
        Team richie = new Team("Richie", 5, 5);
        Team juju = new Team("Juju Train", 6, 9);
        Team rags = new Team("Rags", 5, 7);
        Team boyz = new Team("Boyz in the Hood", 3, 6);
        Team sull = new Team("Sullivan", 7, 10);
        Team ashes = new Team("Ashes", 1, 2);
        Team anvils = new Team("Anvils", 1, 4);
        ArrayList<Team> teams = new ArrayList<Team>(12);
        teams.add(ls);
        teams.add(chungus);
        teams.add(slotty);
        teams.add(ditka);
        teams.add(mcgee);
        teams.add(richie);
        teams.add(juju);
        teams.add(rags);
        teams.add(boyz);
        teams.add(sull);
        teams.add(ashes);
        teams.add(anvils);

        Matchup matchup1 = new Matchup(sull, rags, 100, 9);
        Matchup matchup2 = new Matchup(ls, slotty, 0, 9);
        Matchup matchup3 = new Matchup(juju, ditka, 0, 9);
        Matchup matchup4 = new Matchup(anvils, mcgee, 0, 9);
        Matchup matchup5 = new Matchup(richie, boyz, 0, 9);
        Matchup matchup6 = new Matchup(ashes, chungus, 0, 9);

        Matchup[] week9matchups = {matchup1, matchup2, matchup3, matchup4, matchup5, matchup6};
        Week week9 = new Week(week9matchups);

        matchup1 = new Matchup(rags, juju, 0, 10);
        matchup2 = new Matchup(ls, sull, 100, 10);
        matchup3 = new Matchup(slotty, anvils, 91, 10);
        matchup4 = new Matchup(ditka, richie, 100, 10);
        matchup5 = new Matchup(mcgee, ashes, 74, 10);
        matchup6 = new Matchup(boyz, chungus, 53, 10);

        Matchup[] week10matchups = {matchup1, matchup2, matchup3, matchup4, matchup5, matchup6};
        Week week10 = new Week(week10matchups);

        matchup1 = new Matchup(richie, rags, 50, 11);
        matchup2 = new Matchup(sull, juju, 56, 11);
        matchup3 = new Matchup(anvils, ls, 22, 11);
        matchup4 = new Matchup(ashes, slotty, 27, 11);
        matchup5 = new Matchup(chungus, ditka, 63, 11);
        matchup6 = new Matchup(boyz, mcgee, 62, 11);

        Matchup[] week11matchups = {matchup1, matchup2, matchup3, matchup4, matchup5, matchup6};
        Week week11 = new Week(week11matchups);

        matchup1 = new Matchup(rags, chungus, 42, 12);
        matchup2 = new Matchup(anvils, sull, 34, 12);
        matchup3 = new Matchup(juju, richie, 54, 12);
        matchup4 = new Matchup(ls, ashes, 85, 12);
        matchup5 = new Matchup(slotty, boyz, 58, 12);
        matchup6 = new Matchup(ditka, mcgee, 57, 12);

        Matchup[] week12matchups = {matchup1, matchup2, matchup3, matchup4, matchup5, matchup6};
        Week week12 = new Week(week12matchups);

        matchup1 = new Matchup(mcgee, rags, 34, 13);
        matchup2 = new Matchup(sull, richie, 59, 13);
        matchup3 = new Matchup(ashes, anvils, 42, 13);
        matchup4 = new Matchup(chungus, juju, 55, 13);
        matchup5 = new Matchup(boyz, ls, 32, 13);
        matchup6 = new Matchup(ditka, slotty, 38, 13);

        Matchup[] week13matchups = {matchup1, matchup2, matchup3, matchup4, matchup5, matchup6};
        Week week13 = new Week(week13matchups);
        
        Week[] weeks = {week9, week10, week11, week12, week13};
        Schedule schedule = new Schedule(weeks);
        League league = new League(teams, schedule);
        int runs = Integer.parseInt(args[0]);
        league.simSeason(runs);
    }
}