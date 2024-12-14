package PaooGame.Graphics;

import PaooGame.Entity.Entity;
import PaooGame.Entity.Player;
import PaooGame.Game;
import PaooGame.Save;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;



public class MainMenu implements MouseListener {
    private static Game game;
    public final static int centerX=21;
    public final static int centerY=36;
    public MainMenu(Game game, Graphics g) {
        this.game = game;
    }
    private static MenuScreen mainMenu;
    private static MenuScreen levelSelection;
    private static class MenuOption{
        String label;
        Rectangle bounds;
        Runnable action;

        MenuOption(String label, int x, int y, int width, int height, Runnable action){
            this.label = label;
            this.bounds=new Rectangle(x,y,width,height);
            this.action=action;
        }

        void render(Graphics g, Font font) {
            g.setFont(font);
            g.drawString(label,bounds.x+centerX,bounds.y+centerY);
            ((Graphics2D)g).draw(bounds);
        }
        boolean isClicked(int mx, int my){
            return bounds.contains(mx,my);
        }
        void performAction(){
            if(action!=null){
                action.run();
            }
        }
    }

    private static class MenuScreen{
        String title;
        List<MenuOption> options = new ArrayList<>();

        MenuScreen(String title){
            this.title = title;
        }

        void addOption(MenuOption option){
            options.add(option);
        }

        void render(Graphics g){
            g.setFont(new Font("arial", Font.BOLD, 50));
            g.drawString(title, 1200 / 2, 100);

            Font optionFont = new Font("arial", Font.BOLD, 30);
            for(MenuOption option : options){
                option.render(g, optionFont);
            }
        }

        void handleClick(int mx, int my){
            for(MenuOption option : options){
                if(option.isClicked(mx,my)){
                    option.performAction();
                    break;
                }
            }
        }
    }

    public static void initialize(){
        mainMenu = new MenuScreen("Knightly Order");

        mainMenu.addOption(new MenuOption("Play", 1200 / 2 + 120, 150, 100, 50, () -> Game.state = Game.GAME_STATE.LEVEL_SELECTION));
        mainMenu.addOption(new MenuOption("Help", 1200 / 2 + 120, 250, 100, 50, () -> System.out.println("Help was not yet implemented")));
        mainMenu.addOption(new MenuOption("Exit", 1200 / 2 + 120, 350, 100, 50, game::StopGame));

        levelSelection = new MenuScreen("Level Selection");
        levelSelection.addOption(new MenuOption("Level 1", 1200 / 2 + 90, 150, 125, 50, () -> loadLevel(Game.GAME_STATE.LEVEL_ONE)));
        levelSelection.addOption(new MenuOption("Level 2", 1200 / 2 + 90, 250, 125, 50, () -> {
            if (Save.converterData() >= 2) loadLevel(Game.GAME_STATE.LEVEL_TWO);
            else System.out.println("LEVEL LOCKED. COMPLETE PREVIOUS LEVEL FIRST!");
        }));
        levelSelection.addOption(new MenuOption("Level 3", 1200 / 2 + 90, 350, 125, 50, () -> {
            if (Save.converterData() >= 3) loadLevel(Game.GAME_STATE.LEVEL_THREE);
            else System.out.println("LEVEL LOCKED. COMPLETE PREVIOUS LEVEL FIRST!");
        }));
    }

    private static void loadLevel(Game.GAME_STATE level){
        Game.state = level;
        Game.currentLevel = level;
        Game.setPlayerCoords(40,900);
        Player.setHealth(100);
        Player.modifyPlayerCamera(0,0);

        if (level == Game.GAME_STATE.LEVEL_ONE) {
            Game.setEntity(Entity.EntityType.ENEMY, 100);
        } else if (level == Game.GAME_STATE.LEVEL_TWO) {
            Game.setEntity(Entity.EntityType.ENEMY, 200);
        } else if (level == Game.GAME_STATE.LEVEL_THREE) {
            Game.setEntity(Entity.EntityType.ENEMY, 400);
            Game.setFriendly(100);
        }
    }

    public static void render(Graphics g) {

        if(Game.state ==Game.GAME_STATE.MENU){
            mainMenu.render(g);
        }else if(Game.state == Game.GAME_STATE.LEVEL_SELECTION){
            levelSelection.render(g);
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
            mainMenu.handleClick(mx, my);
        } else if (Game.state == Game.GAME_STATE.LEVEL_SELECTION) {
            levelSelection.handleClick(mx, my);
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
