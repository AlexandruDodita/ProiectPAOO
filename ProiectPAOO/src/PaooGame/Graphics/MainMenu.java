package PaooGame.Graphics;

import PaooGame.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainMenu implements MouseListener {
    private Game game;  // Add a reference to the Game instance

    public MainMenu(Game game) {
        this.game = game;  // Initialize the Game instance
    }
    public static Rectangle playButton=new Rectangle(1200/2+120,150,100,50);
    public static Rectangle helpButton=new Rectangle(1200/2+120,250,100,50);
    public static Rectangle stopButton=new Rectangle(1200/2+120,350,100,50);
    public static void render(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        Font fnt0=new Font("arial",Font.BOLD,50);
        g.setFont(fnt0);
        g.setColor(Color.white);
        g.drawString("Knightly Order",1200/2,100);

        Font fnt1=new Font("arial",Font.BOLD,30);
        g.setFont(fnt1);
        g.drawString("Play", playButton.x+19, playButton.y+32);
        g.drawString("Help", helpButton.x+19, helpButton.y+32);
        g.drawString("Exit", stopButton.x+19, stopButton.y+32);
        g2d.draw(playButton);
        g2d.draw(helpButton);
        g2d.draw(stopButton);

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        /*public static Rectangle playButton=new Rectangle(1200/2+120,150,100,50);
        public static Rectangle helpButton=new Rectangle(1200/2+120,250,100,50);
        public static Rectangle stopButton=new Rectangle(1200/2+120,350,100,50);*/
        int mx=e.getX();
        //System.out.println("x" + mx);

        int my=e.getY();
        //System.out.println("y" + my);
        //Play button
        if(mx >= (1200/2+120) && mx<= (1200/2+220)){
            if(my>=150&&my<=200){
                //pressedPlayButton
                Game.state = Game.GAME_STATE.GAME;
            }
            if(my>=250&&my<=300){
                //pressedHelpButton
                System.out.println("Help was not yet implemented");
            }
            if(my>=350&&my<=400){
                //System.out.println("Am intrat in exit");
                game.StopGame();
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
    /*public MainMenu(Game game) {
        this.game = game;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        startButton = new JButton("Start Game");
        settingsButton = new JButton("Settings");
        exitButton = new JButton("Exit");

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(startButton, gbc);

        gbc.gridy = 1;
        add(settingsButton, gbc);

        gbc.gridy = 2;
        add(exitButton, gbc);

        // Add action listeners
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.StartGame();
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSettings();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void openSettings() {
        // Code to open the settings menu
    }
    */

}
