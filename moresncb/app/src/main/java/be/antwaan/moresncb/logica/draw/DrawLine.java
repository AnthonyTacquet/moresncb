package be.antwaan.moresncb.logica.draw;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import be.antwaan.moresncb.R;

public class DrawLine extends View {
    private float currentX, currentY;
    private float startX, startY;
    private boolean draw = false;
    private Paint linePaint = new Paint();
    private Paint circlePaint = new Paint();

    private float radius = 25f;

    public DrawLine(Context context) {
        super(context);
        init(context);
    }

    public DrawLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        int color;
        Resources resources = context.getResources();
        if (resources.getConfiguration().uiMode == Configuration.UI_MODE_NIGHT_YES) {
            color = ContextCompat.getColor(getContext(), R.color.lightgrey);
        } else {
            color = ContextCompat.getColor(getContext(), R.color.darkgrey);
        }

        linePaint = new Paint();
        linePaint.setColor(color);
        linePaint.setStrokeWidth(10f);
        linePaint.setStyle(Paint.Style.STROKE);

        float dotSpacing = 10f;
        float dotLength = 5f;
        PathEffect pathEffect = new DashPathEffect(new float[]{dotLength, dotSpacing}, 0f);
        linePaint.setPathEffect(pathEffect);

        circlePaint= new Paint();
        circlePaint.setColor(color);
        circlePaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (draw){
            canvas.drawLine(startX, startY, currentX, currentY, linePaint);
            canvas.drawCircle(startX, startY, radius, circlePaint);
            canvas.drawCircle(currentX, currentY, radius, circlePaint);
        }
    }


    public void setXandY(float x, float y){
        this.startX = x;
        this.startY = y;
    }

    public void setCurrentXandY(float x, float y){
        this.currentX = x;
        this.currentY = y;
    }

    public void setDraw(boolean status){
        draw = status;
    }

    public boolean getDraw(){
        return draw;
    }

    public float getCurrentX() {
        return currentX;
    }

    public void setCurrentX(float currentX) {
        this.currentX = currentX;
    }

    public float getCurrentY() {
        return currentY;
    }

    public void setCurrentY(float currentY) {
        this.currentY = currentY;
    }

    public float getStartX() {
        return startX;
    }

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public float getStartY() {
        return startY;
    }

    public void setStartY(float startY) {
        this.startY = startY;
    }
}
