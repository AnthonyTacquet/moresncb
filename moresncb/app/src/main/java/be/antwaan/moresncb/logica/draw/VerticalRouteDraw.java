package be.antwaan.moresncb.logica.draw;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.ContextCompat;

import be.antwaan.moresncb.R;

public class VerticalRouteDraw extends View {

    private Paint linePaint;
    private Paint circlePaint;
    private Bitmap iconBitmap;

    public VerticalRouteDraw(Context context) {
        super(context);
        init();
    }

    public VerticalRouteDraw(Context context, AttributeSet attrs) {
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

        // Draw the vertical line
        canvas.drawLine(width / 2f, 0, width / 2f, height, linePaint);

        // Draw the hollow circle at the bottom of the line
        float circleRadius = 25.0f;
        float circleX = width / 2f;
        float circleY = height - circleRadius;

        // Draw the hollow circle background
        canvas.drawCircle(circleX, circleY, circleRadius, circlePaint);

        // Draw the filled circle with icon at the start of the line
        float startCircleRadius = 25.0f;
        float startCircleX = width / 2f;
        float startCircleY = startCircleRadius;

        // Draw the filled circle background
        canvas.drawCircle(startCircleX, startCircleY, startCircleRadius, circlePaint);

        Drawable iconDrawable = ContextCompat.getDrawable(getContext(), R.drawable.baseline_train_24);
        int color = ContextCompat.getColor(getContext(), R.color.white);

        if (iconDrawable != null) {
            // Apply color to the drawable
            iconDrawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);

            // Calculate the bounds of the destination rectangle
            float iconSize = 20.0f;
            int left = (int) (startCircleX - iconSize);
            int top = (int) (startCircleY - iconSize);
            int right = (int) (startCircleX + iconSize);
            int bottom = (int) (startCircleY + iconSize);
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