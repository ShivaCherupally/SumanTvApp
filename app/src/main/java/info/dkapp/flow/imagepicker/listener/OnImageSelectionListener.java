package info.dkapp.flow.imagepicker.listener;


import java.util.List;

import info.dkapp.flow.imagepicker.model.Image;

/**
 * Created by hoanglam on 8/18/17.
 */

public interface OnImageSelectionListener {
    void onSelectionUpdate(List<Image> images);
}
