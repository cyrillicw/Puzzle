package com.example.android.puzzle;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private GridLayout gridLayout;
    private PuzzleElement[] puzzleElements;
    private PuzzleManager puzzleManager;
    private int n;
    private SharedPreferences preferences;
    private String order = "points";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getPreferences(MODE_PRIVATE);
        gridLayout = findViewById(R.id.framePuzzle);
        n = 4;
        puzzleElements = new PuzzleElement[n * n];
        fillPuzzleElements();
//        TextView textView = (TextView) gridLayout.getChildAt(0);
//        GridLayout.LayoutParams layoutParams = (GridLayout.LayoutParams) textView.getLayoutParams();
//        textView.setLayoutParams(new GridLayout.LayoutParams(GridLayout.spec(3), GridLayout.spec(3)));
//        /*for (int i = 0; i < gridLayout.getChildCount(); i++) {
//            TextView child = gridLayout.getChildAt(i);
//        }*/
    }

    void updatePuzzleElementView(int ... points) {
        for (int point: points) {
            TextView child = (TextView)gridLayout.getChildAt(point);
            PuzzleElement element = puzzleElements[puzzleManager.getPuzzleElementId(point)];
            int text = element.getId() + 1;
            child.setText(text == n * n ? "" : Integer.toString(text));
            child.setBackground(element.getColor());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_shuffle) {
            shuffleView();
        }
        return super.onOptionsItemSelected(item);
    }

    private void shuffleView() {
        puzzleManager.shufflePuzzle();
        updateView();
    }

    private void updateView() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                updatePuzzleElementView(i * n + j);
            }
        }
    }

    private void fillPuzzleElements() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                TextView child = (TextView) gridLayout.getChildAt(i * n + j);
                puzzleElements[i * n + j] = new PuzzleElement(child.getBackground(), i * n + j);
            }
        }
    }

    @Override
    protected void onStop() {
        long memory = puzzleManager.memorize();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(order, memory);
        editor.commit();
        super.onStop();
    }

    @Override
    protected void onStart() {
        long memory = preferences.getLong(order, 0);
        if (memory == 0) {
            puzzleManager = new PuzzleManager(n, n);
            shuffleView();
            // PuzzleElementOnTouch puzzleElementOnTouch = new PuzzleElementOnTouch(getApplicationContext(), gridLayout);
        }
        else {
            puzzleManager = new PuzzleManager(n, n, memory);
            updateView();
        }
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            gridLayout.getChildAt(i).setOnTouchListener(new PuzzleElementOnTouch(this, puzzleManager, i));
        }
        super.onStart();
    }
}
