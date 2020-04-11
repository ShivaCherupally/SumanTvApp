package info.sumantv.flow.stories.fragments;


import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import info.sumantv.flow.R;
import info.sumantv.flow.stories.models.StoryProfileInfo;

public class StoriesViewPager extends Fragment {
    public ViewPagerAdapter mViewPagerAdapter;
    int POSITION;

    @BindView(R.id.pager)
    ViewPager viewPager;

    public static List<StoryProfileInfo> celeblist = new ArrayList<>();
    public static StoriesViewPager instance;

    int counter;

    public static StoriesViewPager getInstance() {
        return instance;
    }

    public StoriesViewPager() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (getArguments() != null) {
            celeblist = new ArrayList<>();
            POSITION = getArguments().getInt("POSITION");
            celeblist = getArguments().getParcelableArrayList("celeblist");
        }
    }

    public static StoriesViewPager newInstance(int position, ArrayList<StoryProfileInfo> celeblist) {
        StoriesViewPager fragment = new StoriesViewPager();
        Bundle args = new Bundle();
        args.putInt("POSITION", position);
        args.putParcelableArrayList("celeblist", (ArrayList<? extends Parcelable>) celeblist);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.storiesviewpager, container, false);

        ButterKnife.bind(this, root);

        instance = this;

        mViewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(mViewPagerAdapter);

        viewPager.setCurrentItem(POSITION);
        viewPager.setPageTransformer(true, new CardTransformer());// Animation.

        /*viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int position) {
                if (StoriesViewPager.getInstance().mViewPagerAdapter.getCount() == StoriesViewPager.getInstance().viewPager.getCurrentItem() + 1) {
                    getActivity().finish();
                }
            }
        });*/

        return root;
    }

    public void setViewPagerPosition(int position) {
        if (position >= mViewPagerAdapter.getCount()) {
            if (StoriesFragment.getInstance() != null) {
                StoriesFragment.getInstance().getStoriesAllSeenStatus();
            }
            getActivity().finish();
        } else if (position < mViewPagerAdapter.getCount()) {
            viewPager.setCurrentItem(position);
        }
    }

    public void refreshAdapter(int position) {
        celeblist.remove(position);
        mViewPagerAdapter.notifyDataSetChanged();
    }


    public class ViewPagerAdapter extends FragmentStatePagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return StoriesFragment.newInstance(position, celeblist.get(position).memberId, celeblist.get(position));
        }

        @Override
        public int getCount() {
            return celeblist.size();
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    }

    public class CardTransformer implements ViewPager.PageTransformer {
        @Override
        public void transformPage(View page, float position) {

            if (position < -1) {
                page.setAlpha(0);

            } else if (position <= 0) {    // [-1,0]
                page.setAlpha(1);
                page.setPivotX(page.getWidth());
                page.setRotationY(-90 * Math.abs(position));

            } else if (position <= 1) {    // (0,1]
                page.setAlpha(1);
                page.setPivotX(0);
                page.setRotationY(90 * Math.abs(position));
            } else {
                page.setAlpha(0);

            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }
}
