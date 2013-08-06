
package br.gdgsp.volleydemo.domain;

import java.util.Random;

/**
 * @author : ubiratanfsoares
 **/

public class City {

    private static final String BASE_URL =
            "http://api.openweathermap.org/data/2.5/find?lat=#latitude#&lon=#longitude#&cnt=50&units=metric";

    private static final Random sRANDOM = new Random();

    private String mLatitude;
    private String mLongitude;
    private String mName;

    public static City newInstance() {
        return sCITIES[sRANDOM.nextInt(sCITIES.length)];
    }

    public String getRequestUrl() {
        return BASE_URL.replace("#latitude#", mLatitude).replace("#longitude#", mLongitude);
    }
    
    public String getName() {
        return mName;
    }

    private City(String latitude, String longitude, String name) {
        mLatitude = latitude;
        mLongitude = longitude;
        mName = name;
    }

    private static final City[] sCITIES = {
            new City("-23.5333", "-46.6167","São Paulo"), 
            new City("40.714353", "-74.005973","Nova York"),
            new City("34.052234", "-118.243685","Los Angeles"), 
            new City("55.751242", "37.618422","Moscow"),
            new City("35.689487", "139.691706","Tokyo"),
            new City("-35.282000", "149.128684","Canberra"),
            new City("48.856614", "2.352222","Paris"),
            new City("30.044420", "31.235712","Cairo"),
    };
}
