package birdy;

import java.util.*;

import static birdy.BotGame.getString;

public class Game {
    private ArrayList<Player> players;
    private Queue<Pipe> pipes;
    private GameState status;
    int windowWidth;
    int windowHeight;
    Random rand;

    public Game(ArrayList<Player> players,int windowWidth,int windowHeight, Random r) {
        this.players = players;
        rand = r;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        pipes = new LinkedList<>();
        for (int i = 200; i <= windowWidth; i+=200) {
            pipes.add(new Pipe(i,rand,windowHeight));
        }
        status = GameState.READY;
    }

    public List<Player> getPlayers(){
        return players;
    }

    public GameState getStatus(){
        return status;
    }

    public Queue<Pipe> getPipes(){
        return pipes;
    }

    public String toString(){
        String res =  "(Game){\n    Players:{\n      ";
        return getString(res, players.toArray(), pipes, status);
    }

    public void updateGame(Random r,int windowWidth, int windowHeight) {
        pipes.forEach(p -> p.update(3));
        players.forEach(p-> {
            if (p.state == PlayerState.ALIVE){
                p.updatePlayer(windowWidth,windowHeight);
            }
        });
        if (!pipes.isEmpty()) {
            players.forEach(p -> p.handleCollision(pipes.peek()));
        }
        if (!pipes.isEmpty() && (pipes.peek().getX()+pipes.peek().getWidth() < 0)){
            pipes.poll();
            pipes.add(new Pipe(windowWidth,r, windowHeight));
            players.forEach(Player::updateScore);
        }
        if (!checkAlive()){
            System.out.println("Game over");
            status = GameState.ENDED;
        }
    }

    public boolean checkAlive(){
        return players.stream().anyMatch(p->p.state==PlayerState.ALIVE);
    }
}
