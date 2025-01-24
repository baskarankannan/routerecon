package com.techacsent.route_recon.custom_view;

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;

public class RouteHintSpinner extends AppCompatSpinner {

    private boolean hasHint = false;

    public RouteHintSpinner(Context context) {
        this(context, null, android.R.attr.spinnerStyle);
    }

    public RouteHintSpinner(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.spinnerStyle);
    }

    public RouteHintSpinner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setClickable(true);
    }

    public void setAdapter(RouteSpinnerAdapter adapter) {
        super.setAdapter(adapter);
        hasHint = adapter.hasHint();
    }

    @Override
    public void setOnItemSelectedListener(@Nullable final OnItemSelectedListener listener) {
        super.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null) {
                    if (!hasHint) {
                        listener.onItemSelected(parent, view, position, id);
                    } else if (position > 0) {
                        listener.onItemSelected(parent, view, position - 1, id);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                if (listener != null) {
                    listener.onNothingSelected(parent);
                }

            }
        });
    }
}
