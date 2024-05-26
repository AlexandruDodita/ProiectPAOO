package PaooGame.Graphics;

import PaooGame.Entity.Entity;
import PaooGame.Entity.Player;
import PaooGame.Game;
import PaooGame.Save;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainMenu implements MouseListener {
    private Game game;

    public MainMenu(Game game, Graphics g) {
        this.game = game;
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
        // This method can be left empty if not used
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (Game.state == Game.GAME_STATE.MENU) {
            if (playButton.contains(mx, my)) {
                Game.state = Game.GAME_STATE.LEVEL_SELECTION;
            } else if (helpButton.contains(mx, my)) {
                System.out.println("Help was not yet implemented");
            } else if (stopButton.contains(mx, my)) {
                game.StopGame();
            }
        } else if (Game.state == Game.GAME_STATE.LEVEL_SELECTION) {
            System.out.println("Mouse clicked at: " + mx + ", " + my); // Debugging output
            System.out.println("level2button x" + level2Button.x + ", y" + level2Button.y);
//            if (mx >= level1Button.x && mx <= level1Button.x + level1Button.width) {
//                if (my >= level1Button.y && my <= level1Button.y + level1Button.height) {
//                    // Load level 1
//                    Game.state = Game.GAME_STATE.LEVEL_ONE;
//                    MapBuilder.mapBuilder();
//                }
//            } else if (mx >= level2Button.x && mx <= level2Button.x + level2Button.width) {
//                if (my >= level2Button.y && my <= level2Button.y + level2Button.height) {
//                    // Load level 2
//                    Game.state = Game.GAME_STATE.LEVEL_TWO;
//                    System.out.println("Level 2 selected"); // Debugging output
//                    MapBuilder.mapBuilder();
//                }
//            } else if (mx >= level3Button.x && mx <= level3Button.x + level3Button.width) {
//                if (my >= level3Button.y && my <= level3Button.y + level3Button.height) {
//                    // Load level 3
//                    Game.state = Game.GAME_STATE.LEVEL_THREE;
//                    System.out.println("Level 3 selected"); // Debugging output
//                    MapBuilder.mapBuilder();
//                }
//            }
            int saveChecker=Save.converterData();
            if (level1Button.contains(mx, my)) {
                Game.state = Game.GAME_STATE.LEVEL_ONE;
                Game.currentLevel= Game.GAME_STATE.LEVEL_ONE;
                Game.setEntity(Entity.EntityType.ENEMY,100);
                Game.setEntity2(Entity.EntityType.ENEMY,200);
                Game.setPlayerCoords(40,900);
                Player.setHealth(100);
                Player.modifyPlayerCamera(0,0);
               // Camera.setX(0);
                //Camera.setY(0);

            } else if (level2Button.contains(mx, my) && saveChecker==2 || saveChecker == 3) {
                Game.state = Game.GAME_STATE.LEVEL_TWO;
                Game.currentLevel= Game.GAME_STATE.LEVEL_TWO;
                Game.setEntity(Entity.EntityType.ENEMY,200);
                Game.setPlayerCoords(40,900);
                Player.setHealth(100);
                Player.modifyPlayerCamera(0,0);
//                Camera.setX(0);
//                Camera.setY(0);
            } else if (level3Button.contains(mx, my) && saveChecker==3) {
                Game.state= Game.GAME_STATE.LEVEL_THREE;
                Game.currentLevel= Game.GAME_STATE.LEVEL_THREE;
                Game.setPlayerCoords(40,900);
                Game.setEntity(Entity.EntityType.ENEMY,400);
                Game.setFriendly(100);
                Player.setHealth(100);
                Player.modifyPlayerCamera(0,0);
//                Camera.setX(0);
//                Camera.setY(0);
            }else{
                System.out.println("LEVEL LOCKED. COMPLETE PREVIOUS LEVEL FIRST!");
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // This method can be left empty if not used
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // This method can be left empty if not used
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // This method can be left empty if not used
    }
}
