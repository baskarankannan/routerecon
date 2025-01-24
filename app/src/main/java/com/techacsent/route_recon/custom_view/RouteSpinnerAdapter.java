package com.techacsent.route_recon.custom_view;

import android.content.Context;
import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class RouteSpinnerAdapter <T> extends ArrayAdapter<T> {
    private static final String TAG = RouteSpinnerAdapter.class.getSimpleName();
    private List<T> objects;
    private String hint;
    private Context context;

    private RouteSpinnerAdapter() {
        super(null, 0);
    }

    public RouteSpinnerAdapter(Context context, T[] objects) {
        this(context, Arrays.asList(objects));
    }

    public RouteSpinnerAdapter(Context context, List<T> objects) {
        this(context, objects, null);
    }

    public RouteSpinnerAdapter(Context context, T[] objects, @StringRes int hint) {
        this(context, Arrays.asList(objects), hint);
    }

    public RouteSpinnerAdapter(Context context, T[] objects, String hint) {
        this(context, Arrays.asList(objects), hint);
    }

    public RouteSpinnerAdapter(Context context, List<T> objects, @StringRes int hint) {
        this(context, objects, context.getResources().getString(hint));
    }

    public RouteSpinnerAdapter(Context context, List<T> objects, String hint) {
        super(context, android.R.layout.simple_list_item_1, objects);
        this.context = context;
        this.objects = objects;
        this.hint = hint;
    }

    public boolean hasHint() {
        return hint != null;
    }

    @Override
    @CallSuper
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        textView = (TextView) convertView;

        if (hint != null && position == 0) {
            textView.setText(hint);
            textView.setTextColor(ContextCompat.getColor(context, android.R.color.tertiary_text_light));
        } else {
            textView.setText(getLabelFor(objects.get(position - 1)));
            textView.setTextColor(ContextCompat.getColor(context, android.R.color.primary_text_light));
        }
        return convertView;
    }

    @NonNull
    @Override
    @CallSuper
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            textView = (TextView) convertView;
            textView.setCompoundDrawables(null, null,
                    ContextCompat.getDrawable(context, android.R.drawable.spinner_background), null);
        } else {
            textView = (TextView) convertView;
        }

        if (hint != null && position == 0) {
            textView.setText(hint);
        } else {
            textView.setText(getLabelFor(objects.get(position - 1)));
        }

        return convertView;
    }

    @Override
    public boolean isEnabled(int position) {
        if (hint != null)
            return position != 0 && super.isEnabled(position);

        return super.isEnabled(position);
    }

    public String getLabelFor(T object) {
        return object.toString();
    }

    @Override
    public int getCount() {
        if (hint != null) {
            return super.getCount() + 1;
        } else {
            return super.getCount();
        }
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
