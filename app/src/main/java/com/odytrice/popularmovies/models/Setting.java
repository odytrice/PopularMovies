package com.odytrice.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Setting implements Parcelable {
    public String sort_order;
    public Setting(String sort_order){
        this.sort_order = sort_order;
    }

    protected Setting(Parcel in) {
        sort_order = in.readString();
    }

    public static final Creator<Setting> CREATOR = new Creator<Setting>() {
        @Override
        public Setting createFromParcel(Parcel in) {
            return new Setting(in);
        }

        @Override
        public Setting[] newArray(int size) {
            return new Setting[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sort_order);
    }
}
