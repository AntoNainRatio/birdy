package birdy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

public class BotDisplay extends JFrame {
    int windowWidth = 900;
    int windowHeight = 600;
    Random rand;
    BotGame botGame;
    int highscore = 0;
    Neuron bestNeuron;

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
                Bot best = botGame.findBestBot();
                if (best.getTickCount() > highscore){
                    highscore = best.getTickCount();
                    bestNeuron = best.neuron;
                    System.out.println("New Highscore: " + highscore);
                    System.out.println(best.neuron);
                }
                ArrayList<Bot> bots = new ArrayList<>();
                for (int i = 0; i < 1000; i++) {
                    Bot tmp = new Bot(best.neuron.mutate(-1,1));
                    bots.add(tmp);
                }
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
        for (int i = 0; i < game.getBots().size(); i++) {
            if (game.getBots().get(i).state == PlayerState.ALIVE) {
                drawPlayer(g, game.getBots().get(i));
            }
        }
    }
}
