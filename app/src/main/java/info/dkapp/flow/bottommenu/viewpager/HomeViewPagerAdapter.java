package info.dkapp.flow.bottommenu.viewpager;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import info.dkapp.flow.bottommenu.celebritiestab.CelebritiesTabFragment;
import info.dkapp.flow.bottommenu.menuitemsmanager.fragments.HomeMenuFragment;
import info.dkapp.flow.chat.Fragment.FragmentTabsChat;
import info.dkapp.flow.ipoll.fragments.dummy.DummyFragment;
import info.dkapp.flow.ipoll.fragments.feeds.FeedsFragment;


/**
 * Created by User on 28-07-2018.
 **/

public class HomeViewPagerAdapter extends FragmentPagerAdapter {
    int type = 1;
    String CelebId = null;
    Context context;
    int addPosition = 0;
    boolean isFromAdd = false;

    public HomeViewPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    public HomeViewPagerAdapter(FragmentManager fm, String CelebId, Context mContext) {
        super(fm);
        this.CelebId = CelebId;
        this.context = mContext;

    }
    public HomeViewPagerAdapter(FragmentManager fm,Context mContext,int addPosition,boolean isFromAdd) {
        super(fm);
        this.context = mContext;
        this.addPosition = addPosition;
        this.isFromAdd = isFromAdd;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (type == 1) {
            switch (position) {
                case 0:
                    if (CelebId != null) {
                        fragment = FeedsFragment.newInstance(null, CelebId);
                    } else {
                        fragment = FeedsFragment.newInstance(null, null);
                    }
                    break;
                case 1:
//                    fragment = CelebrityBaseFragment.newInstance();
                    //For new screens CK
                    fragment = CelebritiesTabFragment.newInstance();
                    break;
                case 2:
                    fragment = DummyFragment.newInstance(null, null);
                    Log.e("middleicon", true + "");
                    break;
                case 3:
                    fragment = FragmentTabsChat.newInstance(null, addPosition,isFromAdd);
                    //fragment = PrefrencesExpentableListviewFragment.newInstance(false, null);
                    break;
                case 4:
                    fragment = HomeMenuFragment.newInstance(null, null);
                    break;
            }
            return fragment;
        } else {
            return fragment;
        }
    }

    @Override
    public int getCount() {
        return type == 1 ? CelebId == null ? 5 : 1 : 0;
    }

}
