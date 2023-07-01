package be.antwaan.moresncb.logica;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class ItemSpacingDecorator extends RecyclerView.ItemDecoration {

    private int spacing;
    private int spanCount;

    public ItemSpacingDecorator(int spacing, int spanCount) {
        this.spacing = spacing;
        this.spanCount = spanCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int column = position % spanCount;

        outRect.left = spacing - column * spacing / spanCount;
        outRect.right = (column + 1) * spacing / spanCount;

        if (position >= spanCount) {
            outRect.top = spacing;
        }
    }
}
