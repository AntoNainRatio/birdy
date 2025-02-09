package birdy;

public class Player {
    PlayerState state;
    private final Bird bird;
    private int score;

    public Player(){
        bird = new Bird();
        state = PlayerState.ALIVE;
        score = 0;
    }

    public Bird getBird(){
        return bird;
    }

    public int getScore(){
        return score;
    }

    public void setState(PlayerState state){
        this.state = state;
    }

    public void updateScore(){
        score += 1;
    }

    public void handleCollision(Pipe pipe){
        int pipeXLeft = pipe.getX();
        int pipeXRight = pipe.getX() + pipe.getWidth();
        int pipeYLower = pipe.getyLower();
        int pipeYUpper = pipe.getyUpper();
        Bird b = getBird();
        if (b.x + b.radius >= pipeXLeft && b.x <= pipeXRight){
            if (b.y <= pipeYUpper || b.y + b.radius >= pipeYLower){
                /*System.out.println(b.toString());
                System.out.println("TopBird: "+(b.y));
                System.out.println("BotBird: "+(b.y+b.radius));
                System.out.println(pipe.toString());*/
                state = PlayerState.DEAD;
            }
        }
    }

    public void updatePlayer(int width, int height){
        Bird b = getBird();
        b.update();
        if (b.y < 0 || b.y > height){
            setState(PlayerState.DEAD);
        }
    }

    public String toString(){
        return "(Player){Score: "+score+", "+bird.toString()+"}";
    }
}
