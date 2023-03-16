package com.example.wishhair.review;

import android.content.Context;

import java.util.List;
import java.util.Vector;

public class WriteReviewAdapter {
    private final Context context;
    private List<WriteReviewItem> items = new Vector<>();

    public WriteReviewAdapter(Context context, List<WriteReviewItem> items) {
        this.context = context;
        this.items = items;
    }

    public class WriteReviewItem {
        private int image;

        public WriteReviewItem(int image) {
            this.image = image;
        }

        public int getImage() {
            return image;
        }

        public void setImage(int image) {
            this.image = image;
        }
    }
}
