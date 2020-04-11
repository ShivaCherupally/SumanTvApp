package info.sumantv.flow.ipoll.interfaces.recyclerview;

import java.util.List;

/**
 * Created by User on 11-07-2018.
 **/

public interface IRecyclerViewAdapter
{
    public void loadMore(List<Object> subList);
    public void addBottomLoader();
    public void addRetryOption();
    public void onRetry();
}
