package birdy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.Collectors;

public class BotDisplay extends JFrame {
    int windowWidth = 900;
    int windowHeight = 600;
    Random rand;
    BotGame botGame;
    int highscore = 0;
    NeuralNetwork bestNetwork;
    int generation = 0;

    public BotDisplay(BotGame bg, Random r, int w, int h) {
        botGame = bg;
        rand = r;
        windowWidth = w;
        windowHeight = h;
        setTitle("birdy");
        setFocusable(true);
        requestFocus();
        setSize(windowWidth,windowHeight);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
        setVisible(true);

        GamePanel panel = new GamePanel();
        setContentPane(panel);
        setVisible(true);

        Timer timer = new Timer(20, e -> {
            botGame.updateGame(rand,windowWidth, windowHeight);
            repaint();
            if (botGame.getStatus() == GameState.ENDED){
                double mutationRate = Math.max(0.02, 0.2 - (generation * 0.001));
                ArrayList<Bot> bests = botGame.findBestBots();
                bests.sort((b1, b2) -> Double.compare(b2.getTickCount(), b1.getTickCount()));
                ArrayList<Bot> bots = new ArrayList<>();
                bots.add(bests.get(0));
                bots.add(bests.get(1));
                bots.add(bests.get(2));
                if (bests.get(0).tickCount > highscore){
                    highscore = bests.get(0).tickCount;
                    bestNetwork = bests.get(0).network;
                    System.out.println("New Best ("+highscore+"): "+bests.get(0).network.toString());
                }
                for (int i = 3; i < 1000; i++) {
                    NeuralNetwork n1 = bests.get(rand.nextInt(10)).network;
                    NeuralNetwork n2 = bests.get(rand.nextInt(10)).network;
                    NeuralNetwork child = n1.merge(n2,mutationRate);
                    Bot tmp = new Bot(child);
                    bots.add(tmp);

                }
                generation++;
                botGame = new BotGame(bots,windowWidth,windowHeight,r);
            }
        });
        timer.start();

    }

    private class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); // Clears the screen before drawing
            drawGame(g, botGame);
        }
    }

    public void drawBird(Graphics g, Bird b){
        g.setColor(new Color(0,0,0, 120));
        g.fillArc(b.x, b.y, b.radius, b.radius, 0, 360);
        g.setColor(Color.black);
    }

    public void drawPlayer(Graphics g, Player p){
        drawBird(g, p.getBird());
        String str = "Score:"+p.getScore();

        g.drawString(str, 10, 20);
    }

    public void drawPipe(Graphics g, Pipe p){
        int height = getHeight();
        int upperHeight = p.getyUpper();
        int lowerHeight = height - p.getyLower();
        g.fillRect(p.getX(),p.getyLower(), p.getWidth() ,lowerHeight);
        g.fillRect(p.getX(),0, p.getWidth() ,upperHeight);
    }

    public void drawPipes(Graphics g, java.util.Queue<Pipe> pipes){
        Color c = new Color(10, 230, 10);
        g.setColor(c);
        pipes.forEach(p -> drawPipe(g, p));
        g.setColor(new Color(0, 0, 0));
    }

    public void drawGame(Graphics g, BotGame game){
        drawPipes(g, game.getPipes());
        int alive = 0;
        for (int i = 0; i < game.getBots().size(); i++) {
            if (game.getBots().get(i).state == PlayerState.ALIVE) {
                drawPlayer(g, game.getBots().get(i));
                alive++;
            }
        }
        g.drawString("Alive: "+alive, 10, 34);
        g.drawString("Generation: "+generation, 10, 48);
    }
}
