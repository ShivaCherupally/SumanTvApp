package info.sumantv.flow.utils.RecyclerUtil;

import androidx.recyclerview.widget.RecyclerView;

public interface IRecyclerViewCommon {

    public void setSkelltonView(RecyclerView recyclerView, boolean firstTime);

    public void nodataList(RecyclerView recyclerView, String title, String subTitle, int imageResource);

    public boolean checkInterConnection(RecyclerView recyclerView);

    public void showSnackBar(String snackBarText, int type);

    public void fetchDataFromServer(boolean firstTime);


}
