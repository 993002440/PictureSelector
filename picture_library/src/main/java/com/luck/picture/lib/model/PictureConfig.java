package com.luck.picture.lib.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.luck.picture.lib.R;
import com.luck.picture.lib.ui.PictureExternalPreviewActivity;
import com.luck.picture.lib.ui.PictureImageGridActivity;
import com.luck.picture.lib.ui.PictureVideoPlayActivity;
import com.yalantis.ucrop.entity.LocalMedia;
import com.yalantis.ucrop.util.Utils;

import java.io.Serializable;
import java.util.List;


/**
 * author：luck
 * project：PictureSelector
 * package：com.luck.picture.util
 * email：893855882@qq.com
 * data：17/1/5
 */
public class PictureConfig {
    public FunctionOptions options;
    public static PictureConfig sInstance;

    public static PictureConfig getInstance() {
        if (sInstance == null) {
            synchronized (PictureConfig.class) {
                if (sInstance == null) {
                    sInstance = new PictureConfig();
                }
            }
        }
        return sInstance;
    }

    public PictureConfig() {
        super();
    }


    public OnSelectResultCallback resultCallback;

    public OnSelectResultCallback getResultCallback() {
        return resultCallback;
    }

    public PictureConfig init(FunctionOptions options) {
        this.options = options;
        return this;
    }

    /**
     * 启动相册
     */
    public void openPhoto(Context mContext, OnSelectResultCallback resultCall) {
        if (Utils.isFastDoubleClick()) {
            return;
        }
        if (options == null) {
            options = new FunctionOptions.Builder().create();
        }
        Intent intent = new Intent(mContext, PictureImageGridActivity.class);
        intent.putExtra(FunctionConfig.EXTRA_THIS_CONFIG, options);
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.slide_bottom_in, 0);
        // 绑定图片接口回调函数事件
        resultCallback = resultCall;
    }

    /**
     * start to camera、preview、crop
     */
    public void startOpenCamera(Context mContext, OnSelectResultCallback resultCall) {
        if (options == null) {
            options = new FunctionOptions.Builder().create();
        }
        Intent intent = new Intent(mContext, PictureImageGridActivity.class);
        intent.putExtra(FunctionConfig.EXTRA_THIS_CONFIG, options);
        intent.putExtra(FunctionConfig.FUNCTION_TAKE, true);
        mContext.startActivity(intent);
        ((Activity) mContext).overridePendingTransition(R.anim.fade, R.anim.hold);
        // 绑定图片接口回调函数事件
        resultCallback = resultCall;
    }

    /**
     * 外部图片预览
     *
     * @param position
     * @param medias
     */
    public void externalPicturePreview(Context mContext, int position, List<LocalMedia> medias) {
        if (medias != null && medias.size() > 0) {
            Intent intent = new Intent();
            intent.putExtra(FunctionConfig.EXTRA_PREVIEW_SELECT_LIST, (Serializable) medias);
            intent.putExtra(FunctionConfig.EXTRA_POSITION, position);
            intent.setClass(mContext, PictureExternalPreviewActivity.class);
            mContext.startActivity(intent);
            ((Activity) mContext).overridePendingTransition(R.anim.toast_enter, 0);
        }
    }

    /**
     * 外部视频播放
     *
     * @param path
     */
    public void externalPictureVideo(Context mContext, String path) {
        if (!Utils.isNull(path)) {
            Intent intent = new Intent();
            intent.putExtra("video_path", path);
            intent.setClass(mContext, PictureVideoPlayActivity.class);
            mContext.startActivity(intent);
        }
    }


    /**
     * 处理结果
     */
    public interface OnSelectResultCallback {
        /**
         * 处理成功
         *
         * @param resultList
         */
        public void onSelectSuccess(List<LocalMedia> resultList);

    }
}
