package com.example.android.firstproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public String post_title;
    String text = "";
    ArrayList<Recorddata> List;
    //    HashMap<String, ArrayList<Recorddata.Images>> mapOfList = new HashMap<String, ArrayList<Recorddata.Images>>();
//    ArrayList<Recorddata.Images> post_imagesList = new ArrayList<Recorddata.Images>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ListView listview;
    private ListviewAdapter mAdapter;
    private String response;
    private BufferedReader reader;
    private String REGISTER_URL = "http://192.168.0.16/mp28/DetailApi/getPostByType";
    private String jsonResponse;
    private String mRequestBody;
    private String TAG = "BlankFragment";
    private String post_id;
    private String full_name;
    private ProgressBar progressBar;
    private String varFirstpost_type = "1";
    private String varFirstpost_no = "0";
    private String varFirstadvertisement_no = "0";
    private String varNewpost_type;
    private String varNewpost_no;
    private String varNewadvertisement_no;
    //    private RelativeLayout bottomLayout;
    private boolean varFirstTimeOpen = true;
    private ArrayList<String> ArrayListContentCondittion;
    private String[] post_images = new String[100];
    private int post_imagesSize = 0;
    private ArrayList<String> post_imagesList;

    public BlankFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
//        bottomLayout = (RelativeLayout) view.findViewById(R.id.loadItemsLayout);
        List = new ArrayList<Recorddata>();
        post_imagesList = new ArrayList<String>();

//        List.add(new Recorddata());
//        getAllData();
        ArrayListContentCondittion = new ArrayList<String>();
        ArrayListContentCondittion.isEmpty();

        new RemoteDataTask().execute();

        listview = (ListView) view.findViewById(R.id.ListView1);

//        mAdapter = new ListviewAdapter(List, getActivity());


        listview.setOnScrollListener(new OnScrollListener() {
            public int totalItemCount;
            public int visibleItemCount;
            public int firstVisibleItem;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                final int lastItem = firstVisibleItem + visibleItemCount;
                if (scrollState == SCROLL_STATE_IDLE) {
//                        expandapleInt++;
//
//                        new AsyncTask().execute();
                    if (lastItem == totalItemCount) {
//                        new RemoteDataTask().execute();
//                        int position = listview.getLastVisiblePosition();
//                        listview.setSelectionFromTop(position, 0);
//                            listview.setSelectionFromTop(lastItem, 0);
                        new LoadMoreDataTask().execute();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                this.firstVisibleItem = firstVisibleItem;
                this.visibleItemCount = visibleItemCount;
                this.totalItemCount = totalItemCount;
//                Log.d(TAG, firstVisibleItem + " aa" + visibleItemCount + " " + totalItemCount + " ");
            }

        });
//        post_images[0] = "http://4.bp.blogspot.com/-g0vQrTEVWv8/VhyzPhP_-AI/AAAAAAAAAf0/CeMPp2swG1w/s1600/20150117_175938.jpg";
//        post_images[1] = "http://4.bp.blogspot.com/-g0vQrTEVWv8/VhyzPhP_-AI/AAAAAAAAAf0/CeMPp2swG1w/s1600/20150117_175938.jpg";
//        post_images[2] = "http://4.bp.blogspot.com/-g0vQrTEVWv8/VhyzPhP_-AI/AAAAAAAAAf0/CeMPp2swG1w/s1600/20150117_175938.jpg";
//        post_images[3] = "http://4.bp.blogspot.com/-g0vQrTEVWv8/VhyzPhP_-AI/AAAAAAAAAf0/CeMPp2swG1w/s1600/20150117_175938.jpg";
//        post_images[4] = "http://4.bp.blogspot.com/-g0vQrTEVWv8/VhyzPhP_-AI/AAAAAAAAAf0/CeMPp2swG1w/s1600/20150117_175938.jpg";
//
//        List.add(new Recorddata("1", "2", "3", post_images, post_imagesSize));
        return view;

    }

    public void getAllData() {
//        final StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_LONG).show();
//                        Log.i("LOG_VOLLEY", response.toString());
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
//                    }
//                }) {
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("post_type", "1");
//                params.put("post_no", "0");
//                params.put("advertisement_no", "0");
//                return params;
//            }
//
//            @Override
//            public String getBodyContentType() {
//                return "application/json; charset=utf-8";
//            }
//
//            @Override
//            public byte[] getBody() throws AuthFailureError {
//                try {
//                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
//                } catch (UnsupportedEncodingException uee) {
//                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
//                    return null;
//                }
//            }
//
//            @Override
//            protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                String responseString = "";
//                if (response != null) {
//
//                    responseString = String.valueOf(response.statusCode);
//
//                }
//                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
//            }
//
//
//
//        };

 /*       JSONObject result=new JSONObject();
        try {
            result.put("post_type", "1");
            result.put("post_no", "0");
            result.put("advertisement_no", "0");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, "http://api.androidhive.info/volley/person_object.json", null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.i("Response", response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Error.Response", error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("post_type", "1");
                params.put("post_no", "0");
                params.put("advertisement_no", "0");
//                Log.i()
                return params;
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    // solution 1:
//                    String jsonString = new String(response.data, "UTF-8");
                    // solution 2:
//                    response.headers.put(HTTP.CONTENT_TYPE, response.headers.get("content-type"));
                    String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    //
                    return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException je) {
                    return Response.error(new ParseError(je));
                }
            }
        };
*/

//        JsonObjectRequest getRequest1 = new JsonObjectRequest(Request.Method.POST, REGISTER_URL, null,new Response.Listener<JSONArray>(){});

//        JsonArrayRequest getRequest = new JsonArrayRequest(REGISTER_URL,
//                new Response.Listener<JSONArray>()
//                {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        // display response
//                        Log.d("Response", response.toString());
//                    }
//                },
//                new Response.ErrorListener()
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d("Error.Response", error.getMessage());
//                    }
//                }
//        ){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("post_type", "1");
//                params.put("post_no", "0");
//                params.put("advertisement_no", "0");
//                return params;
//            }
//
//            @Override
//            public String getBodyContentType() {
//                return "application/json; charset=utf-8";
//            }
//
//            @Override
//            public byte[] getBody() {
//                try {
//                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
//                } catch (UnsupportedEncodingException uee) {
//                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
//                    return null;
//                }
//            }
//
//            @Override
//            protected VolleyError parseNetworkError(VolleyError volleyError) {
//                return super.parseNetworkError(volleyError);
//            }
//
////            @Override
////            protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
////                String responseString = "";
////                if (response != null) {
////
////                    responseString = String.valueOf(response.statusCode);
////
////                }
////                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
////            }
//        };

//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//        requestQueue.add(getRequest);

        JSONObject request1 = new JSONObject();
        try {
            request1.put("post_type", "1");
            request1.put("post_no", "0");
            request1.put("advertisement_no", "0");
        } catch (Exception e) {
            e.printStackTrace();

        }

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                REGISTER_URL, request1, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {
//                Log.i(TAG, response.toString());
//
                try {
                    JSONObject response1 = response.getJSONObject("response");
                    JSONArray postWithAdv = response1.getJSONArray("postWithAdv");
                    try {
                        for (int i = 0; i < postWithAdv.length(); i++) {
                            JSONObject mJsonObject = postWithAdv.getJSONObject(i);
                            try {
//                                Log.d(TAG, i + " and " + mJsonObject.getString("post_id"));
                                post_id = mJsonObject.getString("post_id");
//                                Log.d(TAG, i + " and " + mJsonObject.getString("post_title"));
                                post_title = mJsonObject.getString("post_title");
//                                Log.d(TAG, i + " and " + mJsonObject.getString("full_name"));
                                full_name = mJsonObject.getString("full_name");


                                JSONArray user_image = mJsonObject.getJSONArray("post_images");
                                try {
                                    for (int j = 0; j < postWithAdv.length(); j++) {
                                        if (j == 0) {
//                                            Log.d(TAG, i + " and " + user_image.getString(j));
//                                            post_images[j] = user_image.getString(j);
                                        }
                                    }
                                } catch (Exception ex) {
                                    Log.d(TAG, ex.toString());
                                }
//                                Log.d(TAG, i + " and " + post_id + "" + post_title);
//                                List.add(new Recorddata(post_id, post_title, full_name, post_images));
                                mAdapter.notifyDataSetChanged();
                            } catch (Exception ex) {
                                Log.d(TAG, ex.toString());
                            }
//                            if(i==1){
//                                Log.d(TAG, mJsonObject.getString("post_id"));
//                                Log.d(TAG, mJsonObject.getString("post_title"));
//                            }
                        }
                    } catch (Exception ex) {
                        Log.d(TAG, ex.toString());
                    }
//                    Log.i(TAG, postWithAdv.length()+"");
////                    // Parsing json object response
////                    // response will be a json object
//                    String outPut = response.getString("response");
//                    String email = response.getString("email");


//                    JSONArray response1 = response.getJSONArray("response");
//                    try {
//                        JSONArray postWithAdv = response1.getJSONArray("postWithAdv");
//                        for (int i = 0; i < postWithAdv.length(); i++) {
//                            JSONObject mJsonObject = postWithAdv.getJSONObject(i);
//                            Log.d(TAG, mJsonObject.getString("adv_id"));
//                            Log.d(TAG, mJsonObject.getString("post_id"));
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//
//                    JSONObject response1 = response.getJSONObject("response");
//                    JSONArray postWithAdv = response1.getJSONArray("postWithAdv");
//                    JSONArray mobile = postWithAdv.getJSONArray("se");
//////
//                    jsonResponse = "";
//                    jsonResponse += "response: " + postWithAdv + "\n\n";
//                    jsonResponse += "Email: " + email + "\n\n";
//                    jsonResponse += "Home: " + home + "\n\n";
//                    jsonResponse += "Mobile: " + mobile + "\n\n";

////                    txtResponse.setText(jsonResponse);
//                    Log.d(TAG, response.toString());
//                    Toast.makeText(getActivity(), jsonResponse, Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
            }
        });

        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(jsonObjReq);



/*

        StringRequest strReq = new StringRequest(Request.Method.POST,
                REGISTER_URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getActivity(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("post_type", "1");
                params.put("post_no", "0");
                params.put("advertisement_no", "0");
                return params;
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {

                    responseString = String.valueOf(response.statusCode);

                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
*/
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonObjReq);


    }

    class RemoteDataTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            mAdapter = new ListviewAdapter(List, getActivity(), (ListviewAdapter.ListItemClickListener) getActivity());
            mAdapter.notifyDataSetChanged();
        }

        @Override
        protected String doInBackground(String... params) {
            JSONObject request1 = new JSONObject();
            try {
                request1.put("post_type", varFirstpost_type);
                request1.put("post_no", varFirstpost_no);
                request1.put("advertisement_no", varFirstadvertisement_no);
            } catch (Exception e) {
                e.printStackTrace();

            }

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    REGISTER_URL, request1, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
//                Log.i(TAG, response.toString());
//
                    try {
                        JSONObject response1 = response.getJSONObject("response");
                        JSONArray postWithAdv = response1.getJSONArray("postWithAdv");

                        varNewpost_no = response1.getString("post_no");
                        varNewadvertisement_no = response1.getString("advertisement_no");

                        if (!ArrayListContentCondittion.contains(varFirstpost_no)) {
                            try {
                                for (int i = 0; i < postWithAdv.length(); i++) {
                                    JSONObject mJsonObject = postWithAdv.getJSONObject(i);
                                    try {
//                                Log.d(TAG, i + " and " + mJsonObject.getString("post_id"));
                                        post_id = mJsonObject.getString("post_id");
//                                Log.d(TAG, i + " and " + mJsonObject.getString("post_title"));
                                        post_title = mJsonObject.getString("post_title");
//                                Log.d(TAG, i + " and " + mJsonObject.getString("full_name"));
                                        full_name = mJsonObject.getString("full_name");


                                        JSONArray user_image = mJsonObject.getJSONArray("post_images");
                                        try {
                                            //                                                if (j == 0) {
//                                            Log.d(TAG, i + " and " + user_image.getString(j));
//                                                mapOfList.add(user_image.getString(j));
//                                                }
                                            for (int j = 0; j < user_image.length(); j++) {
                                                post_images[j] = user_image.getString(j);
                                                post_imagesList.add(user_image.getString(j));
                                            }
                                        } catch (Exception ex) {
                                            Log.d(TAG, ex.toString());
                                        }
//                                        mapOfList.put(post_id, post_imagesList);

//                                        Log.d("vinay", post_id + " AND " + post_images[0] + " AND "+post_imagesList.get(0));

                                        List.add(new Recorddata(post_id, post_title, full_name, post_images, user_image.length(), post_imagesList));
//                                        List.add(new Recorddata(post_imagesList));
                                        post_imagesList.clear();
                                        mAdapter.notifyDataSetChanged();
                                    } catch (Exception ex) {
                                        Log.d(TAG, "Error: 1" + ex.toString());
                                    }
                                }
                            } catch (Exception ex) {
                                Log.d(TAG, "Error: 2" + ex.toString());
                            }
                            Log.d(TAG, varNewpost_no + " and " + varNewadvertisement_no);
                            mAdapter.notifyDataSetChanged();
                            ArrayListContentCondittion.add(varFirstpost_no);
                        }
                    } catch (JSONException e) {
                        Log.d(TAG, "Error: 3" + e.toString());
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "Error: " + error.getMessage());
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(jsonObjReq);
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mAdapter.notifyDataSetChanged();
            listview.setAdapter(mAdapter);
            progressBar.setVisibility(ProgressBar.INVISIBLE);

        }
    }

    private class LoadMoreDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params) {

            JSONObject request1 = new JSONObject();
            try {
                request1.put("post_type", varFirstpost_type);
                request1.put("post_no", varNewpost_no);
                request1.put("advertisement_no", varNewadvertisement_no);
            } catch (Exception e) {
                Log.d(TAG, e.toString());

            }

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    REGISTER_URL, request1, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
//                Log.i(TAG, response.toString());
//
                    try {
                        JSONObject response1 = response.getJSONObject("response");
                        JSONArray postWithAdv = response1.getJSONArray("postWithAdv");
                        varNewpost_no = response1.getString("post_no");
                        varNewadvertisement_no = response1.getString("advertisement_no");

                        if (!ArrayListContentCondittion.contains(varNewpost_no)) {
                            try {
                                for (int i = 0; i < postWithAdv.length(); i++) {
                                    JSONObject mJsonObject = postWithAdv.getJSONObject(i);
                                    try {
//                                Log.d(TAG, i + " and " + mJsonObject.getString("post_id"));
                                        post_id = mJsonObject.getString("post_id");
//                                Log.d(TAG, i + " and " + mJsonObject.getString("post_title"));
                                        post_title = mJsonObject.getString("post_title");
//                                Log.d(TAG, i + " and " + mJsonObject.getString("full_name"));
                                        full_name = mJsonObject.getString("full_name");


                                        JSONArray user_image = mJsonObject.getJSONArray("post_images");
                                        try {
                                            for (int j = 0; j < user_image.length(); j++) {
                                                post_images[j] = user_image.getString(j);
                                                post_imagesList.add(user_image.getString(j));
                                            }
                                        } catch (Exception ex) {
                                            Log.d(TAG, ex.toString());
                                        }
//                                    Log.d(TAG, i + " and " + post_id + "" + post_title);
//                                    mAdapter.notifyDataSetChanged();
                                        List.add(new Recorddata(post_id, post_title, full_name, post_images, user_image.length(), post_imagesList));
                                        post_imagesList.clear();
                                        mAdapter.notifyDataSetChanged();
                                    } catch (JSONException ex) {
                                        Log.d(TAG, "Error: 1" + ex.toString());
                                    }
                                }
                            } catch (JSONException ex) {
                                Log.d(TAG, "Error: 2" + ex.toString());
                            }
                            Log.d(TAG, varNewpost_no + " and " + varNewadvertisement_no);
                            mAdapter.notifyDataSetChanged();
                            ArrayListContentCondittion.add(varNewpost_no);
                        }
                    } catch (JSONException e) {
                        Log.d(TAG, "Error: 3" + e.toString());
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "Error: " + error.getMessage());
                    progressBar.setVisibility(ProgressBar.VISIBLE);
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(jsonObjReq);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            int position = listview.getLastVisiblePosition() - 3;
//            mAdapter = new ListviewAdapter(List, getActivity());
            mAdapter.notifyDataSetChanged();
//            listview.setAdapter(mAdapter);
            listview.setSelection(position);
            progressBar.setVisibility(View.INVISIBLE);


//            int position = listview.getLastVisiblePosition() - 3;
//            mAdapter = new ListviewAdapter(List, getActivity());
//            listview.setAdapter(mAdapter);
//            listview.setSelection(position);
//            mAdapter.notifyDataSetChanged();
////            bottomLayout.setVisibility(View.GONE);
//            progressBar.setVisibility(View.INVISIBLE);
        }
    }

}

