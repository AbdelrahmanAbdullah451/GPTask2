package com.example.helloworld;

public class Point {
    private int pointID;
    private float x;
    private float y;
    private float z;
    private boolean streamFlag;

    public Point(int pointID, float x, float y, float z ,boolean streamFlag) {
        this.pointID = pointID;
        this.x = x;
        this.y = y;
        this.z = z;
        this.streamFlag = streamFlag;
    }

    public Point() {
    }

    @Override
    public String toString() {
        return "Point{" +
                "pointID=" + pointID +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", streamFlag=" + streamFlag +
                '}';
    }

    public int getPointID() {
        return pointID;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public boolean isStreamFlag() {
        return streamFlag;
    }

    public void setPointID(int pointID) {
        this.pointID = pointID;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public void setStreamFlag(boolean streamFlag) {
        this.streamFlag = streamFlag;
    }
}


