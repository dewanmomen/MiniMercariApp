package com.mercari.minimercariapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mercari.minimercariapp.model.MasterData;
import com.mercari.minimercariapp.adapter.MyFragmentPageAdapter;
import com.mercari.minimercariapp.utility.ConnectionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Context context;
    private ProgressDialog progressDialog;
    SharedPreferences pref ;
    SharedPreferences.Editor editor ;

    //private PagerSlidingTabStrip tabLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int currentItem = 0;
    private int tempCurrentItem = 0;

    MyFragmentPageAdapter myPageAdapter;
    List<MasterData> masterDataList = null;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        setTitle("Mercari");
        //getSupportActionBar().setIcon(R.drawable.icon_launcher);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setIcon(R.drawable.icon_launcher);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        //tabLayout = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabLayout = (TabLayout) findViewById(R.id.tabs);


        masterDataList = new ArrayList<MasterData>();

        //call api here
        callProgressDialog();
        CallApiForMasterProductList();

    }

    public void callProgressDialog() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgress(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
    }

    // Method to Call api for Food Invoice Status Item
    private void CallApiForMasterProductList() {
        String loc_url = ConnectionManager.SERVER_URL + ConnectionManager.master; //url
        RequestQueue queue = Volley.newRequestQueue(context);
        Log.d("TAG", "API call started");
        StringRequest putRequest = new StringRequest(Request.Method.GET, loc_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", "Server Response : " + response);
                if (response != null)
                {
                    try {
                        //JSONObject jsonObj = new JSONObject(response);
                        //JSONArray jsonArr = new JSONArray(response);
                        JSONArray dataArray = new JSONArray(response);

                        masterDataList.clear();
                        if(dataArray != null)
                        {

                            //JSONArray dataArray = jsonObj.getJSONArray("");


                            MasterData masterData;
                            for(int i=0; i< dataArray.length(); i++)
                            {
                                JSONObject jsonObject = dataArray.getJSONObject(i);
                                Log.d("TAG", "products array " + jsonObject);

                                String name = jsonObject.getString("name");
                                String data = jsonObject.getString("data");
                                Log.d("TAG", "name " + name);
                                Log.d("TAG", "data " + data);

                                masterData = new MasterData(name, data);


                                masterDataList.add(masterData);
                            }
                            if(masterDataList.size() > 0)
                            {
                                //Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                                myPageAdapter = new MyFragmentPageAdapter(context, getSupportFragmentManager(), masterDataList, currentItem);
                                viewPager.setAdapter(myPageAdapter);
                                tabLayout.setupWithViewPager(viewPager);
                                currentItem = viewPager.getCurrentItem();
                                myPageAdapter.notifyDataSetChanged();
                                //Log.d("TAG","Current Item is : " + currentItem);
                                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                                    @Override
                                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                                    }

                                    @Override
                                    public void onPageSelected(int position) {
                                        tempCurrentItem = currentItem;
                                        currentItem = position;
                                        Log.d("TAG", "Current Item is : " + currentItem);
                                    }

                                    @Override
                                    public void onPageScrollStateChanged(int state) {

                                    }
                                });
                            }
                            progressDialog.dismiss();

                        }
                        else {
                            //no_list.setVisibility(View.VISIBLE);
                            progressDialog.dismiss();

                            Log.d("TAG", "Data Not found");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                else {
                    Log.d("TAG", "Response Null");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (error instanceof TimeoutError) {
                    //Toast.makeText(context,context.getResources().getString(R.string.error_timeout),Toast.LENGTH_LONG).show();
                    Log.e("ERROR Timeout",context.getResources().getString(R.string.error_timeout));
                }else if(error instanceof NoConnectionError){
                    Log.e("ERROR internet",context.getResources().getString(R.string.error_internet));
                    Toast.makeText(context, "Please check your network connection or try again.", Toast.LENGTH_LONG).show();
                }
                else if (error instanceof AuthFailureError) {
                    Log.e("ERROR auth",context.getResources().getString(R.string.error_auth));
                } else if (error instanceof ServerError) {
                    Log.e("ERROR server",context.getResources().getString(R.string.error_server));
                } else if (error instanceof NetworkError) {
                    Log.e("ERROR network",context.getResources().getString(R.string.error_network));
                } else if (error instanceof ParseError) {
                    Log.e("ERROR parse",context.getResources().getString(R.string.error_parse));
                }
            }
        }) {

            /*@Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("platform", "1");
                params.put("lang", "en");


                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");

                String android_device_id = new FirebaseInstanceIDService().getAndroidDeviceID(context);

                return params;
            }*/
        };
        queue.add(putRequest);
    }
}
