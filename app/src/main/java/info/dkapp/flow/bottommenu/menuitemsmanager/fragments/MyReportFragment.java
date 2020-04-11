package info.dkapp.flow.bottommenu.menuitemsmanager.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.constants.Constants;
//import io.reactivex.Observable;
//import io.reactivex.Observer;
//import io.reactivex.android.schedulers.AndroidSchedulers;


public class MyReportFragment extends Fragment {

    private ImageView imgError;
    private static final String TAG = "";//MyReports.class.getSimpleName();
    private int currentapiVersionNo;
    TextView tvError, tvNote;

    public static MyReportFragment newInstance(String param1, String param2) {
        MyReportFragment fragment = new MyReportFragment();

        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.empty_data_item_new, null);
//        rxjavaImplementations();
        imgError = (ImageView) root.findViewById(R.id.imgError);

        tvNote = (TextView) root.findViewById(R.id.tvNote);
        tvNote.setVisibility(View.GONE);


        tvError = (TextView) root.findViewById(R.id.tvError);
        tvError.setText(Constants.UPCOMING_REPORTS);

        imgError.setImageResource(R.drawable.ic_up_coming_reports);

        return root;
    }

    /*private void rxjavaImplementations() {


        // observable
        Observable<String> animalsObservable = getAnimalsObservable();

        // observer
        Observer<String> animalsObserver = getAnimalsObserver();

        // observer subscribing to observable
        animalsObservable
                .observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(animalsObserver);
    }*/

  /*  private Observer<String> getAnimalsObserver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "Name: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "All items are emitted!");
            }
        };
    }

    private Observable<String> getAnimalsObservable() {
        return Observable.just("Ant", "Bee", "Cat", "Dog", "Fox");
    }
*/

}
