package PaooGame.InventoryManager;

public interface Armor extends InventoryObject{
    int getProtection();
    void changeArmorStats(int health, int price);
}
