package PaooGame.Graphics;

import PaooGame.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainMenu implements MouseListener {
    private Game game;
    private Graphics g;// Add a reference to the Game instance

    public MainMenu(Game game, Graphics g) {
        this.game = game;  // Initialize the Game instance
        //this.g=g;
    }

    public static Rectangle playButton = new Rectangle(1200 / 2 + 120, 150, 100, 50);
    public static Rectangle helpButton = new Rectangle(1200 / 2 + 120, 250, 100, 50);
    public static Rectangle stopButton = new Rectangle(1200 / 2 + 120, 350, 100, 50);

    public static Rectangle level1Button = new Rectangle(1200 / 2 + 90, 150, 125, 50);
    public static Rectangle level2Button = new Rectangle(1200 / 2 + 90, 250, 125, 50);
    public static Rectangle level3Button = new Rectangle(1200 / 2 + 90, 350, 125, 50);

    public static void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        Font fnt0 = new Font("arial", Font.BOLD, 50);
        g.setFont(fnt0);
        Font fnt1 = new Font("arial", Font.BOLD, 30);
        if (Game.state == Game.GAME_STATE.MENU) {
            g.drawString("Knightly Order", 1200 / 2, 100);
            g.setFont(fnt1);
            g.drawString("Play", playButton.x + 19, playButton.y + 32);
            g.drawString("Help", helpButton.x + 19, helpButton.y + 32);
            g.drawString("Exit", stopButton.x + 19, stopButton.y + 32);
            g2d.draw(playButton);
            g2d.draw(helpButton);
            g2d.draw(stopButton);
        } else if (Game.state == Game.GAME_STATE.LEVEL_SELECTION) {
            g.drawString("Select Level", 1200 / 2, 100);
            g.setFont(fnt1);
            g.drawString("Level 1", level1Button.x + 19, level1Button.y + 32);
            g.drawString("Level 2", level2Button.x + 19, level2Button.y + 32);
            g.drawString("Level 3", level3Button.x + 19, level3Button.y + 32);
            g2d.draw(level1Button);
            g2d.draw(level2Button);
            g2d.draw(level3Button);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        /*public static Rectangle playButton=new Rectangle(1200/2+120,150,100,50);
        public static Rectangle helpButton=new Rectangle(1200/2+120,250,100,50);
        public static Rectangle stopButton=new Rectangle(1200/2+120,350,100,50);*/
        int mx = e.getX();
        //System.out.println("x" + mx);

        int my = e.getY();
        //System.out.println("y" + my);
        //Play button
        if (Game.state == Game.GAME_STATE.MENU) {
            if (mx >= playButton.x && mx <= playButton.x + playButton.width) {
                if (my >= playButton.y && my <= playButton.y + playButton.height) {
                    Game.state = Game.GAME_STATE.LEVEL_SELECTION;
                }
            } else if (mx >= helpButton.x && mx <= helpButton.x + helpButton.width) {
                if (my >= helpButton.y && my <= helpButton.y + helpButton.height) {
                    System.out.println("Help was not yet implemented");
                }
            } else if (mx >= stopButton.x && mx <= stopButton.x + stopButton.width) {
                if (my >= stopButton.y && my <= stopButton.y + stopButton.height) {
                    game.StopGame();
                }
            }
        } else if (Game.state == Game.GAME_STATE.LEVEL_SELECTION) {
            if (mx >= level1Button.x && mx <= level1Button.x + level1Button.width) {
                if (my >= level1Button.y && my <= level1Button.y + level1Button.height) {
                    // Load level 1
                    Game.state = Game.GAME_STATE.GAME;
                }
            } else if (mx >= level2Button.x && mx <= level2Button.x + level2Button.width) {
                if (my >= level2Button.y && my <= level2Button.y + level2Button.height) {
                    // Load level 2
                    Game.state = Game.GAME_STATE.GAME;
                }
            } else if (mx >= level3Button.x && mx <= level3Button.x + level3Button.width) {
                if (my >= level3Button.y && my <= level3Button.y + level3Button.height) {
                    // Load level 3
                    Game.state = Game.GAME_STATE.GAME;
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}