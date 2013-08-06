
package br.gdgsp.volleydemo.domain;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author : ubiratanfsoares
 **/

public class CityWeatherInfo {

    private static final String BASE_IMAGE_URL = "http://openweathermap.org/img/w/";

    public static String getBaseImageUrl() {
        return BASE_IMAGE_URL;
    }

    private String mName;
    private int mTemperature;
    private int mHumidity;
    private String mWeatherCondition;
    private String mWeatherIconURL;

    public CityWeatherInfo(JSONObject infoJson) throws JSONException {
        mName = infoJson.getString("name");
        parseTemperature(infoJson);
        parseConditions(infoJson);
    }

    public String getName() {
        return mName;
    }

    public int getTemperature() {
        return mTemperature;
    }

    public int getHumidity() {
        return mHumidity;
    }

    public String getWeatherCondition() {
        return mWeatherCondition;
    }

    public String getWeatherIconURL() {
        return mWeatherIconURL;
    }

    @Override
    public String toString() {
        return mName + " | " + mTemperature;
    }
    private void parseConditions(JSONObject infoJson) throws JSONException {
        final JSONArray conditionsArray = infoJson.getJSONArray("weather");
        final JSONObject conditionInfo = conditionsArray.getJSONObject(0);
        mWeatherCondition = conditionInfo.getString("main");
        mWeatherIconURL = BASE_IMAGE_URL + conditionInfo.getString("icon");
    }

    private void parseTemperature(JSONObject infoJson) throws JSONException {
        final JSONObject mainJson = infoJson.getJSONObject("main");
        mTemperature = mainJson.getInt("temp");
        mHumidity = mainJson.getInt("humidity");
    }
}
