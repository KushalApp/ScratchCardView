package com.stackhour.lib.scratchcard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/**
 * Created by Kushal on 23,Feb,2020
 */
public class ScratchCardView extends View {

    private int scratchColor = -1;
    private float scratchTouchAreaWidth;

    private final int DEFAULT_SCRATCH_COLOR = 0xFFFFDE03;
    private final float DEFAULT_TOUCH_AREA_WIDTH = dpToPx(25);
    private int scratchImage;
    private Bitmap bitmap;
    private Drawable drawable;
    private Path fullScratchPath;
    private Path intermediatesScratchPath;


    public ScratchCardView(Context context) {
        super(context);
        init();
    }

    public ScratchCardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //initialize
        init();
        config(context, attrs);
    }

    public ScratchCardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //initialize
        init();
        config(context, attrs);
    }

    /**
     * Initialize the view
     */
    private void init(){

        //Path that represents the sum of all intermediate scratch path
        fullScratchPath = new Path();
        fullScratchPath.setFillType(Path.FillType.INVERSE_WINDING);

        // Intermediate Scratch Path
        intermediatesScratchPath = new Path();
        intermediatesScratchPath.setFillType(Path.FillType.INVERSE_WINDING);

        registerTouchListener();

    }

    /**
     * Initialize the view with user-configured attributes
     *
     * @param context View Context
     * @param attrs   to read user-configured attributes value
     */
    private void config(Context context, @Nullable AttributeSet attrs) {


        //Extract user configured values of the ScratchCardView
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.ScratchCardViewAttrs, 0, 0);

        try {

            //reads user-configured scratch card top layer color
            scratchColor = typedArray.getColor(R.styleable.ScratchCardViewAttrs_scratchColor,
                    DEFAULT_SCRATCH_COLOR);
            //reads user-configured scratch card Image/Watermark that masks/hides the content
            scratchImage = typedArray.getResourceId(R.styleable.ScratchCardViewAttrs_scratchImage, -1);
            //reads user-configured scratch touch area width
            scratchTouchAreaWidth = typedArray.getDimension(R.styleable.ScratchCardViewAttrs_scratchTouchWidth,
                    DEFAULT_TOUCH_AREA_WIDTH);

            if(scratchImage != -1) {
                drawable = ContextCompat.getDrawable(getContext(), scratchImage);

                if (drawable instanceof BitmapDrawable) {
                    //Supports bitmap creation from raster image drawables (png,jpg, etc.,)
                    bitmap = BitmapFactory.decodeResource(getResources(), scratchImage);
                } else if (drawable instanceof VectorDrawable) {

                    //Supports bitmap creation from VectorDrawable
                    bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                    drawable.draw(canvas);

                }
            }


        } finally {
            //once the user-configured values are read, recycle the TypedArray
            typedArray.recycle();
        }


    }


    /**
     * Method listens to finger movement on the ScratchCardView
     */
    private void registerTouchListener() {

        setOnTouchListener(new View.OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                if (event.getAction() == MotionEvent.ACTION_MOVE ||
                        event.getAction() == MotionEvent.ACTION_POINTER_DOWN ||
                        event.getAction() == MotionEvent.ACTION_DOWN) {

                    //clears earlier set path
                    intermediatesScratchPath.reset();
                    intermediatesScratchPath.addCircle(event.getX(), event.getY(), scratchTouchAreaWidth,
                            Path.Direction.CCW);
                    fullScratchPath.addPath(intermediatesScratchPath);
                    invalidate();
                }

                return true;
            }
        });
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //transformation
        canvas.save();
        canvas.clipPath(fullScratchPath);

        //if user has not configured any image/watermark - view shows a colored background on top of
        // the content to be hidden
        if (scratchColor != -1) {
            canvas.drawColor(scratchColor);
        }

        //if user has configured an image as a mask to hide the content center-align the image to the view
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, halfValue(getWidth()) - halfValue(bitmap.getWidth()),
                    halfValue(getHeight()) - halfValue(bitmap.getHeight()), null);
        }

        canvas.restore();


    }


    /**
     * Converts Density independent pixel(DIP) value to pixel
     *
     * @param dpValue DIP
     * @return value in pixels
     */
    public int dpToPx(int dpValue) {
        return (int) (dpValue * getResources().getDisplayMetrics().density + 0.5);
    }

    private int halfValue(int value){
        return (int) (value * 0.5);
    }

    /**
     * Resets the scratch path on the view
     */
    public void reset() {

        fullScratchPath.reset();
        invalidate();
    }

}
