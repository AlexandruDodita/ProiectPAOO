package PaooGame.GameWindow;

import PaooGame.Entity.Entity;
import PaooGame.Entity.Player;
import PaooGame.Game;
import PaooGame.Save;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;



public class MainMenu implements MouseListener, MouseMotionListener {
    private static Game game;
    public final static int centerX=21;
    public final static int centerY=36;
    private static int width, height;

    public MainMenu(Game game, Graphics g) {
        this.game = game;
        width=GameWindow.WIDTH;
        height=GameWindow.HEIGHT;
    }

    private static MenuScreen mainMenu;
    private static MenuScreen levelSelection;



    private static class MenuOption{
        String label;
        Rectangle bounds;
        Runnable action;
        boolean hovered;

        MenuOption(String label, int x, int y, int width, int height, Runnable action){
            this.label = label;
            this.bounds=new Rectangle(x,y,width,height);
            this.action=action;
        }

        void render(Graphics g, Font font) {
            g.setFont(font);
            FontMetrics fm = g.getFontMetrics(font);

            int textWidth = fm.stringWidth(label);
            int textHeight = fm.getAscent()/2 - fm.getDescent()*5;

            int textX = bounds.x + (bounds.width - textWidth) / 2;
            int textY = bounds.y + (bounds.height - textHeight) / 2;
            g.drawString(label,textX,textY);
            //((Graphics2D)g).draw(bounds); //no longer needed, shows the boundaries for each clickable part

            if(hovered){
                g.setColor(new Color(255,255,255,128));
                g.fillRect(bounds.x,bounds.y,bounds.width,bounds.height);
            }
            g.setColor(Color.BLACK);
            g.drawString(label,textX,textY);
        }

        boolean isClicked(int mx, int my){
            return bounds.contains(mx,my);
        }

        void performAction(){
            if(action!=null){
                action.run();
            }
        }

        void setHovered(boolean hovered){
            this.hovered=hovered;
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
            // Render title
            g.setFont(new Font("arial", Font.BOLD, 50));
            FontMetrics titleMetrics = g.getFontMetrics();
            int titleWidth = titleMetrics.stringWidth(title);
            g.drawString(title, (width - titleWidth) / 2, 100);

            // Render options
            Font optionFont = new Font("arial", Font.BOLD, 30);
            int optionYStart = height / 2 - options.size() * 60 / 2; // Space evenly around the center

            for (int i = 0; i < options.size(); i++) {
                MenuOption option = options.get(i);
                int optionWidth = 125; // Arbitrary button width
                int optionHeight = 50; // Arbitrary button height
                int optionX = (width - optionWidth) / 2; // Center horizontally
                int optionY = optionYStart + i * 70; // Add spacing between options

                // Update the bounds to reflect the new dynamic positioning
                option.bounds = new Rectangle(optionX, optionY, optionWidth, optionHeight);
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

        void handleHover(int mx, int my) {
            for (MenuOption option : options) {
                option.setHovered(option.bounds.contains(mx, my));
            }
        }
    }

    public static void initialize(){ //initialises both mainMenu and levelSelection screens with their respective options
        mainMenu = new MenuScreen("Knightly Order");
                    //Play is currently set to CHAR_CREATION state for easier testing. To be modified back to LEVEL_SELECTION
        mainMenu.addOption(new MenuOption("Play", width / 2 - width/20, 100, 100, 50, () -> Game.state = Game.GAME_STATE.CHAR_CREATION));
        mainMenu.addOption(new MenuOption("Help", width / 2 - width/20, 200, 100, 50, () -> System.out.println("Help was not yet implemented")));
        mainMenu.addOption(new MenuOption("Exit", width / 2 - width/20, 300, 100, 50, game::StopGame));

        levelSelection = new MenuScreen("Level Selection");
        levelSelection.addOption(new MenuOption("Level 1", width / 2- width/20, 150, 125, 50, () -> loadLevel(Game.GAME_STATE.LEVEL_ONE)));
        levelSelection.addOption(new MenuOption("Level 2", width / 2 - width/20, 250, 125, 50, () -> {
            if (Save.converterData() >= 2) loadLevel(Game.GAME_STATE.LEVEL_TWO);
            else System.out.println("LEVEL LOCKED. COMPLETE PREVIOUS LEVEL FIRST!");
        }));
        levelSelection.addOption(new MenuOption("Level 3", width / 2 + width/10, 350, 125, 50, () -> {
            if (Save.converterData() >= 3) loadLevel(Game.GAME_STATE.LEVEL_THREE);
            else System.out.println("LEVEL LOCKED. COMPLETE PREVIOUS LEVEL FIRST!");
        }));
    }

    private static void loadLevel(Game.GAME_STATE level){ //loads the level as needed
        Game.state = level;
        Game.currentLevel = level;
        Game.setPlayerCoords(40,900);
        Player.setHealth(100);


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

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    //mouseMotionListener methods
    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if (Game.state == Game.GAME_STATE.MENU) {
            mainMenu.handleHover(mx, my);
        } else if (Game.state == Game.GAME_STATE.LEVEL_SELECTION) {
            levelSelection.handleHover(mx, my);
        }
    }
}
