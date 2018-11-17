package com.mercari.minimercariapp.tabs;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
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
import com.mercari.minimercariapp.R;
import com.mercari.minimercariapp.adapter.AdapterGridMasterData;
import com.mercari.minimercariapp.model.DetailMasterData;
import com.mercari.minimercariapp.model.MasterData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MasterProductListFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    Context context;
    private ProgressDialog progressDialog;
    private Dialog dialog;
    private Typeface iconFont;
    SharedPreferences pref ;
    SharedPreferences.Editor editor ;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPage = 0;

    private GridView idGridview;
    private LinearLayoutManager linearLayoutManager;
    private TextView no_pg_list;


    private MasterData masterData = null;
    private DetailMasterData detailMasterData = null;
    private List<DetailMasterData> detailMasterDataList = null;
    private AdapterGridMasterData adapterGridData = null;


    public MasterProductListFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static MasterProductListFragment newInstance(int page, MasterData masterData) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);

        args.putParcelable("masterData", masterData);

        MasterProductListFragment fragment = new MasterProductListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
        masterData = getArguments().getParcelable("masterData");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_product_gridview, container,
                false);
        context = getActivity().getApplicationContext();

        Log.v("TAG"," Page Number is: "+mPage);

        idGridview = (GridView) rootView.findViewById(R.id.idGridview);
        detailMasterDataList = new ArrayList<DetailMasterData>();

        callProgressDialog();
        CallApiForDetatilMasterList();




        return rootView;
    }
    public void callProgressDialog()
    {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgress(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
    }

    // Method to Call api for list of booked table
    private void CallApiForDetatilMasterList() {
        String loc_url = masterData.getData(); //data value on api call url
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest putRequest = new StringRequest(Request.Method.GET, loc_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TAG", "Server Response : " + response);
                if (response != null)
                {
                    try {
                        //JSONObject jsonObj = new JSONObject(response);
                        JSONArray dataArray = new JSONArray(response);
                        detailMasterDataList.clear();
                        if(dataArray != null)
                        {
                            DetailMasterData detailMasterData;
                            for(int i=0; i< dataArray.length(); i++)
                            {
                                JSONObject jsonObject = dataArray.getJSONObject(i);

                                String id = jsonObject.getString("id");
                                String name = jsonObject.getString("name");
                                String status = jsonObject.getString("status");
                                String num_likes = jsonObject.getString("num_likes");
                                String num_comments = jsonObject.getString("num_comments");
                                String price = jsonObject.getString("price");
                                String photo = jsonObject.getString("photo");

                                detailMasterData = new DetailMasterData(id, name, status, num_likes, num_comments, price,photo);

                                detailMasterDataList.add(detailMasterData);
                            }
                            if(detailMasterDataList.size() > 0) {
                                adapterGridData = new AdapterGridMasterData(context, detailMasterDataList, progressDialog);
                                idGridview.setAdapter(adapterGridData);
                                //Toast.makeText(context, message, Toast.LENGTH_LONG).show();

                            }
                            progressDialog.dismiss();
                        }
                        else {
                            progressDialog.dismiss();
                            idGridview.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                else {
                    progressDialog.dismiss();
                    idGridview.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (error instanceof TimeoutError) {
                    //Toast.makeText(context,context.getResources().getString(R.string.error_timeout),Toast.LENGTH_LONG).show();
                    Log.e("ERROR",context.getResources().getString(R.string.error_timeout));
                }else if(error instanceof NoConnectionError){
                    Log.e("ERROR",context.getResources().getString(R.string.error_internet));
                    Toast.makeText(context, "Please check your network connection or try again.", Toast.LENGTH_LONG).show();
                }
                else if (error instanceof AuthFailureError) {
                    Log.e("ERROR",context.getResources().getString(R.string.error_auth));
                } else if (error instanceof ServerError) {
                    Log.e("ERROR",context.getResources().getString(R.string.error_server));
                } else if (error instanceof NetworkError) {
                    Log.e("ERROR",context.getResources().getString(R.string.error_network));
                } else if (error instanceof ParseError) {
                    Log.e("ERROR",context.getResources().getString(R.string.error_parse));
                }
                //Log.e("Error", error.getMessage());

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
                //params.put("Content-Type","application/json");

                String android_device_id = new FirebaseInstanceIDService().getAndroidDeviceID(context);


                return params;
            }*/
        };
        queue.add(putRequest);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
