package PaooGame.GameWindow;

import PaooGame.Game;
import PaooGame.Graphics.DynamicAssetBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class CharacterCreation implements MouseListener {

    private String selectedArmor = "default_armor";
    private String selectedPants = "default_pants";
    private String selectedWeapon = "default_weapon";
    private HashMap<String, String[]> options;

    private int dropdownWidth = 150;
    private int dropdownHeight = 30;
    private static boolean[] dropdownActive = {false, false, false};
    private int mouseX = -1, mouseY = -1;
    private final int spacing = 10;
    private Graphics g = null;
    private static Rectangle[] dropdownAreas;
    private static int activeDropdown = -1; //used to check where the active dropdown menu is

    private DynamicAssetBuilder assetBuilder;
    private BufferedImage charImage;

    public CharacterCreation(DynamicAssetBuilder builder) {
        this.assetBuilder = builder;
        initializeOptions();
        dropdownAreas = new Rectangle[options.size()];
        for (int i = 0; i < dropdownAreas.length; i++) {
            dropdownAreas[i] = new Rectangle(50, 50 + i * (dropdownHeight + spacing), dropdownWidth, dropdownHeight);
        }
        updateCharacterImage(); //TODO implement
    }

    private void initializeOptions() {
        options = new HashMap<>();
        options.put("armor", new String[]{"default_armor", "heavy_armor", "light_armor"});
        options.put("pants", new String[]{"default_pants", "heavy_pants", "light_pants"});
        options.put("weapon", new String[]{"empty", "sword", "bow"});
    }
    public void render() {
        GameWindow gameWindow = Game.getGameWnd();
        JFrame wnd = gameWindow.getWndFrame();

        JPanel panelSouth = new JPanel();
        JPanel[] dropdownPairs = new JPanel[3];
        for (int i = 0; i < dropdownPairs.length; i++) {
            dropdownPairs[i] = new JPanel();
        }
        panelSouth.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelSouth.setBackground(Color.RED);
//        wnd.setPreferredSize(new Dimension(GameWindow.getWidth(), GameWindow.getHeight()));

        JPanel panelWest = new JPanel();
        panelWest.setLayout(new GridLayout(10, 1, 10, 5));
        panelWest.setBackground(Color.GREEN);
        // Create dropdown menus for armor, pants, and weapons
        JComboBox<String> armorDropdown = new JComboBox<>(options.get("armor"));
        JComboBox<String> pantsDropdown = new JComboBox<>(options.get("pants"));
        JComboBox<String> weaponDropdown = new JComboBox<>(options.get("weapon"));

        // Set default selections
        armorDropdown.setSelectedItem(selectedArmor);
        pantsDropdown.setSelectedItem(selectedPants);
        weaponDropdown.setSelectedItem(selectedWeapon);

        // Add action listeners to update selections
        armorDropdown.addActionListener(e -> selectedArmor = (String) armorDropdown.getSelectedItem());
        pantsDropdown.addActionListener(e -> selectedPants = (String) pantsDropdown.getSelectedItem());
        weaponDropdown.addActionListener(e -> selectedWeapon = (String) weaponDropdown.getSelectedItem());

        // Add dropdowns to the panelSouth
        dropdownPairs[0].add(new JLabel("Armor:"));
        dropdownPairs[0].add(armorDropdown);

        dropdownPairs[1].add(new JLabel("Pants/Boots:"));
        dropdownPairs[1].add(pantsDropdown);

        dropdownPairs[2].add(new JLabel("Weapon:"));
        dropdownPairs[2].add(weaponDropdown);

        for(int i=0;i<dropdownPairs.length;i++) {
            dropdownPairs[i].setPreferredSize(new Dimension(125,100));
            panelWest.add(dropdownPairs[i]);
        }


        JButton createCharBtt = new JButton("Create Character");
        createCharBtt.setFocusable(false);
        createCharBtt.setForeground(Color.BLACK);
        createCharBtt.setBackground(Color.LIGHT_GRAY);
        panelSouth.add(createCharBtt);

        gameWindow.getContentPane().add(panelSouth, BorderLayout.SOUTH);
        gameWindow.getContentPane().add(panelWest, BorderLayout.WEST);

        wnd.revalidate();
        wnd.repaint();
        wnd.setVisible(true);
    }



    public void draw() {
        // Draw the character
        //  g.drawImage(characterImage, 300, 50, null);
        if (g == null) {
            g = Game.getGraphicalContext();
        }
        // Draw dropdown menus
        String[] labels = {"Armor", "Pants/Boots", "Weapons"};
        for (int i = 0; i < dropdownAreas.length; i++) {
            Rectangle rect = dropdownAreas[i];

            // Draw dropdown box
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(rect.x, rect.y, rect.width, rect.height);
            g.setColor(Color.BLACK);
            g.drawRect(rect.x, rect.y, rect.width, rect.height);

            // Draw current selection
            String selectedOption = getSelectedOption(i);
            g.drawString(labels[i] + ": " + selectedOption, rect.x + 5, rect.y + 20);
        }

        // Render dropdown options last, to ensure they are drawn on top
        for (int i = 0; i < dropdownAreas.length; i++) {
            if (dropdownActive[i]) {
                Rectangle rect = dropdownAreas[i];
                String[] optionList = getOptionsForDropdown(i);

                for (int j = 0; j < optionList.length; j++) {
                    int optionY = rect.y + (j + 1) * dropdownHeight;

                    // Draw the option background
                    g.setColor(Color.WHITE);
                    g.fillRect(rect.x, optionY, rect.width, dropdownHeight);

                    // Draw the option border
                    g.setColor(Color.BLACK);
                    g.drawRect(rect.x, optionY, rect.width, dropdownHeight);

                    // Draw the option text
                    g.drawString(optionList[j], rect.x + 5, optionY + 20);
                }
            }
        }
    }


    private String getSelectedOption(int dropdownIndex) {
        return switch (dropdownIndex) {
            case 0 -> selectedArmor;
            case 1 -> selectedPants;
            case 2 -> selectedWeapon;
            default -> "unknown";
        };
    }

    private String[] getOptionsForDropdown(int dropdownIndex) {
        return switch (dropdownIndex) {
            case 0 -> options.get("armor");
            case 1 -> options.get("pants");
            case 2 -> options.get("weapon");
            default -> new String[0];
        };
    }

    private void setSelectedOption(int dropdownIndex, String option) {
        switch (dropdownIndex) {
            case 0 -> selectedArmor = option;
            case 1 -> selectedPants = option;
            case 2 -> selectedWeapon = option;
        }
    }

    private void updateCharacterImage() {
//        try{
//            assetBuilder.addLayer(selectedArmor);
//            assetBuilder.addLayer(selectedWeapon);
//            assetBuilder.buildAsset("textures/playerAnim/","testingCharCreation.png");
//        }catch (IOException e){
//            e.printStackTrace();
//        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        boolean optionSelected = false;
        for (int i = 0; i < dropdownAreas.length; i++) {
            Rectangle recti = dropdownAreas[i];
            if (activeDropdown != -1) {

                Rectangle rect = dropdownAreas[activeDropdown];
                String[] optionList = getOptionsForDropdown(activeDropdown);

                for (int j = 0; j < optionList.length; j++) {
                    int optionY = rect.y + (j + 1) * dropdownHeight;
                    Rectangle optionRect = new Rectangle(rect.x, optionY, rect.width, dropdownHeight);

                    if (optionRect.contains(mouseX, mouseY)) {
                        setSelectedOption(activeDropdown, optionList[j]);
                        System.out.println("Selected option: " + optionList[j]);
                        dropdownActive[activeDropdown] = false;  // Close dropdown after selecting
//                        updated = true;
                        optionSelected = true;
                        break;

                    }
                }
            } else if (recti.contains(mouseX, mouseY)) {
                // Close all dropdowns except the one clicked
                for (int j = 0; j < dropdownActive.length; j++) {
                    dropdownActive[j] = (j == i) ? !dropdownActive[j] : false;
                }
                return;  // Exit early to avoid immediately processing options
            } else {
                activeDropdown = -1;
            }
            // Identify the active dropdown under the cursor
            if (optionSelected) {
                activeDropdown = -1;
            }
            if (dropdownActive[i]) {
                activeDropdown = i;
            }
        }
    }

    // If a dropdown is active, check for clicks on its options


    @Override
    public void mousePressed(MouseEvent e) {

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