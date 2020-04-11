package info.sumantv.flow.stories.fragments;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.TypedArray;
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

import java.util.ArrayList;

import info.sumantv.flow.bottommenu.interfaces.fragments.IFragment;

import info.sumantv.flow.R;

/**
 * Created by Ahmed Adel on 5/4/17.
 */

public class ImageFragment extends Fragment implements ImageAdapter.OnImageClickListener , IFragment {

    private ArrayList<String > stickerBitmaps;
    RecyclerView imageRecyclerView;
    int position;

    public static ImageFragment newInstance( int position) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putInt("position",position);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        TypedArray images = getResources().obtainTypedArray(R.array.photo_editor_photos);

        stickerBitmaps = new ArrayList<>();
        for (int i = 0; i < images.length(); i++) {
//            stickerBitmaps.add(decodeSampledBitmapFromResource(activity().getResources(), images.getResourceId(i, -1), 120, 120));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_photo_edit_image, container, false);
        if (getArguments() != null) {
            position = getArguments().getInt("position");
        }
        imageRecyclerView = (RecyclerView) rootView.findViewById(R.id.fragment_main_photo_edit_image_rv);
        imageRecyclerView.setLayoutManager(new GridLayoutManager(activity(), 3));
        ImageAdapter adapter = new ImageAdapter(activity(), stickerBitmaps);
        adapter.setOnImageClickListener(this);
        imageRecyclerView.setAdapter(adapter);

        return rootView;
    }

    public Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
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

    @Override
    public void onImageClickListener(String  image) {
       /* if (MultipleImageCreateStoryFragment.getInstance() != null){
            MultipleImageCreateStoryFragment.getInstance().addImage(image,position);
        } else if (CreateStoriesFragment.getInstance() !=null) {
            CreateStoriesFragment.getInstance().addImage(image);
        }*/
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