package info.dkapp.flow.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.util.AttributeSet;
import android.widget.EditText;

import info.dkapp.flow.R;

public class KeyboardConstraintLayout extends ConstraintLayout {

    private KeyboardListener keyboardListener;
    private EditText targetEditText;
    private int minKeyboardHeight;
    private boolean isShow;

    public KeyboardConstraintLayout(Context context) {
        super(context);
        minKeyboardHeight = getResources().getDimensionPixelSize(R.dimen._128sdp); //128dp
    }

    public KeyboardConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        minKeyboardHeight = getResources().getDimensionPixelSize(R.dimen._128sdp); // 128dp
    }

    public KeyboardConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        minKeyboardHeight = getResources().getDimensionPixelSize(R.dimen._128sdp); // 128dp
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!isInEditMode()) {
            Activity activity = (Activity) getContext();
            @SuppressLint("DrawAllocation")
            Rect rect = new Rect();
            getWindowVisibleDisplayFrame(rect);

            int statusBarHeight = rect.top;
            int keyboardHeight = activity.getWindowManager().getDefaultDisplay().getHeight() - (rect.bottom - rect.top) - statusBarHeight;

            if (keyboardListener != null && targetEditText != null && targetEditText.isFocused()) {
                if (keyboardHeight > minKeyboardHeight) {
                    if (!isShow) {
                        isShow = true;
                        keyboardListener.onKeyboardVisibility(true);
                    }
                }else {
                    if (isShow) {
                        isShow = false;
                        keyboardListener.onKeyboardVisibility(false);
                    }
                }
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public boolean isShowKeyboard() {
        return isShow;
    }

    public void setKeyboardListener(EditText targetEditText, KeyboardListener keyboardListener) {
        this.targetEditText = targetEditText;
        this.keyboardListener = keyboardListener;
    }

    public interface KeyboardListener {
        void onKeyboardVisibility (boolean isVisible);
    }
}
