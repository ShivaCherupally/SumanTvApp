package info.sumantv.flow.userflow.Util;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

public class CircularProgressButton extends AppCompatButton {
    public CircularProgressButton(Context context) {
        super(context);
    }

    public CircularProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircularProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private enum State {
        PROGRESS, IDLE
    }

    public class LoadingButton extends AppCompatButton {
        public LoadingButton(Context context) {
            super(context);
            init(context);
        }

        public LoadingButton(Context context, AttributeSet attrs) {
            super(context, attrs);
            init(context);
        }

        public LoadingButton(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init(context);
        }

        public LoadingButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr);
            init(context);
        }

        private void init(Context context) {
        }
    }
}
