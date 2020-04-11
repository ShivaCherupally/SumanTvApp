//package info.celebkonnect.flow.commonuses;
//
//import android.support.v7.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
///**
// * Created by Shiva on 5/29/2018.
// */
//
//public abstract class EndlessOnScrollListener extends RecyclerView.OnScrollListener {
//
//    public static String TAG = EndlessOnScrollListener.class.getSimpleName();
//
//    // use your LayoutManager instead
//    private LinearLayoutManager llm;
//
//    public EndlessOnScrollListener() {
//        this.llm = llm;
//    }
//
//    @Override
//    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//        super.onScrolled(recyclerView, dx, dy);
//
//        if (!recyclerView.canScrollVertically(1)) {
//            onScrolledToEnd();
//        }
//    }
//
//    public abstract void onScrolledToEnd();
//}
