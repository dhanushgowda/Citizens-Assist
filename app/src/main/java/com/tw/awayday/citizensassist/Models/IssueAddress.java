package com.tw.awayday.citizensassist.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class IssueAddress implements Parcelable {
    public static final Parcelable.Creator<IssueAddress> CREATOR = new Parcelable.Creator<IssueAddress>() {

        @Override
        public IssueAddress createFromParcel(Parcel source) {
            return new IssueAddress(source);
        }

        @Override
        public IssueAddress[] newArray(int size) {
            return new IssueAddress[size];
        }
    };
    private String addressLine;
    private String city;
    private String state;
    private String country;

    public IssueAddress() {
    }

    private IssueAddress(Parcel in) {
        this.addressLine = in.readString();
        this.city = in.readString();
        this.state = in.readString();
        this.country = in.readString();
    }

    public IssueAddress setAddressLine(String addressLine) {
        this.addressLine = addressLine;
        return this;
    }

    public IssueAddress setCity(String city) {
        this.city = city;
        return this;
    }

    public IssueAddress setState(String state) {
        this.state = state;
        return this;
    }

    public IssueAddress setCountry(String country) {
        this.country = country;
        return this;
    }

    @Override
    public String toString() {
        return "AddressLine: " + addressLine + "\n" +
                "City: " + city + "\n" +
                "State: " + state + "\n" +
                "Country: " + country;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(addressLine);
        parcel.writeString(city);
        parcel.writeString(state);
        parcel.writeString(country);
    }
}
