package birdy;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void printHelp(){
        System.out.println("Usage: java [exec][options]");
        System.out.println("Options:");
        System.out.println("\t-h\t\tPrint this message");
        System.out.println("\t-p\t\tPlay the game");
    }

    public static void main(String[] args) {
        if (args.length == 1) {
            if (args[0].equals("-p")){
                Random r  = new Random();
                int width = 1000;
                int height = 800;
                Player p = new Player();
                ArrayList<Player> players = new ArrayList<Player>();
                players.add(p);
                Game g = new Game(players,width,height,r);
                KeyManaging k = new KeyManaging(g);
                GameDisplay d = new GameDisplay(g,k,r,width,height);
            } else if (args[0].equals("-h")) {
                printHelp();
            } else{
                System.out.println("Error: invalid command line arguments");
                printHelp();
            }
        }
        else if (args.length == 0) {
            Random r  = new Random();
            int width = 1000;
            int height = 800;
            ArrayList<Bot> bots = new ArrayList<Bot>();
            for (int i = 0; i < 1000; i++) {
                NeuralNetwork nn = new NeuralNetwork();
                bots.add(new Bot(nn));
            }
            BotGame g = new BotGame(bots,width,height,r);
            BotDisplay d = new BotDisplay(g,r,width,height);
        }
        else {
            System.out.println("Error: invalid command line arguments");
        }
    }
}