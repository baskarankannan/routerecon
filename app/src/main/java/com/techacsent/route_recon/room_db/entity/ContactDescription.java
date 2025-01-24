package com.techacsent.route_recon.room_db.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity
public class ContactDescription implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int key;

    private int userId;
    private int id;
    private String contactName;
    private String contactNo;
    private String email;

    public ContactDescription() {
    }

    protected ContactDescription(Parcel in) {
        key = in.readInt();
        userId = in.readInt();
        id = in.readInt();
        contactName = in.readString();
        contactNo = in.readString();
        email = in.readString();
    }

    public static final Creator<ContactDescription> CREATOR = new Creator<ContactDescription>() {
        @Override
        public ContactDescription createFromParcel(Parcel in) {
            return new ContactDescription(in);
        }

        @Override
        public ContactDescription[] newArray(int size) {
            return new ContactDescription[size];
        }
    };

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(key);
        dest.writeInt(userId);
        dest.writeInt(id);
        dest.writeString(contactName);
        dest.writeString(contactNo);
        dest.writeString(email);
    }
}
