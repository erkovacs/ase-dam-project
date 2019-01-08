package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.Display;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Utils.PieChart;
import comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Utils.ProjectIdentifier;

public class StatsActivity extends AppCompatActivity {
    private ArrayList<Float> values;
    private int screenWidth;
    private LinearLayoutCompat linear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        Button buttonReturnStats = findViewById(R.id.button_return_stats);
        linear = findViewById(R.id.pie_chart_container);

        values = new ArrayList<Float>();

        // Get values
        Intent intent = getIntent();
        int count = intent.getIntExtra(ProjectIdentifier.BUNDLE_PREFIX + ".total_questions", 0);
        int score = intent.getIntExtra(ProjectIdentifier.BUNDLE_PREFIX + ".total_score", 0);
        // Add the proportions
        values.add((float)score);
        values.add((float)count);
        screenWidth = getWindowManager().getDefaultDisplay().getWidth();

        // Draw Pie chart
        drawPieChart(screenWidth);

        buttonReturnStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void drawPieChart(int width){
        values = calculateSlices(values);
        linear.addView(new PieChart(this, values, width));
    }

    private ArrayList<Float> calculateSlices( ArrayList<Float> data) {
        float total = 0;
        for(int i = 0; i < data.size(); i++) {
            total += data.get(i);
        }
        ArrayList<Float> data2 = new ArrayList<Float>();
        for(int i=0;i<data.size();i++) {
            data2.add(360*(data.get(i)/total));
        }
        return data2;
    }
}
