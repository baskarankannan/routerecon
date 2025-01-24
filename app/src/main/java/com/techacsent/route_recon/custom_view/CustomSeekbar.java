package com.techacsent.route_recon.custom_view;

import android.annotation.SuppressLint;
import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.techacsent.route_recon.R;

import timber.log.Timber;

public class CustomSeekbar extends LinearLayout {
    private static final int MAX_PROGRESS = 5;
    private static final int NUMBER_OF_INTERVALS = 5;
    public int currentSetting = 30; // 15 mins
    private Context mContext;
    private IProgressUpdater mProgressUpdater;

    private LayoutInflater mInflater;

    public CustomSeekbar(Context context) {
        super(context);
    }

    public CustomSeekbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        mContext = context;
        if (context != null) {
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (mInflater != null) {
                mInflater.inflate(R.layout.custom_component_seekbar, this, true);
                SeekBar seekBar = (SeekBar) getChildAt(0);

                LinearLayout seekbarIntervals = (LinearLayout) getChildAt(1);
                if (seekBar != null) {
                    seekBar.setMax(MAX_PROGRESS);
                    seekBar.setProgress(MAX_PROGRESS / NUMBER_OF_INTERVALS);
                    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            calibrate(progress);
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {}

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {}
                    });
                    addIntervalLabel(seekbarIntervals, 15, "Mins");
                    addIntervalLabel(seekbarIntervals, 30, "Mins");
                    addIntervalLabel(seekbarIntervals, 1, "Hr");
                    addIntervalLabel(seekbarIntervals, 6, "Hrs");
                    addIntervalLabel(seekbarIntervals, 12, "Hrs");
                    addIntervalLabel(seekbarIntervals, 24, "Hrs");
                }
            }
        }
    }

    public CustomSeekbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomSeekbar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setEventListener(IProgressUpdater eventListener) {
        this.mProgressUpdater = eventListener;
    }

    @SuppressLint({"StringFormatMatches", "TimberArgCount"})
    private void calibrate(int progress) {

        int hours = 0;
        int minutes = 0;

        String textToShow = null;
        int intervalSize = MAX_PROGRESS / NUMBER_OF_INTERVALS; // 240/5 = 48
        int whichInterval = progress / intervalSize;  // Current progress falls under which interval
        progress = progress - intervalSize * whichInterval;
        switch (whichInterval) {
            case 0:
                hours = 0;
                minutes = 15;
                break;
            case 1:
                hours = 0;
                minutes = 30;
                break;
            case 2:
                hours = 1;
                minutes = 0;
                break;
            case 3:
                hours = 6;
                minutes = 0;
                break;
            case 4:
                hours = 12;
                minutes = 0;
                break;
            case 5:
                hours = 24;
                minutes = 0;
                break;
        }
        if (hours > 0) {
            textToShow = String.format(mContext.getString(R.string.hours_template), hours);
        } else {
            textToShow = String.format(mContext.getString(R.string.minutes_template), minutes);
        }
        if (textToShow != null && mProgressUpdater != null) {
            mProgressUpdater.onProgress(textToShow, hours, minutes);
        }
        currentSetting = hours * 60 + minutes;
        Timber.d("Time selected ",currentSetting);
    }

    private void addIntervalLabel(LinearLayout seekbarIntervals, int i, String units) {

        if (mInflater != null) {
            LinearLayout labelParent = (LinearLayout) mInflater.inflate(R.layout.seekbar_step_label, seekbarIntervals, false);
            seekbarIntervals.addView(labelParent);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) labelParent.getLayoutParams();
            if (params != null) {
                params.weight = 1.0f;
            }
            TextView timeView = labelParent.findViewById(R.id.time);
            TextView timeUnitsView = labelParent.findViewById(R.id.time_units);
            timeView.setText(String.valueOf(i));
            timeUnitsView.setText(units);
        }
    }

    public interface IProgressUpdater {
        void onProgress(String textToShow, int hours, int minutes);
    }
}
