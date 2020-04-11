package info.sumantv.flow.imagepicker.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import info.sumantv.flow.R;
import info.sumantv.flow.imagepicker.helper.ImageHelper;
import info.sumantv.flow.imagepicker.listener.OnImageClickListener;
import info.sumantv.flow.imagepicker.listener.OnImageSelectionListener;
import info.sumantv.flow.imagepicker.model.Image;
import info.sumantv.flow.imagepicker.ui.common.BaseRecyclerViewAdapter;
import info.sumantv.flow.imagepicker.ui.imagepicker.ImageLoader;

/**
 * Created by hoanglam on 7/31/16.
 */
public class ImagePickerAdapter extends BaseRecyclerViewAdapter<ImagePickerAdapter.ImageViewHolder> {

    private List<Image> images = new ArrayList<>();
    private Image SelectedImage;
    private List<Image> selectedImages = new ArrayList<>();
    private OnImageClickListener itemClickListener;
    private OnImageSelectionListener imageSelectionListener;

    public ImagePickerAdapter(Context context, ImageLoader imageLoader, List<Image> selectedImages, OnImageClickListener itemClickListener) {
        super(context, imageLoader);
        this.itemClickListener = itemClickListener;

        if (selectedImages != null && !selectedImages.isEmpty()) {
            this.selectedImages.addAll(selectedImages);
        }
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = getInflater().inflate(R.layout.imagepicker_item_image, parent, false);
        return new ImageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder viewHolder, final int position) {

        final Image image = images.get(position);
        final boolean isSelected = isSelected(image);

        getImageLoader().loadImage(image.dataFilePath, viewHolder.image);
        viewHolder.gifIndicator.setVisibility(ImageHelper.isGifFormat(image) ? View.VISIBLE : View.GONE);
        viewHolder.imageViewVideo.setVisibility(ImageHelper.isVideoFormat(image) ? View.VISIBLE : View.GONE);
        viewHolder.alphaView.setAlpha(isSelected ? 0.5f : 0.0f);
        viewHolder.container.setForeground(isSelected
                ? ContextCompat.getDrawable(

                getContext(), R.drawable.imagepicker_ic_selected)
                : null);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectedImage = images.get(position);
                boolean shouldSelect = itemClickListener.onImageClick(view, viewHolder.getAdapterPosition(), !isSelected);
                if (isSelected) {
                    removeSelected(image, position);
                } else if (shouldSelect) {
                    addSelected(image, position);
                }
            }
        });
    }

    private boolean isSelected(Image image) {
        for (Image selectedImage : selectedImages) {
            if (selectedImage.dataFilePath.equals(image.dataFilePath)) {
                return true;
            }
        }
        return false;
    }

    public void setOnImageSelectionListener(OnImageSelectionListener imageSelectedListener) {
        this.imageSelectionListener = imageSelectedListener;
    }

    @Override
    public int getItemCount() {
        return images.size();
    }


    public void setData(List<Image> images) {
        if (images != null) {
            this.images.clear();
            this.images.addAll(images);
        }
        notifyDataSetChanged();
    }

    public void addSelected(List<Image> images) {
        selectedImages.addAll(images);
        notifySelectionChanged();
    }

    public void addSelected(Image image, int position) {
        selectedImages.add(image);
        notifyItemChanged(position);
        notifySelectionChanged();
    }

    public void removeSelected(Image image, int position) {
        Iterator<Image> itr = selectedImages.iterator();
        while (itr.hasNext()) {
            Image itrImage = itr.next();
            if (itrImage.getId() == image.getId()) {
                itr.remove();
                break;
            }
        }
        notifyItemChanged(position);
        notifySelectionChanged();
    }

    public void removeAllSelected() {
        selectedImages.clear();
        notifyDataSetChanged();
        notifySelectionChanged();
    }

    private void notifySelectionChanged() {
        if (imageSelectionListener != null) {
            imageSelectionListener.onSelectionUpdate(selectedImages);
        }
    }

    public List<Image> getSelectedImages() {
        return selectedImages;
    }

    public Image getSelectedImage() {
        return SelectedImage;
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {

        private FrameLayout container;
        private ImageView image,imageViewVideo;
        private View alphaView;
        private TextView gifIndicator;

        public ImageViewHolder(View itemView) {
            super(itemView);
            container = (FrameLayout) itemView;
            image = itemView.findViewById(R.id.image_thumbnail);
            alphaView = itemView.findViewById(R.id.view_alpha);
            gifIndicator = itemView.findViewById(R.id.gif_indicator);
            imageViewVideo = itemView.findViewById(R.id.imageViewVideo);

        }

    }

}
