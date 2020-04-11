package info.dkapp.flow.ipoll.adapters.feeds.celebrities;

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.dkapp.flow.bottommenu.activity.BottomMenuActivity;
import info.dkapp.flow.bottommenu.activity.HelperActivity;

import info.dkapp.flow.R;
import info.dkapp.flow.celebflow.constants.Constants;
import info.dkapp.flow.ipoll.viewholders.CelebrityViewHolder;
import info.dkapp.flow.retrofitcall.ApiClient;
import info.dkapp.flow.retrofitcall.SessionManager;
import info.dkapp.flow.stories.models.StoryProfileInfo;
import info.dkapp.flow.userflow.Util.Common;
//import jp.wasabeef.fresco.processors.BlurPostprocessor;

import java.util.ArrayList;
import java.util.List;


public class StoriesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<StoryProfileInfo> list;
    Context context;

    public StoriesAdapter(Context context, List<StoryProfileInfo> list) {
        Log.e("storiesCelebList", list.size() + "");
        this.context = context;
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return new CelebrityViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_celeb_profile_circle_item, parent, false));
        if (viewType == 0 && Common.isCelebStatus(context)) {
            return new AddStoryAdapter(LayoutInflater.from(parent.getContext()).inflate(R.layout.add_story, parent, false));
        } else {
            return new CelebrityViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_celeb_profile_circle_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof AddStoryAdapter) {
            AddStoryAdapter addStoryAdapter = (AddStoryAdapter) holder;
            Common.getInstance().setGlideImage(context, ApiClient.BASE_URL + SessionManager.userLogin.profilePic, addStoryAdapter.addImage);
            addStoryAdapter.rLCreateStore.setOnClickListener(view -> {
                ((BottomMenuActivity) context).openCreateStore();
            });

        } else if (holder instanceof CelebrityViewHolder) {
            if (Common.isCelebStatus(context)) {
                position = position - 1;
            }
            CelebrityViewHolder celebrityViewHolder = (CelebrityViewHolder) holder;
            try {
                if (list.get(position).storyMemberInfo != null) {
                    if (list.get(position).isStorySeen) {
                        celebrityViewHolder.circleFramelayout.setBackgroundResource(0);
                        celebrityViewHolder.circleFramelayout.setBackgroundResource(R.drawable.circle_grey);
                    } else {
                        celebrityViewHolder.circleFramelayout.setBackgroundResource(0);
                        celebrityViewHolder.circleFramelayout.setBackgroundResource(R.drawable.circle_skyblue);
                    }

                    if (list.get(position).storyMemberInfo.id != null && !list.get(position).storyMemberInfo.id.isEmpty()) {
                        if (list.get(position).storyMemberInfo.firstName != null && !list.get(position).storyMemberInfo.firstName.isEmpty()) {
                            if (list.get(position).storyMemberInfo.id.equals(Common.isLoginId())) {
                                celebrityViewHolder.user_name.setText("My Story");
                            } else {
                                celebrityViewHolder.user_name.setText(Character.toUpperCase(list.get(position).storyMemberInfo.firstName.charAt(0))
                                        + list.get(position).storyMemberInfo.firstName.substring(1));
                            }
                        } else {
                            celebrityViewHolder.user_name.setText("");
                        }


                        if (list.get(position).storyMemberInfo.avtarImgPath != null && !list.get(position).storyMemberInfo.avtarImgPath.isEmpty()) {
                            celebrityViewHolder.shadowuser_image.setVisibility(View.GONE);
                            celebrityViewHolder.user_image.setVisibility(View.VISIBLE);
                            Common.getInstance().setGlideImage(context, ApiClient.BASE_URL + list.get(position).storyMemberInfo.avtarImgPath, celebrityViewHolder.user_image);
                        } else {
                            celebrityViewHolder.shadowuser_image.setVisibility(View.GONE);
                            celebrityViewHolder.user_image.setImageResource(R.drawable.ic_profile_circle_pleace_holder);
                        }
                    }
                }
            } catch (Exception e) {

            }
            int finalPosition = position;
            celebrityViewHolder.profileLayout.setOnClickListener(view -> {
                Intent intent = new Intent(context, HelperActivity.class);
                intent.putExtra(Constants.FRAGMENT_TITLE, "Stories");
                intent.putExtra("POSITION", finalPosition);
                intent.putParcelableArrayListExtra("celeblist", (ArrayList<? extends Parcelable>) list);
                intent.putExtra(Constants.FRAGMENT_KEY, 2020);
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        if (Common.isCelebStatus(context)) {
            return (list.size() + 1);
        } else {
            return list.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public Bitmap applyReflection(Drawable drawable) {
        Bitmap originalImage = ((BitmapDrawable) drawable).getBitmap();
        final int reflectionGap = 4;
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);
        Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height / 2, width, height / 2, matrix, false);
        Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height / 2), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(originalImage, 0, 0, null);
        Paint defaultPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, defaultPaint);
        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, originalImage.getHeight(), 0,

                bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() + reflectionGap, paint);

        return bitmapWithReflection;
    }


}
