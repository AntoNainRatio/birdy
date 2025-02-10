package birdy;

public class Bot extends Player{
    NeuralNetwork network;
    int tickCount;

    public Bot(NeuralNetwork network) {
        super();
        this.network = network;
        state = PlayerState.ALIVE;
        tickCount = 0;
    }

    public int getTickCount() {
        return tickCount;
    }

    private double[] getInputsForBot(Pipe nextPipe, double height, double width) {
        Bird bird = getBird();

        return new double[]{
                bird.y / height,
                bird.speed / 10.0,
                (nextPipe.getX() - bird.x) / width,
                (nextPipe.getyUpper() - bird.y) / height,
                (nextPipe.getyLower() - bird.y) / height
        };
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
        double[] inputs = getInputsForBot(pipe,height,width);
        if (network.compute(inputs) > 0.5){
            b.flap();
        }
    }
}
