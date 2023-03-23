package com.example.wishhair.MyPage;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerDecoration extends RecyclerView.ItemDecoration {

    private final int decoValue;

    public RecyclerDecoration(int decoValue) {
        this.decoValue = decoValue;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.right = decoValue;
//        if(parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() -1)
//            outRect.right = decoValue;
    }
}
