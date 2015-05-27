package poznawajkamobile.pz2.aplicationandroid.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.util.ArrayList;

import poznawajkamobile.pz2.aplicationandroid.utils.Utils;


public class MainGrid extends LinearLayout {
    private ArrayList<LinearLayout> lines = new ArrayList<LinearLayout>();
    private OnMainGridItemClickListener mListener = null;

    public MainGrid(Context context) {
        super(context);
        init();
    }

    public MainGrid(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.BOTTOM;
        setLayoutParams(params);
        setOrientation(VERTICAL);
    }

    public void setOnMainGridItemClickListener(OnMainGridItemClickListener listener) {
        mListener = listener;
    }

    public void setAdapter(Adapter adapter) {
        removeAllViews();
        if (adapter != null) {
            portraitFill(adapter);
        }
    }

    private void portraitFill(Adapter adapter) {
        int divider = 2;
        final int adapterCount = adapter.getCount();
        int lineCount = Math.round(adapterCount / divider) + 1;
        for (int l = 0; l < lineCount; l++) {
            addLine(l);
        }
        for (int i = 0; i < adapterCount; i++) {
            int line = i / divider;
            final int position = i;
            LinearLayout item = (LinearLayout) adapter.getView(position, null, null);
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.CENTER;
            params.weight = 1f;
            int innerMargin = 0;
            params.leftMargin = innerMargin;
            params.bottomMargin = innerMargin;
            params.rightMargin = innerMargin;
            params.topMargin = innerMargin;
            item.setLayoutParams(params);
            item.setClickable(true);
            setClickableChildren(item);
            if (mListener != null) {
                item.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onItemClick(v, position);
                    }
                });
            }
            lines.get(line).addView(item);
        }
        invalidate();
    }

    private void addLine(int order) {
        LinearLayout line = new LinearLayout(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        params.weight = 1f;
        if (order == 3) {
            params.topMargin = 10;
        }
        line.setLayoutParams(params);
        line.setOrientation(HORIZONTAL);
        lines.add(line);
        addView(line);
    }

    public interface OnMainGridItemClickListener {
        void onItemClick(View view, int position);
    }

    private void setClickableChildren(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) view;
            for (int n = 0; n < vg.getChildCount(); n++) {
                View inner = vg.getChildAt(n);
                inner.setDuplicateParentStateEnabled(true);
                setClickableChildren(inner);
            }
        }
    }
}
