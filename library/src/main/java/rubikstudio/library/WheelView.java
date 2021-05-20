package rubikstudio.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.List;
import java.util.Random;

import rubikstudio.library.model.WheelItem;



public class WheelView extends RelativeLayout implements PielView.PieRotateListener {
    private int mBackgroundColor;
    private int mTextColor;
    private int mTopTextSize;
    private int mSecondaryTextSize;
    private int mBorderColor;
    private int mTopTextPadding;
    private int mEdgeWidth;
    private Drawable mCenterImage;
    private Drawable mCursorImage;

    private PielView pielView;
    private ImageView ivCursorView;

    private RoundItemSelectedListener mRoundItemSelectedListener;

    @Override
    public void rotateDone(int index) {
        if (mRoundItemSelectedListener != null) {
            mRoundItemSelectedListener.onRoundItemSelected(index);
        }
    }

    public interface RoundItemSelectedListener {
        void onRoundItemSelected(int index);
    }

    public void setOnRoundItemSelectedListener(RoundItemSelectedListener listener) {
        this.mRoundItemSelectedListener = listener;
    }


    public WheelView(Context context) {
        super(context);
        init(context, null);
    }

    public WheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * @param ctx
     * @param attrs
     */
    private void init(Context ctx, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = ctx.obtainStyledAttributes(attrs, R.styleable.WheelView);
            mBackgroundColor = typedArray.getColor(R.styleable.WheelView_lkwBackgroundColor, 0xffcc0000);
            mTopTextSize = typedArray.getDimensionPixelSize(R.styleable.WheelView_lkwTopTextSize, (int) WheelUtils.convertDpToPixel(10f, getContext()));
            mSecondaryTextSize = typedArray.getDimensionPixelSize(R.styleable.WheelView_lkwSecondaryTextSize, (int) WheelUtils.convertDpToPixel(20f, getContext()));
            mTextColor = typedArray.getColor(R.styleable.WheelView_lkwTopTextColor, 0);
            mTopTextPadding = typedArray.getDimensionPixelSize(R.styleable.WheelView_lkwTopTextPadding, (int) WheelUtils.convertDpToPixel(10f, getContext())) + (int) WheelUtils.convertDpToPixel(10f, getContext());
            mCursorImage = typedArray.getDrawable(R.styleable.WheelView_lkwCursor);
            mCenterImage = typedArray.getDrawable(R.styleable.WheelView_lkwCenterImage);
            mEdgeWidth = typedArray.getInt(R.styleable.WheelView_lkwEdgeWidth, 10);
            mBorderColor = typedArray.getColor(R.styleable.WheelView_lkwEdgeColor, 0);
            typedArray.recycle();
        }

        LayoutInflater inflater = LayoutInflater.from(getContext());
        FrameLayout frameLayout = (FrameLayout) inflater.inflate(R.layout.lucky_wheel_layout, this, false);

        pielView = frameLayout.findViewById(R.id.pieView);
        ivCursorView = frameLayout.findViewById(R.id.cursorView);

        pielView.setPieRotateListener(this);
        pielView.setPieBackgroundColor(mBackgroundColor);
        pielView.setTopTextPadding(mTopTextPadding);
        pielView.setTopTextSize(mTopTextSize);
        pielView.setSecondaryTextSizeSize(mSecondaryTextSize);
        pielView.setPieCenterImage(mCenterImage);
        pielView.setBorderColor(mBorderColor);
        pielView.setBorderWidth(mEdgeWidth);


        if (mTextColor != 0)
            pielView.setPieTextColor(mTextColor);

        ivCursorView.setImageDrawable(mCursorImage);

        addView(frameLayout);
    }

    
    public boolean isTouchEnabled() {
        return pielView.isTouchEnabled();
    }

    public void setTouchEnabled(boolean touchEnabled) {
        pielView.setTouchEnabled(touchEnabled);
    }

    
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //This is to control that the touch events triggered are only going to the PieView
        for (int i = 0; i < getChildCount(); i++) {
            if (isPielView(getChildAt(i))) {
                return super.dispatchTouchEvent(ev);
            }
        }
        return false;
    }

    private boolean isPielView(View view) {
        if (view instanceof ViewGroup) {
            for (int i = 0; i < getChildCount(); i++) {
                if (isPielView(((ViewGroup) view).getChildAt(i))) {
                    return true;
                }
            }
        }
        return view instanceof PielView;
    }

    public void setWheelBackgrouldColor(int color) {
        pielView.setPieBackgroundColor(color);
    }

    public void setWheelCursorImage(int drawable) {
        ivCursorView.setBackgroundResource(drawable);
    }

    public void setLuckyWheelCenterImage(Drawable drawable) {
        pielView.setPieCenterImage(drawable);
    }

    public void setBorderColor(int color) {
        pielView.setBorderColor(color);
    }

    public void setLuckyWheelTextColor(int color) {
        pielView.setPieTextColor(color);
    }

    /**
     * @param data
     */
    public void setData(List<WheelItem> data) {
        pielView.setData(data);
    }

    /**
     * @param numberOfRound
     */
    public void setRound(int numberOfRound) {
        pielView.setRound(numberOfRound);
    }

    /**
     * @param fixedNumber
     */
    public void setPredeterminedNumber(int fixedNumber) {
        pielView.setPredeterminedNumber(fixedNumber);
    }

    public void startWheelWithTargetIndex(int index) {
        pielView.rotateTo(index);
    }
    
    public void startWheelWithRandomTarget() {
        Random r = new Random();
        pielView.rotateTo(r.nextInt(pielView.getLuckyItemListSize() - 1));
    }

    public void setTextSize(int textSize){
        pielView.setTopTextSize(textSize);
    }
}
