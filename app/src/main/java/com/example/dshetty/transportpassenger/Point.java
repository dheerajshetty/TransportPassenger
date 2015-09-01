package com.example.dshetty.transportpassenger;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dheerajshetty on 8/31/2015.
 */
public class Point implements Parcelable{
    public static final Creator<Point> CREATOR
            = new Creator<Point>() {
        public Point createFromParcel(Parcel in) {
            return new Point(in);
        }

        public Point[] newArray(int size) {
            return new Point[size];
        }
    };
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(Parcel in) {
        x = in.readInt();
        y = in.readInt();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getX());
        dest.writeInt(getY());
    }
}
