package com.xfdmao.fcat.coin.base.entity;

import java.util.Comparator;

/**
 * Created by cissa on 2019/7/27.
 */
public class Matrix {
    /**
     * 矩阵的纵轴
     */
    private int m;
    /**
     * 矩阵的横轴
     */
    private int n;
    /**
     * 矩阵的值
     */
    private double value;
    /**
     * 矩阵值排名
     */
    private int index;

    public Matrix() {
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
