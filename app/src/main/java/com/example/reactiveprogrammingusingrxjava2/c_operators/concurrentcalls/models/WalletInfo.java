package com.example.reactiveprogrammingusingrxjava2.c_operators.concurrentcalls.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletInfo {

    @SerializedName("central")
    @Expose
    private double central;
    @SerializedName("brandFactory")
    @Expose
    private int brandFactory;
    @SerializedName("bigBazaar")
    @Expose
    private int bigBazaar;

    public double getCentral() {
        return central;
    }

    public void setCentral(double central) {
        this.central = central;
    }

    public int getBrandFactory() {
        return brandFactory;
    }

    public void setBrandFactory(int brandFactory) {
        this.brandFactory = brandFactory;
    }

    public int getBigBazaar() {
        return bigBazaar;
    }

    public void setBigBazaar(int bigBazaar) {
        this.bigBazaar = bigBazaar;
    }
}