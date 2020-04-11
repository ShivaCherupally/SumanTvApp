package info.dkapp.flow.ipoll.fragments.gallery;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.ml.vision.face.FirebaseVisionFace;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.dkapp.flow.appmanagers.feed.models.FacePoint;
import info.dkapp.flow.appmanagers.feed.models.GalleryFile;
import info.dkapp.flow.appmanagers.feed.models.Media;
import info.dkapp.flow.bottommenu.activity.HelperActivity;
import info.dkapp.flow.bottommenu.interfaces.fragments.IFragment;
import info.dkapp.flow.databaseutil.appstart.AppController;

import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.celebflow.constants.MediaType;
import info.dkapp.flow.ipoll.adapters.gallery.GalleryAdapter;
import info.dkapp.flow.ipoll.interfaces.gallery.IGalleryAdapter;
import info.dkapp.flow.utils.Logger;
import info.dkapp.flow.utils.Utility;

public class GalleryFragment extends Fragment implements IFragment, IGalleryAdapter {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    List<GalleryFile> list = new ArrayList<>();
    public List<GalleryFile> selectedItems = new ArrayList<>();

    GalleryAdapter galleryAdapter;

    int count = 0;
    long size = 0;

    public boolean isPick = false;

    public String cameraMediaName = null;

    public boolean isSizeLimitExceed = false;

    public boolean isCountLimitExceed = false;

    String fetchQuery = null, isFrom = "";

    int existingMedia = 0;


    public GalleryFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static GalleryFragment newInstance(List<GalleryFile> list, List<Media> mediaList, String isFrom) {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("GalleryFileList", (ArrayList<? extends Parcelable>) list);
        args.putParcelableArrayList("MediaList", (ArrayList<? extends Parcelable>) mediaList);
        fragment.setArguments(args);
        args.putString("isFrom", isFrom);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isFrom = getArguments().getString("isFrom", "");
            selectedItems = getArguments().getParcelableArrayList("GalleryFileList");
            if (selectedItems == null) {
                selectedItems = new ArrayList<>();
            }
            if (getArguments().getParcelableArrayList("MediaList") != null) {
                List<Media> mediaList = getArguments().getParcelableArrayList("MediaList");
                if (mediaList != null && mediaList.size() > 0) {
                    existingMedia = mediaList.size();
                }
                Logger.d("Existed media size", "" + existingMedia);
            }

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.ipoll_fragment_gallery, container, false);
        ButterKnife.bind(this, view);

        setUp();
        return view;
    }

    private void setUp() {
        addPickerType(3);
        recyclerView.setLayoutManager(new GridLayoutManager(activity(), 3));
        new LoadMediaFromDevice().execute();
    }


    public void refresh() {
        activity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                isPick = true;
                new LoadMediaFromDevice().execute();
            }
        });
    }

    private void addPickerType(int value) {
        switch (value) {
            case 1: // only images
                fetchQuery = MediaStore.Files.FileColumns.MEDIA_TYPE + " = 1";
                break;

            case 2: // only videos
                fetchQuery = MediaStore.Files.FileColumns.MEDIA_TYPE + " = 3";
                break;

            case 3: // both images and videos
                fetchQuery = MediaStore.Files.FileColumns.MEDIA_TYPE + " = 1 OR " + MediaStore.Files.FileColumns.MEDIA_TYPE + " = 3";
                break;
        }

    }

    @Override
    public void changeTitle(String title) {
        if(activity() instanceof HelperActivity){
            ((HelperActivity) activity()).changeTitle(title);
        }
    }

    @Override
    public void showSnackBar(String snackBarText, int type) {
        Utility.showSnackBar(activity(), coordinatorLayout, snackBarText, type);
    }

    @Override
    public Activity activity() {
        if(getActivity() == null){
            return AppController.getInstance().getCurrentRegisteredActivity();
        }
        return getActivity();
    }


    public void updateAdapter() {
        if (list.size() > 0) {
            recyclerView.setAdapter(galleryAdapter = new GalleryAdapter(activity(), list, this));
        }
    }

    private String getConditionValue() {
        return isFrom.equalsIgnoreCase("Feed") ? "upload" : "add";
    }

    @Override
    public void click(GalleryFile galleryFile, int position) {
        Logger.d("Gallery File", galleryFile.name);


        if (isFrom.equalsIgnoreCase("CreateStories")) {
            if (count + existingMedia == Constants.MEDIA_PICK_LIMIT_CREATE) {
                if (!galleryFile.isSelect) {
                    showSnackBar("You can " + getConditionValue() + " maximum " + (Constants.MEDIA_PICK_LIMIT_CREATE - existingMedia) + " files.", 2);
                    return;
                }
            }
        } else {
            if (size <= Constants.MEDIA_SIZE_LIMIT_IN_BYES) {
                if (!galleryFile.isSelect) {
                    if (size + Long.parseLong(galleryFile.size) >= Constants.MEDIA_SIZE_LIMIT_IN_BYES) {
                        showSnackBar("You can " + getConditionValue() + " upto " + Constants.MEDIA_SIZE_LIMIT_IN_BYES / (1024 * 1024) + " MB only.", 2);
                        return;
                    }
                }
            }
            if (count + existingMedia == Constants.MEDIA_PICK_LIMIT) {
                if (isFrom.equalsIgnoreCase("CreateStories")){
                    showSnackBar("You can upload only 1 file.", 2);
                    return;
                }
                if (!galleryFile.isSelect) {
                    showSnackBar("You can " + getConditionValue() + " maximum " + (Constants.MEDIA_PICK_LIMIT - existingMedia) + " files.", 2);
                    return;
                }
            }
        }

        list.get(position).isSelect = !galleryFile.isSelect;
        galleryAdapter.notifyItemChanged(position);

        for (GalleryFile item : selectedItems) {
            if (item.id == galleryFile.id) {
                selectedItems.remove(item);
                updateData();
                return;
            }
        }
        selectedItems.add(list.get(position));
        updateData();
        // inside single click


    }


    @Override
    public void longPressed(GalleryFile galleryFile, int position) {


    }

    public class LoadMediaFromDevice extends AsyncTask<Void, Void, List<GalleryFile>> {

        List<GalleryFile> result;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            result = new ArrayList<>();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<GalleryFile> doInBackground(Void... params) {
            String[] projection =
                    {
                            MediaStore.Files.FileColumns._ID,
                            MediaStore.Files.FileColumns.DISPLAY_NAME,
                            MediaStore.Files.FileColumns.SIZE,
                            MediaStore.Files.FileColumns.MEDIA_TYPE,
                            MediaStore.Files.FileColumns.MIME_TYPE,
                    };
            // Create the cursor pointing to the SDCard
            Cursor cursor = activity().getContentResolver().query(MediaStore.Files.getContentUri("external"),
                    projection,
                    fetchQuery,
                    null,
                    MediaStore.Files.FileColumns.DATE_ADDED + " DESC");
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                Uri uri = null;
                Uri thumbnailUri = null;
                if (cursor.getString(3).equals("1")) {
                    uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + cursor.getInt(0));
                    thumbnailUri = Uri.withAppendedPath(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, "" + cursor.getInt(0));
                } else {
                    uri = Uri.withAppendedPath(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, "" + cursor.getInt(0));
                    thumbnailUri = Uri.withAppendedPath(MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, "" + cursor.getInt(0));
                }
                GalleryFile galleryFile = new GalleryFile();
                galleryFile.id = cursor.getInt(0);
                galleryFile.name = cursor.getString(1);
                galleryFile.mediaType = cursor.getString(3).equals("1") ? MediaType.IMAGE : MediaType.VIDEO;
                galleryFile.mimeType = cursor.getString(4);
                galleryFile.uri = uri;
                galleryFile.thumbnailUri = thumbnailUri;
                galleryFile.size = cursor.getString(2) == null ? "100000" : cursor.getString(2);
                if (isFrom.equalsIgnoreCase("CreateStories")) {
                    if ( !galleryFile.mimeType.equals("image/gif") && !galleryFile.mimeType.equals("image/webp")) {
                        if (i == 0 && isPick) {
                            if (selectedItems.size() + existingMedia == Constants.MEDIA_PICK_LIMIT) {
                                isCountLimitExceed = true;
                            } else if (checkSizeLimit(galleryFile)) {
                                isSizeLimitExceed = true;
                            } else {
                                selectedItems.add(galleryFile);
                            }
                            isPick = false;
                        }
                        if (selectedItems != null && selectedItems.size() > 0) {
                            for (int j = 0; j < selectedItems.size(); j++) {
                                if (galleryFile.id == selectedItems.get(j).id) {
                                    galleryFile.isSelect = true;
                                    break;
                                }
                            }
                        }
                        result.add(galleryFile);

                    }
                }else {
                    if (i == 0 && isPick) {
                        if (selectedItems.size() + existingMedia == Constants.MEDIA_PICK_LIMIT) {
                            isCountLimitExceed = true;
                        } else if (checkSizeLimit(galleryFile)) {
                            isSizeLimitExceed = true;
                        } else {
                            selectedItems.add(galleryFile);
                        }
                        isPick = false;
                    }
                    if (selectedItems != null && selectedItems.size() > 0) {
                        for (int j = 0; j < selectedItems.size(); j++) {
                            if (galleryFile.id == selectedItems.get(j).id) {
                                galleryFile.isSelect = true;
                                break;
                            }
                        }
                    }
                    result.add(galleryFile);
                }
            }
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            for (int j = 0; j < result.size(); j++) {
                Logger.d("imageType",result.get(j).mimeType);
            }

            return result;
        }


        @Override
        protected void onPostExecute(List<GalleryFile> result) {
            progressBar.setVisibility(View.GONE);
            list = result;
            updateAdapter();
            updateData();
            //addFacePoints();
            if (isSizeLimitExceed) {
                showSnackBar("You can " + getConditionValue() + " upto " + Constants.MEDIA_SIZE_LIMIT_IN_BYES / (1024 * 1024) + " MB only.", 2);
                isSizeLimitExceed = false;
            }
            if (isCountLimitExceed) {
                showSnackBar("You can " + getConditionValue() + " maximum " + (Constants.MEDIA_PICK_LIMIT) + " files.", 2);
                isCountLimitExceed = false;
            }
        }
    }


    public boolean checkSizeLimit(GalleryFile galleryFile) {
        long selectedFilesSize = 0;
        for (int i = 0; i < selectedItems.size(); i++) {
            selectedFilesSize += Long.parseLong(selectedItems.get(i).size);
        }
        return selectedFilesSize + Long.parseLong(galleryFile.size) >= Constants.MEDIA_SIZE_LIMIT_IN_BYES ? true : false;
    }

    public void updateData() {

        if (selectedItems != null && selectedItems.size() > 0) {
            count = 0;
            size = 0;
            for (int i = 0; i < selectedItems.size(); i++) {
                count++;
                size += Long.parseLong(selectedItems.get(i).size);
            }
            Logger.d("Total Size", "" + size);

            changeTitle(count == 0 ? "Media" : count + " Selected");
            if(activity() instanceof HelperActivity) {
                if (((HelperActivity) activity()).menuDone != null)
                    ((HelperActivity) activity()).menuDone.setVisible(count == 0 ? false : true);
            }
        } else {
            count = 0;
            size = 0;
            changeTitle("Media");
            if(activity() instanceof HelperActivity){
                if (((HelperActivity) activity()).menuDone != null)
                    ((HelperActivity) activity()).menuDone.setVisible(false);
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    public void addFacePoints() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                if (list == null || list.size() <= 0) {
                    return null;
                }
                final ArrayList<Task<List<FirebaseVisionFace>>> tasks = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).facePointList == null) {
                        Task<List<FirebaseVisionFace>> mediaTask = Utility.getFacePoints(list.get(i).uri);
                        if (mediaTask != null) {
                            int finalPosition = i;
                            mediaTask.addOnSuccessListener(faces -> {
                                List<FacePoint> facePointList = new ArrayList<>();
                                for (FirebaseVisionFace face : faces) {
                                    facePointList.add(Utility.generateFacePoint(face));
                                }
                                list.get(finalPosition).facePointList = facePointList;
                            });
                            tasks.add(mediaTask);
                        }
                    }
                }
                Tasks.whenAll(tasks).continueWith(task -> {
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            //
                            return null;
                        }
                    }.execute();
                    return null;
                });
                return null;
            }
        }.execute();
    }
}
