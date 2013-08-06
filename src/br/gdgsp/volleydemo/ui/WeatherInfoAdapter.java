
package br.gdgsp.volleydemo.ui;

import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import br.gdgsp.volleydemo.R;
import br.gdgsp.volleydemo.domain.CityWeatherInfo;

/**
 * @author : ubiratanfsoares
 **/

public class WeatherInfoAdapter extends ArrayAdapter<CityWeatherInfo> {
    
    private LayoutInflater mInflater;
    private ImageLoader mLoader;
    private int mStrongBG;
    private int mLightBG;

    public WeatherInfoAdapter(Context context, List<CityWeatherInfo> objects, ImageLoader loader) {
        super(context, 0, objects);
        mInflater = LayoutInflater.from(context);
        mStrongBG = context.getResources().getColor(R.color.strong_gray);
        mLightBG = context.getResources().getColor(R.color.light_gray);
        mLoader = loader;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_weather_status, parent, false);
        }
        
        fillItem(convertView, position);
        return convertView;
    }

    private void fillItem(View convertView, int position) {
        convertView.setBackgroundColor((position % 2 == 0) ? mStrongBG : mLightBG);
        
        final TextView name = (TextView) convertView.findViewById(R.id.name);
        final TextView temperature = (TextView) convertView.findViewById(R.id.temperature);
        final TextView condition = (TextView) convertView.findViewById(R.id.condition);
        final TextView humidity = (TextView) convertView.findViewById(R.id.humidity);
        
        CityWeatherInfo info = getItem(position);
        
        name.setText(info.getName());
        temperature.setText(info.getTemperature() + "°");
        condition.setText(info.getWeatherCondition());
        humidity.setText(info.getHumidity() + "%");
        
        NetworkImageView weatherImage = (NetworkImageView) convertView.findViewById(R.id.image_weather);
        weatherImage.setImageUrl(info.getWeatherIconURL(), mLoader);
    }

}
