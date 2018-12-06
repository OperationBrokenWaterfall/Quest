package teamb.cs262.calvin.edu.quest;
/*
* Database Access Object to bridge the data impedance mismatch
* */
public class Team {


    public String name;
    public int score;

    public Team() {
        name = "Default";
        score = 0;
    }

    public Team(String name, int score) {
        this.name = name;
        this.score = score;
    }
}
