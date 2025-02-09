package birdy;

import java.util.*;

public class BotGame {
    private ArrayList<Bot> bots;
    private Queue<Pipe> pipes;
    private GameState status;
    int windowWidth;
    int windowHeight;
    Random rand;

    public BotGame(ArrayList<Bot> bots,int windowWidth,int windowHeight, Random r) {
        this.bots = bots;
        rand = r;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        pipes = new LinkedList<>();
        for (int i = 200; i <= windowWidth; i+=200) {
            pipes.add(new Pipe(i,rand,windowHeight));
        }
        status = GameState.RUNNIG;
    }

    public List<Bot> getBots(){
        return bots;
    }

    public GameState getStatus(){
        return status;
    }

    public Queue<Pipe> getPipes(){
        return pipes;
    }

    public String toString(){
        String res =  "(Game){\n    Bots:{\n      ";
        return getString(res, bots.toArray(), pipes, status);
    }

    static String getString(String res, Object[] array, Queue<Pipe> pipes, GameState status) {
        Object[] arr1 = array;
        for (int i = 0; i < arr1.length; i++) {
            res += "        "+arr1[i].toString()+",\n";
        }
        res += "    },\n    Pipes: \n";
        Object[] arr = pipes.toArray();
        for (int i = 0; i < arr.length; i++) {
            res+="      "+i+" -> "+arr[i].toString()+",\n";
        }
        res += "    Status: "+ status.toString()+"\n}";
        return res;
    }

    public Pipe getNextPipe(){
        Pipe peek = pipes.peek();
        if(peek.getX() + peek.getWidth() < bots.get(0).getBird().x){
            return (Pipe)pipes.toArray()[1];
        }
        return peek;
    }

    public void updateGame(Random r,int windowWidth, int windowHeight) {
        pipes.forEach(p -> p.update(3));
        Pipe nextPipe = getNextPipe();
        bots.forEach(b-> {
            if (b.state == PlayerState.ALIVE){
                b.updateBot(windowWidth,windowHeight,nextPipe);
            }
        });
        if (!pipes.isEmpty()) {
            bots.forEach(b -> b.handleCollision(pipes.peek()));
        }
        if (!pipes.isEmpty() && (pipes.peek().getX()+pipes.peek().getWidth() < 0)){
            pipes.poll();
            pipes.add(new Pipe(windowWidth,r, windowHeight));
            bots.forEach(Player::updateScore);
        }
        if (!checkAlive()){
            status = GameState.ENDED;
        }
    }

    public boolean checkAlive(){
        return bots.stream().anyMatch(b->b.state==PlayerState.ALIVE);
    }

    public Bot findBestBot(){
        Bot best = bots.get(0);
        for (int i = 1; i < bots.size(); i++) {
            if (bots.get(i).getTickCount() > best.getTickCount()){
                best = bots.get(i);
            }
        }
        return best;
    }
}
