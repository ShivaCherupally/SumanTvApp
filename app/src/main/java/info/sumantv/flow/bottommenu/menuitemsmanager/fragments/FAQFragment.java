package info.sumantv.flow.bottommenu.menuitemsmanager.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import info.sumantv.flow.bottommenu.interfaces.fragments.IFragment;

import info.sumantv.flow.R;
import info.sumantv.flow.celebflow.constants.Constants;
import info.sumantv.flow.retrofitcall.ApiClient;
import info.sumantv.flow.utils.UtilityNew;

public class FAQFragment extends Fragment implements IFragment {
    private WebView mFaq_webview;
    private ProgressBar mFaq_progressbar;

    SwipeRefreshLayout swipeRefreshLayout;
    RelativeLayout relativeLayout;

    public static FAQFragment newInstance(String param1, String param2) {
        FAQFragment fragment = new FAQFragment();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_faq, null);
        initializeComponents(root);

        mFaq_webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("android_asset")) {
                    mFaq_progressbar.setVisibility(View.GONE);
                    return false;
                }
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mFaq_progressbar.setVisibility(View.GONE);
            }
        });

        return root;
    }

    private void initializeComponents(View root) {
        mFaq_webview = root.findViewById(R.id.faq_webview);
        mFaq_progressbar = root.findViewById(R.id.faq_progressbar);
        mFaq_webview.getSettings().setJavaScriptEnabled(true);
        swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout);
        relativeLayout = root.findViewById(R.id.relativeLayout);
        getUrls();

        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(false);
            if (UtilityNew.isNetworkAvailable(getActivity())) {
                //  getCompletePreferenceList();
                getUrls();
            } else {
                Snackbar snackBar = Snackbar.make(relativeLayout, Constants.PLEASE_CHECK_INTERNET, Snackbar.LENGTH_SHORT);

            }
        });

    }

    public void getUrls() {
        Bundle arguments = getArguments();
        if (arguments.getString("FAQ").equals("Contact Us")) {
            mFaq_webview.loadUrl(ApiClient.CONTACT_US_WEB);
        } else if (arguments.getString("FAQ").equals("About Us")) {
            mFaq_webview.loadUrl(ApiClient.ABOUT_US_WEB);
        } else if (arguments.getString("FAQ").equals("FeedAdvertisement")) {
            mFaq_webview.loadUrl(arguments.getString("FeedAdvertisementURL", ""));
        } else {
            mFaq_webview.loadUrl(ApiClient.FAQ_WEB_LINK);
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
        return null;
    }
}

