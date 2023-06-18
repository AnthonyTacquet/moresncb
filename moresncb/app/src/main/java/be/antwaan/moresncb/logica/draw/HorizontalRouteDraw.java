package be.antwaan.moresncb.logica.draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.ContextCompat;

import be.antwaan.moresncb.R;

public class HorizontalRouteDraw extends View {

    private Paint linePaint;
    private Paint circlePaint;
    private Bitmap iconBitmap;

    public HorizontalRouteDraw(Context context) {
        super(context);
        init();
    }

    public HorizontalRouteDraw(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        linePaint = new Paint();
        linePaint.setColor(ContextCompat.getColor(getContext(), R.color.lightblue));
        linePaint.setStrokeWidth(8.0f);

        circlePaint = new Paint();
        circlePaint.setColor(ContextCompat.getColor(getContext(), R.color.lightblue));

        iconBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.baseline_train_24);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        // Draw the horizontal line
        canvas.drawLine(0, height / 2f, width, height / 2f, linePaint);

        // Draw the circle at the start of the line
        float circleRadius = 25.0f;
        float circleX = circleRadius;
        float circleY = height / 2f;

        // Draw the circle background
        canvas.drawCircle(circleX, circleY, circleRadius, circlePaint);

        Drawable iconDrawable = ContextCompat.getDrawable(getContext(), R.drawable.baseline_train_24);
        int color = ContextCompat.getColor(getContext(), R.color.white);

        iconDrawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);

        if (iconDrawable != null) {
            // Calculate the bounds of the destination rectangle
            float iconSize = 20.0f;
            int left = (int) (circleX - iconSize);
            int top = (int) (circleY - iconSize);
            int right = (int) (circleX + iconSize);
            int bottom = (int) (circleY + iconSize);
            Rect destinationRect = new Rect(left, top, right, bottom);

            // Set the bounds of the drawable
            iconDrawable.setBounds(destinationRect);

            // Draw the drawable onto the canvas
            iconDrawable.draw(canvas);
        } else {
            // Handle the case when the iconDrawable is null
            // Display an error or fallback icon
        }
    }
}