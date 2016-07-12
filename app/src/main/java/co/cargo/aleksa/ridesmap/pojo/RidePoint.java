package co.cargo.aleksa.ridesmap.pojo;

import com.google.gson.annotations.SerializedName;

import co.cargo.aleksa.ridesmap.constants.Consts;

/**
 * Created by aleksa on 7/11/16.
 */
public class RidePoint {

    @SerializedName(Consts.JSON_PARAM_LATITUDE)
    private double latitude;
    @SerializedName(Consts.JSON_PARAM_LONGITUDE)
    private double longitude;
    @SerializedName(Consts.JSON_PARAM_TIME_CREATED)
    private String timeCreated;

    private long timeCreatedMillis;

    public RidePoint(){
        latitude = 0;
        longitude = 0;
        timeCreated = "1970-01-01 00:00:00";
        timeCreatedMillis = 0;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    public long getTimeCreatedMillis() {
        return timeCreatedMillis;
    }

    public void setTimeCreatedMillis(long timeCreatedMillis) {
        this.timeCreatedMillis = timeCreatedMillis;
    }
}
