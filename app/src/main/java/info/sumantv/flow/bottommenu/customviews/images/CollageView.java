package info.sumantv.flow.bottommenu.customviews.images;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.google.android.exoplayer2.ui.PlayerView;

import info.sumantv.flow.appmanagers.feed.models.Media;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.CollageController;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.ipoll.fragments.feeds.FeedsFragment;
import info.sumantv.flow.retrofitcall.SessionManager;
import info.sumantv.flow.userflow.Util.Common;
import info.sumantv.flow.utils.Utility;
import info.sumantv.flow.utils.facecrop.FaceCenterCrop;
import info.sumantv.flow.utils.zoom.ImageZoomHelper;

import java.util.List;

/**
 * Created by User on 21-07-2018.
 **/

public class CollageView extends LinearLayout {
    Context context;
    List<Media> mediaList;
    CollageController collageController;
    CollageItemClickListener collageItemClickListener = null;
    int width = 0;
    boolean doFaceCrop = false;


    public CollageView(Context context) {
        super(context);
        this.context = context;
    }


    public CollageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public CollageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CollageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
    }


    public void addMedia(List<Media> mediaList, CollageController collageController) {
        this.mediaList = mediaList;
        this.collageController = collageController;
        post(new Runnable() {
            @Override
            public void run() {
                width = getWidth();
                init();
            }
        });
    }

    public void addMedia(List<Media> mediaList, CollageController collageController, int width, boolean doFaceCrop) {
        this.mediaList = mediaList;
        this.collageController = collageController;
        this.doFaceCrop = doFaceCrop;
        this.width = width;
        init();
    }

    public void addMedia(List<Media> mediaList, CollageController collageController, int width) {
        this.mediaList = mediaList;
        this.collageController = collageController;
        this.width = width;
        init();
    }

    public void addMedia(List<Media> mediaList, Media media, int width, boolean doFaceCrop, Boolean isFeedGridStyle) {
        this.mediaList = mediaList;
        this.collageController = CollageController.SINGLE;
        this.doFaceCrop = doFaceCrop;
        this.width = width;
        //
        if(isFeedGridStyle){
            addView(generateCollageView(new LinearLayout.LayoutParams(Utility.getScreenWidth(context), Utility.getFeedViewPagerHeight(context)), media, 0, 0));
        }
    }

    private void init() {
        removeAllViews();
        if (collageController == null)
            return;
        Log.e("TAG", "init: "+collageController.toString());
        switch (collageController) {
            case SINGLE:
                LinearLayout.LayoutParams params = Utility.getLayoutParams(1 / mediaList.get(0).ratio, width);
                setLayoutParams(params);
                addView(generateCollageView(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT), mediaList.get(0), 2, 0));
                return;
            case TWO_EQUAL:
                LayoutParams equalParams = new LayoutParams(width, width / 2);
                setLayoutParams(equalParams);
                setOrientation(LinearLayout.HORIZONTAL);
                for (int i = 0; i <= 1; i++) {
                    addView(generateCollageView(new LayoutParams(width / 2, width / 2), mediaList.get(i), 2, i));
                }
                return;
            case TWO_HORIZONTAL:
                LayoutParams params1 = Utility.getLayoutParams(1, width);
                setLayoutParams(params1);
                setOrientation(LinearLayout.VERTICAL);
                for (int i = 0; i <= 1; i++) {
                    addView(generateCollageView(new LayoutParams(width, width / 2), mediaList.get(i), 2, i));
                }
                return;
            case TWO_VERTICAL:
                LayoutParams params2 = Utility.getLayoutParams(1, width);
                setLayoutParams(params2);
                setOrientation(LinearLayout.HORIZONTAL);
                for (int i = 0; i <= 1; i++) {
                    addView(generateCollageView(new LayoutParams(width / 2, width), mediaList.get(i), 2, i));
                }
                return;
            case THREE_EQUAL:
                LayoutParams paramsEqual = new LayoutParams(width, width * 2 / 3);
                setLayoutParams(paramsEqual);
                setOrientation(LinearLayout.HORIZONTAL);
                addView(generateCollageView(new LayoutParams(width * 2 / 3, width * 2 / 3), mediaList.get(0), 2, 0));
                LayoutParams paramsEqual1 = new LayoutParams(width / 3, width * 2 / 3);
                LinearLayout subViewEqual1 = generateLinearLayout(paramsEqual1, LinearLayout.VERTICAL, 2);
                subViewEqual1.setOrientation(LinearLayout.VERTICAL);
                for (int i = 1; i <= 2; i++) {
                    subViewEqual1.addView(generateCollageView(
                            new LayoutParams(width / 3, width / 3),
                            mediaList.get(i), 2, i));
                }
                addView(subViewEqual1);
                return;
            case THREE_1H2S:
                LayoutParams params3 = Utility.getLayoutParams(1, width);
                setLayoutParams(params3);
                setOrientation(LinearLayout.VERTICAL);
                addView(generateCollageView(new LayoutParams(width, width / 2), mediaList.get(0), 2, 0));
                LayoutParams layoutParams = new LayoutParams(width, width / 2);
                LinearLayout subView = generateLinearLayout(layoutParams, LinearLayout.HORIZONTAL, 2);
                for (int i = 1; i <= 2; i++) {
                    subView.addView(generateCollageView(
                            new LayoutParams(width / 2, width / 2),
                            mediaList.get(i), 2, i));
                }
                addView(subView);
                return;
            case THREE_1V2S:
                LayoutParams params4 = Utility.getLayoutParams(1, width);
                setLayoutParams(params4);
                setOrientation(LinearLayout.HORIZONTAL);
                addView(generateCollageView(new LayoutParams(width / 2, width), mediaList.get(0), 2, 0));
                LayoutParams layoutParams1 = new LayoutParams(width / 2, width);
                LinearLayout subView1 = generateLinearLayout(layoutParams1, LinearLayout.VERTICAL, 2);
                subView1.setOrientation(LinearLayout.VERTICAL);
                for (int i = 1; i <= 2; i++) {
                    subView1.addView(generateCollageView(
                            new LayoutParams(width / 2, width / 2),
                            mediaList.get(i), 2, i));
                }
                addView(subView1);
                return;
            case THREE_HORIZONTAL:
                LayoutParams params3H = Utility.getLayoutParams(1, width);
                setLayoutParams(params3H);
                setOrientation(LinearLayout.VERTICAL);
                addView(generateCollageView(new LayoutParams(width, width * 2 / 3), mediaList.get(0), 2, 0));
                LayoutParams params2H = new LayoutParams(width, width / 3);
                LinearLayout ll2H = generateLinearLayout(params2H, LinearLayout.VERTICAL, 2);
                ll2H.setOrientation(LinearLayout.HORIZONTAL);
                for (int i = 1; i <= 2; i++) {
                    ll2H.addView(generateCollageView(
                            new LayoutParams(width / 2, width / 3),
                            mediaList.get(i), 2, i));
                }
                addView(ll2H);
                break;
            case THREE_VERTICAL:
                LayoutParams params3V = Utility.getLayoutParams(1, width);
                setLayoutParams(params3V);
                setOrientation(LinearLayout.HORIZONTAL);
                addView(generateCollageView(new LayoutParams(width * 2 / 3, width), mediaList.get(0), 2, 0));
                LayoutParams params12V = new LayoutParams(width / 3, width);
                LinearLayout ll12V = generateLinearLayout(params12V, LinearLayout.VERTICAL, 2);
                ll12V.setOrientation(LinearLayout.VERTICAL);
                for (int i = 1; i <= 2; i++) {
                    ll12V.addView(generateCollageView(
                            new LayoutParams(width * 1 / 3, width / 2),
                            mediaList.get(i), 2, i));
                }
                addView(ll12V);
                break;
            case FOUR_EQUAL:
                LayoutParams equal4 = Utility.getLayoutParams(1, width);
                setLayoutParams(equal4);
                setOrientation(VERTICAL);
                int counter = 0;
                for (int i = 0; i <= 1; i++) {
                    LayoutParams layoutP = new LayoutParams(width, width / 2);
                    LinearLayout subViewEqual = generateLinearLayout(layoutP, LinearLayout.VERTICAL, 2);
                    subViewEqual.setOrientation(LinearLayout.HORIZONTAL);
                    for (int j = 0; j <= 1; j++) {

                        subViewEqual.addView(generateCollageView(
                                new LayoutParams(width / 2, width / 2),
                                mediaList.get(counter), 2, counter));

                        counter++;
                    }
                    addView(subViewEqual);
                }
                break;
            case FOUR_HORIZONTAL:

                LayoutParams params5 = Utility.getLayoutParams(1, width);
                setLayoutParams(params5);
                setOrientation(LinearLayout.VERTICAL);

                addView(generateCollageView(new LayoutParams(width, width * 2 / 3), mediaList.get(0), 2, 0));

                LayoutParams layoutParams2 = new LayoutParams(width, width / 3);
                LinearLayout subView3 = generateLinearLayout(layoutParams2, LinearLayout.HORIZONTAL, 2);
                for (int i = 1; i <= 3; i++) {
                    subView3.addView(generateCollageView(
                            new LayoutParams(width / 3, width / 3),
                            mediaList.get(i), 2, i));
                }
                addView(subView3);
                break;

            case FOUR_VERTICAL:

                LayoutParams params6 = Utility.getLayoutParams(1, width);
                setLayoutParams(params6);
                setOrientation(LinearLayout.HORIZONTAL);
                addView(generateCollageView(new LayoutParams(width * 2 / 3, width), mediaList.get(0), 2, 0));
                LayoutParams layoutParams3 = new LayoutParams(width / 3, width);
                LinearLayout subView4 = generateLinearLayout(layoutParams3, LinearLayout.VERTICAL, 2);
                subView4.setOrientation(LinearLayout.VERTICAL);
                for (int i = 1; i <= 3; i++) {
                    subView4.addView(generateCollageView(
                            new LayoutParams(width / 3, width / 3),
                            mediaList.get(i), 2, i));
                }
                addView(subView4);

                break;


            case FIVE_EQUAL:

                LayoutParams paramsEqual5 = new LayoutParams(width, width * 5 / 6);
                setLayoutParams(paramsEqual5);
                setOrientation(LinearLayout.VERTICAL);

                LayoutParams layoutEqual5 = new LayoutParams(width, width * 1 / 2);
                LinearLayout subViewEqualTop5 = generateLinearLayout(layoutEqual5, LinearLayout.VERTICAL, 2);
                subViewEqualTop5.setOrientation(LinearLayout.HORIZONTAL);
                for (int j = 0; j <= 1; j++) {

                    subViewEqualTop5.addView(generateCollageView(
                            new LayoutParams(width / 2, width / 2),
                            mediaList.get(j), 2, j));

                }
                addView(subViewEqualTop5);

                LayoutParams layoutParamsEqual5 = new LayoutParams(width, width / 3);
                LinearLayout subViewEqual5 = generateLinearLayout(layoutParamsEqual5, LinearLayout.HORIZONTAL, 2);
                for (int i = 2; i <= 4; i++) {
                    subViewEqual5.addView(generateCollageView(
                            new LayoutParams(width / 3, width / 3),
                            mediaList.get(i), 2, i));
                }
                addView(subViewEqual5);
                break;
            case FIVE_2V3H:

                LayoutParams params2V3H = Utility.getLayoutParams(1, width);
                setLayoutParams(params2V3H);
                setOrientation(HORIZONTAL);

                LayoutParams params2V = new LayoutParams(width / 2, width);
                LinearLayout ll2V = generateLinearLayout(params2V, LinearLayout.VERTICAL, 2);

                for (int i = 0; i <= 1; i++) {
                    ll2V.addView(generateCollageView(
                            new LayoutParams(width / 2, width / 2),
                            mediaList.get(i), 2, i));
                }

                addView(ll2V);

                LayoutParams p3H = new LayoutParams(width / 2, width);
                LinearLayout ll3H = generateLinearLayout(p3H, LinearLayout.VERTICAL, 2);

                for (int i = 2; i <= 4; i++) {
                    ll3H.addView(generateCollageView(
                            new LayoutParams(width / 2, width / 3),
                            mediaList.get(i), 2, i));
                }

                addView(ll3H);

                break;

            case FIVE_3H2V:

                LayoutParams params3H2V = Utility.getLayoutParams(1, width);
                setLayoutParams(params3H2V);
                setOrientation(HORIZONTAL);

                LayoutParams params3H1 = new LayoutParams(width / 2, width);
                LinearLayout ll3H1 = generateLinearLayout(params3H1, LinearLayout.VERTICAL, 2);

                for (int i = 0; i <= 2; i++) {
                    ll3H1.addView(generateCollageView(
                            new LayoutParams(width / 2, width / 3),
                            mediaList.get(i), 2, i));
                }

                addView(ll3H1);

                LayoutParams params2V1 = new LayoutParams(width / 2, width);
                LinearLayout ll2V1 = generateLinearLayout(params2V1, LinearLayout.VERTICAL, 2);

                for (int i = 3; i <= 4; i++) {
                    ll2V1.addView(generateCollageView(
                            new LayoutParams(width / 2, width / 2),
                            mediaList.get(i), 2, i));
                }

                addView(ll2V1);

                break;


            case FIVE_3V2HS:
                LayoutParams params3V2HS = Utility.getLayoutParams(1, width);
                setLayoutParams(params3V2HS);
                setOrientation(VERTICAL);

                LayoutParams params3VVV = new LayoutParams(width, width / 2);
                LinearLayout ll3VVV = generateLinearLayout(params3VVV, LinearLayout.HORIZONTAL, 2);

                for (int i = 0; i <= 2; i++) {
                    ll3VVV.addView(generateCollageView(
                            new LayoutParams(width / 3, width / 2),
                            mediaList.get(i), 2, i));
                }

                addView(ll3VVV);

                LayoutParams params2HS = new LayoutParams(width, width / 2);
                LinearLayout ll2HS = generateLinearLayout(params2HS, LinearLayout.HORIZONTAL, 2);

                for (int i = 3; i <= 4; i++) {
                    ll2HS.addView(generateCollageView(
                            new LayoutParams(width / 2, width / 2),
                            mediaList.get(i), 2, i));
                }

                addView(ll2HS);

                break;

            case FIVE_2HS3V:
                /*LayoutParams params2HS3V = Utility.getLayoutParams(1, width);
                setLayoutParams(params2HS3V);*/
                setOrientation(VERTICAL);
                LayoutParams params2HS1 = new LayoutParams(width, width / 2);
                LinearLayout ll2HS1 = generateLinearLayout(params2HS1, LinearLayout.HORIZONTAL, 2);
                for (int i = 0; i <= 1; i++) {
                    ll2HS1.addView(generateCollageView(new LayoutParams(width / 2, width / 2),mediaList.get(i), 2, i));
                }
                addView(ll2HS1);
                LayoutParams params3VVV1 = new LayoutParams(width, width / 3);
                LinearLayout ll3VVV1 = generateLinearLayout(params3VVV1, LinearLayout.HORIZONTAL, 2);
                for (int i = 2; i <= 4; i++) {
                    ll3VVV1.addView(generateCollageView(new LayoutParams(width / 3, width / 3),mediaList.get(i), 2, i));
                }
                addView(ll3VVV1);
                break;

            case MORE_EQUAL:
                LayoutParams paramsEqualMore5 = new LayoutParams(width, width * 5 / 6);
                setLayoutParams(paramsEqualMore5);
                setOrientation(LinearLayout.VERTICAL);

                LayoutParams layoutEqualMore5 = new LayoutParams(width, width * 1 / 2);
                LinearLayout subViewEqualTopMore5 = generateLinearLayout(layoutEqualMore5, LinearLayout.VERTICAL, 2);
                subViewEqualTopMore5.setOrientation(LinearLayout.HORIZONTAL);
                for (int j = 0; j <= 1; j++) {

                    subViewEqualTopMore5.addView(generateCollageView(
                            new LayoutParams(width / 2, width / 2),
                            mediaList.get(j), 2, j));

                }
                addView(subViewEqualTopMore5);

                LayoutParams layoutParamsEqualMore5 = new LayoutParams(width, width / 3);
                LinearLayout subViewEqualMore5 = generateLinearLayout(layoutParamsEqualMore5, LinearLayout.HORIZONTAL, 2);
                for (int i = 2; i <= 4; i++) {
                    if (i == 4) {
                        RelativeLayout relativeLayoutMore = generateRelativeLayout(
                                new LayoutParams(width / 3, width / 3), 2);

                        relativeLayoutMore.addView(generateCollageView(
                                new LayoutParams(width / 3, width / 3),
                                mediaList.get(i), 0, i));

                        relativeLayoutMore.addView(generateTextView(
                                new LayoutParams(width / 3, width / 3)
                                , 0, "+" + (mediaList.size() - 5), i));

                        subViewEqualMore5.addView(relativeLayoutMore);
                        break;
                    } else {
                        subViewEqualMore5.addView(generateCollageView(
                                new LayoutParams(width / 3, width / 3),
                                mediaList.get(i), 2, i));
                    }
                }
                addView(subViewEqualMore5);
                break;

            case MORE_2V3H:
                LayoutParams paramsM2V3H = Utility.getLayoutParams(1, width);
                setLayoutParams(paramsM2V3H);
                setOrientation(HORIZONTAL);

                LayoutParams paramsM2V = new LayoutParams(width / 2, width);
                LinearLayout llM2V = generateLinearLayout(paramsM2V, LinearLayout.VERTICAL, 2);

                for (int i = 0; i <= 1; i++) {
                    llM2V.addView(generateCollageView(
                            new LayoutParams(width / 2, width / 2),
                            mediaList.get(i), 2, i));
                }

                addView(llM2V);

                LayoutParams paramsM3H = new LayoutParams(width / 2, width);
                LinearLayout llM3H = generateLinearLayout(paramsM3H, LinearLayout.VERTICAL, 2);

                for (int i = 2; i <= 4; i++) {
                    if (i == 4) {
                        RelativeLayout relativeLayoutMore = generateRelativeLayout(
                                new LayoutParams(width / 2, width / 3), 2);

                        relativeLayoutMore.addView(generateCollageView(
                                new LayoutParams(width / 2, width / 3),
                                mediaList.get(i), 0, i));

                        relativeLayoutMore.addView(generateTextView(
                                new LayoutParams(width / 2, width / 3)
                                , 0, "+" + (mediaList.size() - 5), i));

                        llM3H.addView(relativeLayoutMore);
                        break;
                    } else {
                        llM3H.addView(generateCollageView(
                                new LayoutParams(width / 2, width / 3),
                                mediaList.get(i), 2, i));
                    }
                }

                addView(llM3H);
                break;
            case MORE_3H2V:

                LayoutParams paramsM13H2V = Utility.getLayoutParams(1, width);
                setLayoutParams(paramsM13H2V);
                setOrientation(HORIZONTAL);

                LayoutParams paramsM13H1 = new LayoutParams(width / 2, width);
                LinearLayout llM13H1 = generateLinearLayout(paramsM13H1, LinearLayout.VERTICAL, 2);

                for (int i = 0; i <= 2; i++) {
                    llM13H1.addView(generateCollageView(
                            new LayoutParams(width / 2, width / 3),
                            mediaList.get(i), 2, i));
                }

                addView(llM13H1);

                LayoutParams paramsM12V1 = new LayoutParams(width / 2, width);
                LinearLayout llM12V1 = generateLinearLayout(paramsM12V1, LinearLayout.VERTICAL, 2);

                for (int i = 3; i <= 4; i++) {
                    if (i == 4) {
                        RelativeLayout relativeLayoutMore = generateRelativeLayout(
                                new LayoutParams(width / 2, width / 2), 2);

                        relativeLayoutMore.addView(generateCollageView(
                                new LayoutParams(width / 2, width / 2),
                                mediaList.get(i), 0, i));

                        relativeLayoutMore.addView(generateTextView(
                                new LayoutParams(width / 2, width / 2)
                                , 0, "+" + (mediaList.size() - 5), i));

                        llM12V1.addView(relativeLayoutMore);
                        break;
                    } else {
                        llM12V1.addView(generateCollageView(
                                new LayoutParams(width / 2, width / 2),
                                mediaList.get(i), 2, i));
                    }
                }

                addView(llM12V1);

                break;

            case MORE_3V2HS:
                LayoutParams paramsM3V2HS = Utility.getLayoutParams(1, width);
                setLayoutParams(paramsM3V2HS);
                setOrientation(VERTICAL);

                LayoutParams paramsM3VVV = new LayoutParams(width, width / 2);
                LinearLayout llM3VVV = generateLinearLayout(paramsM3VVV, LinearLayout.HORIZONTAL, 2);

                for (int i = 0; i <= 2; i++) {
                    llM3VVV.addView(generateCollageView(
                            new LayoutParams(width / 3, width / 2),
                            mediaList.get(i), 2, i));
                }

                addView(llM3VVV);

                LayoutParams paramsM2HS = new LayoutParams(width, width / 2);
                LinearLayout llM2HS = generateLinearLayout(paramsM2HS, LinearLayout.HORIZONTAL, 2);

                for (int i = 3; i <= 4; i++) {
                    if (i == 4) {
                        RelativeLayout relativeLayoutMore = generateRelativeLayout(
                                new LayoutParams(width / 2, width / 2), 2);

                        relativeLayoutMore.addView(generateCollageView(
                                new LayoutParams(width / 2, width / 2),
                                mediaList.get(i), 0, i));

                        relativeLayoutMore.addView(generateTextView(
                                new LayoutParams(width / 2, width / 2)
                                , 0, "+" + (mediaList.size() - 5), i));

                        llM2HS.addView(relativeLayoutMore);
                        break;
                    } else {
                        llM2HS.addView(generateCollageView(
                                new LayoutParams(width / 2, width / 2),
                                mediaList.get(i), 2, i));
                    }
                }

                addView(llM2HS);
                break;

            case MORE_2HS3V:

                LayoutParams paramsM2HS3V = Utility.getLayoutParams(1, width);
                setLayoutParams(paramsM2HS3V);
                setOrientation(VERTICAL);

                LayoutParams paramsM2HS1 = new LayoutParams(width, width / 2);
                LinearLayout llM2HS1 = generateLinearLayout(paramsM2HS1, LinearLayout.HORIZONTAL, 2);

                for (int i = 0; i <= 1; i++) {
                    llM2HS1.addView(generateCollageView(
                            new LayoutParams(width / 2, width / 2),
                            mediaList.get(i), 2, i));
                }

                addView(llM2HS1);

                LayoutParams paramsM3VVV1 = new LayoutParams(width, width / 2);
                LinearLayout llM3VVV1 = generateLinearLayout(paramsM3VVV1, LinearLayout.HORIZONTAL, 2);

                for (int i = 2; i <= 4; i++) {
                    if (i == 4) {
                        RelativeLayout relativeLayoutMore = generateRelativeLayout(
                                new LayoutParams(width / 3, width / 2), 2);

                        relativeLayoutMore.addView(generateCollageView(
                                new LayoutParams(width / 3, width / 2),
                                mediaList.get(i), 0, i));

                        relativeLayoutMore.addView(generateTextView(
                                new LayoutParams(width / 3, width / 2)
                                , 0, "+" + (mediaList.size() - 5), i));

                        llM3VVV1.addView(relativeLayoutMore);
                        break;
                    } else {
                        llM3VVV1.addView(generateCollageView(
                                new LayoutParams(width / 3, width / 2),
                                mediaList.get(i), 2, i));
                    }
                }

                addView(llM3VVV1);
                break;


            case MORE_HORIZONTAL:

                LayoutParams params7 = Utility.getLayoutParams(1, width);
                setLayoutParams(params7);
                setOrientation(LinearLayout.VERTICAL);

                addView(generateCollageView(new LayoutParams(width, width * 2 / 3), mediaList.get(0), 2, 0));

                LayoutParams layoutParams4 = new LayoutParams(width, width / 3);
                LinearLayout subView5 = generateLinearLayout(layoutParams4, LinearLayout.HORIZONTAL, 2);
                for (int i = 1; i <= 3; i++) {
                    if (i == 3) {
                        RelativeLayout relativeLayout = generateRelativeLayout(
                                new LayoutParams(width / 3, width / 3), 2);

                        relativeLayout.addView(generateCollageView(
                                new LayoutParams(width / 3, width / 3),
                                mediaList.get(i), 0, i));

                        relativeLayout.addView(generateTextView(
                                new LayoutParams(width / 3, width / 3)
                                , 0, "+" + (mediaList.size() - 4), i));

                        subView5.addView(relativeLayout);
                        break;
                    } else {
                        subView5.addView(generateCollageView(
                                new LayoutParams(width / 3, width / 3),
                                mediaList.get(i), 2, i));
                    }
                }
                addView(subView5);
                break;

            case MORE_VERTICAL:

                LayoutParams params8 = Utility.getLayoutParams(1, width);
                setLayoutParams(params8);
                setOrientation(LinearLayout.HORIZONTAL);
                addView(generateCollageView(new LayoutParams(width * 2 / 3, width), mediaList.get(0), 2, 0));
                LayoutParams layoutParams5 = new LayoutParams(width / 3, width);
                LinearLayout subView6 = generateLinearLayout(layoutParams5, LinearLayout.VERTICAL, 2);
                for (int i = 1; i <= 3; i++) {

                    if (i == 3) {
                        RelativeLayout relativeLayout1 = generateRelativeLayout(
                                new LayoutParams(width / 3, width / 3), 2);

                        relativeLayout1.addView(generateCollageView(
                                new LayoutParams(width / 3, width / 3),
                                mediaList.get(i), 0, i));

                        relativeLayout1.addView(generateTextView(
                                new LayoutParams(width / 3, width / 3)
                                , 0, "+" + (mediaList.size() - 4), i));

                        subView6.addView(relativeLayout1);
                    } else {

                        subView6.addView(generateCollageView(
                                new LayoutParams(width / 3, width / 3),
                                mediaList.get(i), 2, i));
                    }
                }

                addView(subView6);
                break;
        }
    }

    private LinearLayout generateLinearLayout(LinearLayout.LayoutParams params, int orientation, int padding) {
        final LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setPadding(padding, padding, padding, padding);
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(orientation);
        return linearLayout;
    }

    private RelativeLayout generateRelativeLayout(LinearLayout.LayoutParams params, int padding) {
        final RelativeLayout relativeLayout = new RelativeLayout(getContext());
        relativeLayout.setPadding(padding, padding, padding, padding);
        relativeLayout.setLayoutParams(params);
        return relativeLayout;
    }

    private TextView generateTextView(LinearLayout.LayoutParams params, int padding, String text, final int position) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        padding = 15;
        //
        final TextView textView = new TextView(getContext());
        textView.setPadding(padding, padding, padding, padding);
        textView.setLayoutParams(layoutParams);
        textView.setGravity(Gravity.CENTER);
        //textView.setBackgroundResource(R.drawable.round_bg_transparent);
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        textView.setTextSize(25);
        textView.setPadding(padding, padding, padding, padding);
        textView.setText(text);
        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (collageItemClickListener != null) {
                    collageItemClickListener.onClick(position);
                }
            }
        });
        return textView;
    }

    private View generateCollageView(LinearLayout.LayoutParams params, Media media, int padding, final int position) {
        final SimpleDraweeView simpleDraweeView = new SimpleDraweeView(getContext());
        simpleDraweeView.setPadding(padding, padding, padding, padding);
        simpleDraweeView.setLayoutParams(params);
        GenericDraweeHierarchy hierarchy = null;
        hierarchy = GenericDraweeHierarchyBuilder.newInstance(getResources())
                .setActualImageScaleType(ScalingUtils.ScaleType.FOCUS_CROP).build();
        simpleDraweeView.setHierarchy(hierarchy);
        //
        /*ImageRequest request = ImageRequestBuilder
                .newBuilderWithSource(Utility.addMediaThumbnailUrl(media))
                .setPostprocessor(new FaceCenterCrop(params.width, params.height, media.facePointList))
                .build();
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(simpleDraweeView.getController())
                .build();
        simpleDraweeView.setController(controller);*/
        //
        ImageRequest request = null;
        if (doFaceCrop) {
            try {
                if (mediaList.size() > 1) {
                    request = ImageRequestBuilder
                            .newBuilderWithSource(Utility.addMediaThumbnailUrl(media))
                            .setResizeOptions(new ResizeOptions(params.width, params.height))
                            //.setPostprocessor(new FrescoFaceCenterCropNew(params.width, params.height))
                            .setPostprocessor(new FaceCenterCrop(params.width, params.height, media.facePointList))
                            .build();
                } else {
                    request = ImageRequestBuilder
                            .newBuilderWithSource(Utility.addMediaThumbnailUrl(media))
                            .build();
                    if (media.type != null && media.type.equals(Constants.MEDIA_TYPE_IMAGE)) {
                        simpleDraweeView.setTag(media.url);
                        ImageZoomHelper.setViewZoomable(simpleDraweeView);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Common.getInstance().setGIFImageToFresco(simpleDraweeView,request,media,true);
        //getBitmapFromFresco(media,simpleDraweeView,params.width, params.height, media.facePointList);
        //
        simpleDraweeView.setOnClickListener(v -> {
            collageItemClickListener.onClick(position);
        });
        if (media.type != null && media.type.equals(Constants.MEDIA_TYPE_GIF)) {
            Common.getInstance().setGIFImageToFresco(simpleDraweeView, null, media, !SessionManager.getInstance().getKeyValue(SessionManager.KEY_AUTO_PLAY_GIFS,false));
        }
        if (media.type != null && media.type.equals(Constants.MEDIA_TYPE_VIDEO)){
            return getVideoView(params, simpleDraweeView, padding, position, media);
        } else if (media.type != null && media.type.equals(Constants.MEDIA_TYPE_GIF)){
            return generateGIFView(params, simpleDraweeView, padding, position);
        } else {
            return simpleDraweeView;
        }
    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    private View getVideoView(LinearLayout.LayoutParams params, SimpleDraweeView simpleDraweeView, int padding, final int mediaPosition, final Media media){
        RelativeLayout rlParent, rlVideoImage, rlVideoView, rlVideoControls;
        PlayerView playerView;
        SimpleDraweeView ivEqualizer;
        TextView tvTime;
        ImageView ivMuteUnMute;
        View rowView = null;
        LayoutInflater inflater =  (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        try {
            rowView = inflater.inflate(R.layout.collage_video_view, null);
            playerView = rowView.findViewById(R.id.playerView);
            ivEqualizer = rowView.findViewById(R.id.ivEqualizer);
            tvTime = rowView.findViewById(R.id.tvTime);
            ivMuteUnMute = rowView.findViewById(R.id.ivMuteUnMute);
            rlParent = rowView.findViewById(R.id.rlParent);
            rlVideoImage = rowView.findViewById(R.id.rlVideoImage);
            rlVideoView = rowView.findViewById(R.id.rlVideoView);
            rlVideoControls = rowView.findViewById(R.id.rlVideoControls);
            //
            rlVideoView.setVisibility(GONE);
            rlVideoControls.setVisibility(GONE);
            rlParent.setPadding(padding, padding, padding, padding);
            rlParent.setLayoutParams(params);
            rlVideoImage.addView(simpleDraweeView);
            rlVideoImage.addView(generateVideoImageView());
            //
            Media mediaUrl = new Media();
            mediaUrl.url = "uploads/feeds/ck_pr2_2019-4-18_1555568259260_IMG_1747.GIF";
            Common.getInstance().setGIFImageToFresco(ivEqualizer, null, mediaUrl, false);
            //
            if(SessionManager.getInstance().getKeyValue(SessionManager.KEY_IS_MUTE_ENABLED, false)){
                ivMuteUnMute.setImageResource(R.drawable.ic_mute);
            } else {
                ivMuteUnMute.setImageResource(R.drawable.ic_un_mute);
            }
            ivMuteUnMute.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(FeedsFragment.getInstance() != null){
                        FeedsFragment.getInstance().setMuteUnMute(!SessionManager.getInstance().getKeyValue(SessionManager.KEY_IS_MUTE_ENABLED, false));
                    }
                }
            });
            rlVideoView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    collageItemClickListener.onClick(mediaPosition);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowView;
    }

    private ImageView generateVideoImageView() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(Utility.dpSize(getContext(), 40), Utility.dpSize(getContext(), 40));
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        ImageView imageView = new ImageView(getContext());
        imageView.setLayoutParams(params);
        imageView.setImageResource(R.drawable.ic_video_play);
        return imageView;
    }

    private RelativeLayout generateGIFView(LinearLayout.LayoutParams params, SimpleDraweeView simpleDraweeView, int padding, final int position) {
        RelativeLayout relativeLayout = new RelativeLayout(getContext());
        relativeLayout.setPadding(padding, padding, padding, padding);
        relativeLayout.setLayoutParams(params);
        relativeLayout.addView(simpleDraweeView);
        if (SessionManager.getInstance().getKeyValue(SessionManager.KEY_AUTO_PLAY_GIFS,false)) {
            relativeLayout.addView(generateTopLeftGIFImageView());
        } else {
            relativeLayout.addView(generateGIFImageView());
        }
        return relativeLayout;
    }

    private ImageView generateGIFImageView() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(Utility.dpSize(getContext(), 40), Utility.dpSize(getContext(), 40));
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        ImageView imageView = new ImageView(getContext());
        imageView.setLayoutParams(params);
        imageView.setImageResource(R.drawable.gif_icon);
        return imageView;
    }

    private TextView generateTopLeftGIFImageView() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.setMargins(20,20,20,20);
        TextView textView = new TextView(getContext());
        textView.setPadding(10,5,10,5);
        textView.setLayoutParams(params);
        textView.setBackgroundColor(context.getResources().getColor(R.color.transparent100));
        textView.setTextColor(context.getResources().getColor(R.color.white));
        textView.setTextSize(12);
        textView.setText("GIF");
        return textView;
    }

    public void setCollageItemClickListener(CollageItemClickListener collageItemClickListener) {
        this.collageItemClickListener = collageItemClickListener;
    }


    public interface CollageItemClickListener {
        public void onClick(int position);
    }





    /*
                    if (media.facePointList == null)
                    {
                        hierarchy = GenericDraweeHierarchyBuilder.newInstance(getResources())
                                .setActualImageScaleType(ScalingUtils.ScaleType.FOCUS_CROP).build();
                    }
                    else
                    {
                        if (media.facePointList.size() > 0)
                        {
                            Logger.d("POSITION FACES", "" + position);
                            hierarchy = GenericDraweeHierarchyBuilder.newInstance(getResources()).build();
                            PointF focusPoint = new PointF(media.facePointList.get(0).x, media.facePointList.get(0).y);
                            hierarchy.setActualImageFocusPoint(focusPoint);
                        } else {
                            hierarchy = GenericDraweeHierarchyBuilder.newInstance(getResources())
                                    .setActualImageScaleType(ScalingUtils.ScaleType.FOCUS_CROP).build();
                        }
                    }
                    */


                    /*
                    ImageRequest request = ImageRequestBuilder
                            .newBuilderWithSource(Utility.addMediaUrl(media))
                            .setPostprocessor(new BasePostprocessor() {
                                @Override
                                public void process(Bitmap destBitmap, Bitmap sourceBitmap) {
                                    super.process(destBitmap, sourceBitmap);
                                }

                                @Override
                                public CloseableReference<Bitmap> process(Bitmap sourceBitmap, PlatformBitmapFactory bitmapFactory)
                                {
                                    sourceBitmap = Utility.drawFacesCrop(sourceBitmap,media.facePointList,context,simpleDraweeView.getWidth(),simpleDraweeView.getHeight());
                                    return super.process(sourceBitmap, bitmapFactory);
                                }

                                @Override
                                public void process(Bitmap bitmap) {
                                    super.process(bitmap);

                                }
                            })
                            .build();
   */
}
