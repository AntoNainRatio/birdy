package birdy;

import javax.swing.text.Utilities;
import java.util.Random;

public class Neuron {
    private double bias = 0;
    private double weight1;
    private double weight2;
    private double weight3;
    private double weight4;
    private double weight5;
    private Random rand = new Random();

    public Neuron(double weight1, double weight2, double weight3, double weight4, double weight5) {
        this.weight1 = weight1;
        this.weight2 = weight2;
        this.weight3 = weight3;
        this.weight4 = weight4;
        this.weight5 = weight5;
    }

    public static double sigmoid(double in){
        return 1 / (1 + Math.exp(-in));
    }

    public boolean compute(int yBird, int speed, int xDist, int yTopDist, int yBotDist) {
        double moy = weight1*yBird + weight2*speed + weight3*xDist + weight4*yTopDist + weight5*yBotDist + this.bias;
        double output = sigmoid(moy);
        return output > 0;
    }

    public Neuron mutate(double min, double max) {
        double newweight1 = this.weight1 + rand.nextDouble(min, max);
        double newweight2 = this.weight2 + rand.nextDouble(min, max);
        double newweight3 = this.weight3 + rand.nextDouble(min, max);
        double newweight4 = this.weight4 + rand.nextDouble(min, max);
        double newweight5 = this.weight5 + rand.nextDouble(min, max);
        return new Neuron(newweight1, newweight2, newweight3, newweight4, newweight5);
    }

    public String toString() {
        return "Neuron{" + "w1: " + weight1 +", w2: "+weight2+", w23: "+weight3+", w4: "+weight4+", w5: "+weight5+'}';
    }
}
