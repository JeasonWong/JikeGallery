package me.wangyuwei.galleryview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * 作者： 巴掌 on 16/8/6 11:15
 * Github: https://github.com/JeasonWong
 */
public class GalleryView extends FrameLayout {

    private Gallery mGallery;
    private TitleView mTitleView;

    public GalleryView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GalleryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.gallery_view, this);
        mGallery = (Gallery) findViewById(R.id.gallery);
        mTitleView = (TitleView) findViewById(R.id.title_view);
    }

    public void startSmooth() {
        mGallery.startSmooth();
        mTitleView.startSmooth();
    }

    public void addGalleryData(List<GalleryEntity> listEntities) {
        List<String> imgList = new ArrayList<>();
        for (GalleryEntity entity : listEntities) {
            imgList.add(entity.imgUrl);
        }
        mGallery.setImgList(imgList);

        List<String> titleList = new ArrayList<>();
        for (GalleryEntity entity : listEntities) {
            titleList.add(entity.title);
        }
        mTitleView.setTitleList(titleList);
    }

}
