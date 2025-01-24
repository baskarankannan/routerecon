package com.techacsent.route_recon.event_bus_object;

import android.os.Parcel;
import android.os.Parcelable;

public class StartServiceObj implements Parcelable {
    private boolean isStart;
    private int shareId;
    private int timeDuration;

    protected StartServiceObj(Parcel in) {
        isStart = in.readByte() != 0;
        shareId = in.readInt();
        timeDuration = in.readInt();
    }

    public static final Creator<StartServiceObj> CREATOR = new Creator<StartServiceObj>() {
        @Override
        public StartServiceObj createFromParcel(Parcel in) {
            return new StartServiceObj(in);
        }

        @Override
        public StartServiceObj[] newArray(int size) {
            return new StartServiceObj[size];
        }
    };

    public int getTimeDuration() {
        return timeDuration;
    }

    public void setTimeDuration(int timeDuration) {
        this.timeDuration = timeDuration;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public int getShareId() {
        return shareId;
    }

    public void setShareId(int shareId) {
        this.shareId = shareId;
    }

    public StartServiceObj(boolean isStart, int shareId, int timeDuration) {

        this.isStart = isStart;
        this.shareId = shareId;
        this.timeDuration = timeDuration;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (isStart ? 1 : 0));
        dest.writeInt(shareId);
        dest.writeInt(timeDuration);
    }
}
