package info.dkapp.flow.bottommenu.models;

/**
 * Created by User on 31-07-2018.
 **/

public class IPollOption
{
    public int id;
    public String name;
    public int image;

    public boolean access = true;

    public IPollOption()
    {

    }

    public IPollOption(int id, String name, int image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }
}
