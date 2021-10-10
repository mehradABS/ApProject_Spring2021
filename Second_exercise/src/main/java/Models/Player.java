package Models;

public class Player {
    private int score;
    public static String id;
    private Game game;
    public Player(){
        game = new Game();
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        Player.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
