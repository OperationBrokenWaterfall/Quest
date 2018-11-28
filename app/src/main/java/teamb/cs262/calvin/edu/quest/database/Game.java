package teamb.cs262.calvin.edu.quest.database;

/**
 * This class implements a Game Data-Access Object (DAO) class for the Game relation.
 * This provides an object-oriented way to represent and manipulate player "objects" from
 * the traditional (non-object-oriented) Monopoly database.
 *
 */
public class Game {

    private String teamName, location;

    public Game() {
        // The JSON marshaller used by Endpoints requires this default constructor.
    }
    public Game(String teamName, String location) {
        this.teamName = teamName;
        this.location = location;
    }

    public String getTeamName() {
        return this.teamName;
    }
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getLocation() {
        return this.location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

}
