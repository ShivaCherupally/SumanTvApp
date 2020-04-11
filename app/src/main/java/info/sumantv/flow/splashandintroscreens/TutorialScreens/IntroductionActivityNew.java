package info.sumantv.flow.splashandintroscreens.TutorialScreens;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;


import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.SignInActivity;
import info.sumantv.flow.retrofitcall.SessionManager;
import info.sumantv.flow.splashandintroscreens.SplashScreenActivity;

public class IntroductionActivityNew extends AppCompatActivity {
    Context context;
    AutoScrollViewPager viewPagerBanners;
    LinearLayout vDots;
    RelativeLayout rlHomePager;
    private Button btnSkip, btnNext;
    Integer bannerPageTime = 3000;
    ArrayList<Bitmap> bitmapArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.introduction_activity_new);
        context = getApplicationContext();
        try {
            SplashScreenActivity Object = new SplashScreenActivity();
            bitmapArray = new ArrayList<>();
            bitmapArray = Object.splashScreen();
            initViews();
            changeStatusBarColor();
            if (bitmapArray.size() != 0) {
                setUpBanners(bitmapArray);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initViews() {
        viewPagerBanners = findViewById(R.id.pagerBanners);
        vDots = findViewById(R.id.vDots);
        rlHomePager = findViewById(R.id.rlHomePager);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnNext = (Button) findViewById(R.id.btn_next);
        //
        viewPagerBanners.startAutoScroll(bannerPageTime);
        viewPagerBanners.setInterval(bannerPageTime);
        viewPagerBanners.setCycle(false);
        viewPagerBanners.setStopScrollWhenTouch(true);
        //
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currPos = viewPagerBanners.getCurrentItem();
                viewPagerBanners.setCurrentItem(currPos + 1);
                if (viewPagerBanners.getCurrentItem() + 1 == bitmapArray.size()) {
                    btnNext.setText("DONE");
                }
                if (btnNext.getText().toString().equals("DONE")) {
                    launchHomeScreen();
                }
            }
        });
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void launchHomeScreen() {
        SessionManager.getInstance().setKeyValue(SessionManager.KEY_FIRST_TIME_SPLASH_LOAD,true);
        startActivity(new Intent(this, SignInActivity.class));
        this.finish();
    }

    private void setUpBanners(ArrayList<Bitmap> array) {
        if (array != null && array.size() > 0) {
            ViewPagerAdapterBanners adapter = new ViewPagerAdapterBanners(context, array);
            viewPagerBanners.setAdapter(adapter);
            setupDotBar(array.size());
            //
            viewPagerBanners.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    if (vDots != null && vDots.getTag() != null) {
                        ((ImageView) vDots.getTag()).setImageResource(R.drawable.dot_empty);
                        ((ImageView) vDots.getChildAt(position)).setImageResource(R.drawable.dot_fill);
                        vDots.setTag(vDots.getChildAt(position));
                    }
                    if (viewPagerBanners.getCurrentItem() + 1 == bitmapArray.size()) {
                        btnNext.setText("DONE");
                    } else {
                        btnNext.setText("NEXT");
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        } else {
            rlHomePager.setVisibility(View.GONE);
        }
    }

    private class ViewPagerAdapterBanners extends PagerAdapter {
        Context context;
        LayoutInflater inflater;
        ArrayList<Bitmap> array;

        private ViewPagerAdapterBanners(Context context, ArrayList<Bitmap> array) {
            this.context = context;
            this.array = array;
        }

        @Override
        public int getCount() {
            return array.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.item_introduction_activity_new, container, false);
            ImageView ivItem = itemView.findViewById(R.id.ivItem);
            try {
                if (array.get(position) != null && !array.get(position).isRecycled()) {
                    ivItem.setImageBitmap(array.get(position));
                    Log.d("Bitmap Size", "" + array.get(position).getByteCount() / 1024);
                    container.addView(itemView);
                }
            } catch (Exception e) {
            }
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private void setupDotBar(final Integer length) {
        float scale = getResources().getDisplayMetrics().density;
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams((int) (40 * scale), (int) (40 * scale));
        param.setMargins(15, 0, 15, 0);
        vDots.removeAllViews();
        for (int i = 0; i < length; i++) {
            int i2;
            ImageView img = new ImageView(context);
            /*LinearLayout.LayoutParams ivParams = new LinearLayout.LayoutParams(5,5);
            img.setLayoutParams(ivParams);*/
            if (i == 0) {
                i2 = R.drawable.dot_fill;
            } else {
                i2 = R.drawable.dot_empty;
            }
            img.setImageResource(i2);
            img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            vDots.addView(img, param);
            if (i == 0) {
                vDots.setTag(img);
            }
        }
    }
}
