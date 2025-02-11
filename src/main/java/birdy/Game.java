package birdy;

import java.util.*;

import static birdy.BotGame.getString;

public class Game {
    private Player player;
    private Queue<Pipe> pipes;
    private GameState status;
    int windowWidth;
    int windowHeight;
    Random rand;

    public Game(Player player,int windowWidth,int windowHeight, Random r) {
        this.player = player;
        rand = r;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        pipes = new LinkedList<>();
        for (int i = 200; i <= windowWidth; i+=200) {
            pipes.add(new Pipe(i,rand,windowHeight));
        }
        status = GameState.RUNNIG;
    }

    public Player getPlayer(){
        return player;
    }

    public GameState getStatus(){
        return status;
    }

    public Queue<Pipe> getPipes(){
        return pipes;
    }

    public String toString(){
        String res =  "(Game){\n    "+player.toString()+"\n      ";
        return res + pipes.toString() + "\n   Status: "+status.toString()+"\n}";
    }

    public void updateGame(Random r,int windowWidth, int windowHeight) {
        pipes.forEach(p -> p.update(3));
        player.updatePlayer(windowWidth, windowHeight);
        if (!pipes.isEmpty()) {
            player.handleCollision(pipes.peek());
        }
        if (!pipes.isEmpty() && (pipes.peek().getX()+pipes.peek().getWidth() < 0)){
            pipes.poll();
            pipes.add(new Pipe(windowWidth,r, windowHeight));
            player.updateScore();
        }
        if (!checkAlive()){
            status = GameState.ENDED;
        }
    }

    public boolean checkAlive(){
        return player.state == PlayerState.ALIVE;
    }
}
