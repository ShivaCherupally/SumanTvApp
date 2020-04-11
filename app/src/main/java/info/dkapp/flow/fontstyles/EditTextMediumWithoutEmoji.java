package info.dkapp.flow.fontstyles;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.KeyEvent;

import info.dkapp.flow.utils.Utility;

/**
 * Created by Uday Kumay Vurukonda on 15-May-19.
 * <p>
 * Mr.Psycho
 */
public class EditTextMediumWithoutEmoji extends androidx.appcompat.widget.AppCompatEditText {

    public EditTextMediumWithoutEmoji(Context context) {
        super(context);
        init();
        applyCustomFont(context);
    }

    public EditTextMediumWithoutEmoji(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        applyCustomFont(context);
    }

    public EditTextMediumWithoutEmoji(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        /*Typeface customFont = FontCache.getTypeface("helveticaneue-light-webfont.ttf", context);
        setTypeface(customFont);*/
        setTypeface(Utility.getTypeface(10,getContext()));

    }
    private void init() {
        setFilters(new InputFilter[]{new EmojiExcludeFilter()});
    }

    private class EmojiExcludeFilter implements InputFilter {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; i++) {
                int type = Character.getType(source.charAt(i));
                if (type == Character.SURROGATE || type == Character.OTHER_SYMBOL) {
                    return "";
                }
            }
            return null;
        }
    }

    //don't remove this method
    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        return super.dispatchKeyEvent(event);
    }

}


