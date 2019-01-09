package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import java.util.ArrayList;

public class PieChart extends View {
    private final int PADDING = 20;
    private final int OFFSET = 40;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float[] valueDegree;
    private int[] COLORS = {Color.GREEN,Color.RED,Color.BLUE,Color.YELLOW,Color.CYAN};
    private RectF rectf;
    int temp = 0;

    private void initRectf(int width){
        rectf = new RectF (PADDING, PADDING, width - OFFSET, width - OFFSET);
    }

    public PieChart(Context context, float[] values, int width) {
        super(context);
        initRectf(width);
        valueDegree = new float[values.length];
        for(int i = 0; i < values.length; i++) {
            valueDegree[i]=values[i];
        }
    }

    public PieChart(Context context, ArrayList<Float> values, int width) {
        super(context);
        initRectf(width);
        valueDegree = new float[values.size()];
        for(int i=0; i < values.size(); i++) {
            valueDegree[i] = values.get(i);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < valueDegree.length; i++) {
            if (i == 0) {
                paint.setColor(COLORS[i]);
                canvas.drawArc(rectf, 0, valueDegree[i], true, paint);
            } else {
                temp += (int) valueDegree[i - 1];
                paint.setColor(COLORS[i]);
                canvas.drawArc(rectf, temp, valueDegree[i], true, paint);
            }
        }
    }
}