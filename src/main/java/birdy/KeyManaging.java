package birdy;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManaging implements KeyListener {
    Game game;

    public KeyManaging(Game g){
        game = g;
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && game.getPlayer().state == PlayerState.ALIVE){
            game.getPlayer().getBird().flap();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
