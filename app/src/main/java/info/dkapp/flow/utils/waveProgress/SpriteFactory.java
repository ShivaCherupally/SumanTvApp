package info.dkapp.flow.utils.waveProgress;


/**
 * Created by ybq.
 */
public class SpriteFactory {

    public static Sprite create(Style style) {
        Sprite sprite = null;
        switch (style) {

            case WAVE:
                sprite = new Wave();
                break;

            default:
                break;
        }
        return sprite;
    }
}
