package com.mercari.minimercariapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MasterData implements Parcelable {

    private String name;
    private String data;

    public MasterData(){}
    public MasterData(String name, String data) {
        this.name = name;
        this.data = data;
    }

    protected MasterData(Parcel in) {
        this.name = in.readString();
        this.data = in.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
    public static final Creator<MasterData> CREATOR = new Creator<MasterData>() {
        @Override
        public MasterData createFromParcel(Parcel in) {
            return new MasterData(in);
        }

        @Override
        public MasterData[] newArray(int size) {
            return new MasterData[size];
        }
    };


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
