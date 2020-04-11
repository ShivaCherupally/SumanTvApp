package info.dkapp.flow.fontstyles;

import android.content.Context;
import android.util.AttributeSet;

import info.dkapp.flow.utils.Utility;

/**
 * Created by Uday Kumay Vurukonda on 1/8/2019.
 * <p>
 * Mr.Psycho  shivac
 */
public class TextViewSemiBold extends androidx.appcompat.widget.AppCompatTextView {

    public TextViewSemiBold(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public TextViewSemiBold(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public TextViewSemiBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        /*Typeface customFont = FontCache.getTypeface("helveticaneue-light-webfont.ttf", context);
        setTypeface(customFont);*/
        setTypeface(Utility.getTypeface(11,getContext()));

    }
}

