package co.cargo.aleksa.ridesmap.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import co.cargo.aleksa.ridesmap.constants.Consts;
import co.cargo.aleksa.ridesmap.pojo.RidePoint;

/**
 * Created by aleksa on 7/12/16.
 */
public class RidePointDeserializer implements JsonDeserializer<RidePoint> {
    @Override
    public RidePoint deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        RidePoint rp = new RidePoint();

        rp.setLatitude(json.getAsJsonObject().get(Consts.JSON_PARAM_LATITUDE).getAsDouble());
        rp.setLongitude(json.getAsJsonObject().get(Consts.JSON_PARAM_LONGITUDE).getAsDouble());
        rp.setTimeCreated(json.getAsJsonObject().get(Consts.JSON_PARAM_TIME_CREATED).getAsString());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

        try {
            Date date = sdf.parse(json.getAsJsonObject().get(Consts.JSON_PARAM_TIME_CREATED).getAsString());
            rp.setTimeCreatedMillis(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return rp;
    }
}
