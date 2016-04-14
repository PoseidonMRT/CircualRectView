package com.tt.circualrectview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.nfc.Tag;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.tt.circualrectview.entity.RectModel;
import com.tt.circualrectview.utils.MeasureUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TuoZhaoBing on 2016/4/14 0014.
 */
public class CircualRectView extends View implements ViewTreeObserver.OnGlobalLayoutListener{
    public static final String TAG = "CircualRectView";
    private Context mContext;
    private int mViewWidth,mViewHeight;
    private Paint mRectPaint,mTextPaint,mLinePaint;
    private List<String> mDrawingText;
    private List<RectModel> mRectModels;
    private int mCenterRectHeight = 50;
    private int mChildRectDistance = 200 ;
    private int mSingleChildDegreeDistance;

    public CircualRectView(Context context) {
        super(context);
        init(context);
    }

    public CircualRectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CircualRectView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context){
        mContext = context;
        mRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint = new Paint();
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRectPaint.setStyle(Paint.Style.FILL);
        mRectPaint.setColor(Color.BLUE);
        mTextPaint.setStrokeWidth(3);
        mTextPaint.setColor(Color.RED);
        mLinePaint.setColor(Color.BLUE);
        mRectModels = new ArrayList<>();
        mDrawingText = new ArrayList<>();
        mDrawingText.add("中心节点");
        mDrawingText.add("节点1");
        mDrawingText.add("节点2");
        mDrawingText.add("节点3");
        mDrawingText.add("节点4");
        mDrawingText.add("节点5");
        mDrawingText.add("节点6");
        mDrawingText.add("节点7");
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        mViewWidth = getWidth();
        mViewHeight = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /*
        * 绘制中心节点
        * */
        RectModel rectModel = new RectModel(MeasureUtils.getRect(mViewWidth/2,mViewHeight/2,mDrawingText.get(0).length()*20,mCenterRectHeight),mDrawingText.get(0),0);
        mRectModels.add(rectModel);
        drawNode(canvas,rectModel);
        /*
        * 画周围子节点
        * */
        mSingleChildDegreeDistance = 360 / (mDrawingText.size()-1);

        for (int i=0;i<mDrawingText.size()-1;i++){
            RectModel rectModel1 = new RectModel(MeasureUtils.getRect(mChildRectDistance+mViewWidth/2,mViewHeight/2,mDrawingText.get(i+1).length()*20,mCenterRectHeight),mDrawingText.get(i+1),i*mSingleChildDegreeDistance);
            mRectModels.add(rectModel1);
            drawNode(canvas,rectModel1);
            canvas.drawLine(mViewWidth/2,mViewHeight/2,rectModel1.getCenterX(),mViewHeight/2,mLinePaint);
            canvas.rotate(mSingleChildDegreeDistance,mViewWidth/2,mViewHeight/2);
        }

        super.onDraw(canvas);
    }

    public void drawNode(Canvas canvas,RectModel rectModel){
        /*
        * 锁定画布，将画布旋转回原始坐标系
        * */
        canvas.save();
        canvas.rotate(-rectModel.getAngle(),rectModel.getCenterX(),rectModel.getCenterY());
        canvas.drawRect(rectModel.getLeft(),rectModel.getTop(),rectModel.getRight(),rectModel.getBottom(),mRectPaint);
        Paint.FontMetricsInt fontMetrics = mRectPaint.getFontMetricsInt();
        int baseline = (rectModel.getBottom() + rectModel.getTop() - fontMetrics.bottom - fontMetrics.top) / 2;
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(rectModel.getText(), rectModel.getCenterX(), baseline, mTextPaint);
        /*
        * 释放画布
        * */
        canvas.restore();
    }
}
