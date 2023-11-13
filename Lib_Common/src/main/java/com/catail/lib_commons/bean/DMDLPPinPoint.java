package com.catail.lib_commons.bean;

import java.io.Serializable;

public class DMDLPPinPoint implements Serializable {
    private float pin_x;
    private float pin_y;
    private int pin_pos;
    private int drawing_id;
    private String drawing_name;
    private String drawing_path;
    private String drawing_position;
    private String unit;
    private String zone;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getDrawing_position() {
        return drawing_position;
    }

    public void setDrawing_position(String drawing_position) {
        this.drawing_position = drawing_position;
    }

    public float getPin_x() {
        return pin_x;
    }

    public void setPin_x(float pin_x) {
        this.pin_x = pin_x;
    }

    public float getPin_y() {
        return pin_y;
    }

    public void setPin_y(float pin_y) {
        this.pin_y = pin_y;
    }

    public int getPin_pos() {
        return pin_pos;
    }

    public void setPin_pos(int pin_pos) {
        this.pin_pos = pin_pos;
    }

    public int getDrawing_id() {
        return drawing_id;
    }

    public void setDrawing_id(int drawing_id) {
        this.drawing_id = drawing_id;
    }

    public String getDrawing_name() {
        return drawing_name;
    }

    public void setDrawing_name(String drawing_name) {
        this.drawing_name = drawing_name;
    }

    public String getDrawing_path() {
        return drawing_path;
    }

    public void setDrawing_path(String drawing_path) {
        this.drawing_path = drawing_path;
    }

    @Override
    public String toString() {
        return "DMDLPPinPoint{" +
                "pin_x=" + pin_x +
                ", pin_y=" + pin_y +
                ", pin_pos=" + pin_pos +
                ", drawing_id=" + drawing_id +
                ", drawing_name=" + drawing_name +
                ", drawing_path=" + drawing_path +
                '}';
    }
}
