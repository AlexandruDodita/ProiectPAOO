package PaooGame.InventoryManager;

import java.awt.image.BufferedImage;

public interface InventoryObject {

    boolean isEquippable();
    int getItemId();
    String getItemName();
    int getItemQuantity();
    int getItemType();
    public BufferedImage getTexture();
}
