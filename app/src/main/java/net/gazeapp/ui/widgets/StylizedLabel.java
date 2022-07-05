package net.gazeapp.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.gazeapp.R;

/**
 * Custom View Tutorial: http://www.vogella.com/tutorials/AndroidCustomViews/article.html
 * Created by taar1 on 19.07.2017.
 */
public class StylizedLabel extends LinearLayout {
    private final TextView title;

    public StylizedLabel(Context context) {
        this(context, null);
    }

    public StylizedLabel(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StylizedLabelView, 0, 0);

        String titleText = a.getString(R.styleable.StylizedLabelView_titleText);

        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.stylized_label_view, this, true);

        title = (TextView) getChildAt(0);
        title.setText(titleText);
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(String titleStr) {
        title.setText(titleStr);
    }
}
