//package info.celebkonnect.flow.commonuses;
//
//import android.content.Context;
//import android.graphics.Color;
//import android.graphics.Typeface;
//import android.net.Uri;
//import android.os.Build;
//import androidx.annotation.Nullable;
//import androidx.annotation.RequiresApi;
//import android.util.AttributeSet;
//import android.view.Gravity;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.facebook.drawee.drawable.ScalingUtils;
//import com.facebook.drawee.generic.GenericDraweeHierarchy;
//import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
//import com.facebook.drawee.view.SimpleDraweeView;
//
//import java.util.List;
//
//import info.celebkonnect.flow.ipoll.FeedMediaArray;
//import info.celebkonnect.flow.celebflow.FeedPageNew.Media;
//import info.celebkonnect.flow.celebflow.R;
//import info.celebkonnect.flow.celebflow.constants.CollageController;
//import info.celebkonnect.flow.retrofitcall.ApiClient;
//import info.celebkonnect.flow.utils.Utility;
//
///**
// * Created by User on 21-07-2018.
// **/
//
//public class OwnCollageView extends LinearLayout {
//    Context context;
//    List<Media> mediaList;
//    List<FeedMediaArray> mediaArrays;
//    CollageController collageController;
//    CollageItemClickListener collageItemClickListener = null;
//    int width;
//
//
//    public OwnCollageView(Context context) {
//        super(context);
//        this.context = context;
//    }
//
//    public OwnCollageView(Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//        this.context = context;
//    }
//
//    public OwnCollageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        this.context = context;
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public OwnCollageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//        this.context = context;
//    }
//
//
//    public void addMedia(List<Media> mediaList, CollageController collageController) {
//        if (mediaList != null && mediaList.size() > 0) {
//            this.mediaList = mediaList;
//            this.collageController = collageController;
//
//            post(new Runnable() {
//                @Override
//                public void run() {
//                    width = getWidth();
//                    init();
//                }
//            });
//        }
//    }
//
//    public void addFeedMedia(List<FeedMediaArray> mediaList, CollageController collageController) {
//        if (mediaList != null && mediaList.size() > 0) {
//            this.mediaArrays = mediaList;
//            this.collageController = collageController;
//
//            post(new Runnable() {
//                @Override
//                public void run() {
//                    width = getWidth();
//                    feedInit();
//                }
//            });
//        }
//    }
//
//
//    public void addFeedMedia(List<FeedMediaArray> mediaList, CollageController collageController, int width) {
//        if (mediaList != null && mediaList.size() > 0) {
//            this.mediaArrays = mediaList;
//            this.collageController = collageController;
//            this.width = width;
//            feedInit();
//
//        }
//    }
//
//    private void init() {
//        removeAllViews();
//
//        if (collageController == null)
//            return;
//        switch (collageController) {
//            case SINGLE:
//                LayoutParams params = Utility.getLayoutParams(1 / mediaList.get(0).ratio, width);
//                setLayoutParams(params);
//                addView(generateImageView(context, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT), mediaList.get(0), 2, 0));
//                return;
//
//            case TWO_EQUAL:
//                LayoutParams equalParams = new LayoutParams(width, width / 2);
//                setLayoutParams(equalParams);
//                setOrientation(LinearLayout.HORIZONTAL);
//                for (int i = 0; i <= 1; i++) {
//                    addView(generateImageView(context, new LayoutParams(width / 2, width / 2), mediaList.get(i), 2, i));
//                }
//                return;
//            case TWO_HORIZONTAL:
//                LayoutParams params1 = Utility.getLayoutParams(1, width);
//                setLayoutParams(params1);
//                setOrientation(LinearLayout.VERTICAL);
//                for (int i = 0; i <= 1; i++) {
//                    addView(generateImageView(context, new LayoutParams(width, width / 2), mediaList.get(i), 2, i));
//                }
//
//                return;
//            case TWO_VERTICAL:
//                LayoutParams params2 = Utility.getLayoutParams(1, width);
//                setLayoutParams(params2);
//                setOrientation(LinearLayout.HORIZONTAL);
//                for (int i = 0; i <= 1; i++) {
//                    addView(generateImageView(context, new LayoutParams(width / 2, width), mediaList.get(i), 2, i));
//                }
//
//                return;
//
//            case THREE_EQUAL:
//                LayoutParams paramsEqual = new LayoutParams(width, width * 2 / 3);
//                setLayoutParams(paramsEqual);
//                setOrientation(LinearLayout.HORIZONTAL);
//                addView(generateImageView(context, new LayoutParams(width * 2 / 3, width * 2 / 3), mediaList.get(0), 2, 0));
//                LayoutParams paramsEqual1 = new LayoutParams(width / 3, width * 2 / 3);
//                LinearLayout subViewEqual1 = generateLinearLayout(context, paramsEqual1, LinearLayout.VERTICAL, 2);
//                subViewEqual1.setOrientation(LinearLayout.VERTICAL);
//                for (int i = 1; i <= 2; i++) {
//                    subViewEqual1.addView(generateImageView(context,
//                            new LayoutParams(width / 3, width / 3),
//                            mediaList.get(i), 2, i));
//                }
//                addView(subViewEqual1);
//                return;
//
//
//            case THREE_HORIZONTAL:
//                LayoutParams params3 = Utility.getLayoutParams(1, width);
//                setLayoutParams(params3);
//                setOrientation(LinearLayout.VERTICAL);
//                addView(generateImageView(context, new LayoutParams(width, width / 2), mediaList.get(0), 2, 0));
//                LayoutParams layoutParams = new LayoutParams(width, width / 2);
//                LinearLayout subView = generateLinearLayout(context, layoutParams, LinearLayout.HORIZONTAL, 2);
//                for (int i = 1; i <= 2; i++) {
//                    subView.addView(generateImageView(context,
//                            new LayoutParams(width / 2, width / 2),
//                            mediaList.get(i), 2, i));
//                }
//                addView(subView);
//
//
//                return;
//
//            case THREE_VERTICAL:
//
//                LayoutParams params4 = Utility.getLayoutParams(1, width);
//                setLayoutParams(params4);
//                setOrientation(LinearLayout.HORIZONTAL);
//                addView(generateImageView(context, new LayoutParams(width / 2, width), mediaList.get(0), 2, 0));
//                LayoutParams layoutParams1 = new LayoutParams(width / 2, width);
//                LinearLayout subView1 = generateLinearLayout(context, layoutParams1, LinearLayout.VERTICAL, 2);
//                subView1.setOrientation(LinearLayout.VERTICAL);
//                for (int i = 1; i <= 2; i++) {
//                    subView1.addView(generateImageView(context,
//                            new LayoutParams(width / 2, width / 2),
//                            mediaList.get(i), 2, i));
//                }
//                addView(subView1);
//                return;
//
//
//            case FOUR_EQUAL:
//                LayoutParams equal4 = Utility.getLayoutParams(1, width);
//                setLayoutParams(equal4);
//                setOrientation(VERTICAL);
//                int counter = 0;
//                for (int i = 0; i <= 1; i++) {
//                    LayoutParams layoutP = new LayoutParams(width, width / 2);
//                    LinearLayout subViewEqual = generateLinearLayout(context, layoutP, LinearLayout.VERTICAL, 2);
//                    subViewEqual.setOrientation(LinearLayout.HORIZONTAL);
//                    for (int j = 0; j <= 1; j++) {
//
//                        subViewEqual.addView(generateImageView(context,
//                                new LayoutParams(width / 2, width / 2),
//                                mediaList.get(counter), 2, counter));
//
//                        counter++;
//                    }
//                    addView(subViewEqual);
//                }
//                return;
//            case FOUR_HORIZONTAL:
//
//                LayoutParams params5 = Utility.getLayoutParams(1, width);
//                setLayoutParams(params5);
//                setOrientation(LinearLayout.VERTICAL);
//
//                addView(generateImageView(context, new LayoutParams(width, width * 2 / 3), mediaList.get(0), 2, 0));
//
//                LayoutParams layoutParams2 = new LayoutParams(width, width / 3);
//                LinearLayout subView3 = generateLinearLayout(context, layoutParams2, LinearLayout.HORIZONTAL, 2);
//                for (int i = 1; i <= 3; i++) {
//                    subView3.addView(generateImageView(context,
//                            new LayoutParams(width / 3, width / 3),
//                            mediaList.get(i), 2, i));
//                }
//                addView(subView3);
//                break;
//
//            case FOUR_VERTICAL:
//
//                LayoutParams params6 = Utility.getLayoutParams(1, width);
//                setLayoutParams(params6);
//                setOrientation(LinearLayout.HORIZONTAL);
//                addView(generateImageView(context, new LayoutParams(width * 2 / 3, width), mediaList.get(0), 2, 0));
//                LayoutParams layoutParams3 = new LayoutParams(width / 3, width);
//                LinearLayout subView4 = generateLinearLayout(context, layoutParams3, LinearLayout.VERTICAL, 2);
//                subView4.setOrientation(LinearLayout.VERTICAL);
//                for (int i = 1; i <= 3; i++) {
//                    subView4.addView(generateImageView(context,
//                            new LayoutParams(width / 3, width / 3),
//                            mediaList.get(i), 2, i));
//                }
//                addView(subView4);
//
//                return;
//
//
//            case FIVE_EQUAL:
//
//                LayoutParams paramsEqual5 = new LayoutParams(width, width * 5 / 6);
//                setLayoutParams(paramsEqual5);
//                setOrientation(LinearLayout.VERTICAL);
//
//                LayoutParams layoutEqual5 = new LayoutParams(width, width * 1 / 2);
//                LinearLayout subViewEqualTop5 = generateLinearLayout(context, layoutEqual5, LinearLayout.VERTICAL, 2);
//                subViewEqualTop5.setOrientation(LinearLayout.HORIZONTAL);
//                for (int j = 0; j <= 1; j++) {
//
//                    subViewEqualTop5.addView(generateImageView(context,
//                            new LayoutParams(width / 2, width / 2),
//                            mediaList.get(j), 2, j));
//
//                }
//                addView(subViewEqualTop5);
//
//                LayoutParams layoutParamsEqual5 = new LayoutParams(width, width / 3);
//                LinearLayout subViewEqual5 = generateLinearLayout(context, layoutParamsEqual5, LinearLayout.HORIZONTAL, 2);
//                for (int i = 2; i <= 4; i++) {
//                    subViewEqual5.addView(generateImageView(context,
//                            new LayoutParams(width / 3, width / 3),
//                            mediaList.get(i), 2, i));
//                }
//                addView(subViewEqual5);
//                break;
//
//            case MORE_EQUAL:
//                LayoutParams paramsEqualMore5 = new LayoutParams(width, width * 5 / 6);
//                setLayoutParams(paramsEqualMore5);
//                setOrientation(LinearLayout.VERTICAL);
//
//                LayoutParams layoutEqualMore5 = new LayoutParams(width, width * 1 / 2);
//                LinearLayout subViewEqualTopMore5 = generateLinearLayout(context, layoutEqualMore5, LinearLayout.VERTICAL, 2);
//                subViewEqualTopMore5.setOrientation(LinearLayout.HORIZONTAL);
//                for (int j = 0; j <= 1; j++) {
//
//                    subViewEqualTopMore5.addView(generateImageView(context,
//                            new LayoutParams(width / 2, width / 2),
//                            mediaList.get(j), 2, j));
//
//                }
//                addView(subViewEqualTopMore5);
//
//                LayoutParams layoutParamsEqualMore5 = new LayoutParams(width, width / 3);
//                LinearLayout subViewEqualMore5 = generateLinearLayout(context, layoutParamsEqualMore5, LinearLayout.HORIZONTAL, 2);
//                for (int i = 2; i <= 4; i++) {
//                    if (i == 4) {
//                        RelativeLayout relativeLayoutMore = generateRelativeLayout(context,
//                                new LayoutParams(width / 3, width / 3), 2);
//
//                        relativeLayoutMore.addView(generateImageView(context,
//                                new LayoutParams(width / 3, width / 3),
//                                mediaList.get(i), 0, i));
//
//                        relativeLayoutMore.addView(generateTextView(context,
//                                new LayoutParams(width / 3, width / 3)
//                                , 0, "" + "+" + (mediaList.size() - 5), i));
//
//                        subViewEqualMore5.addView(relativeLayoutMore);
//                        break;
//                    } else {
//                        subViewEqualMore5.addView(generateImageView(context,
//                                new LayoutParams(width / 3, width / 3),
//                                mediaList.get(i), 2, i));
//                    }
//                }
//                addView(subViewEqualMore5);
//                return;
//
//            case MORE_HORIZONTAL:
//
//                LayoutParams params7 = Utility.getLayoutParams(1, width);
//                setLayoutParams(params7);
//                setOrientation(LinearLayout.VERTICAL);
//
//                addView(generateImageView(context, new LayoutParams(width, width * 2 / 3), mediaList.get(0), 2, 0));
//
//                LayoutParams layoutParams4 = new LayoutParams(width, width / 3);
//                LinearLayout subView5 = generateLinearLayout(context, layoutParams4, LinearLayout.HORIZONTAL, 2);
//                for (int i = 1; i <= 3; i++) {
//                    if (i == 3) {
//                        RelativeLayout relativeLayout = generateRelativeLayout(context,
//                                new LayoutParams(width / 3, width / 3), 2);
//
//                        relativeLayout.addView(generateImageView(context,
//                                new LayoutParams(width / 3, width / 3),
//                                mediaList.get(i), 0, i));
//
//                        relativeLayout.addView(generateTextView(context,
//                                new LayoutParams(width / 3, width / 3)
//                                , 0, "" + "+" + (mediaList.size() - 4), i));
//
//                        subView5.addView(relativeLayout);
//                        break;
//                    } else {
//                        subView5.addView(generateImageView(context,
//                                new LayoutParams(width / 3, width / 3),
//                                mediaList.get(i), 2, i));
//                    }
//                }
//                addView(subView5);
//                return;
//
//            case MORE_VERTICAL:
//
//                LayoutParams params8 = Utility.getLayoutParams(1, width);
//                setLayoutParams(params8);
//                setOrientation(LinearLayout.HORIZONTAL);
//                addView(generateImageView(context, new LayoutParams(width * 2 / 3, width), mediaList.get(0), 2, 0));
//                LayoutParams layoutParams5 = new LayoutParams(width / 3, width);
//                LinearLayout subView6 = generateLinearLayout(context, layoutParams5, LinearLayout.VERTICAL, 2);
//                for (int i = 1; i <= 3; i++) {
//
//                    if (i == 3) {
//                        RelativeLayout relativeLayout1 = generateRelativeLayout(context,
//                                new LayoutParams(width / 3, width / 3), 2);
//
//                        relativeLayout1.addView(generateImageView(context,
//                                new LayoutParams(width / 3, width / 3),
//                                mediaList.get(i), 0, i));
//
//                        relativeLayout1.addView(generateTextView(context,
//                                new LayoutParams(width / 3, width / 3)
//                                , 0, "" + "+" + (mediaList.size() - 4), i));
//
//                        subView6.addView(relativeLayout1);
//                    } else {
//
//                        subView6.addView(generateImageView(context,
//                                new LayoutParams(width / 3, width / 3),
//                                mediaList.get(i), 2, i));
//                    }
//                }
//
//                addView(subView6);
//                return;
//        }
//    }
//
//    private void feedInit() {
//        removeAllViews();
//
//        if (collageController == null)
//            return;
//        switch (collageController) {
//            case SINGLE:
//                LayoutParams params;
//                if (mediaArrays.get(0).getMediaRatio() != null && !mediaArrays.get(0).getMediaRatio().isEmpty()) {
//                    params = Utility.getLayoutParams(1 / Double.parseDouble(mediaArrays.get(0).getMediaRatio()), width);
//                } else {
//                    params = Utility.getLayoutParams(1 / Double.parseDouble("1.0"), width);
//                }
//                setLayoutParams(params);
//                addView(generateFeedImageView(context, new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT), mediaArrays.get(0), 2, 0));
//                return;
//
//            case TWO_EQUAL:
//                LayoutParams equalParams = new LayoutParams(width, width / 2);
//                setLayoutParams(equalParams);
//                setOrientation(LinearLayout.HORIZONTAL);
//                for (int i = 0; i <= 1; i++) {
//                    addView(generateFeedImageView(context, new LayoutParams(width / 2, width / 2), mediaArrays.get(i), 2, i));
//                }
//                return;
//            case TWO_HORIZONTAL:
//                LayoutParams params1 = Utility.getLayoutParams(1, width);
//                setLayoutParams(params1);
//                setOrientation(LinearLayout.VERTICAL);
//                for (int i = 0; i <= 1; i++) {
//                    addView(generateFeedImageView(context, new LayoutParams(width, width / 2), mediaArrays.get(i), 2, i));
//                }
//
//                return;
//            case TWO_VERTICAL:
//                LayoutParams params2 = Utility.getLayoutParams(1, width);
//                setLayoutParams(params2);
//                setOrientation(LinearLayout.HORIZONTAL);
//                for (int i = 0; i <= 1; i++) {
//                    addView(generateFeedImageView(context, new LayoutParams(width / 2, width), mediaArrays.get(i), 2, i));
//                }
//
//                return;
//
//            case THREE_EQUAL:
//                LayoutParams paramsEqual = new LayoutParams(width, width * 2 / 3);
//                setLayoutParams(paramsEqual);
//                setOrientation(LinearLayout.HORIZONTAL);
//                addView(generateFeedImageView(context, new LayoutParams(width * 2 / 3, width * 2 / 3), mediaArrays.get(0), 2, 0));
//                LayoutParams paramsEqual1 = new LayoutParams(width / 3, width * 2 / 3);
//                LinearLayout subViewEqual1 = generateLinearLayout(context, paramsEqual1, LinearLayout.VERTICAL, 2);
//                subViewEqual1.setOrientation(LinearLayout.VERTICAL);
//                for (int i = 1; i <= 2; i++) {
//                    subViewEqual1.addView(generateFeedImageView(context,
//                            new LayoutParams(width / 3, width / 3),
//                            mediaArrays.get(i), 2, i));
//                }
//                addView(subViewEqual1);
//                return;
//
//
//            case THREE_HORIZONTAL:
//                LayoutParams params3 = Utility.getLayoutParams(1, width);
//                setLayoutParams(params3);
//                setOrientation(LinearLayout.VERTICAL);
//                addView(generateFeedImageView(context, new LayoutParams(width, width / 2), mediaArrays.get(0), 2, 0));
//                LayoutParams layoutParams = new LayoutParams(width, width / 2);
//                LinearLayout subView = generateLinearLayout(context, layoutParams, LinearLayout.HORIZONTAL, 2);
//                for (int i = 1; i <= 2; i++) {
//                    subView.addView(generateFeedImageView(context,
//                            new LayoutParams(width / 2, width / 2),
//                            mediaArrays.get(i), 2, i));
//                }
//                addView(subView);
//
//
//                return;
//
//            case THREE_VERTICAL:
//
//                LayoutParams params4 = Utility.getLayoutParams(1, width);
//                setLayoutParams(params4);
//                setOrientation(LinearLayout.HORIZONTAL);
//                addView(generateFeedImageView(context, new LayoutParams(width / 2, width), mediaArrays.get(0), 2, 0));
//                LayoutParams layoutParams1 = new LayoutParams(width / 2, width);
//                LinearLayout subView1 = generateLinearLayout(context, layoutParams1, LinearLayout.VERTICAL, 2);
//                subView1.setOrientation(LinearLayout.VERTICAL);
//                for (int i = 1; i <= 2; i++) {
//                    subView1.addView(generateFeedImageView(context,
//                            new LayoutParams(width / 2, width / 2),
//                            mediaArrays.get(i), 2, i));
//                }
//                addView(subView1);
//                return;
//
//
//            case FOUR_EQUAL:
//                LayoutParams equal4 = Utility.getLayoutParams(1, width);
//                setLayoutParams(equal4);
//                setOrientation(VERTICAL);
//                int counter = 0;
//                for (int i = 0; i <= 1; i++) {
//                    LayoutParams layoutP = new LayoutParams(width, width / 2);
//                    LinearLayout subViewEqual = generateLinearLayout(context, layoutP, LinearLayout.VERTICAL, 2);
//                    subViewEqual.setOrientation(LinearLayout.HORIZONTAL);
//                    for (int j = 0; j <= 1; j++) {
//
//                        subViewEqual.addView(generateFeedImageView(context,
//                                new LayoutParams(width / 2, width / 2),
//                                mediaArrays.get(counter), 2, counter));
//
//                        counter++;
//                    }
//                    addView(subViewEqual);
//                }
//                return;
//            case FOUR_HORIZONTAL:
//
//                LayoutParams params5 = Utility.getLayoutParams(1, width);
//                setLayoutParams(params5);
//                setOrientation(LinearLayout.VERTICAL);
//
//                addView(generateFeedImageView(context, new LayoutParams(width, width * 2 / 3), mediaArrays.get(0), 2, 0));
//
//                LayoutParams layoutParams2 = new LayoutParams(width, width / 3);
//                LinearLayout subView3 = generateLinearLayout(context, layoutParams2, LinearLayout.HORIZONTAL, 2);
//                for (int i = 1; i <= 3; i++) {
//                    subView3.addView(generateFeedImageView(context,
//                            new LayoutParams(width / 3, width / 3),
//                            mediaArrays.get(i), 2, i));
//                }
//                addView(subView3);
//                break;
//
//            case FOUR_VERTICAL:
//
//                LayoutParams params6 = Utility.getLayoutParams(1, width);
//                setLayoutParams(params6);
//                setOrientation(LinearLayout.HORIZONTAL);
//                addView(generateFeedImageView(context, new LayoutParams(width * 2 / 3, width), mediaArrays.get(0), 2, 0));
//                LayoutParams layoutParams3 = new LayoutParams(width / 3, width);
//                LinearLayout subView4 = generateLinearLayout(context, layoutParams3, LinearLayout.VERTICAL, 2);
//                subView4.setOrientation(LinearLayout.VERTICAL);
//                for (int i = 1; i <= 3; i++) {
//                    subView4.addView(generateFeedImageView(context,
//                            new LayoutParams(width / 3, width / 3),
//                            mediaArrays.get(i), 2, i));
//                }
//                addView(subView4);
//
//                return;
//
//
//            case FIVE_EQUAL:
//
//                LayoutParams paramsEqual5 = new LayoutParams(width, width * 5 / 6);
//                setLayoutParams(paramsEqual5);
//                setOrientation(LinearLayout.VERTICAL);
//
//                LayoutParams layoutEqual5 = new LayoutParams(width, width * 1 / 2);
//                LinearLayout subViewEqualTop5 = generateLinearLayout(context, layoutEqual5, LinearLayout.VERTICAL, 2);
//                subViewEqualTop5.setOrientation(LinearLayout.HORIZONTAL);
//                for (int j = 0; j <= 1; j++) {
//
//                    subViewEqualTop5.addView(generateFeedImageView(context,
//                            new LayoutParams(width / 2, width / 2),
//                            mediaArrays.get(j), 2, j));
//
//                }
//                addView(subViewEqualTop5);
//
//                LayoutParams layoutParamsEqual5 = new LayoutParams(width, width / 3);
//                LinearLayout subViewEqual5 = generateLinearLayout(context, layoutParamsEqual5, LinearLayout.HORIZONTAL, 2);
//                for (int i = 2; i <= 4; i++) {
//                    subViewEqual5.addView(generateFeedImageView(context,
//                            new LayoutParams(width / 3, width / 3),
//                            mediaArrays.get(i), 2, i));
//                }
//                addView(subViewEqual5);
//                break;
//
//            case MORE_EQUAL:
//                LayoutParams paramsEqualMore5 = new LayoutParams(width, width * 5 / 6);
//                setLayoutParams(paramsEqualMore5);
//                setOrientation(LinearLayout.VERTICAL);
//
//                LayoutParams layoutEqualMore5 = new LayoutParams(width, width * 1 / 2);
//                LinearLayout subViewEqualTopMore5 = generateLinearLayout(context, layoutEqualMore5, LinearLayout.VERTICAL, 2);
//                subViewEqualTopMore5.setOrientation(LinearLayout.HORIZONTAL);
//                for (int j = 0; j <= 1; j++) {
//
//                    subViewEqualTopMore5.addView(generateFeedImageView(context,
//                            new LayoutParams(width / 2, width / 2),
//                            mediaArrays.get(j), 2, j));
//
//                }
//                addView(subViewEqualTopMore5);
//
//                LayoutParams layoutParamsEqualMore5 = new LayoutParams(width, width / 3);
//                LinearLayout subViewEqualMore5 = generateLinearLayout(context, layoutParamsEqualMore5, LinearLayout.HORIZONTAL, 2);
//                for (int i = 2; i <= 4; i++) {
//                    if (i == 4) {
//                        RelativeLayout relativeLayoutMore = generateRelativeLayout(context,
//                                new LayoutParams(width / 3, width / 3), 2);
//
//                        relativeLayoutMore.addView(generateFeedImageView(context,
//                                new LayoutParams(width / 3, width / 3),
//                                mediaArrays.get(i), 0, i));
//
//                        relativeLayoutMore.addView(generateTextView(context,
//                                new LayoutParams(width / 3, width / 3)
//                                , 0, "" + "+" + (mediaArrays.size() - 5), i));
//
//                        subViewEqualMore5.addView(relativeLayoutMore);
//                        break;
//                    } else {
//                        subViewEqualMore5.addView(generateFeedImageView(context,
//                                new LayoutParams(width / 3, width / 3),
//                                mediaArrays.get(i), 2, i));
//                    }
//                }
//                addView(subViewEqualMore5);
//                return;
//
//            case MORE_HORIZONTAL:
//
//                LayoutParams params7 = Utility.getLayoutParams(1, width);
//                setLayoutParams(params7);
//                setOrientation(LinearLayout.VERTICAL);
//
//                addView(generateFeedImageView(context, new LayoutParams(width, width * 2 / 3), mediaArrays.get(0), 2, 0));
//
//                LayoutParams layoutParams4 = new LayoutParams(width, width / 3);
//                LinearLayout subView5 = generateLinearLayout(context, layoutParams4, LinearLayout.HORIZONTAL, 2);
//                for (int i = 1; i <= 3; i++) {
//                    if (i == 3) {
//                        RelativeLayout relativeLayout = generateRelativeLayout(context,
//                                new LayoutParams(width / 3, width / 3), 2);
//
//                        relativeLayout.addView(generateFeedImageView(context,
//                                new LayoutParams(width / 3, width / 3),
//                                mediaArrays.get(i), 0, i));
//
//                        relativeLayout.addView(generateTextView(context,
//                                new LayoutParams(width / 3, width / 3)
//                                , 0, "" + "+" + (mediaArrays.size() - 4), i));
//
//                        subView5.addView(relativeLayout);
//                        break;
//                    } else {
//                        subView5.addView(generateFeedImageView(context,
//                                new LayoutParams(width / 3, width / 3),
//                                mediaArrays.get(i), 2, i));
//                    }
//                }
//                addView(subView5);
//                return;
//
//            case MORE_VERTICAL:
//
//                LayoutParams params8 = Utility.getLayoutParams(1, width);
//                setLayoutParams(params8);
//                setOrientation(LinearLayout.HORIZONTAL);
//                addView(generateFeedImageView(context, new LayoutParams(width * 2 / 3, width), mediaArrays.get(0), 2, 0));
//                LayoutParams layoutParams5 = new LayoutParams(width / 3, width);
//                LinearLayout subView6 = generateLinearLayout(context, layoutParams5, LinearLayout.VERTICAL, 2);
//                for (int i = 1; i <= 3; i++) {
//
//                    if (i == 3) {
//                        RelativeLayout relativeLayout1 = generateRelativeLayout(context,
//                                new LayoutParams(width / 3, width / 3), 2);
//
//                        relativeLayout1.addView(generateFeedImageView(context,
//                                new LayoutParams(width / 3, width / 3),
//                                mediaArrays.get(i), 0, i));
//
//                        relativeLayout1.addView(generateTextView(context,
//                                new LayoutParams(width / 3, width / 3)
//                                , 0, "" + "+" + (mediaArrays.size() - 4), i));
//
//                        subView6.addView(relativeLayout1);
//                    } else {
//
//                        subView6.addView(generateFeedImageView(context,
//                                new LayoutParams(width / 3, width / 3),
//                                mediaArrays.get(i), 2, i));
//                    }
//                }
//
//                addView(subView6);
//                return;
//        }
//    }
//
//    public LinearLayout generateLinearLayout(Context context, LayoutParams params, int orientation, int padding) {
//        final LinearLayout linearLayout = new LinearLayout(getContext());
//        linearLayout.setPadding(padding, padding, padding, padding);
//        linearLayout.setLayoutParams(params);
//        linearLayout.setOrientation(orientation);
//        return linearLayout;
//    }
//
//    public RelativeLayout generateRelativeLayout(Context context, LayoutParams params, int padding) {
//        final RelativeLayout relativeLayout = new RelativeLayout(getContext());
//        relativeLayout.setPadding(padding, padding, padding, padding);
//        relativeLayout.setLayoutParams(params);
//        return relativeLayout;
//    }
//
//    public TextView generateTextView(Context context, LayoutParams params, int padding, String text, final int position) {
//        final TextView textView = new TextView(getContext());
//        textView.setPadding(padding, padding, padding, padding);
//        textView.setLayoutParams(params);
//        textView.setGravity(Gravity.CENTER);
//        textView.setBackgroundColor(Color.parseColor("#77000000"));
//        textView.setTextColor(Color.parseColor("#FFFFFF"));
//        textView.setTextSize(25);
//        textView.setPadding(padding, padding, padding, padding);
//        textView.setText(text);
//        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
//        textView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (collageItemClickListener != null) {
//                    collageItemClickListener.onClick(position);
//                }
//            }
//        });
//        return textView;
//    }
//
//
//    public SimpleDraweeView generateImageView(Context context, LayoutParams params, Media media, int padding, final int position) {
//        final SimpleDraweeView simpleDraweeView = new SimpleDraweeView(getContext());
//
//        GenericDraweeHierarchy hierarchy =
//                GenericDraweeHierarchyBuilder.newInstance(getResources())
//                        .setFailureImage(R.drawable.appiconandroid)
//                        .setActualImageScaleType(ScalingUtils.ScaleType.FOCUS_CROP).build();
////
//        simpleDraweeView.setHierarchy(hierarchy);
//        simpleDraweeView.setPadding(padding, padding, padding, padding);
//        simpleDraweeView.setLayoutParams(params);
//        if (media.url != null && media.url.length() > 0)
//            simpleDraweeView.setImageURI(Uri.parse(media.url));
//        else {
//            simpleDraweeView.setImageResource(R.drawable.logo_with_text);
//        }
//        simpleDraweeView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (collageItemClickListener != null) {
//                    collageItemClickListener.onClick(position);
//                }
//            }
//        });
//
//
//        return simpleDraweeView;
//    }
//
//    public SimpleDraweeView generateFeedImageView(Context context, LayoutParams params, FeedMediaArray media, int padding, final int position) {
//        final SimpleDraweeView simpleDraweeView = new SimpleDraweeView(getContext());
//
//        GenericDraweeHierarchy hierarchy =
//                GenericDraweeHierarchyBuilder.newInstance(getResources())
//                        .setFailureImage(R.drawable.appiconandroid)
//                        .setActualImageScaleType(ScalingUtils.ScaleType.FOCUS_CROP).build();
////
//        simpleDraweeView.setHierarchy(hierarchy);
//        simpleDraweeView.setPadding(padding, padding, padding, padding);
//        simpleDraweeView.setLayoutParams(params);
//        if (media.getMediaUrl() != null && media.getMediaUrl().length() > 0)
//            if (media.getMediaType().equals("video")) {
//                simpleDraweeView.setImageURI(Uri.parse(media.getMediaUrl()));
//            } else {
//                simpleDraweeView.setImageURI(Uri.parse(ApiClient.BASE_URL + media.getMediaUrl()));
//            }
//        else {
//            simpleDraweeView.setImageResource(R.drawable.logo_with_text);
//        }
//        simpleDraweeView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (collageItemClickListener != null) {
//                    collageItemClickListener.onClick(position);
//                }
//            }
//        });
//
//
//        return simpleDraweeView;
//    }
//
//    public void setCollageItemClickListener(CollageItemClickListener collageItemClickListener) {
//        this.collageItemClickListener = collageItemClickListener;
//    }
//
//
//    public interface CollageItemClickListener {
//        public void onClick(int position);
//    }
//}
