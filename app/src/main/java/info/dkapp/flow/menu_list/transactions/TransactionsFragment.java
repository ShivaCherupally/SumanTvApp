package info.dkapp.flow.menu_list.transactions;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.dkapp.flow.bottommenu.interfaces.fragments.IFragment;

import info.dkapp.flow.R;

/**
 */
public class TransactionsFragment extends Fragment implements IFragment, TabLayout.OnTabSelectedListener {


    public static TabLayout tabLayout;
    private ViewPager viewPager;
    Context context;
    private ViewPagerAdapter mViewPagerAdapter;

    public TransactionsFragment() {
        // Required empty public constructor
    }

    public static TransactionsFragment newInstance(String param1, String param2) {
        TransactionsFragment fragment = new TransactionsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.mycelebrity_activity, container, false);
        initializeViews(root);
        if(getArguments() != null){
            String condition = getArguments().getString("condition","");
            if(condition.equalsIgnoreCase("Orders")){
                selectPage(1);
            }
        }
        return root;
    }

    private void initializeViews(View root) {
        tabLayout = root.findViewById(R.id.tab_layout_fans);
        viewPager = root.findViewById(R.id.view_pager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setOnTabSelectedListener((TabLayout.OnTabSelectedListener) context);
        tabLayout.setupWithViewPager(viewPager);
        mViewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(mViewPagerAdapter);


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setOnTabSelectedListener((TabLayout.OnTabSelectedListener) context);

//        if (classType != null) {
//            if (classType.equals("isFan")) {
//                selectPage(0);
//
//            } else if (classType.equals("isFollower")) {
//                TabLayout.Tab tab = tabLayout.getTabAt(2);
//                selectPage(1);
//            }
//        }

        viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();
            }
        });
       /* if (!CommonAccessPermissionOfCeleb.cartPermissonAvailabilty(getContext(), true, false)) {
            if (CommonAccessPermissionOfCeleb.orderPermissonAvailabilty(getContext(),
                    true, false)) {
                viewPager.setCurrentItem(1);
            }
        }else {
            viewPager.setCurrentItem(0);
        }*/
    }

    void selectPage(int pageIndex) {
        tabLayout.setScrollPosition(pageIndex, 0f, true);
        viewPager.setCurrentItem(pageIndex);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

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


    public class ViewPagerAdapter extends FragmentStatePagerAdapter {
        int tabCount;
        private String[] tabTitles = new String[]{"Cart", "Orders"};

        public ViewPagerAdapter(FragmentManager fm, int tabCount) {
            super(fm);
            this.tabCount = tabCount;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        //Overriding method getItem
        @Override
        public Fragment getItem(int position) {
            //Returning the current tabs
            switch (position) {
                case 0:
                    //once cart came un comment and Check the available code
//                    MyCartfragment myCartfragment = new MyCartfragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("MyCartfragment", tabTitles[position]);
//                    myCartfragment.setArguments(bundle);
//                    if (CommonAccessPermissionOfCeleb.cartPermissonAvailabilty(getContext(), true, false)) {
                        CartFragment cartFragment = new CartFragment();
                        return cartFragment;
//                    }

                case 1:
                   /* if (CommonAccessPermissionOfCeleb.orderPermissonAvailabilty(getContext(),
                            true, false)) {*/
//                    MyCartfragment myOrderfragment = new MyCartfragment(); //old class acces
                        OrdersListfragment myOrderfragment = new OrdersListfragment(); //old class acces
                        Bundle bundle = new Bundle();
                        bundle = new Bundle();
                        bundle.putString("MyCartfragment", tabTitles[position]);
                        myOrderfragment.setArguments(bundle);
                        return myOrderfragment;
//                    }
                default:
                    return null;
            }
        }

        //Overriden method getCount to get the number of tabs
        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }
}
