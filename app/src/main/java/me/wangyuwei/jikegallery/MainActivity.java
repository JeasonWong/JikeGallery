package me.wangyuwei.jikegallery;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Button btn= (Button) findViewById(R.id.btn_refresh);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map map = new HashMap();
                map.put("巴", "掌");
                map.put("菜", "比");

                map.put("TF-Boys", "嘿嘿嘿");
                map.put("id", "111");
                v.setTag(R.id.btn_refresh, map);
            }
        });
        HookView(btn);
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

    public void onClick(View view) {
        Map map = new HashMap();
                map.put("巴", "掌");
                map.put("菜", "比");

                map.put("TF-Boys", "嘿嘿嘿");
                map.put("id", "111");


        view.setTag(R.id.btn_refresh, map);
    }

    private void HookView(View view ){
        try {
            Class clazzView =Class.forName("android.view.View");

            Method method=clazzView.getDeclaredMethod("getListenerInfo");

            method.setAccessible(true);

            Object listenerInfo = method.invoke(view);
            //继续拿下ListenerInfo内部类的Class对象
            Class clazzInfo = Class.forName("android.view.View$ListenerInfo");
            //拿到主角mOnClickListener成员变量
            Field field = clazzInfo.getDeclaredField("mOnClickListener");
            field.set(listenerInfo,new HookListener((View.OnClickListener) field.get(listenerInfo)));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    

    public class HookListener implements View.OnClickListener{

        private View.OnClickListener mOriginalListener;

        public HookListener(View.OnClickListener originalListener) {
            mOriginalListener = originalListener;
        }
        @Override
        public void onClick(View v) {
            if (mOriginalListener != null) {
                mOriginalListener.onClick(v);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("hook succeed.\n");
            Object obj = v.getTag(R.id.btn_refresh);
            if (obj instanceof HashMap && !((Map) obj).isEmpty()) {
                for (Map.Entry<String, String> entry : ((Map<String, String>) obj).entrySet()) {
                    sb.append("key => ")
                            .append(entry.getKey())
                            .append(" ")
                            .append("value => ")
                            .append(entry.getValue())
                            .append("\n");
                }
            } else {
                sb.append("params => null\n");
            }

            Toast.makeText(v.getContext(), sb.toString(), Toast.LENGTH_LONG).show();
        }

    }

    private void startSmooth() {
        for (int i = 0; i < mGalleryList.size(); i++) {
            final int index = i;
//            mGalleryList.get(i).postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mGalleryList.get(index).startSmooth();
//                }
//            }, 100 * i);
            mGalleryList.get(index).startSmooth();
        }

    }

    public void onRefresh(View view) {
        Map map = new HashMap();
        map.put("巴", "掌");
        map.put("菜", "比");

        map.put("TF-Boys", "嘿嘿嘿");
        map.put("id", "111");


        view.setTag(R.id.btn_refresh, map);

        startSmooth();
    }

}
