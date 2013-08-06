
package br.gdgsp.volleydemo.ui;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import br.gdgsp.volleydemo.R;
import br.gdgsp.volleydemo.domain.BitmapLruCache;
import br.gdgsp.volleydemo.domain.City;
import br.gdgsp.volleydemo.domain.CityWeatherInfo;
import br.gdgsp.volleydemo.domain.WeatherRequest;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class MainActivity extends Activity {

    private WeatherRequest mRequest;
    private RequestQueue mQueue;
    private ProgressBar mLoading;
    private ListView mListView;
    private WeatherInfoAdapter mAdapter;
    private ImageLoader mImageLoader;
    private City mCenterCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        mQueue = Volley.newRequestQueue(this);
        mImageLoader = new ImageLoader(mQueue, new BitmapLruCache());
    }

    @Override
    protected void onStart() {
        reloadList();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_reload, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_item_action_reload) {
            reloadList();
        }

        return super.onOptionsItemSelected(item);
    }

    private void reloadList() {
        
        if(mRequest != null && !mRequest.hasHadResponseDelivered()) {
            mRequest.cancel();
        }
        
        mCenterCity = City.newInstance();
        mListView.setVisibility(View.INVISIBLE);
        mLoading.setVisibility(View.VISIBLE);
        Toast.makeText(this, "Pesquisando em : " + mCenterCity.getName(), Toast.LENGTH_SHORT).show();
        fetchWeatherInfo();
    }

    private void findViews() {
        mListView = (ListView) findViewById(R.id.listView);
        mLoading = (ProgressBar) findViewById(R.id.loading);
    }

    private void fetchWeatherInfo() {

        final String requestURL = mCenterCity.getRequestUrl();
        mRequest = new WeatherRequest(requestURL,

                new Listener<List<CityWeatherInfo>>() {

                    @Override
                    public void onResponse(List<CityWeatherInfo> citiesInfo) {
                        fillList(citiesInfo);
                    }

                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        reportError();
                    }
                });

        mQueue.add(mRequest);
    }

    protected void fillList(List<CityWeatherInfo> citiesInfo) {
        mLoading.setVisibility(View.INVISIBLE);
        mAdapter = new WeatherInfoAdapter(this, citiesInfo, mImageLoader);
        mListView.setAdapter(mAdapter);
        mListView.setVisibility(View.VISIBLE);
    }

    protected void reportError() {
        mLoading.setVisibility(View.INVISIBLE);
        Toast.makeText(this, "Um erro aconteceu....", Toast.LENGTH_SHORT).show();
    }

}
