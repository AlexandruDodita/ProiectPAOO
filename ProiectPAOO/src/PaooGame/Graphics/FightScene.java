package PaooGame.Graphics;

import PaooGame.Entity.Entity;
import PaooGame.Game;
import PaooGame.Entity.Player;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class FightScene implements MouseListener {
    private Game game;
    public static Rectangle attackButton = new Rectangle(0, 750, 90, 50);
    public static Rectangle tauntButton = new Rectangle( 0, 850, 90, 50);
    public static Rectangle dodgeButton = new Rectangle(0, 950, 90, 50);

    private static Player player;
    private static Entity enemy;
    public FightScene(Game game){
        this.game=game;
        player=new Player();
        enemy =new Entity(Entity.EntityType.ENEMY,100);

    }

    public static void render(Graphics g){
        Graphics2D g2d=(Graphics2D) g;
        Font fnt0=new Font("arial", Font.BOLD,20);
        g.setFont(fnt0);
        Game.retCamera().backToZero();
        player.setX(100);
        player.setY(500);

        enemy.setX(1200);
        enemy.setY(425);
        g.drawImage(Assets.battleBackground,0,0,null);
        player.render(g);
        player.update(enemy);
        enemy.render(g);
        enemy.update();

        g.drawString("Attack", attackButton.x + 19, attackButton.y + 32);
        g.drawString("Taunt", tauntButton.x + 19, tauntButton.y + 32);
        g.drawString("Dodge", dodgeButton.x + 19, dodgeButton.y + 32);
        g2d.draw(attackButton);
        g2d.draw(tauntButton);
        g2d.draw(dodgeButton);
        //Health bar player
        g.setColor(Color.lightGray);
        g.fillRect(5,25, 200, 20);
        g.drawString("Health",5, 15);
        g.setColor(Color.red);
        g.fillRect(5,25, player.getHealth()*2, 20);

        g.setColor(Color.white);
        g.drawRect(5,25,200,20);

        //Health bar enemy
        g.setColor(Color.lightGray);
        g.fillRect(1275,25, 200, 20);
        g.drawString("Enemy Health",1275, 15);
        g.setColor(Color.DARK_GRAY);
        g.fillRect(1275,25, enemy.getHealth()*2, 20);

        g.setColor(Color.white);
        g.drawRect(1275,25,200,20);

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();
        if (attackButton.contains(mx, my)) {
            //System.out.println("Pretend I'm attacking an enemy");
            enemy.setHealth(enemy.getHealth()-20);
        } else if (tauntButton.contains(mx, my)) {
            System.out.println("Taunting never works, does it?");
            player.setHealth(player.getHealth()-10);
        } else if (dodgeButton.contains(mx, my)) {
            System.out.println("At least you tried lol.");
            player.setHealth(player.getHealth()-50);
        }
       // System.out.println("Mouse clicked at: " + mx + ", " + my);
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
