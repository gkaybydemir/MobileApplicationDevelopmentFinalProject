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
    private HashMap<String, String> capacity;
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
    private HashMap<String, String> tent;
    private boolean toilet;
    private boolean water;
    private boolean wifi;
    private String placeKey;

    public CampingPlace(String name, String price, String location) {
        this.name = name;
        this.price = price;
        this.location = location;
    }

    protected CampingPlace(Parcel in) {
        beach = in.readByte() != 0;
        capacity = new HashMap<>();
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


    public String getPlaceKey() {
        return placeKey;
    }

    public void setPlaceKey(String placeKey) {
        this.placeKey = placeKey;
    }

    public boolean isBeach() {
        return beach;
    }

    public void setBeach(boolean beach) {
        this.beach = beach;
    }

    public HashMap<String, String> getTent() {
        return (HashMap<String, String>) tent;
    }

    public void setTent(HashMap<String, String> tent) {
        this.tent = tent;
    }

    public HashMap<String, String> getCapacity() {
        System.out.println((HashMap<String, String>) capacity);
        return (HashMap<String, String>) capacity;
    }

    public void setCapacity(HashMap<String, String> capacity) {
        this.capacity = capacity;
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
