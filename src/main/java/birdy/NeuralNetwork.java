package birdy;

import java.util.ArrayList;
import java.util.Random;

public class NeuralNetwork {
    private double[][] weightsHiddenInput;
    private double[] biasesHidden;
    private double[] weightsHiddenOutput;
    private double biasOutput;
    private Random rand;

    public NeuralNetwork() {
        rand = new Random();

        weightsHiddenInput = new double[3][5];
        biasesHidden = new double[3];
        weightsHiddenOutput = new double[3];
        biasOutput = rand.nextGaussian();

        for (int i = 0; i < 3; i++) {
            biasesHidden[i] = rand.nextGaussian();
            weightsHiddenOutput[i] = rand.nextGaussian();
            for (int j = 0; j < 5; j++) {
                weightsHiddenInput[i][j] = rand.nextGaussian();
            }
        }
    }

    private double sigmoid(double x) {
        return 1.0 / (1.0 + Math.exp(-x));
    }

    public double compute(double[] inputs) {
        if (inputs.length != 5) throw new IllegalArgumentException("Expected 5 inputs");

        double[] hiddenOutputs = new double[3];
        for (int i = 0; i < 3; i++) {
            double sum = biasesHidden[i];
            for (int j = 0; j < 5; j++) {
                sum += weightsHiddenInput[i][j] * inputs[j];
            }
            hiddenOutputs[i] = sigmoid(sum);
        }

        double sumOutput = biasOutput;
        for (int i = 0; i < 3; i++) {
            sumOutput += weightsHiddenOutput[i] * hiddenOutputs[i];
        }

        return sigmoid(sumOutput);
    }

    public NeuralNetwork mutate(double mutationRate) {
        NeuralNetwork mutated = new NeuralNetwork();

        for (int i = 0; i < 3; i++) {
            mutated.biasesHidden[i] = biasesHidden[i] + rand.nextGaussian() * mutationRate;
            mutated.weightsHiddenOutput[i] = weightsHiddenOutput[i] + rand.nextGaussian() * mutationRate;
            for (int j = 0; j < 5; j++) {
                mutated.weightsHiddenInput[i][j] = weightsHiddenInput[i][j] + rand.nextGaussian() * mutationRate;
            }
        }

        mutated.biasOutput = biasOutput + rand.nextGaussian() * mutationRate;
        return mutated;
    }

    public NeuralNetwork merge(NeuralNetwork n1, double mutationRate) {
        NeuralNetwork merged = new NeuralNetwork();

        for (int i = 0; i < 3; i++) {
            merged.biasesHidden[i] = (rand.nextBoolean()) ? biasesHidden[i] : n1.biasesHidden[i];
            merged.weightsHiddenOutput[i] = (rand.nextBoolean()) ? weightsHiddenOutput[i] : n1.weightsHiddenOutput[i];
            for (int j = 0; j < 5; j++) {
                merged.weightsHiddenInput[i][j] = (rand.nextBoolean()) ? weightsHiddenInput[i][j] : n1.weightsHiddenInput[i][j];
            }
        }
        merged.biasOutput = (rand.nextBoolean()) ? biasOutput : n1.biasOutput;

        return merged.mutate(mutationRate);
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Neural Network:{");
        for (int i = 0; i < 3; i++) {
            sb.append("Bias Hidden[").append(i).append("]: ").append(biasesHidden[i]).append(", ");
            sb.append("WeightsHiddenOutput[").append(i).append("]: ").append(weightsHiddenOutput[i]).append(", ");
            for (int j = 0; j < 5; j++) {
                sb.append(String.format("weightsHiddenInput["+i+"]["+j+"]: %.2f, ", weightsHiddenInput[i][j]));
            }
            sb.append("BiasOutput[").append(i).append("]: ").append(biasOutput);
        }
        return sb.append("}").toString();
    }
}
