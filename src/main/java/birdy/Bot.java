package birdy;

public class Bot extends Player{
    Neuron neuron;
    int tickCount;

    public Bot(Neuron neuron) {
        super();
        this.neuron = neuron;
        state = PlayerState.ALIVE;
        tickCount = 0;
    }

    public int getTickCount() {
        return tickCount;
    }

    public void updateBot(int width, int height,Pipe pipe) {
        tickCount++;
        Bird b = getBird();
        b.update();
        if (b.y < 0 || b.y > height){
            setState(PlayerState.DEAD);
        }
        int xDist = Math.abs(pipe.getX() - b.x);
        int yTopDist = Math.abs(pipe.getyUpper() - b.y);
        int yBotDist = Math.abs(pipe.getyUpper() - b.y);
        if (neuron.compute(b.y,b.speed,xDist,yTopDist,yBotDist)){
            b.flap();
        }
    }
}
