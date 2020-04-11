package info.sumantv.flow.stories.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.MediaType;
import info.sumantv.flow.ipoll.fragments.feeds.FeedsFragment;
import info.sumantv.flow.retrofitcall.ApiClient;
import info.sumantv.flow.retrofitcall.ApiInterface;
import info.sumantv.flow.retrofitcall.ApiRequestModel;
import info.sumantv.flow.retrofitcall.ApiResponseModel;
import info.sumantv.flow.retrofitcall.EnumConstants;
import info.sumantv.flow.retrofitcall.IApiListener;
import info.sumantv.flow.retrofitcall.SessionManager;
import info.sumantv.flow.stories.emojiView.BrushDrawingView;
import info.sumantv.flow.stories.emojiView.OnPhotoEditorSDKListener;
import info.sumantv.flow.stories.emojiView.PhotoEditorSDK;
import info.sumantv.flow.stories.emojiView.ViewType;
import info.sumantv.flow.stories.models.CreateStoreData;
import info.sumantv.flow.stories.models.CreateStoreMedia;
import info.sumantv.flow.userflow.Util.Common;
import info.sumantv.flow.utils.Logger;
import info.sumantv.flow.utils.Utility;
import info.sumantv.flow.utils.videoTrimmer.HgLVideoTrimmer;
import info.sumantv.flow.utils.videoTrimmer.interfaces.OnHgLVideoListener;
import info.sumantv.flow.utils.videoTrimmer.interfaces.OnTrimVideoListener;
import info.sumantv.flow.utils.zoommulti.ImageMatrixTouchHandler;
import info.sumantv.flow.imagepicker.model.Image;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Uday Kumay Vurukonda on 28-Nov-19.
 * <p>
 * Mr.Psycho
 */
public class MultipleStorieAdapter extends RecyclerView.Adapter<MultipleStorieAdapter.MyViewHolder> implements View.OnClickListener, OnTrimVideoListener, OnHgLVideoListener, OnPhotoEditorSDKListener, EmojiAdapter.OnEmojiClickListener, ImageAdapter.OnImageClickListener {
    private Context context;
    public int frameId; //Unique identifier for ValueFrame
    public List<Image> galleryFile;
    private Typeface emojiFont;
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
    ArrayList<String> imageList;
    List<MyViewHolder> myViewHolderList = new ArrayList<>();


    public MultipleStorieAdapter(Context context, List<Image> galleryfile, boolean forMultiImages, boolean isVideoSingleStory,ArrayList<String> imageList) {
        this.context = context;
        this.galleryFile = galleryfile;
        this.forMultiImages = forMultiImages;
        this.isVideoSingleStory = isVideoSingleStory;
        this.imageList =imageList;
    }

    @NonNull
    @Override
    public MultipleStorieAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.multiple_image_item_adapter, parent, false));
    }

    public MyViewHolder getViewHolderView() {
        return myViewHolderList.get(getAdapterPosition());
    }

    public MyViewHolder getViewHolderView(int position) {
        return myViewHolderList.get(position);
    }

    public int getAdapterPosition() {
        if (MultipleImageCreateStoryFragment.getInstance() != null) {
            return MultipleImageCreateStoryFragment.getInstance().getViewPagerCurrentPosition();
        }
        return -1;
    }

    @Override
    public void onBindViewHolder(@NonNull MultipleStorieAdapter.MyViewHolder holder, int position) {

        myViewHolderList.add((MyViewHolder) holder);
        holder.tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.tvShare.setEnabled(false);
                if (!isVideo) {
                    /*source = ((BitmapDrawable) iVImageView.getDrawable()).getBitmap();*/
                    holder.rLImage.setDrawingCacheEnabled(true);
                    source = holder.rLImage.getDrawingCache();
                    double imageratio = 0.0;
                    if (source.getWidth() > (double) source.getHeight()) {
                        imageratio = (double) source.getWidth() / (double) source.getHeight();
                    } else {
                        imageratio = (double) source.getHeight() / (double) source.getWidth();
                    }

                    Uri uri = null;
                    try {
                        uri = getImageUri(context, source, fileConvert);
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
                            createStoreMedia = new CreateStoreMedia("image", imageratio + "", "", holder.etCaption.getText().toString().trim());
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
                    holder.mVideoTrimmer.trimVideo();
                }
            }
        });
        holder.photoEditorSDK.setOnPhotoEditorSDKListener(this);
        holder.iVClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity)context).finish();
            }
        });
        holder.iVEmoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 /*Drawable drawable1 =
                        ContextCompat.getDrawable(activity(), R.drawable.ic_happy);
                stickerView.addSticker(new DrawableSticker(drawable1), Sticker.Position.CENTER );*/
                emojiFont = Typeface.createFromAsset(context.getAssets(), "emojione-android.ttf");


        /*adapter = new PreviewSlidePagerAdapter(getChildFragmentManager(), fragmentsList);
        image_emoji_view_pager.setAdapter(adapter);
        indicator.setViewPager(image_emoji_view_pager);*/

                /*FragmentTransaction ft = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
// Replace the contents of the container with the new fragment
                ft.replace(R.id.fmEmojiLayout, EmojiFragment.newInstance(position));
// or ft.add(R.id.your_placeholder, new FooFragment());
// Complete the changes added above
                ft.commit();*/
                holder.image_emoji_bottom_sheet.setVisibility(View.VISIBLE);
            }
        });
        holder.iVfilters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.iVCloseEmoji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.image_emoji_bottom_sheet.setVisibility(View.GONE);
            }
        });
        holder.iVImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //  iVAddMediaSingleImage.setOnClickListener(this);
        if (galleryFile.get(position).mediaType == MediaType.IMAGE) {
            isVideo = false;
            holder.rLImage.setVisibility(View.VISIBLE);
            holder.rLVideo.setVisibility(View.GONE);
            holder.iVRortate.setVisibility(View.VISIBLE);
            holder.iVCrop.setVisibility(View.VISIBLE);
            holder.iVEmoji.setVisibility(View.VISIBLE);
            holder.iVImageView.setImageURI(galleryFile.get(position).uri);
            holder.iVCrop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    try {
                        source = ((BitmapDrawable) holder.iVImageView.getDrawable()).getBitmap();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (MultipleImageCreateStoryFragment.getInstance() != null){
                        MultipleImageCreateStoryFragment.getInstance().cropDetails(source,fileConvert);
                    }

                   /* Intent intent = null;
                    try {
                        intent = CropImage.activity(getImageUri(context, source, fileConvert))
                                .getIntent(context);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ((Activity) context).startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);*/
                }
            });
            holder.iVRortate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    angle = 90;
                    source = ((BitmapDrawable) holder.iVImageView.getDrawable()).getBitmap();
                    Bitmap rotatedImage = rotateImage(source, angle);
                    Logger.d("angle", angle + "");
                    holder.iVImageView.setImageBitmap(rotatedImage);
                    ImageMatrixTouchHandler imageMatrixTouchHandler = new ImageMatrixTouchHandler(context);
                    try {
                        refreshImage(getImageUri(context, rotatedImage, fileConvert));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
           /* try {
                source = ((BitmapDrawable) iVImageView.getDrawable()).getBitmap();
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            /*ImageMatrixTouchHandler imageMatrixTouchHandler = new ImageMatrixTouchHandler(activity());
            iVImageView.setOnTouchListener(imageMatrixTouchHandler);*/

        } else {
            isVideo = true;
            holder.rLImage.setVisibility(View.GONE);
            holder.rLVideo.setVisibility(View.VISIBLE);
            holder.iVRortate.setVisibility(View.GONE);
            holder.iVCrop.setVisibility(View.GONE);
            holder.iVEmoji.setVisibility(View.GONE);
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage(context.getString(R.string.trimming_progress));
            if (holder.mVideoTrimmer != null) {
                holder.mVideoTrimmer.setOnTrimVideoListener(this);
                holder.mVideoTrimmer.setVideoURI(galleryFile.get(position).uri);
                holder.mVideoTrimmer.setMaxDuration(30);
            }
        }
        holder.etCaption.setImeOptions(EditorInfo.IME_ACTION_DONE);
        holder.etCaption.setRawInputType(InputType.TYPE_CLASS_TEXT);
       /* setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);*/
        //filtersListFragment.prepareThumbnail(relativeToBitmap(rLImage));


        fileConvert = createImageFile();
        if (forMultiImages) {
            holder.tvShare.setVisibility(View.GONE);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.etCaption.getLayoutParams();
            params.rightMargin = context.getResources().getDimensionPixelSize(R.dimen._65sdp);
            holder.etCaption.setLayoutParams(params);
        } else {
            holder.tvShare.setVisibility(View.VISIBLE);
        }
        if (isVideoSingleStory) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.etCaption.getLayoutParams();
            params.rightMargin = context.getResources().getDimensionPixelSize(R.dimen._5sdp);
            holder.etCaption.setLayoutParams(params);
            holder.tvShare.setVisibility(View.VISIBLE);
        } else {
            holder.tvShare.setVisibility(View.GONE);
        }
        holder.emojiRecyclerView.setHasFixedSize(true);
        holder.emojiRecyclerView.setLayoutManager(new GridLayoutManager(context, 4));
        String[] emojis = context.getResources().getStringArray(R.array.photo_editor_emoji);
        ArrayList<String> emojiIds = new ArrayList<>();
        Collections.addAll(emojiIds, emojis);
        EmojiAdapter adapter = new EmojiAdapter(context, emojiIds);
        adapter.setOnEmojiClickListener(this);
        holder.emojiRecyclerView.setAdapter(adapter);
        holder.emojiRecyclerView.setNestedScrollingEnabled(false);
        holder.imageRecyclerView.setLayoutManager(new GridLayoutManager(context, 4));
        holder.imageRecyclerView.setHasFixedSize(true);
        ImageAdapter imageAdapter = new ImageAdapter(context, imageList);
        holder.imageRecyclerView.setAdapter(imageAdapter);
        holder.imageRecyclerView.setNestedScrollingEnabled(false);
        imageAdapter.setOnImageClickListener(this);


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            //iVCrop.setEnabled(true);
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                getViewHolderView().iVImageView.setImageURI(resultUri);
                refreshImage(resultUri);
                ImageMatrixTouchHandler imageMatrixTouchHandler = new ImageMatrixTouchHandler(context);
                /*iVImageView.setOnTouchListener(imageMatrixTouchHandler);
                iVImageView.setImageURI(resultUri);*/


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public void loadmore(List<Fragment> fragmentList) {
        int positionStart = getItemCount() + 1;
        notifyItemRangeInserted(positionStart, fragmentList.size());
//            notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return galleryFile.size();
    }

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

    @Override
    public void onEmojiClickListener(String emojiName) {
        if (MultipleImageCreateStoryFragment.getInstance() != null){
            MultipleImageCreateStoryFragment.getInstance().addEmoji(emojiName,getAdapterPosition());
        } else if (CreateStoriesFragment.getInstance() !=null) {
            CreateStoriesFragment.getInstance().addEmoji(emojiName);
        }
    }

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
                MultipleImageCreateStoryFragment.getInstance().addImage(myBitmap,getAdapterPosition());
            } else if (CreateStoriesFragment.getInstance() !=null) {
                CreateStoriesFragment.getInstance().addImage(myBitmap);
            }
        } catch (IOException e) {
            // Log exception

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

                break;
            case R.id.iVRortate:


               /* iVImageView.setOnTouchListener(imageMatrixTouchHandler);
                iVImageView.setImageBitmap(rotatedImage);*/

                break;
            case R.id.iVClose:
                ((Activity) context).finish();
                break;
            case R.id.iVImageView:
                //stickerView.
                break;
            case R.id.iVCloseEmoji:

                break;
            case R.id.iVEmoji:

                break;
            case R.id.iVfilters:

                if (MultipleImageCreateStoryFragment.getInstance() != null) {
                    MultipleImageCreateStoryFragment.getInstance().deleteItem(position);
                }

                break;
            case R.id.tvShare:

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
        Common.getInstance().callAPI(new ApiRequestModel().setModel(context, call, ApiClient.CREATE_STORE, true, new IApiListener() {
            @Override
            public void apiSuccessResponse(String condition, ApiResponseModel apiResponseModel) {
                getViewHolderView().tvShare.setEnabled(true);
                Common.getInstance().cusToast(context, apiResponseModel.message);
                ((Activity)context).finish();
                if (FeedsFragment.getInstance() != null) {
                    FeedsFragment.getInstance().fetchStories();
                }
            }

            @Override
            public void apiErrorResponse(String condition, EnumConstants enumConstants, ApiResponseModel apiResponseModel) {

            }
        }, true));
    }


    public Bitmap rotateImage(Bitmap sourceImage, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(sourceImage, 0, 0, sourceImage.getWidth(), sourceImage.getHeight(), matrix, true);
    }

    public File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File mFileTemp = null;
        String root = context.getDir("my_sub_dir", Context.MODE_PRIVATE).getAbsolutePath();
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
    public void onVideoPrepared() {

    }

    @Override
    public void onTrimStarted() {
        Common.getInstance().showProgressDialog(context);
    }

    @Override
    public void getResult(Uri uri, boolean isNotTrim) {
        String imageUrlProfilePic = " ";
        if (isNotTrim) {
            imageUrlProfilePic = new File(Common.getInstance().getRealPathFromUri(context, uri)).getAbsolutePath();
        } else {
            imageUrlProfilePic = new File(uri.getPath()).getAbsolutePath();
        }

        double imageratio = Utility.getImageRatio(context, uri);
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
            imageUrlProfilePic_thumb = Common.getLocalBitmapUri(thumb, context).getPath();
        } else {
            Bitmap thumb_ = ThumbnailUtils.createVideoThumbnail(Common.getInstance().getRealPathFromUriIma(context, uri), MediaStore.Video.Thumbnails.MINI_KIND);
            if (thumb_ != null) {
                imageUrlProfilePic_thumb = Common.getLocalBitmapUri(thumb_, context).getPath();
            } else {
                Bitmap thumb_drawble = Common.getInstance().drawableToBitmap(context.getResources().getDrawable(R.drawable.logo_with_text));
                imageUrlProfilePic_thumb = Common.getLocalBitmapUri(thumb_drawble, context).getPath();
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

        createStoreMedia = new CreateStoreMedia("video", imageratio + "", "", getViewHolderView().etCaption.getText().toString().trim(), getViewHolderView().mVideoTrimmer.getVideoDuration());
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
        getViewHolderView().mVideoTrimmer.destroy();
    }

    @Override
    public void onError(String message) {

    }


    public Uri relativeToURI(RelativeLayout relativeLayout) throws IOException {
        relativeLayout.setDrawingCacheEnabled(true);
        relativeLayout.buildDrawingCache();
        Bitmap bm = relativeLayout.getDrawingCache();
        return getImageUri(context, bm, fileConvert);
    }

    public Bitmap relativeToBitmap(RelativeLayout relativeLayout) {
        relativeLayout.setDrawingCacheEnabled(true);
        relativeLayout.buildDrawingCache();
        Bitmap bm = relativeLayout.getDrawingCache();
        return bm;
    }

    public void addEmoji(String emojiName) {
        getViewHolderView().photoEditorSDK.addEmoji(emojiName, emojiFont);
        getViewHolderView().image_emoji_bottom_sheet.setVisibility(View.GONE);
        // adapter.notifyDataSetChanged();
    }

    public void requestFouse() {
        getViewHolderView().iVImageView.requestFocus();
    }

    public void addImage(Bitmap image) {

        getViewHolderView().photoEditorSDK.addImage(image);
        getViewHolderView().image_emoji_bottom_sheet.setVisibility(View.GONE);
        //  adapter.notifyDataSetChanged();
    }

    public void hideEmojiBottomSheet() {
        getViewHolderView().image_emoji_bottom_sheet.setVisibility(View.GONE);
        // adapter.notifyDataSetChanged();
    }

    private void addText(String text, int colorCodeTextView) {
        getViewHolderView().photoEditorSDK.addText(text, colorCodeTextView);
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

    public CreateStoreData sharemulitpleImage(int position) {

        getViewHolderView(position).deleteRelativeLayout.setVisibility(View.GONE);
        getViewHolderView(position).rLImage.setDrawingCacheEnabled(true);
        source = getViewHolderView(position).rLImage.getDrawingCache();
        double imageratio = 0.0;
        if (source.getWidth() > (double) source.getHeight()) {
            imageratio = (double) source.getWidth() / (double) source.getHeight();
        } else {
            imageratio = (double) source.getHeight() / (double) source.getWidth();
        }

        Uri uri = null;
        try {
            uri = getImageUri(context, source, fileConvert);
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
                createStoreMedia = new CreateStoreMedia("image", imageratio + "", "", getViewHolderView(position).etCaption.getText().toString().trim());
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
        getViewHolderView().deleteRelativeLayout.setVisibility(View.GONE);
    }

    public void unfosueViewpager() {

    }

    public void refreshImage(Uri uri) {
        if (MultipleImageCreateStoryFragment.getInstance() != null) {
            MultipleImageCreateStoryFragment.getInstance().updateRecyclerview(getAdapterPosition(), uri);
        }
    }

/*    public void removeItem(int position) {
        listFragment.remove(position);
        notifyItemRemoved(position);
    }*/

    public class MyViewHolder extends RecyclerView.ViewHolder {
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

        @BindView(R.id.fragment_main_photo_edit_emoji_rv)
        RecyclerView emojiRecyclerView;

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

        @BindView(R.id.fragment_main_photo_edit_image_rv)
        RecyclerView imageRecyclerView;

        @BindView(R.id.delete_rl)
        RelativeLayout deleteRelativeLayout;

        @BindView(R.id.iVCloseEmoji)
        ImageView iVCloseEmoji;

        private PhotoEditorSDK photoEditorSDK;


   /* @BindView(R.id.iVAddMediaSingleImage)
    ImageView iVAddMediaSingleImage;*/


        /* @BindView(R.id.image_emoji_indicator)
         PageIndicator indicator;
     */

        public MyViewHolder(@NonNull View root) {
            super(root);
            ButterKnife.bind(this, root);
            photoEditorSDK = new PhotoEditorSDK.PhotoEditorSDKBuilder(context)
                    .parentView(rLImage) // add parent image view
                    .childView(iVImageView) // add the desired image view
                    .deleteView(deleteRelativeLayout) // add the deleted view that will appear during the movement of the views
                    .brushDrawingView(brushDrawingView) // add the brush drawing view that is responsible for drawing on the image view
                    .buildPhotoEditorSDK(); // build photo editor sdk

        }
/*

        private void resetControls() {
            if (editImageFragment != null) {
                editImageFragment.resetControls();
            }
            brightnessFinal = 0;
            saturationFinal = 1.0f;
            contrastFinal = 1.0f;
        }
*/

    /*@Override
    public void onFilterSelected(Filter filter) {
        resetControls();
        // applying the selected filter
        filteredImage = relativeToBitmap(rLImage).copy(Bitmap.Config.ARGB_8888, true);
        // preview filtered image
        iVImageView.setImageBitmap(filter.processFilter(filteredImage));

        finalImage = filteredImage.copy(Bitmap.Config.ARGB_8888, true);
    }*/


    }
    public void updateImageCrop(Uri resultUri){
        getViewHolderView().iVCrop.setEnabled(true);
        getViewHolderView().iVImageView.setImageURI(resultUri);
        refreshImage(resultUri);
    }
    public void removeViewHolder(int position){
        myViewHolderList.remove(position);
    }
}
