package info.dkapp.flow.stories.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import info.dkapp.flow.appmanagers.feed.models.GalleryFile;
import info.dkapp.flow.bottommenu.interfaces.fragments.IFragment;

import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.constants.MediaType;
import info.dkapp.flow.ipoll.fragments.feeds.FeedsFragment;
import info.dkapp.flow.retrofitcall.ApiClient;
import info.dkapp.flow.retrofitcall.ApiInterface;
import info.dkapp.flow.retrofitcall.ApiRequestModel;
import info.dkapp.flow.retrofitcall.ApiResponseModel;
import info.dkapp.flow.retrofitcall.EnumConstants;
import info.dkapp.flow.retrofitcall.IApiListener;
import info.dkapp.flow.retrofitcall.SessionManager;
import info.dkapp.flow.stories.models.CreateStoreData;
import info.dkapp.flow.stories.models.CreateStoreMedia;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.utils.Utility;
import info.dkapp.flow.utils.zoommulti.ImageMatrixTouchHandler;
import info.dkapp.flow.imagepicker.model.Image;
import info.dkapp.flow.imagepicker.ui.imagepicker.ImagePicker;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class MultipleImageCreateStoryFragment extends Fragment implements IFragment, IMultipleOnclick, IApiListener {

    private ViewPager2 viewPager;
    RecyclerView recyclerMultipleImage;
    ImageView iVAddMedia;
    private ViewPagerAdapter mViewPagerAdapter;
    public ArrayList<Image> gallerySelectedList;
    IMultipleOnclick iMultipleOnclick;
    MultipleImageAdapter multipleImageAdapter;
    boolean addImage = false;
    String emojiName;
    Bitmap image;
    int itemDeletePosition = 0;
    IApiListener apiListener;
    TextView tvShare;
    boolean isVideoSingleStory = false;
    MyAdapter MyAdapter;
    List<Fragment> listFragment;
    private static MultipleImageCreateStoryFragment instance;
    MultipleStorieAdapter multipleStorieAdapter;
    ArrayList<String> imageList;

    public static MultipleImageCreateStoryFragment getInstance() {
        return instance;
    }

    public MultipleImageCreateStoryFragment() {
        // Required empty public constructor
    }

    public static MultipleImageCreateStoryFragment newInstance(List<GalleryFile> gallerySelectedList) {
        MultipleImageCreateStoryFragment fragment = new MultipleImageCreateStoryFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("GalleryFileList", (ArrayList<? extends Parcelable>) gallerySelectedList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        iMultipleOnclick = this;
        instance = this;
        apiListener = this;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_muliple_image_create_story, container, false);
        if (getArguments() != null) {
            gallerySelectedList = getArguments().getParcelableArrayList("GalleryFileList");
        }

        getImage();
        viewPager = root.findViewById(R.id.view_pager);
        tvShare = root.findViewById(R.id.tvShare);
        iVAddMedia = root.findViewById(R.id.iVAddMedia);
        recyclerMultipleImage = root.findViewById(R.id.recyclerMultipleImage);
        if (gallerySelectedList.size() == 1) {
            if (gallerySelectedList.get(0).mediaType == MediaType.VIDEO) {
                iVAddMedia.setVisibility(View.GONE);
                tvShare.setVisibility(View.GONE);
                recyclerMultipleImage.setVisibility(View.GONE);
                isVideoSingleStory = true;
            }
        } else {
            isVideoSingleStory = false;
            tvShare.setVisibility(View.VISIBLE);
            iVAddMedia.setVisibility(View.VISIBLE);
            recyclerMultipleImage.setVisibility(View.VISIBLE);
        }


        /*viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Logger.d("ViewPager", "onPageScrolled");
                Fragment page = getChildFragmentManager().getFragments().get(viewPager.getCurrentItem());
                ((CreateStoriesFragment) page).hideDeleteIcon();
            }

            @Override
            public void onPageSelected(int position) {
                Logger.d("ViewPager", "onPageSelected");
                Fragment page = getChildFragmentManager().getFragments().get(viewPager.getCurrentItem());
                ((CreateStoriesFragment) page).hideDeleteIcon();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Logger.d("ViewPager", "onPageScrollStateChanged");
                Fragment page = getChildFragmentManager().getFragments().get(viewPager.getCurrentItem());
                ((CreateStoriesFragment) page).hideDeleteIcon();
            }
        });*/
        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAllData();
            }
        });
        iVAddMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Image> image_ = new ArrayList<>();
                int remaingItem = 10 - gallerySelectedList.size();
                ImagePicker.with(activity())
                        .setFolderMode(true)
                        .setCameraOnly(false)
                        .setFolderTitle("Album")
                        .setMultipleMode(true)
                        .setGif(false)
                        .setSelectedImages(image_)
                        .setMaxSize(remaingItem)
                        .setBackgroundColor("#212121")
                        .setAlwaysShowDoneButton(false)
                        .setRequestCode(Utility.generateRequestCodes().get("ADD_MEDIA_TO_STORY"))
                        .setKeepScreenOn(true)
                        .setIsFeed(false)
                        .setMultipleImages(true)
                        .start();
            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Utility.generateRequestCodes().get("ADD_MEDIA_TO_STORY")) {
            if (data != null && resultCode == Activity.RESULT_OK) {

            }
        }
        else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            //iVCrop.setEnabled(true);
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                multipleStorieAdapter.updateImageCrop(resultUri);
                //holder.iVImageView.setImageURI(resultUri);
                //refreshImage(resultUri);
                ImageMatrixTouchHandler imageMatrixTouchHandler = new ImageMatrixTouchHandler(activity());
                /*iVImageView.setOnTouchListener(imageMatrixTouchHandler);
                iVImageView.setImageURI(resultUri);*/


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
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

    public void addEmoji(String emojiName_, int position) {
        viewPager.setCurrentItem(multipleStorieAdapter.getAdapterPosition());
        multipleStorieAdapter.addEmoji(emojiName_);
        multipleStorieAdapter.hideEmojiBottomSheet();
        //  viewPager.setCurrentItem(position);
        /*Fragment page = ((FragmentActivity) activity()).getSupportFragmentManager().findFragmentById(position);
        if (page != null) {
            ((CreateStoriesFragment) page).addEmoji(emojiName_);
            ((CreateStoriesFragment) page).hideEmojiBottomSheet();
        }*/
    }

    public void addImage(Bitmap image_, int position) {
        //viewPager.setCurrentItem(position);
       /* Fragment page = ((FragmentActivity) activity()).getSupportFragmentManager().findFragmentById(position);
        if (page != null) {
            ((CreateStoriesFragment) page).addImage(image_);
            ((CreateStoriesFragment) page).hideEmojiBottomSheet();
        }*/
        viewPager.setCurrentItem(multipleStorieAdapter.getAdapterPosition());
        multipleStorieAdapter.addImage(image_);
        multipleStorieAdapter.hideEmojiBottomSheet();
    }

    public int getViewPagerCurrentPosition() {
        return viewPager.getCurrentItem();
    }

    @Override
    public void IMultipleOnclick(int position) {
        viewPager.setCurrentItem(position);
    }

    @Override
    public void IMultipleOnclickDelete(int position) {

        //gallerySelectedList.remove(position);
//        mViewPagerAdapter.removeItem(position);
        //viewPager.setAdapter(mViewPagerAdapter);
//        multipleImageAdapter.notifyDataSetChanged();
        if (gallerySelectedList.size() == 1) {
            activity().finish();
        } else {
            gallerySelectedList.remove(position);
            multipleStorieAdapter.notifyItemRemoved(position);
            multipleStorieAdapter.removeViewHolder(position);
            recyclerMultipleImage.setAdapter(multipleImageAdapter = new MultipleImageAdapter(activity(), gallerySelectedList, iMultipleOnclick));
            viewPager.setOffscreenPageLimit(gallerySelectedList.size());
            // mViewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), activity());
            // viewPager.setAdapter(mViewPagerAdapter);
            // viewPager.setOffscreenPageLimit(gallerySelectedList.size());
        }
    }


    @Override
    public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
        if (condition.equals(ApiClient.CREATE_STORE)) {
            try {
                tvShare.setEnabled(true);
                Common.getInstance().cusToast(activity(), apiResponseModel.message);
                activity().finish();
                if (FeedsFragment.getInstance() != null) {
                    FeedsFragment.getInstance().fetchStories();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {

    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {
        int itemPosition;
        ArrayList<Fragment> oPooledFragments;
        FragmentManager fragmentManager;
        LayoutInflater inflater;

        public ViewPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.fragmentManager = fm;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


        @Override
        public Fragment getItem(int position) {
            itemPosition = position;
            return CreateStoriesFragment.newInstance(gallerySelectedList.get(position), true, position, isVideoSingleStory);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // TODO Auto-generated method stub
            super.destroyItem(container, position, object);
        }


        public void removeItem(int position) {
            destroyItem(null, position, fragmentManager.getFragments().get(position));
            gallerySelectedList.remove(position);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return gallerySelectedList.size();
        }

    }

    public void getAllData() {
        ArrayList<MultipartBody.Part> partArrayList = new ArrayList<>();
        MultipartBody.Part[] mediaMultiParts = new MultipartBody.Part[partArrayList.size()];
        CreateStoreData createStoreData;
        CreateStoreData createStoreData_ = null;
        ArrayList<CreateStoreMedia> createStoreMediaList = new ArrayList<>();
        for (int i = 0; i < gallerySelectedList.size(); i++) {
            // Fragment page = getChildFragmentManager().getFragments().get(i);
            createStoreData = multipleStorieAdapter.sharemulitpleImage(i);
            multipleStorieAdapter.hideDeleteIcon();
            createStoreMediaList.addAll(createStoreData.media);
            partArrayList.addAll(createStoreData.partArrayList);
            if (partArrayList.size() > 0) {
                mediaMultiParts = new MultipartBody.Part[partArrayList.size()];
                for (int index = 0; index < partArrayList.size(); index++) {
                    mediaMultiParts[index] = partArrayList.get(index);
                }
            }
            createStoreData_ = new CreateStoreData(SessionManager.userLogin.userId, "", createStoreMediaList);
        }
        shareStore(createStoreData_, mediaMultiParts);
    }

    private void shareStore(CreateStoreData createStoreData, MultipartBody.Part[] mediaMultiParts) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.createStore(RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"), "" + new Gson().toJson(createStoreData)), mediaMultiParts);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, ApiClient.CREATE_STORE, true, apiListener, true));
    }

    public void mediaUpdate(ArrayList<Image> gallerySelectedListdata) {
        int size = gallerySelectedList.size();
        gallerySelectedList.addAll(gallerySelectedListdata);
        multipleStorieAdapter.notifyItemRangeChanged(size, gallerySelectedList.size());
        recyclerMultipleImage.setAdapter(multipleImageAdapter = new MultipleImageAdapter(activity(), gallerySelectedList, iMultipleOnclick));
        viewPager.setOffscreenPageLimit(gallerySelectedList.size());
        /*MyAdapter.isUpdate = true;
        progressBar.setVisibility(View.VISIBLE);
        progressBar.postDelayed(new Runnable() {
            public void run() {
                if (progressBar.getVisibility() == View.VISIBLE)
                    progressBar.setVisibility(View.GONE);
            }
        }, 2000);
        int size = gallerySelectedList.size();
        List<Fragment> newList = new ArrayList<>();
        gallerySelectedList.addAll(gallerySelectedListdata);
        List<Fragment> listFragmentTemp = new ArrayList<>();
        for (int i = 0; i < gallerySelectedListdata.size(); i++) {
            listFragmentTemp.add(CreateStoriesFragment.newInstance(gallerySelectedListdata.get(i), true, i+listFragment.size(), isVideoSingleStory));
        }
        listFragment.addAll(listFragmentTemp);
//        MyAdapter.notifyItemRangeChanged(size, gallerySelectedList.size());
        MyAdapter.loadmore(listFragment);
//        MyAdapter.notifyDataSetChanged();
        recyclerMultipleImage.setAdapter(multipleImageAdapter = new MultipleImageAdapter(activity(), gallerySelectedList, iMultipleOnclick));

        viewPager.setOffscreenPageLimit(gallerySelectedList.size());*/
       /*
       gallerySelectedList.addAll(gallerySelectedListdata);
       MyAdapter = new MyAdapter(activity(), listFragment);
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        viewPager.setAdapter(MyAdapter);
        viewPager.setOffscreenPageLimit(gallerySelectedList.size());
        recyclerMultipleImage.setAdapter(multipleImageAdapter = new MultipleImageAdapter(activity(), gallerySelectedList, iMultipleOnclick));
        viewPager.setCurrentItem(0);*/



        /*int size = gallerySelectedList.size();
        for (int i = 0; i < gallerySelectedListdata.size(); i++) {
            for (int j = 0; j < gallerySelectedList.size(); j++) {
                if ((gallerySelectedList.get(j).id) != gallerySelectedListdata.get(i).id) {
                    gallerySelectedList.add(gallerySelectedListdata.get(i));
                }
            }
        }
        List<Fragment> newList = new ArrayList<>();
        for (int i = size; i < gallerySelectedList.size(); i++) {
            newList.add(CreateStoriesFragment.newInstance(gallerySelectedList.get(i), true, i, isVideoSingleStory));
        }
        int newSize = gallerySelectedList.size();
        MyAdapter.notifyItemRangeChanged(size, newSize);
       // MyAdapter.addItems(newList);
        multipleImageAdapter.notifyItemRangeChanged(size, newSize);*/
    }

    public void updateRecyclerview(int position, Uri uri) {
        gallerySelectedList.get(position).uri = uri;
        multipleImageAdapter.notifyDataSetChanged();
    }

    public void deleteItem(int itemPosition) {
        itemDeletePosition = itemPosition;
        gallerySelectedList.remove(itemPosition);
        mViewPagerAdapter.notifyDataSetChanged();
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
        public FragmentManager mFragmentManager;
        private Context context;
        public int frameId; //Unique identifier for ValueFrame
        //List<Fragment> fragmentsList;
        int fragmentId = 0;
        boolean isUpdate = false;

        public MyAdapter(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.row_item_viewpager2, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            ((FragmentActivity) context).getSupportFragmentManager().
                    beginTransaction().
                    add(frameId, listFragment.get(position)).commit();

        }

        public void loadmore(List<Fragment> fragmentList) {
            int positionStart = getItemCount() + 1;
            notifyItemRangeInserted(positionStart, fragmentList.size());
//            notifyDataSetChanged();
        }


        @Override
        public int getItemCount() {
            return listFragment.size();
        }

        public void removeItem(int position) {
            listFragment.remove(position);
            notifyItemRemoved(position);
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                frameId = itemView.generateViewId();
                itemView.findViewById(R.id.imgBanner).setId(frameId);
            }
        }

    }

    public void disableSwipe(boolean isDisable) {
        viewPager.setUserInputEnabled(isDisable);
    }

    private void getImage() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.GET(ApiClient.STORY_IMAGES);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, ApiClient.STORY_IMAGES, true, new IApiListener() {
            @Override
            public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {

                try {
                    Type type = new TypeToken<ArrayList<String>>() {
                    }.getType();

                    imageList = new Gson().fromJson(new Gson().toJson(apiResponseModel.data), type);
                    recyclerMultipleImage.setLayoutManager(new LinearLayoutManager(activity(), LinearLayoutManager.HORIZONTAL, false));
                    recyclerMultipleImage.setAdapter(multipleImageAdapter = new MultipleImageAdapter(activity(), gallerySelectedList, iMultipleOnclick));
                    mViewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), activity());
        /*viewPager.setAdapter(mViewPagerAdapter);
        viewPager.setSaveFromParentEnabled(true);*/

                    multipleStorieAdapter = new MultipleStorieAdapter(activity(), gallerySelectedList, true, isVideoSingleStory, imageList);
//        MyAdapter = new MyAdapter(activity());
                    viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
                    viewPager.setAdapter(multipleStorieAdapter);
                    viewPager.setOffscreenPageLimit(gallerySelectedList.size());
                    viewPager.setCurrentItem(0);

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

    public void cropDetails(Bitmap source , File file){
        Intent intent = null;
        try {
            intent = CropImage.activity(getImageUri(activity(), source, file))
                    .getIntent(activity());
        } catch (IOException e) {
            e.printStackTrace();
        }
        startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);

    }
    public Uri getImageUri(Context inContext, Bitmap inImage, File file) throws IOException {
        /*Log.e("Check Image", "came");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(activity().getContentResolver(), inImage, "temp" + ".png", "drawing");
      *//*  File folder = new File(folderPath);
        if (!folder.exists()) {
            File wallpaperDirectory = new File(folderPath);
            wallpaperDirectory.mkdirs();
        }*//*
        imageUri = Uri.parse(path);
        return imageUri;*/
        /*Uri uri = null;
        Common.getInstance().showProgressDialog(inContext);
        if (file != null) {
            FileOutputStream fout;
            try {
                fout = new FileOutputStream(file);
                inImage.compress(Bitmap.CompressFormat.PNG, 70, fout);
                fout.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Common.getInstance().closeProgressDialog();
           uri = Uri.fromFile(file);

        }
        return uri;*/
        File tempDir = Environment.getExternalStorageDirectory();
        tempDir = new File(tempDir.getAbsolutePath() + "/.temp/");
        tempDir.mkdir();
        File tempFile = File.createTempFile("tempppp", ".jpg", tempDir);
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        byte[] bitmapData = bytes.toByteArray();

        //write the bytes in file
        FileOutputStream fos = new FileOutputStream(tempFile);
        fos.write(bitmapData);
        fos.flush();
        fos.close();
        return Uri.fromFile(tempFile);
    }
}
