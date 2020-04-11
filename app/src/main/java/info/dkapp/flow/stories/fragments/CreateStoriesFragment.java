package info.dkapp.flow.stories.fragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.theartofdev.edmodo.cropper.CropImage;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubfilter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
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
import info.dkapp.flow.stories.emojiView.BrushDrawingView;
import info.dkapp.flow.stories.emojiView.OnPhotoEditorSDKListener;
import info.dkapp.flow.stories.emojiView.PhotoEditorSDK;
import info.dkapp.flow.stories.emojiView.ViewType;
import info.dkapp.flow.stories.models.CreateStoreData;
import info.dkapp.flow.stories.models.CreateStoreMedia;
import info.dkapp.flow.userflow.Util.Common;
import info.dkapp.flow.utils.Logger;
import info.dkapp.flow.utils.Utility;
import info.dkapp.flow.utils.videoTrimmer.HgLVideoTrimmer;
import info.dkapp.flow.utils.videoTrimmer.interfaces.OnHgLVideoListener;
import info.dkapp.flow.utils.videoTrimmer.interfaces.OnTrimVideoListener;
import info.dkapp.flow.utils.zoommulti.ImageMatrixTouchHandler;
import info.dkapp.flow.imagepicker.model.Image;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateStoriesFragment extends Fragment implements View.OnClickListener, IFragment, EditImageFragment.EditImageFragmentListener, OnTrimVideoListener, OnHgLVideoListener, IApiListener, OnPhotoEditorSDKListener {

    public Image galleryFile;

    @BindView(R.id.iVCrop)
    ImageView iVCrop;

    @BindView(R.id.iVClose)
    ImageView iVClose;

    @BindView(R.id.iVEmoji)
    ImageView iVEmoji;

    @BindView(R.id.iVfilters)
    ImageView iVfilters;

    @BindView(R.id.iVRortate)
    ImageView iVRortate;

    @BindView(R.id.iVImageView)
    ImageView iVImageView;

    @BindView(R.id.rLImage)
    RelativeLayout rLImage;

    @BindView(R.id.rLVideo)
    RelativeLayout rLVideo;


    @BindView(R.id.timeLine)
    HgLVideoTrimmer mVideoTrimmer;

    @BindView(R.id.tvShare)
    TextView tvShare;

    @BindView(R.id.linerlayoutComment)
    LinearLayout linerlayoutComment;

    /*@BindView(R.id.filterImagelayout)
    LinearLayout filterImagelayout;*/


    @BindView(R.id.etCaption)
    EditText etCaption;

    @BindView(R.id.tabs)
    TabLayout tabLayout;

   /* @BindView(R.id.viewpager)
    ViewPager viewPager;*/

    /*  @BindView(R.id.image_emoji_view_pager)
      ViewPager image_emoji_view_pager;
  */
    @BindView(R.id.drawing_view)
    BrushDrawingView brushDrawingView;

    @BindView(R.id.image_emoji_bottom_sheet)
    RelativeLayout image_emoji_bottom_sheet;

    @BindView(R.id.delete_rl)
    RelativeLayout deleteRelativeLayout;

    @BindView(R.id.iVCloseEmoji)
    ImageView iVCloseEmoji;

   /* @BindView(R.id.iVAddMediaSingleImage)
    ImageView iVAddMediaSingleImage;*/


    /* @BindView(R.id.image_emoji_indicator)
     PageIndicator indicator;
 */
    private PhotoEditorSDK photoEditorSDK;
    private Typeface emojiFont;

    PreviewSlidePagerAdapter adapter;
    Bitmap source;
    Bitmap finalImage;
    Bitmap filteredImage;
    float angle = 0;
    IApiListener apiListener;
    private ProgressDialog mProgressDialog;
    MultipartBody.Part[] mediaMultiParts;
    CreateStoreData createStoreData;
    CreateStoreMedia createStoreMedia;
    MultipartBody.Part bodyPicVideo;
    ArrayList<MultipartBody.Part> partArrayList;
    ArrayList<CreateStoreMedia> createStoreMediaList = new ArrayList<>();
    boolean isVideo = false, isCrop = false, isVideoSingleStory = false;
    FiltersListFragment filtersListFragment;
    EditImageFragment editImageFragment;

    // modified image values
    int brightnessFinal = 0;
    float saturationFinal = 1.0f;
    float contrastFinal = 1.0f;
    Uri imageUri;
    File fileConvert;
    boolean forMultiImages;
    int position;

    public CreateStoriesFragment() {
        // Required empty public constructor
    }

    private static CreateStoriesFragment instance;

    public static CreateStoriesFragment getInstance() {
        return instance;
    }

    public static CreateStoriesFragment newInstance(Image galleryfile, boolean forMultiImages, int position, boolean isVideoSingleStory) {
        CreateStoriesFragment fragment = new CreateStoriesFragment();
        Bundle args = new Bundle();
        args.putParcelable("GalleryFile", galleryfile);
        args.putBoolean("forMultiImages", forMultiImages);
        args.putInt("position", position);
        args.putBoolean("isVideoSingleStory", isVideoSingleStory);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_create_stories, container, false);
        ButterKnife.bind(this, root);
        apiListener = this;
        instance = this;
        if (getArguments() != null) {
            galleryFile = getArguments().getParcelable("GalleryFile");
            forMultiImages = getArguments().getBoolean("forMultiImages");
            position = getArguments().getInt("position");
            isVideoSingleStory = getArguments().getBoolean("isVideoSingleStory");
        }
        tvShare.setOnClickListener(this);
        iVClose.setOnClickListener(this);
        iVEmoji.setOnClickListener(this);
        iVfilters.setOnClickListener(this);
        iVCloseEmoji.setOnClickListener(this);
        iVImageView.setOnClickListener(this);
        //  iVAddMediaSingleImage.setOnClickListener(this);
        if (galleryFile.mediaType == MediaType.IMAGE) {
            isVideo = false;
            rLImage.setVisibility(View.VISIBLE);
            rLVideo.setVisibility(View.GONE);
            iVRortate.setVisibility(View.VISIBLE);
            iVCrop.setVisibility(View.VISIBLE);
            iVEmoji.setVisibility(View.VISIBLE);
            iVImageView.setImageURI(galleryFile.uri);
            iVCrop.setOnClickListener(this);
            iVRortate.setOnClickListener(this);
           /* try {
                source = ((BitmapDrawable) iVImageView.getDrawable()).getBitmap();
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            /*ImageMatrixTouchHandler imageMatrixTouchHandler = new ImageMatrixTouchHandler(activity());
            iVImageView.setOnTouchListener(imageMatrixTouchHandler);*/

        } else {
            isVideo = true;
            rLImage.setVisibility(View.GONE);
            rLVideo.setVisibility(View.VISIBLE);
            iVRortate.setVisibility(View.GONE);
            iVCrop.setVisibility(View.GONE);
            iVEmoji.setVisibility(View.GONE);
            mProgressDialog = new ProgressDialog(activity());
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage(getString(R.string.trimming_progress));
            if (mVideoTrimmer != null) {
                mVideoTrimmer.setOnTrimVideoListener(this);
                mVideoTrimmer.setOnHgLVideoListener(this);
                mVideoTrimmer.setVideoURI(galleryFile.uri);
                mVideoTrimmer.setmTextSize(Long.valueOf(galleryFile.size));
                mVideoTrimmer.setVideoInformationVisibility(true);
                mVideoTrimmer.setMaxDuration(30);
            }
        }
        etCaption.setImeOptions(EditorInfo.IME_ACTION_DONE);
        etCaption.setRawInputType(InputType.TYPE_CLASS_TEXT);
       /* setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);*/
        //filtersListFragment.prepareThumbnail(relativeToBitmap(rLImage));
        fileConvert = createImageFile();
        if (forMultiImages) {
            tvShare.setVisibility(View.GONE);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) etCaption.getLayoutParams();
            params.rightMargin = activity().getResources().getDimensionPixelSize(R.dimen._65sdp);
            etCaption.setLayoutParams(params);
        } else {
            tvShare.setVisibility(View.VISIBLE);
        }
        if (isVideoSingleStory) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) etCaption.getLayoutParams();
            params.rightMargin = activity().getResources().getDimensionPixelSize(R.dimen._5sdp);
            etCaption.setLayoutParams(params);
            tvShare.setVisibility(View.VISIBLE);
        } else {
            tvShare.setVisibility(View.GONE);
        }

        photoEditorSDK = new PhotoEditorSDK.PhotoEditorSDKBuilder(activity())
                .parentView(rLImage) // add parent image view
                .childView(iVImageView) // add the desired image view
                .deleteView(deleteRelativeLayout) // add the deleted view that will appear during the movement of the views
                .brushDrawingView(brushDrawingView) // add the brush drawing view that is responsible for drawing on the image view
                .buildPhotoEditorSDK(); // build photo editor sdk
        photoEditorSDK.setOnPhotoEditorSDKListener(this);

        return root;
    }

   /* private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        // adding filter list fragment
        filtersListFragment = new FiltersListFragment();
        filtersListFragment.setListener(this);

        // adding edit image fragment
        editImageFragment = new EditImageFragment();
        editImageFragment.setListener(this);

        adapter.addFragment(filtersListFragment, getString(R.string.tab_filters));
        adapter.addFragment(editImageFragment, getString(R.string.tab_edit));

        viewPager.setAdapter(adapter);
    }*/

    @Override
    public void onBrightnessChanged(int brightness) {
        brightnessFinal = brightness;
        Filter myFilter = new Filter();
        myFilter.addSubFilter(new BrightnessSubFilter(brightness));
        iVImageView.setImageBitmap(myFilter.processFilter(finalImage.copy(Bitmap.Config.ARGB_8888, true)));
    }

    @Override
    public void onSaturationChanged(float saturation) {
        saturationFinal = saturation;
        Filter myFilter = new Filter();
        myFilter.addSubFilter(new SaturationSubfilter(saturation));
        iVImageView.setImageBitmap(myFilter.processFilter(finalImage.copy(Bitmap.Config.ARGB_8888, true)));
    }

    @Override
    public void onContrastChanged(float contrast) {
        contrastFinal = contrast;
        Filter myFilter = new Filter();
        myFilter.addSubFilter(new ContrastSubFilter(contrast));
        iVImageView.setImageBitmap(myFilter.processFilter(finalImage.copy(Bitmap.Config.ARGB_8888, true)));
    }

    @Override
    public void onEditStarted() {

    }

    @Override
    public void onEditCompleted() {
        final Bitmap bitmap = filteredImage.copy(Bitmap.Config.ARGB_8888, true);

        Filter myFilter = new Filter();
        myFilter.addSubFilter(new BrightnessSubFilter(brightnessFinal));
        myFilter.addSubFilter(new ContrastSubFilter(contrastFinal));
        myFilter.addSubFilter(new SaturationSubfilter(saturationFinal));
        finalImage = myFilter.processFilter(bitmap);
    }

    private void resetControls() {
        if (editImageFragment != null) {
            editImageFragment.resetControls();
        }
        brightnessFinal = 0;
        saturationFinal = 1.0f;
        contrastFinal = 1.0f;
    }

    /*@Override
    public void onFilterSelected(Filter filter) {
        resetControls();
        // applying the selected filter
        filteredImage = relativeToBitmap(rLImage).copy(Bitmap.Config.ARGB_8888, true);
        // preview filtered image
        iVImageView.setImageBitmap(filter.processFilter(filteredImage));

        finalImage = filteredImage.copy(Bitmap.Config.ARGB_8888, true);
    }*/

    @Override
    public void onEditTextChangeListener(String text, int colorCode) {
        Logger.d("EmojiImage", "onEditTextChangeListener");
        if (MultipleImageCreateStoryFragment.getInstance() != null) {
            MultipleImageCreateStoryFragment.getInstance().disableSwipe(false);
        }
    }

    @Override
    public void onAddViewListener(ViewType viewType, int numberOfAddedViews) {
        Logger.d("EmojiImage", "onAddViewListener");

    }

    @Override
    public void onRemoveViewListener(int numberOfAddedViews) {
        Logger.d("EmojiImage", "onRemoveViewListener");
        if (MultipleImageCreateStoryFragment.getInstance() != null) {
            MultipleImageCreateStoryFragment.getInstance().disableSwipe(true);
        }
    }

    @Override
    public void onStartViewChangeListener(ViewType viewType) {
        Logger.d("EmojiImage", "onStartViewChangeListener");
        if (MultipleImageCreateStoryFragment.getInstance() != null) {
            MultipleImageCreateStoryFragment.getInstance().disableSwipe(false);
        }
    }

    @Override
    public void onStopViewChangeListener(ViewType viewType) {
        Logger.d("EmojiImage", "onStopViewChangeListener");
        if (MultipleImageCreateStoryFragment.getInstance() != null) {
            MultipleImageCreateStoryFragment.getInstance().disableSwipe(true);
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iVCrop:
                iVCrop.setEnabled(false);
                try {
                    source = ((BitmapDrawable) iVImageView.getDrawable()).getBitmap();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = null;
                try {
                    intent = CropImage.activity(getImageUri(activity(), source, fileConvert))
                            .getIntent(activity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
                break;
            case R.id.iVRortate:

                angle = 90;
                source = ((BitmapDrawable) iVImageView.getDrawable()).getBitmap();
                Bitmap rotatedImage = rotateImage(source, angle);
                Logger.d("angle", angle + "");
                iVImageView.setImageBitmap(rotatedImage);
                ImageMatrixTouchHandler imageMatrixTouchHandler = new ImageMatrixTouchHandler(activity());
                try {
                    refreshImage(getImageUri(activity(), rotatedImage, fileConvert));
                } catch (IOException e) {
                    e.printStackTrace();
                }
               /* iVImageView.setOnTouchListener(imageMatrixTouchHandler);
                iVImageView.setImageBitmap(rotatedImage);*/

                break;
            case R.id.iVClose:
                activity().finish();
                break;
            case R.id.iVImageView:
                //stickerView.
                break;
            case R.id.iVCloseEmoji:
                image_emoji_bottom_sheet.setVisibility(View.GONE);
                break;
            case R.id.iVEmoji:
                /*Drawable drawable1 =
                        ContextCompat.getDrawable(activity(), R.drawable.ic_happy);
                stickerView.addSticker(new DrawableSticker(drawable1), Sticker.Position.CENTER );*/
                emojiFont = Typeface.createFromAsset(activity().getAssets(), "emojione-android.ttf");


        /*adapter = new PreviewSlidePagerAdapter(getChildFragmentManager(), fragmentsList);
        image_emoji_view_pager.setAdapter(adapter);
        indicator.setViewPager(image_emoji_view_pager);*/

                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
                ft.replace(R.id.fmEmojiLayout, EmojiFragment.newInstance(this.getId()));
// or ft.add(R.id.your_placeholder, new FooFragment());
// Complete the changes added above
                ft.commit();
                image_emoji_bottom_sheet.setVisibility(View.VISIBLE);
                break;
            case R.id.iVfilters:

                if (MultipleImageCreateStoryFragment.getInstance() != null) {
                    MultipleImageCreateStoryFragment.getInstance().deleteItem(position);
                }

                break;
            case R.id.tvShare:
                tvShare.setEnabled(false);
                if (!isVideo) {
                    /*source = ((BitmapDrawable) iVImageView.getDrawable()).getBitmap();*/
                    rLImage.setDrawingCacheEnabled(true);
                    source = rLImage.getDrawingCache();
                    double imageratio = 0.0;
                    if (source.getWidth() > (double) source.getHeight()) {
                        imageratio = (double) source.getWidth() / (double) source.getHeight();
                    } else {
                        imageratio = (double) source.getHeight() / (double) source.getWidth();
                    }

                    Uri uri = null;
                    try {
                        uri = getImageUri(activity(), source, fileConvert);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String imageUrlProfilePic = "";
                    if (uri != null) {
                        imageUrlProfilePic = new File(uri.getPath()).getAbsolutePath();
                    }
                    File image_video_file = null;
                    if (!imageUrlProfilePic.isEmpty() && imageUrlProfilePic != null) {
                        try {
                            image_video_file = new File(imageUrlProfilePic);
                            RequestBody requestFile = RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"), image_video_file);
                            bodyPicVideo = MultipartBody.Part.createFormData("image_video", image_video_file
                                    .getName(), requestFile);
                            String file_size = String.valueOf(image_video_file.length() / 1024);
                            createStoreMedia = new CreateStoreMedia("image", imageratio + "", "", etCaption.getText().toString().trim());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    partArrayList = new ArrayList<>();
                    createStoreMediaList = new ArrayList<>();
                    if (bodyPicVideo != null) {
                        partArrayList.add(bodyPicVideo);
                        if (partArrayList.size() > 0) {
                            mediaMultiParts = new MultipartBody.Part[partArrayList.size()];
                            for (int index = 0; index < partArrayList.size(); index++) {
                                mediaMultiParts[index] = partArrayList.get(index);
                            }
                        }
                    }
                    createStoreMediaList.add(createStoreMedia);
                    createStoreData = new CreateStoreData(SessionManager.userLogin.userId, "", createStoreMediaList);
                    shareStore();
                } else {
                    mVideoTrimmer.trimVideo();
                }
                break;
            /*case R.id.iVAddMediaSingleImage:
                ArrayList<Image> imageArrayList = new ArrayList<>();
                imageArrayList.add(galleryFile);
                ImagePicker.with(activity())
                        .setFolderMode(true)
                        .setCameraOnly(false)
                        .setFolderTitle("Album")
                        .setMultipleMode(true)
                        .setGif(false)
                        .setSelectedImages(imageArrayList)
                        .setMaxSize(10)
                        .setBackgroundColor("#212121")
                        .setAlwaysShowDoneButton(false)
                        .setRequestCode(Utility.generateRequestCodes().get("ADD_MEDIA_TO_STORY_FOR_SINGLE_IMAGE"))
                        .setKeepScreenOn(true)
                        .setIsFeed(false)
                        .setMultipleImages(true)
                        .start();
                break;*/
        }
    }

    private void shareStore() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ApiResponseModel> call = apiInterface.createStore(RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"), "" + new Gson().toJson(createStoreData)), mediaMultiParts);
        Common.getInstance().callAPI(new ApiRequestModel().setModel(activity(), call, ApiClient.CREATE_STORE, true, apiListener, true));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            iVCrop.setEnabled(true);
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                iVImageView.setImageURI(resultUri);
                refreshImage(resultUri);
                ImageMatrixTouchHandler imageMatrixTouchHandler = new ImageMatrixTouchHandler(activity());
                /*iVImageView.setOnTouchListener(imageMatrixTouchHandler);
                iVImageView.setImageURI(resultUri);*/


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public static Bitmap rotateImage(Bitmap sourceImage, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(sourceImage, 0, 0, sourceImage.getWidth(), sourceImage.getHeight(), matrix, true);
    }

    public File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File mFileTemp = null;
        String root = activity().getDir("my_sub_dir", Context.MODE_PRIVATE).getAbsolutePath();
        File myDir = new File(root + "/Img");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        try {
            mFileTemp = File.createTempFile(imageFileName, ".jpg", myDir.getAbsoluteFile());
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return mFileTemp;
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

    @Override
    public void onVideoPrepared() {

    }

    @Override
    public void onTrimStarted() {
        Common.getInstance().showProgressDialog(activity());
    }

    @Override
    public void getResult(Uri uri, boolean isNotTrim) {
        String imageUrlProfilePic = " ";
        if (isNotTrim) {
            imageUrlProfilePic = new File(Common.getInstance().getRealPathFromUri(activity(), uri)).getAbsolutePath();
        } else {
            imageUrlProfilePic = new File(uri.getPath()).getAbsolutePath();
        }

        double imageratio = Utility.getImageRatio(activity(), uri);
        File image_video_file = null;
        if (!imageUrlProfilePic.isEmpty() && imageUrlProfilePic != null) {
            try {
                image_video_file = new File(imageUrlProfilePic);
                RequestBody requestFile = RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"), image_video_file);
                bodyPicVideo = MultipartBody.Part.createFormData("image_video", image_video_file
                        .getName(), requestFile);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Bitmap thumb = ThumbnailUtils.createVideoThumbnail(uri.getPath(), MediaStore.Video.Thumbnails.MINI_KIND);
        MultipartBody.Part bodyPicVideoThumb = null;
        String imageUrlProfilePic_thumb = "";
        if (thumb != null) {
            imageUrlProfilePic_thumb = Common.getLocalBitmapUri(thumb, activity()).getPath();
        } else {
            Bitmap thumb_ = ThumbnailUtils.createVideoThumbnail(Common.getInstance().getRealPathFromUriIma(activity(), uri), MediaStore.Video.Thumbnails.MINI_KIND);
            if (thumb_ != null) {
                imageUrlProfilePic_thumb = Common.getLocalBitmapUri(thumb_, activity()).getPath();
            } else {
                Bitmap thumb_drawble = Common.getInstance().drawableToBitmap(getActivity().getResources().getDrawable(R.drawable.logo_with_text));
                imageUrlProfilePic_thumb = Common.getLocalBitmapUri(thumb_drawble, activity()).getPath();
            }
        }
        File image_video_file_thumb = null;
        assert imageUrlProfilePic_thumb != null;
        if (!imageUrlProfilePic_thumb.isEmpty()) {
            try {
                image_video_file_thumb = new File(imageUrlProfilePic_thumb);
                RequestBody requestFileThumb = RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"), image_video_file_thumb);
                bodyPicVideoThumb = MultipartBody.Part.createFormData("image_video_thumb", image_video_file_thumb
                        .getName(), requestFileThumb);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        createStoreMedia = new CreateStoreMedia("video", imageratio + "", "", etCaption.getText().toString().trim(), mVideoTrimmer.getVideoDuration());
        partArrayList = new ArrayList<>();
        createStoreMediaList = new ArrayList<>();
        if (bodyPicVideo != null) {
            partArrayList.add(bodyPicVideo);
        }
        if (bodyPicVideoThumb != null) {
            partArrayList.add(bodyPicVideoThumb);
        }

        if (partArrayList.size() > 0) {
            mediaMultiParts = new MultipartBody.Part[partArrayList.size()];
            for (int index = 0; index < partArrayList.size(); index++) {
                mediaMultiParts[index] = partArrayList.get(index);
            }
        }
        createStoreMediaList.add(createStoreMedia);
        createStoreData = new CreateStoreData(SessionManager.userLogin.userId, "", createStoreMediaList);
        shareStore();

    }

    @Override
    public void cancelAction() {
        mVideoTrimmer.destroy();
    }

    @Override
    public void onError(String message) {

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
        if (condition.equals(ApiClient.CREATE_STORE)) {

        }
    }

    public Uri relativeToURI(RelativeLayout relativeLayout) throws IOException {
        relativeLayout.setDrawingCacheEnabled(true);
        relativeLayout.buildDrawingCache();
        Bitmap bm = relativeLayout.getDrawingCache();
        return getImageUri(activity(), bm, fileConvert);
    }

    public Bitmap relativeToBitmap(RelativeLayout relativeLayout) {
        relativeLayout.setDrawingCacheEnabled(true);
        relativeLayout.buildDrawingCache();
        Bitmap bm = relativeLayout.getDrawingCache();
        return bm;
    }

    public void addEmoji(String emojiName) {
        photoEditorSDK.addEmoji(emojiName, emojiFont);
        image_emoji_bottom_sheet.setVisibility(View.GONE);
        // adapter.notifyDataSetChanged();
    }

    public void requestFouse() {
        iVImageView.requestFocus();
    }

    public void addImage(Bitmap image) {
        photoEditorSDK.addImage(image);
        image_emoji_bottom_sheet.setVisibility(View.GONE);
        //  adapter.notifyDataSetChanged();
    }

    public void hideEmojiBottomSheet() {
        image_emoji_bottom_sheet.setVisibility(View.GONE);
        // adapter.notifyDataSetChanged();
    }

    private void addText(String text, int colorCodeTextView) {
        photoEditorSDK.addText(text, colorCodeTextView);
    }

    private class PreviewSlidePagerAdapter extends FragmentStatePagerAdapter {
        private List<Fragment> mFragments;

        PreviewSlidePagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

    public CreateStoreData sharemulitpleImage() {
        deleteRelativeLayout.setVisibility(View.GONE);
        rLImage.setDrawingCacheEnabled(true);
        source = rLImage.getDrawingCache();
        double imageratio = 0.0;
        if (source.getWidth() > (double) source.getHeight()) {
            imageratio = (double) source.getWidth() / (double) source.getHeight();
        } else {
            imageratio = (double) source.getHeight() / (double) source.getWidth();
        }

        Uri uri = null;
        try {
            uri = getImageUri(activity(), source, fileConvert);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String imageUrlProfilePic = "";
        if (uri != null) {
            imageUrlProfilePic = new File(uri.getPath()).getAbsolutePath();
        }
        File image_video_file = null;
        if (!imageUrlProfilePic.isEmpty() && imageUrlProfilePic != null) {
            try {
                image_video_file = new File(imageUrlProfilePic);
                RequestBody requestFile = RequestBody.create(okhttp3.MediaType.parse("multipart/form-data"), image_video_file);
                bodyPicVideo = MultipartBody.Part.createFormData("image_video", image_video_file
                        .getName(), requestFile);
                String file_size = String.valueOf(image_video_file.length() / 1024);
                createStoreMedia = new CreateStoreMedia("image", imageratio + "", "", etCaption.getText().toString().trim());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        partArrayList = new ArrayList<>();
        createStoreMediaList = new ArrayList<>();
        if (bodyPicVideo != null) {
            partArrayList.add(bodyPicVideo);
            if (partArrayList.size() > 0) {
                mediaMultiParts = new MultipartBody.Part[partArrayList.size()];
                for (int index = 0; index < partArrayList.size(); index++) {
                    mediaMultiParts[index] = partArrayList.get(index);
                }
            }
        }
        createStoreMediaList.add(createStoreMedia);
        return createStoreData = new CreateStoreData(SessionManager.userLogin.userId, "", createStoreMediaList, partArrayList);

    }

    public void hideDeleteIcon() {
        deleteRelativeLayout.setVisibility(View.GONE);
    }

    public void unfosueViewpager() {

    }

    public void refreshImage(Uri uri) {
        if (MultipleImageCreateStoryFragment.getInstance() != null) {
            MultipleImageCreateStoryFragment.getInstance().updateRecyclerview(position, uri);
        }
    }

    public void getFrgamentId() {
        this.getId();
    }

}


