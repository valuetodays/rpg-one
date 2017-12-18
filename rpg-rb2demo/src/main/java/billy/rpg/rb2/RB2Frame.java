package billy.rpg.rb2;

import billy.rpg.rb2.draw.BaseDraw;
import billy.rpg.rb2.draw.MapDraw;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Stack;

/**
 * @author liulei@bshf360.com
 * @since 2017-12-15 09:54
 */
public class RB2Frame extends JFrame implements Runnable {
    private static final Logger LOG = Logger.getLogger(RB2Frame.class);

    private Stack<BaseDraw> stacks = new Stack<>();
    private RB2Panel panel;
    private RB2Cavas cavas;

    public static void main(String[] args) {
        RB2Frame frame = new RB2Frame();

        new Thread(frame, "rb2").start();
    }

    public RB2Frame() {
        setTitle("...");
        setLocation(200, 100);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
        panel = new RB2Panel();
        getContentPane().add(panel);
        addKeyListener(createKeyListener());
        cavas = new RB2Cavas(this);
        stacks.add(new MapDraw());
        pack();
    }


    @Override
    public void run() {
        while (true) {
            BaseDraw draw = stacks.peek();

            if (draw != null) {
                draw.update();
                draw.draw(cavas);
            }
            panel.repaint();

            try {
                Thread.sleep(34); // 33 fps ?
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private KeyListener createKeyListener() {
        return new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                BaseDraw peek = stacks.peek();
                if (peek != null) {
                    peek.onKeyUp(e.getKeyCode());
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                BaseDraw peek = stacks.peek();
                if (peek != null) {
                    peek.onKeyDown(e.getKeyCode());
                }
            }
        };
    }

    public RB2Panel getPanel() {
        return panel;
    }
}
