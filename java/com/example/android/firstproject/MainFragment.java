package com.example.android.firstproject;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Recorddata recorddata;
    private int postId;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView mTitle;
    private String[] post_images;
    private String TAG = "Main Fragment";


    public MainFragment() {
        // Required empty public constructor
    }

    public MainFragment(int postId, Recorddata recorddata) {
        this.postId = postId;
        this.recorddata = recorddata;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
//        Log.d("vinay", ""+mParam1+mParam2);
//        mTitle.setText(mParam1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mTitle = (TextView) view.findViewById(R.id.textView6);
        post_images = Arrays.copyOf(recorddata.getPost_images(), recorddata.getPost_imagesSize());
        List<String> post_imagesList = new ArrayList<String>(recorddata.getPost_imagesList());
//        Log.d("vinay", ""+mParam1+mParam2);
        mTitle.setText(recorddata.getPost_title() + " " + recorddata.getPost_imagesSize() + " \n" + post_images[0].toString()+"\n"+post_imagesList.get(0)+"\n"+recorddata.getPost_title() + " " + post_imagesList.size());
//


        CustomPagerAdapter mCustomPagerAdapter = new CustomPagerAdapter(getActivity());

        ViewPager mViewPager = (ViewPager) view.findViewById(R.id.pager);
        mViewPager.setAdapter(mCustomPagerAdapter);

        new RemoteDataTask().execute();
        return view;
    }

    class RemoteDataTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            JSONObject mainObject = new JSONObject();
            JSONObject object = new JSONObject();
            try {
                String paymentFrequency = "360";
                String plateNumber = "MH12HH8942";
                String policyType = "ThirdParty";
                String periodOfCover = "360";
                String vehicleClass = "LP - Veículo Ligeiro particular - Acima 2.500cc";
                String vehicleYear = "2014";
                String vehiclePrice = "100000";
                String inceptionDate = "01/01/2017";
                object.put("paymentFrequency", paymentFrequency);
                object.put("plateNumber", plateNumber);
                object.put("policyType", policyType);
                object.put("periodOfCover", periodOfCover);
                object.put("vehicleClass", vehicleClass);
                object.put("vehicleYear", vehicleYear);
                object.put("vehiclePrice", vehiclePrice);
                object.put("inceptionDate", inceptionDate);

                mainObject.put("policyData", object);
            } catch (Exception ex) {

            }

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    "http://192.168.0.12:8080/GetMotorRates/", mainObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
//                Log.i(TAG, response.toString());
                    try {
                        Log.i(TAG, response.toString());
                    } catch (Exception e) {
                        Log.d(TAG, "Error: 3" + e.toString());
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error instanceof TimeoutError || error instanceof NoConnectionError) {
//                    Toast.makeText(getActivity(),context.getString(R.string.error_network_timeout),Toast.LENGTH_LONG).show();
                    } else if (error instanceof AuthFailureError) {
                        //TODO
                    } else if (error instanceof ServerError) {
                        //TODO
                    } else if (error instanceof NetworkError) {
                        //TODO
                    } else if (error instanceof ParseError) {
                        //TODO
                    }
                    Log.d(TAG, "ErrorListener: " + error.getMessage() + error.toString());
//                    progressBar.setVisibility(ProgressBar.VISIBLE);

                    NetworkResponse response = error.networkResponse;
                    if (response != null && response.data != null) {
                        Toast.makeText(getActivity(), "errorMessage:" + response.statusCode, Toast.LENGTH_SHORT).show();
                    } else {
                        String errorMessage = error.getClass().getSimpleName();
                        if (!TextUtils.isEmpty(errorMessage)) {
                            Toast.makeText(getActivity(), "errorMessage:" + errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            int socketTimeout = 30000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

            jsonObjReq.setRetryPolicy(policy);

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(jsonObjReq);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter(Context context) {
            mContext = context;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return post_images.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((ImageView) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);

            Glide.with(getActivity()).load(Uri.parse("http://192.168.0.16/mp28/" + post_images[position]))
                    .thumbnail(0.5f)
                    .crossFade()
                    .centerCrop()
                    .into(imageView);


            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ImageView) object);
        }
    }
}
