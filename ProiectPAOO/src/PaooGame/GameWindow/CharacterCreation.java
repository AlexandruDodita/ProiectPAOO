package PaooGame.GameWindow;

import PaooGame.Entity.Player;
import PaooGame.Game;
import PaooGame.Graphics.DynamicAssetBuilder;
import PaooGame.Graphics.Assets;

import javax.swing.*;
import java.awt.*;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class CharacterCreation{

    private String selectedArmor = "default_armor";
    private String selectedPants = "default_pants";
    private String selectedWeapon = "default_weapon";
    private HashMap<String, String[]> options;
    private Graphics g;
    private JPanel backgroundPanel;
    private JFrame wnd;

    private static final CharacterCreation instance = new CharacterCreation();

    private CharacterCreation() {
        g=Game.getGraphicalContext();
        GameWindow gameWindow = Game.getGameWnd();
        wnd = gameWindow.getWndFrame();
        initializeOptions();
        updateCharacterImage(); //TODO implement
    }

    private void initializeOptions(){
        options = new HashMap<>();
        options.put("armor", new String[]{"default_armor","heavy_armor","light_armor","gay_armor"});
        options.put("pants", new String[]{"default_pants","heavy_pants","light_pants"});
        options.put("weapon", new String[]{"default","sword","bow"});
    }

    public void render() {

        backgroundPanel= new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(Assets.battleBackground, 0, 0, getWidth(), getHeight(), this);
                int xPlayer = 300, yPlayer = 400;
                g.fillRect(xPlayer+50, yPlayer, Game.getGameWnd().GetWndWidth()/7, Game.getGameWnd().GetWndHeight()/4);
                Game.getPlayer().updatePlayerTextures();
                System.out.println(Game.getPlayer().getIdleFrames()[3][0]);
                g.drawImage(Game.getPlayer().getIdleFrames()[3][0], xPlayer, yPlayer,Game.getGameWnd().GetWndWidth()/5, Game.getGameWnd().GetWndHeight()/4,  null);
            }
        };
        wnd.setContentPane(backgroundPanel);

        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.setBackground(Color.BLACK);  // Fallback if image is null

        JPanel panelSouth = new JPanel();
        panelSouth.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelSouth.setBackground(Color.RED);

        JPanel panelWest = new JPanel();
        panelWest.setLayout(new GridLayout(10, 1, 10, 5));
        panelWest.setBackground(Color.GREEN);

        JComboBox<String> armorDropdown = new JComboBox<>(options.get("armor"));
        JComboBox<String> pantsDropdown = new JComboBox<>(options.get("pants"));
        JComboBox<String> weaponDropdown = new JComboBox<>(options.get("weapon"));

        armorDropdown.setSelectedItem(selectedArmor);
        pantsDropdown.setSelectedItem(selectedPants);
        weaponDropdown.setSelectedItem(selectedWeapon);

        armorDropdown.addActionListener(e -> {
            selectedArmor = (String) armorDropdown.getSelectedItem();
            Game.getPlayer().updatePlayerTextures();
            updateCharacterImage();
        });
        pantsDropdown.addActionListener(e -> {
            selectedPants = (String) pantsDropdown.getSelectedItem();
            Game.getPlayer().updatePlayerTextures();
            updateCharacterImage();
        });
        weaponDropdown.addActionListener(e -> {
            selectedWeapon = (String) weaponDropdown.getSelectedItem();
            Game.getPlayer().updatePlayerTextures();
            updateCharacterImage();
        });

        panelWest.add(new JLabel("Armor:"));
        panelWest.add(armorDropdown);
        panelWest.add(new JLabel("Pants/Boots:"));
        panelWest.add(pantsDropdown);
        panelWest.add(new JLabel("Weapon:"));
        panelWest.add(weaponDropdown);

        JButton createCharBtt = new JButton("Create Character");
        createCharBtt.setFocusable(false);
        createCharBtt.setForeground(Color.BLACK);
        createCharBtt.setBackground(Color.LIGHT_GRAY);
        createCharBtt.addActionListener(e -> {
            Game.isCreatingChar = false;
            Game.state= Game.GAME_STATE.MENU;
           // wnd.dispose();
            wnd.setContentPane(Game.getGameWnd().getContentPane());
        });
        panelSouth.add(createCharBtt);

        backgroundPanel.add(panelSouth, BorderLayout.SOUTH);
        backgroundPanel.add(panelWest, BorderLayout.WEST);



        wnd.revalidate();
        wnd.repaint();
        wnd.setVisible(true);
    }

    private String getArmorPath(int whichTextureAnimToLoad){
        if(whichTextureAnimToLoad == 0) { //IDLE
            switch (selectedArmor) {
                case "default_armor":
                    return "/textures/EntitySpritesheets/playerAnim/charVersionOne/idle.png";
                //break;
                default:
                    return "/textures/EntitySpritesheets/playerAnim/charVersionOne/idle.png";
            }
        }else if(whichTextureAnimToLoad == 1) { //WALKING
            switch (selectedArmor) {
                case "default_armor":
                    return "/textures/EntitySpritesheets/playerAnim/charVersionOne/walk.png";
                //break;
                default:
                    return "/textures/EntitySpritesheets/playerAnim/charVersionOne/walk.png";
            }
        }
        return null;
    }
    private String getPantsPath(){
        return null; //probably useless as the CharacterCreation won't be used to select armor or pants or weapons, but it's a good proof-of-concept
        // and in lack of more textures for character face and such, we're gonna be testing this class' functionality like this
    }
    private String getSelectedWeapon(int whichTextureAnimToLoad){
        if(whichTextureAnimToLoad == 0) { //Loading IDLE animation
            return switch (selectedWeapon) {
                case "default" -> "";
                case "sword" -> "/textures/EntitySpritesheets/playerAnim/weapons/idleLongswordDefault.png";
                case "bow" -> {
                    System.out.println("There is no bow weapon implementation yet!");
                    yield "";
                }
                default -> null;
            };
        }else if(whichTextureAnimToLoad == 1) {
            return switch (selectedWeapon) {
                case "default" -> "";
                case "sword" -> "/textures/EntitySpritesheets/playerAnim/weapons/walkLongswordDefault.png";
                case "bow" -> {
                    System.out.println("There is no bow weapon implementation yet!");
                    yield "";
                }
                default -> null;
            };
        }
        return null;
    }

    public void updateCharacterImage() {
        Assets.buildCharacter(64,256, "/textures/EntitySpritesheets/playerAnim/charVersionOne/idle.png", new String[]{getSelectedWeapon(0)}, "idleDefault.png");
        Assets.buildCharacter(512,256, "/textures/EntitySpritesheets/playerAnim/charVersionOne/walk.png", new String[]{getSelectedWeapon(1)}, "walkDefault.png");
        if(backgroundPanel!=null)
            backgroundPanel.repaint();
        if(wnd!=null){
            wnd.revalidate();
            wnd.repaint();
        }
    }

    public static CharacterCreation getInstance(){
        return instance;
    }

}