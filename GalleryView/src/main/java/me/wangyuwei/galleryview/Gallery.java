package me.wangyuwei.galleryview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： 巴掌 on 16/8/4 23:25
 * Github: https://github.com/JeasonWong
 */
public class Gallery extends SmoothViewGroup {

    private List<String> mImgList = new ArrayList<>();
    private ImageView[] mImgs = new ImageView[2];
    private View mShadowView;

    public Gallery(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Gallery(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initView() {

        //如果没有内容，则不进行初始化操作
        if (mImgList.size() == 0) {
            return;
        }

        removeAllViews();

        MarginLayoutParams params = new MarginLayoutParams(mWidth, mHeight);

        //两个ImageView加载前两张图
        for (int i = 0; i < mImgs.length; i++) {
            mImgs[i] = new ImageView(getContext());
            addViewInLayout(mImgs[i], -1, params, true);
            Glide.with(getContext()).load(getImgPath(i)).centerCrop().into(mImgs[i]);
        }

        //创建阴影View
        mShadowView = new View(getContext());
        mShadowView.setBackgroundColor(Color.parseColor("#60000000"));
        mShadowView.setAlpha(0);
        addViewInLayout(mShadowView, -1, params, true);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int cCount = getChildCount();
        MarginLayoutParams cParams;

        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            cParams = (MarginLayoutParams) childView.getLayoutParams();

            int cl = 0, ct = 0, cr, cb;

            if (isOddCircle()) {
                if (i == 1) {
                    cl = cParams.leftMargin;
                    ct = mSmoothMarginTop + mHeight;
                } else if (i == 0) {
                    cl = cParams.leftMargin;
                    ct = mSmoothMarginTop;
                }
            } else {
                if (i == 0) {
                    cl = cParams.leftMargin;
                    ct = mSmoothMarginTop + mHeight;
                } else if (i == 1) {
                    cl = cParams.leftMargin;
                    ct = mSmoothMarginTop;
                }
            }
            //控制shadowView
            if (i == 2) {
                cl = cParams.leftMargin;
                ct = mSmoothMarginTop + mHeight;
            }

            cr = cl + mWidth;
            cb = ct + mHeight;
            childView.layout(cl, ct, cr, cb);
        }

    }

    @Override
    protected void doAnimFinish() {
        if (isOddCircle()) {
            Glide.with(getContext()).load(getImgPath(mRepeatTimes + 1)).centerCrop().into(mImgs[0]);
        } else {
            Glide.with(getContext()).load(getImgPath(mRepeatTimes + 1)).centerCrop().into(mImgs[1]);
        }
        mShadowView.setAlpha(0);
    }

    @Override
    protected void doAnim() {
        mShadowView.setAlpha(((1 - (-mSmoothMarginTop) / (float) mHeight)));
        requestLayout();
    }

    public void setImgList(List<String> imgList) {
        mImgList = imgList;
        initView();
    }

    /**
     * 获取图片地址
     *
     * @param position 位置
     * @return 图片地址
     */
    private String getImgPath(int position) {
        position = position % mImgList.size();
        return mImgList.get(position);
    }

}
