package info.sumantv.flow.ipoll.fragments.feeds.options;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;



import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import info.sumantv.flow.appmanagers.feed.models.GalleryFile;
import info.sumantv.flow.appmanagers.feed.models.Media;
import info.sumantv.flow.bottommenu.activity.HelperActivity;
import info.sumantv.flow.bottommenu.interfaces.fragments.IFragment;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.celebflow.constants.MediaType;
import info.sumantv.flow.ipoll.adapters.feeds.options.FeedEditAdapter;
import info.sumantv.flow.ipoll.interfaces.feeds.options.IFeedEditAdapter;
import info.sumantv.flow.utils.Logger;
import info.sumantv.flow.utils.Utility;
import info.sumantv.flow.imagepicker.model.Image;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditFeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditFeedFragment extends Fragment implements IFragment, IFeedEditAdapter {
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    public ArrayList<Image> galleryFileList;
    int position = -1;
    IFeedEditAdapter iFeedEditAdapter;
    public FeedEditAdapter feedEditAdapter;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    public EditFeedFragment() {
        // Required empty public constructor
    }

    public static EditFeedFragment newInstance(List<GalleryFile> list,int position) {
        EditFeedFragment fragment = new EditFeedFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("GalleryFileList", (ArrayList<? extends Parcelable>) list);
        args.putInt("Position",position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           galleryFileList = getArguments().getParcelableArrayList("GalleryFileList");
           position = getArguments().getInt("Position");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.ipoll_fragment_edit_feed, container, false);
        ButterKnife.bind(this,view);
        iFeedEditAdapter = this;
        setUp();
        return view;
    }

    private void setUp()
    {
        if(galleryFileList==null) return;

        recyclerView.setLayoutManager(new LinearLayoutManager(activity()));
        recyclerView.setAdapter(feedEditAdapter = new FeedEditAdapter(galleryFileList,activity(),iFeedEditAdapter));
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setChangeDuration(0);//.setSupportsChangeAnimations(false);

        if(position != -1)
        {
            recyclerView.scrollToPosition(position);
        }
    }

    @Override
    public void changeTitle(String title) {

    }

    @Override
    public void showSnackBar(String snackBarText, int type) {
        Utility.showSnackBar(activity(),coordinatorLayout,snackBarText,type);
    }

    @Override
    public Activity activity() {
        return getActivity();
    }


    @Override
    public void addCaptionGalleryFile(Image galleryFile, String caption, int position)
    {
        Logger.d("CAPTION",caption);
        galleryFileList.get(position).caption = caption;
        addMenuDone();
    }

    @Override
    public void removeGalleryFile(Image galleryFile, int position)
    {
        Logger.d("POSITION REMOVED","" + position);
        galleryFileList.remove(position);
        feedEditAdapter.notifyItemRemoved(position);
        addMenuDone();
    }

    @Override
    public void cropGalleryFile(Image galleryFile, int position) {

    }

    @Override
    public void addMoreMedia() {
        ((HelperActivity) activity()).isEdit = true;
        ((HelperActivity) activity()).openCustomGallery();
    }

    public void addMenuDone()
    {
        ((HelperActivity) activity()).menuDone.setVisible(true);
    }

    public void updateMediaPost(ArrayList<Image> galleryFileList)
    {
        this.galleryFileList = galleryFileList;
        new AddCollageMedia().execute();
    }

    public class AddCollageMedia extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            List<Media> result = new ArrayList<>();
            for (int i =0;i<galleryFileList.size();i++)
            {
                Media media = new Media();
                Logger.d("MEDIA TYPE","" + galleryFileList.get(i).mediaType);
                media.type = galleryFileList.get(i).mediaType == MediaType.IMAGE ? Constants.MEDIA_TYPE_IMAGE : Constants.MEDIA_TYPE_VIDEO;
                media.uri = galleryFileList.get(i).uri;
                media.ratio = galleryFileList.get(i).mediaType == MediaType.IMAGE ? Utility.getImageRatio(activity(),media.uri) : Utility.getVideoRatio(activity(),media.uri) ;
                galleryFileList.get(i).ratio = media.ratio;
                media.mimeType = galleryFileList.get(i).mimeType;
                media.sizeKB = Double.parseDouble(galleryFileList.get(i).size) / 1024;
                galleryFileList.get(i).ratio = media.ratio;
                Logger.d("Image Ratio",""+media.ratio);
                result.add(media);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(feedEditAdapter = new FeedEditAdapter(galleryFileList,activity(),iFeedEditAdapter));
            addMenuDone();
        }
    }
}
