package info.dkapp.flow.fontstyles;

import android.content.Context;
import android.util.AttributeSet;

import info.dkapp.flow.utils.Utility;

/**
 * Created by Uday Kumay Vurukonda on 1/10/2019.
 * <p>
 * Mr.Psycho
 */
public class TextViewRegular extends androidx.appcompat.widget.AppCompatTextView {

    public TextViewRegular(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public TextViewRegular(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public TextViewRegular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {

        setTypeface(Utility.getTypeface(9,getContext()));

    }
}
