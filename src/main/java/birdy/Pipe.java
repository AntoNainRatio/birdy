package birdy;

import java.util.Random;

public class Pipe {
    private int x;
    private final int yUpper;
    private final int yLower;
    private final int width;
    private final int holeSize;

    public Pipe(int x, Random r, int height){
        this.x = x;
        holeSize = 100;
        width = 30;
        int minY = holeSize * 2;
        int maxY = height - minY;
        int y = r.nextInt(maxY-minY) + minY;
        yUpper = y - getHoleSize();
        yLower = y + getHoleSize();
    }

    public void update(int pipeSpeed){
        x -= pipeSpeed;
    }

    public int getX(){
        return x;
    }

    public int getyLower(){
        return yLower;
    }

    public int getyUpper(){
        return yUpper;
    }

    public int getHoleSize(){
        return holeSize;
    }

    public int getWidth(){
        return width;
    }

    public String toString(){
        return "(Pipe){X: "+x+", yUpper: "+yUpper+", yLower: "+yLower+", holeSize: "+holeSize+"}";
    }
}
