
package br.gdgsp.volleydemo.domain;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

/**
 * @author : ubiratanfsoares
 **/

public class WeatherRequest extends JsonRequest<List<CityWeatherInfo>> {

    public WeatherRequest(String url, Listener<List<CityWeatherInfo>> listener, ErrorListener errorListener) {
        super(Method.GET, url, null, listener, errorListener);
    }

    @Override
    protected Response<List<CityWeatherInfo>> parseNetworkResponse(NetworkResponse response) {

        try {
            final String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            final JSONObject responseJson = new JSONObject(jsonString);
            return Response.success(parseWeather(responseJson), HttpHeaderParser.parseCacheHeaders(response));

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
            
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    private List<CityWeatherInfo> parseWeather(JSONObject responseJson) throws JSONException {

        final JSONArray citiesArray = responseJson.getJSONArray("list");
        List<CityWeatherInfo> citiesStatus = new ArrayList<CityWeatherInfo>();

        for (int index = 0; index < citiesArray.length(); index++) {
            final JSONObject infoJson = citiesArray.getJSONObject(index);
            citiesStatus.add(new CityWeatherInfo(infoJson));
        }

        return citiesStatus;
    }

}
