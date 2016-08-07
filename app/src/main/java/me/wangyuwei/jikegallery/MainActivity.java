package me.wangyuwei.jikegallery;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.wangyuwei.galleryview.GalleryEntity;
import me.wangyuwei.galleryview.GalleryView;

public class MainActivity extends Activity {

    private List<GalleryView> mGalleryList = new ArrayList<>();
    private List<GalleryEntity> mEntities = new ArrayList<>();

    private String[] mImgs = new String[]{"http://awb.img1.xmtbang.com/wechatmsg2015/article201505/20150525/thumb/9b65bb01da504a12807f50324fe01e3b.jpg",
//            "http://img.gaonengfun.com/attach/img/2015/12/11/1449820178464698.gif",
            "http://p3.gexing.com/G1/M00/B0/E2/rBACE1IaEE2iXDJcAAAY2UyOZcc821_200x200_3.jpg",
            "http://img4.imgtn.bdimg.com/it/u=665141257,1340555319&fm=21&gp=0.jpg"};

    private String[] mTitles = new String[]{"这是一个简单的测试",
//            "这是一个隐藏的福利",
            "必有小新",
            "Tracy McGrady"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGalleryList.add((GalleryView) findViewById(R.id.gallery0));
        mGalleryList.add((GalleryView) findViewById(R.id.gallery1));
        mGalleryList.add((GalleryView) findViewById(R.id.gallery2));

        for (int i = 0; i < mGalleryList.size(); i++) {
            mEntities.clear();
            for (int j = 0; j < mImgs.length; j++) {
                GalleryEntity entity = new GalleryEntity();
                entity.imgUrl = mImgs[j];
                entity.title = mTitles[j];
                mEntities.add(entity);
            }
            mGalleryList.get(i).addGalleryData(mEntities);
        }

    }

    private void startSmooth() {
        for (int i = 0; i < mGalleryList.size(); i++) {
            final int index = i;
            mGalleryList.get(i).postDelayed(new Runnable() {
                @Override
                public void run() {
                    mGalleryList.get(index).startSmooth();
                }
            }, 100 * i);
        }

    }

    public void onRefresh(View view) {
        startSmooth();
    }

}
