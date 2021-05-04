package com.atisapp.hoorateb.MainFragment.ui.category;

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

public class CategoryAPIService {

    private static final String TAG = "CategoryAPIService";
    private Context context;
    private IdentitySharedPref identitySharedPref;

    public CategoryAPIService(Context context)
    {
        this.context = context;
        identitySharedPref = new IdentitySharedPref(context);
    }

    public void GetCategories(String type,final onGetCategories getCategories)
    {
        Log.i(TAG, "GetCategories: start");
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, ClientConfigs.REST_API_BASE_URL +"/v1/categories?type="+type ,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG, "onResponse: Categories recived ");
                try {
                    boolean res = response.getBoolean("success");
                    Log.i(TAG, "onCategoriesResponse: "+res);
                    //Log.i(TAG, "onResponse Episode : "+res);
                    //productPrefs.set_product_msg(response.getString("message"));
                    if(res)
                    {
                        getCategories.onGet(res,CategoryParserResponse(response));
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

    public void GetSubCategories(String categoryId,final onGetSubCategories getCategories)
    {
        Log.i(TAG, "GetMainLists: start");
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, ClientConfigs.REST_API_BASE_URL +"/v1/categories/"+categoryId ,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG, "onResponse: product list recived ");
                try {
                    boolean res = response.getBoolean("success");

                    if(res)
                    {
                        getCategories.onGet(res,SubCategoryParserResponse(response));
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

    private List<CategoryModel> CategoryParserResponse(JSONObject response)
    {
        List<CategoryModel> categoryModelList = new ArrayList<>();

        try {

            JSONArray array_category =  response.getJSONArray("data");
            for (int i = 0; i < array_category.length(); i++) {
                CategoryModel categoryModel = new CategoryModel();
                JSONObject object_category = array_category.getJSONObject(i);


                categoryModel.setCategoryId(object_category.getString("id"));
                categoryModel.setName(object_category.getString("name"));
                categoryModel.setType(object_category.getString("type"));
                categoryModel.setImage(object_category.getString("image"));

                JSONArray array_children =  object_category.getJSONArray("childs");
                List<ProductModel> childModelList = new ArrayList<>();

                for (int j = 0; j < array_children.length(); j++){
                    ProductModel productModel = new ProductModel();
                    JSONObject object_children = array_children.getJSONObject(j);

                    productModel.setProductId(object_children.getString("id"));
                    productModel.setTitle(object_children.getString("name"));
                    productModel.setCover(object_children.getString("image"));

                    childModelList.add(productModel);
                }
                categoryModel.setChildren(childModelList);
                categoryModelList.add(categoryModel);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "CategoryParserResponse: "+categoryModelList.size());
        return categoryModelList;
    }

    private List<ProductModel> SubCategoryParserResponse(JSONObject response)
    {
        List<ProductModel> subCategoryModelList = new ArrayList<>();

        try {

            JSONArray array_subCategory =  response.getJSONArray("data");
            for (int i = 0; i < array_subCategory.length(); i++) {
                ProductModel subCategoryModel = new ProductModel();
                JSONObject object_category = array_subCategory.getJSONObject(i);

                subCategoryModel.setParentId(object_category.getString("parent"));
                subCategoryModel.setProductId(object_category.getString("_id"));
                subCategoryModel.setTitle(object_category.getString("name"));
                subCategoryModel.setCover(object_category.getString("image"));

                subCategoryModelList.add(subCategoryModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return subCategoryModelList;
    }

    // Interface -----------------------------------------------------------------------------------4

    public interface onGetCategories
    {
        void onGet(boolean msg, List<CategoryModel> categoryModelList);
    }

    public interface onGetSubCategories
    {
        void onGet(boolean msg, List<ProductModel> productModels);
    }

}
