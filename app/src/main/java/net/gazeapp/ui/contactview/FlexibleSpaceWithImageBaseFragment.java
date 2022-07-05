package net.gazeapp.ui.contactview;

import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.Scrollable;

import net.gazeapp.R;

public abstract class FlexibleSpaceWithImageBaseFragment<S extends Scrollable> extends Fragment
        implements ObservableScrollViewCallbacks {

    public static final String ARG_SCROLL_Y = "ARG_SCROLL_Y";

    public void setArguments(int scrollY) {
        if (0 <= scrollY) {
            Bundle args = new Bundle();
            args.putInt(ARG_SCROLL_Y, scrollY);
            setArguments(args);
        }
    }

    public void setScrollY(int scrollY, int threshold) {
        View view = getView();
        if (view == null) {
            return;
        }
        Scrollable scrollView = view.findViewById(R.id.scroll);
        if (scrollView == null) {
            return;
        }
        scrollView.scrollVerticallyTo(scrollY);
    }

    protected void updateFlexibleSpace(int scrollY) {
        updateFlexibleSpace(scrollY, getView());
    }

    protected abstract void updateFlexibleSpace(int scrollY, View view);

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        if (getView() == null) {
            return;
        }
        updateFlexibleSpace(scrollY, getView());
    }

    @Override
    public final void onDownMotionEvent() {
        // We don't use this callback in this pattern.
    }

    @Override
    public final void onUpOrCancelMotionEvent(ScrollState scrollState) {
        // We don't use this callback in this pattern.
    }

    protected S getScrollable() {
        View view = getView();
        return view == null ? null : (S) view.findViewById(R.id.scroll);
    }
}
