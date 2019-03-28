package com.example.android.puzzle;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class PuzzleElementOnTouch implements View.OnTouchListener {
    private PuzzleManager puzzleManager;
    private GestureDetector gestureDetector;
    private int id;
    private MainActivity activity;

    public PuzzleElementOnTouch(MainActivity mainActivity, PuzzleManager puzzleManager, int id) {
        this.puzzleManager = puzzleManager;
        this.gestureDetector = new GestureDetector(mainActivity, new GestureListener());
        this.id = id;
        this.activity = mainActivity;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private static final int SWIPE_THRESHOLD = 100;
        private static final int SWIPE_VELOCITY_THRESHOLD = 100;

        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float diffY = e2.getY() - e1.getY();
                float diffX = e2.getX() - e1.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight();
                        } else {
                            onSwipeLeft();
                        }
                        result = true;
                    }
                }
                else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom();
                    } else {
                        onSwipeTop();
                    }
                    result = true;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            return result;
        }
    }

    public void onSwipeRight() {
        int res = puzzleManager.swipe(id, PuzzleManager.RIGHT);
        if (res >= 0) {
                activity.updatePuzzleElementView(id, res);
        }
    }

    public void onSwipeLeft() {
        int res = puzzleManager.swipe(id, PuzzleManager.LEFT);
        if (res >= 0) {
            activity.updatePuzzleElementView(id, res);
        }
    }

    public void onSwipeTop() {
        int res = puzzleManager.swipe(id, PuzzleManager.UP);
        if (res >= 0) {
            activity.updatePuzzleElementView(id, res);
        }
    }

    public void onSwipeBottom() {
        int res = puzzleManager.swipe(id, PuzzleManager.DOWN);
        if (res >= 0) {
            activity.updatePuzzleElementView(id, res);
        }
    }
}
