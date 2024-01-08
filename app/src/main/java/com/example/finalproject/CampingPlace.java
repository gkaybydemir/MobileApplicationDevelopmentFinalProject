// CampingPlace.java
package com.example.finalproject;

import android.os.Parcel;
import android.os.Parcelable;

        import android.os.Parcel;
        import android.os.Parcelable;
        import java.util.HashMap;
        import java.util.Map;

public class CampingPlace implements Parcelable {

    private boolean beach;
    private String capacityMax;
    private String instantNumber;
    private String description;
    private boolean electricity;
    private String photoLink;
    private String location;
    private String mapLink;
    private boolean market;
    private String name;
    private boolean parkingArea;
    private String price;
    private boolean shower;
    private Map<String, String> tent;
    private boolean toilet;
    private boolean water;
    private boolean wifi;

    public CampingPlace(String name, String price, String location) {
        this.name = name;
        this.price = price;
        this.location = location;
    }

    protected CampingPlace(Parcel in) {
        beach = in.readByte() != 0;
        capacityMax = in.readString();
        instantNumber = in.readString();
        description = in.readString();
        electricity = in.readByte() != 0;
        photoLink = in.readString();
        location = in.readString();
        mapLink = in.readString();
        market = in.readByte() != 0;
        name = in.readString();
        parkingArea = in.readByte() != 0;
        price = in.readString();
        shower = in.readByte() != 0;
        tent = new HashMap<>();
        in.readMap(tent, getClass().getClassLoader());
        toilet = in.readByte() != 0;
        water = in.readByte() != 0;
        wifi = in.readByte() != 0;
    }

    public boolean isBeach() {
        return beach;
    }

    public void setBeach(boolean beach) {
        this.beach = beach;
    }

    public String getCapacityMax() {
        return capacityMax;
    }

    public void setCapacityMax(String capacityMax) {
        this.capacityMax = capacityMax;
    }

    public String getInstantNumber() {
        return instantNumber;
    }

    public void setInstantNumber(String instantNumber) {
        this.instantNumber = instantNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isElectricity() {
        return electricity;
    }

    public void setElectricity(boolean electricity) {
        this.electricity = electricity;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMapLink() {
        return mapLink;
    }

    public void setMapLink(String mapLink) {
        this.mapLink = mapLink;
    }

    public boolean isMarket() {
        return market;
    }

    public void setMarket(boolean market) {
        this.market = market;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isParkingArea() {
        return parkingArea;
    }

    public void setParkingArea(boolean parkingArea) {
        this.parkingArea = parkingArea;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isShower() {
        return shower;
    }

    public void setShower(boolean shower) {
        this.shower = shower;
    }

    public Map<String, String> getTent() {
        return tent;
    }

    public void setTent(Map<String, String> tent) {
        this.tent = tent;
    }

    public boolean isToilet() {
        return toilet;
    }

    public void setToilet(boolean toilet) {
        this.toilet = toilet;
    }

    public boolean isWater() {
        return water;
    }

    public void setWater(boolean water) {
        this.water = water;
    }

    public boolean isWifi() {
        return wifi;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }

    // Parcelable implementasyonu
    public static final Parcelable.Creator<CampingPlace> CREATOR = new Parcelable.Creator<CampingPlace>() {
        @Override
        public CampingPlace createFromParcel(Parcel in) {
            return new CampingPlace(in);
        }

        @Override
        public CampingPlace[] newArray(int size) {
            return new CampingPlace[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (beach ? 1 : 0));
        dest.writeString(capacityMax);
        dest.writeString(instantNumber);
        dest.writeString(description);
        dest.writeByte((byte) (electricity ? 1 : 0));
        dest.writeString(photoLink);
        dest.writeString(location);
        dest.writeString(mapLink);
        dest.writeByte((byte) (market ? 1 : 0));
        dest.writeString(name);
        dest.writeByte((byte) (parkingArea ? 1 : 0));
        dest.writeString(price);
        dest.writeByte((byte) (shower ? 1 : 0));
        dest.writeMap(tent);
        dest.writeByte((byte) (toilet ? 1 : 0));
        dest.writeByte((byte) (water ? 1 : 0));
        dest.writeByte((byte) (wifi ? 1 : 0));
    }
}
