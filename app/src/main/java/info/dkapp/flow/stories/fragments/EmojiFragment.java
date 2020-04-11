package info.dkapp.flow.stories.fragments;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import info.dkapp.flow.bottommenu.activity.HelperActivity;
import info.dkapp.flow.bottommenu.interfaces.fragments.IFragment;

import info.dkapp.flow.R;
import info.dkapp.flow.retrofitcall.ApiClient;
import info.dkapp.flow.retrofitcall.ApiInterface;
import info.dkapp.flow.retrofitcall.ApiRequestModel;
import info.dkapp.flow.retrofitcall.ApiResponseModel;
import info.dkapp.flow.retrofitcall.EnumConstants;
import info.dkapp.flow.retrofitcall.IApiListener;
import info.dkapp.flow.userflow.Util.Common;
import retrofit2.Call;

/**
 * Created by Ahmed Adel on 5/5/17.
 */

public class EmojiFragment extends Fragment implements EmojiAdapter.OnEmojiClickListener, IFragment{

    private ArrayList<String> emojiIds;
    private HelperActivity helperActivity;
    RecyclerView emojiRecyclerView;
    private ArrayList<Bitmap> stickerBitmaps;
    RecyclerView imageRecyclerView;
    int position;

    public static EmojiFragment newInstance( int position) {
        EmojiFragment fragment = new EmojiFragment();
        Bundle args = new Bundle();
        args.putInt("position",position);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helperActivity = (HelperActivity) getActivity();
    }

    private void getImage() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.GET(ApiClient.STORY_IMAGES );
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, ApiClient.STORY_IMAGES, false, new IApiListener() {
            @Override
            public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {

                try {
                    Type type = new TypeToken<ArrayList<String>>() {
                    }.getType();
                    ArrayList<String> imageList = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                    if (imageList != null) {

                       /* for (int i = 0; i < imageList.size(); i++){
                            try {
                                URL url = new URL(imageList.get(i));
                                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                connection.setDoInput(true);
                                connection.connect();
                                InputStream input = connection.getInputStream();
                                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                               stickerBitmaps.add(myBitmap);
                            } catch (IOException e) {
                                // Log exception

                            }
                        }*/
                        ImageAdapter imageAdapter = new ImageAdapter(activity(), imageList);
                        imageAdapter.setOnImageClickListener(new ImageAdapter.OnImageClickListener() {
                            @Override
                            public void onImageClickListener(String image) {
                                try {
                                    URL url = new URL(image);
                                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                                    connection.setDoInput(true);
                                    connection.connect();
                                    InputStream input = connection.getInputStream();
                                    Bitmap myBitmap = BitmapFactory.decodeStream(input);
                                    if (MultipleImageCreateStoryFragment.getInstance() != null){
                                        MultipleImageCreateStoryFragment.getInstance().addImage(myBitmap,position);
                                    } else if (CreateStoriesFragment.getInstance() !=null) {
                                        CreateStoriesFragment.getInstance().addImage(myBitmap);
                                    }
                                } catch (IOException e) {
                                    // Log exception

                                }

                            }
                        });
                        imageRecyclerView.setAdapter(imageAdapter);
                        imageRecyclerView.setNestedScrollingEnabled(false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }



              /*  stickerBitmaps = new ArrayList<>();
                for (int i = 0; i < images.length(); i++) {
                    stickerBitmaps.add(decodeSampledBitmapFromResource(activity().getResources(), images.getResourceId(i, -1), 120, 120));
                }*/
            }

            @Override
            public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {

            }
        }, false));

    }

    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
    public Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_photo_edit_emoji, container, false);

        if (getArguments() != null) {
            position = getArguments().getInt("position");
        }
        //getImage();
        emojiRecyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_main_photo_edit_emoji_rv);
        imageRecyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_main_photo_edit_image_rv);
        emojiRecyclerView.setHasFixedSize(true);
        emojiRecyclerView.setLayoutManager(new GridLayoutManager(activity(), 4));
        String[] emojis = helperActivity.getResources().getStringArray(R.array.photo_editor_emoji);
        emojiIds = new ArrayList<>();
        Collections.addAll(emojiIds, emojis);
        EmojiAdapter adapter = new EmojiAdapter(activity(), emojiIds);
        adapter.setOnEmojiClickListener(this);
        emojiRecyclerView.setAdapter(adapter);


        imageRecyclerView.setLayoutManager(new GridLayoutManager(activity(), 3));



        emojiRecyclerView.setNestedScrollingEnabled(false);
        return rootView;
    }

    @Override
    public void onEmojiClickListener(String emojiName) {
        if (MultipleImageCreateStoryFragment.getInstance() != null){
            MultipleImageCreateStoryFragment.getInstance().addEmoji(emojiName,position);
        } else if (CreateStoriesFragment.getInstance() !=null) {
            CreateStoriesFragment.getInstance().addEmoji(emojiName);
        }
    }

    @Override
    public void changeTitle(String title) {

    }

    @Override
    public void showSnackBar(String snackBarText, int type) {

    }

    @Override
    public Activity activity() {
        return getActivity();
    }


}
