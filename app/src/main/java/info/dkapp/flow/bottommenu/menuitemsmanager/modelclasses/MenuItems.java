package info.dkapp.flow.bottommenu.menuitemsmanager.modelclasses;

public class MenuItems {

    int image;
    String itemName;


    public MenuItems(int image, String itemName) {
        this.image = image;
        this.itemName = itemName;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
