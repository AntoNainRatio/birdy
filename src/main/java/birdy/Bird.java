package birdy;

public class Bird {
    public int x;
    public int y;
    public int radius;
    public int speed;

    public Bird(){
        x = 50;
        y = 400;
        speed = 0;
        radius = 20;
    }

    public String toString()
    {
        return "(Bird){X: "+x+", Y: "+y +", Speed: "+speed+"}";
    }

    public void update(){
        y += speed;
        speed += 2;
    }

    public void flap(){
        speed = -20;
    }
}
