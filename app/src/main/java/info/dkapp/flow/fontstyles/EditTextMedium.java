package info.dkapp.flow.fontstyles;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;

import info.dkapp.flow.utils.Utility;

/**
 * Created by Uday Kumay Vurukonda on 1/8/2019.
 * <p>
 * Mr.Psycho
 */
public class EditTextMedium extends androidx.appcompat.widget.AppCompatEditText {

    public EditTextMedium(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public EditTextMedium(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public EditTextMedium(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        /*Typeface customFont = FontCache.getTypeface("helveticaneue-light-webfont.ttf", context);
        setTypeface(customFont);*/
        setTypeface(Utility.getTypeface(10,getContext()));
    }

    //don't remove this method
    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        return super.dispatchKeyEvent(event);
    }
}

