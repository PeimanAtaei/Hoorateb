package com.atisapp.hoorateb.MainFragment.ui.home;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.atisapp.hoorateb.Core.ClientConfigs;
import com.atisapp.hoorateb.ModelsAndAdapters.ProductModel;
import com.atisapp.hoorateb.Storage.SharedPre.IdentitySharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeAPIService {

    private static final String TAG = "HomeAPIService";
    private Context context;
    private IdentitySharedPref identitySharedPref;

    public HomeAPIService(Context context)
    {
        this.context = context;
        identitySharedPref = new IdentitySharedPref(context);
    }

    public void GetMainLists(String filter,final onGetMainLists getMainLists)
    {
        Log.i(TAG, "GetMainLists: start");
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, ClientConfigs.REST_API_BASE_URL +"/v1/products?filter="+filter ,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG, "onResponse: product list recived ");
                try {
                    boolean res = response.getBoolean("success");
                    Log.i(TAG, "onResponse: "+res);
                    //Log.i(TAG, "onResponse Episode : "+res);
                    //productPrefs.set_product_msg(response.getString("message"));
                    if(res)
                    {
                        Log.i(TAG, "onResponse: "+response.getInt("length"));
                        getMainLists.onGet(res,MainListParserResponse(response));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e(TAG, "onErrorResponse: erore"+error.getMessage());

                NetworkResponse response = error.networkResponse;
                if(response != null && response.data != null){
                    switch(response.statusCode){
                        case 401:
                        {
                            //Log.e(TAG, "onErrorResponse: Token Expire" );
                            break;
                        }


                        case 204:
                        {
                            //Log.e(TAG, "onErrorResponse: No New Message" );
                            break;
                        }
                    }
                }
            }
        }){

            @Override
            public Map< String, String > getHeaders() throws AuthFailureError {
                HashMap< String, String > headers = new HashMap < String, String > ();

                headers.put("Authorization",identitySharedPref.getToken());

                return headers;
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError){
                if(volleyError.networkResponse != null && volleyError.networkResponse.data != null){
                    VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
                    volleyError = error;
                }

                return volleyError;
            }


        };

        objectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(objectRequest);

    }

    public void GetMainSlider(final onGetMainSlider getMainSlider)
    {
        Log.i(TAG, "GetMainSlider: start");
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, ClientConfigs.REST_API_BASE_URL +"/v1/sliders",null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG, "onSliderResponse: Slider list recived ");
                try {
                    boolean res = response.getBoolean("success");
                    Log.i(TAG, "onSliderResponse: "+res);

                    if(res)
                    {
                        getMainSlider.onGet(res,MainSliderParserResponse(response));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e(TAG, "onErrorResponse: erore"+error.getMessage());

                NetworkResponse response = error.networkResponse;
                if(response != null && response.data != null){
                    switch(response.statusCode){
                        case 401:
                        {
                            //Log.e(TAG, "onErrorResponse: Token Expire" );
                            break;
                        }


                        case 204:
                        {
                            //Log.e(TAG, "onErrorResponse: No New Message" );
                            break;
                        }
                    }
                }
            }
        }){

            @Override
            public Map< String, String > getHeaders() throws AuthFailureError {
                HashMap< String, String > headers = new HashMap < String, String > ();

                headers.put("Authorization",identitySharedPref.getToken());

                return headers;
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError){
                if(volleyError.networkResponse != null && volleyError.networkResponse.data != null){
                    VolleyError error = new VolleyError(new String(volleyError.networkResponse.data));
                    volleyError = error;
                }

                return volleyError;
            }


        };

        objectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(objectRequest);

    }


    // Parsers -------------------------------------------------------------------------------------

    private List<ProductModel> MainListParserResponse(JSONObject response)
    {
        List<ProductModel> productModelList = new ArrayList<>();

        try {

            JSONArray array_episodes =  response.getJSONArray("data");
            for (int i = 0; i < array_episodes.length(); i++) {
                ProductModel productModel = new ProductModel();
                JSONObject object_product = array_episodes.getJSONObject(i);


                productModel.setProductId(object_product.getString("id"));
                productModel.setTitle(object_product.getString("title"));
                productModel.setDescription(object_product.getString("description"));
                productModel.setPrice(object_product.getInt("price"));
                productModel.setCover(object_product.getString("cover"));
                Log.i(TAG, "MainListParserResponse: "+object_product.getString("cover"));

                JSONObject object_brand = object_product.getJSONObject("brand");
                productModel.setBrand(object_brand.getString("name"));

                Log.i(TAG, "MainListParserResponse: "+productModel.getBrand());
                productModelList.add(productModel);

                //Log.i(TAG, "ProductParsResponse: "+object_products.getString("url"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "EpisodeParserResponse: "+productModelList.size());
        return productModelList;
    }

    private List<HomeSliderModel> MainSliderParserResponse(JSONObject response)
    {
        List<HomeSliderModel> homeSliderModelList = new ArrayList<>();

        try {

            JSONArray array_episodes =  response.getJSONArray("data");
            for (int i = 0; i < array_episodes.length(); i++) {
                HomeSliderModel homeSliderModel = new HomeSliderModel();
                JSONObject object_product = array_episodes.getJSONObject(i);

                homeSliderModel.setSliderId(object_product.getString("url"));
                homeSliderModel.setImageUrl(object_product.getString("image"));

                homeSliderModelList.add(homeSliderModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return homeSliderModelList;
    }

    // Interface -----------------------------------------------------------------------------------4


    public interface onGetMainLists
    {
        void onGet(boolean msg, List<ProductModel> productModelList);
    }
    public interface onGetMainSlider
    {
        void onGet(boolean msg, List<HomeSliderModel> sliderModelList);
    }
}
