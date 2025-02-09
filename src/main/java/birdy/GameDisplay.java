package birdy;

import javax.swing.*;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.Queue;
import java.util.Random;

public class GameDisplay extends JFrame{
    int windowWidth = 900;
    int windowHeight = 600;
    Random rand;
    Game game;
    KeyManaging keyManaging;

    public GameDisplay(Game g, KeyManaging km, Random r, int w, int h) {
        game = g;
        keyManaging = km;
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
        addKeyListener(keyManaging);

        GamePanel panel = new GamePanel();
        setContentPane(panel);
        setVisible(true);

        Timer timer = new Timer(20, e -> {
            game.updateGame(rand,windowWidth, windowHeight);  // Update game logic
            repaint();  // Redraw the frame
        });
        timer.start();
        if (game.getStatus() == GameState.ENDED){
            timer.stop();
        }
    }

    private class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); // Clears the screen before drawing
            drawGame(g, game);
        }
    }

    public void drawBird(Graphics g, Bird b){
        g.fillArc(b.x, b.y, b.radius, b.radius, 0, 360);
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

    public void drawPipes(Graphics g, Queue<Pipe> pipes){
        Color c = new Color(10, 230, 10);
        g.setColor(c);
        pipes.forEach(p -> drawPipe(g, p));
        g.setColor(new Color(0, 0, 0));
    }

    public void drawGame(Graphics g, Game game){
        drawPipes(g, game.getPipes());
        for (int i = 0; i < game.getPlayers().size(); i++) {
            if (game.getPlayers().get(i).state == PlayerState.ALIVE) {
                drawPlayer(g, game.getPlayers().get(i));
            }
        }
    }
}
