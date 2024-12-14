package PaooGame.InventoryManager;

public interface Weapon extends InventoryObject{
    int getWeaponDamage();
    void changeWeaponStats(int damage, int price);
}
