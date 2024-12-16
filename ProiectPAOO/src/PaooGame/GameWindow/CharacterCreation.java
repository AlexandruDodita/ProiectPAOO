package PaooGame.GameWindow;

import PaooGame.Game;
import PaooGame.Graphics.DynamicAssetBuilder;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class CharacterCreation implements MouseListener {

    private String selectedArmor = "default_armor";
    private String selectedPants = "default_pants";
    private String selectedWeapon = "default_weapon";
    private HashMap<String,String[]>options;

    private int dropdownWidth = 150;
    private int dropdownHeight = 30;
    private static boolean[] dropdownActive = {false,false,false};
    private int mouseX=-1,mouseY=-1;
    private final int spacing = 10;
    private Graphics g=null;
    private static Rectangle[] dropdownAreas;

    private DynamicAssetBuilder assetBuilder;
    private BufferedImage charImage;

    public CharacterCreation(DynamicAssetBuilder builder){
        this.assetBuilder = builder;
        initializeOptions();
        dropdownAreas = new Rectangle[options.size()];
        for(int i=0;i<dropdownAreas.length;i++){
            dropdownAreas[i] = new Rectangle(50,50+i*(dropdownHeight+spacing),dropdownWidth,dropdownHeight);
        }
        updateCharacterImage(); //TODO implement
    }

    private void initializeOptions(){
        options = new HashMap<>();
        options.put("armor",new String[] {"default_armor", "heavy_armor", "light_armor"});
        options.put("pants",new String[] {"default_pants", "heavy_pants", "light_pants"});
        options.put("weapon", new String[] {"empty","sword","bow"});
    }

    public void draw() {
        // Draw the character
     //  g.drawImage(characterImage, 300, 50, null);
        if(g==null){
            g= Game.getGraphicalContext();
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


    private String getSelectedOption(int dropdownIndex){
        return switch (dropdownIndex) {
            case 0 -> selectedArmor;
            case 1 -> selectedPants;
            case 2 -> selectedWeapon;
            default -> "unknown";
        };
    }
    private String[] getOptionsForDropdown(int dropdownIndex){
        return switch (dropdownIndex){
            case 0 -> options.get("armor");
            case 1 -> options.get("pants");
            case 2 -> options.get("weapon");
            default -> new String[0];
        };
    }
    private void setSelectedOption(int dropdownIndex, String option){
        switch (dropdownIndex){
            case 0 -> selectedArmor = option;
            case 1 -> selectedPants = option;
            case 2 -> selectedWeapon = option;
        }
    }
    private void updateCharacterImage(){
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
        boolean updated = false;
        System.out.println("Click detected");
        for(int i=0;i<dropdownAreas.length;i++) {
            Rectangle rect = dropdownAreas[i];
            System.out.println("Dropdown active values: " + dropdownActive[0] + dropdownActive[1] + dropdownActive[2]);
            if (rect.contains(mouseX, mouseY)  ) {
                dropdownActive[i] = !dropdownActive[i];
            }else
            if (dropdownActive[0] || dropdownActive[1] || dropdownActive[2]) {
 //TODO fix bug: when clicking on the new dropdown menu, the options from the old dropdown menu may still show up when clicking the new options

                String[] optionList = getOptionsForDropdown(i);
                for (int j = 0; j < optionList.length; j++) {
                    int optionY = rect.y + (j + 1) * dropdownHeight;
                    Rectangle optionRect = new Rectangle(rect.x, rect.y, rect.width, rect.height);

                    if (optionRect.contains(mouseX, mouseY)) {
                        setSelectedOption(i, optionList[j]); //TODO implement
                        dropdownActive[i] = false;
                        updated = true;
                        break;
                    }
                }
            }else{
                dropdownActive[i] = false;
            }
        }

        if(updated) {
            updateCharacterImage(); //irrelevant right now, ignore
        }
    }

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
