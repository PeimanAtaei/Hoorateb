package com.atisapp.hoorateb.Product;

import android.app.ProgressDialog;
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
import com.atisapp.hoorateb.MainFragment.ui.category.CategoryAPIService;
import com.atisapp.hoorateb.MainFragment.ui.category.CategoryModel;
import com.atisapp.hoorateb.ModelsAndAdapters.ProductModel;
import com.atisapp.hoorateb.ModelsAndAdapters.ProductSpecificationModel;
import com.atisapp.hoorateb.Storage.SharedPre.IdentitySharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductAPIService {

    private static final String TAG = "ProductAPIService";
    private Context context;
    private ProgressDialog progressDialog;
    private IdentitySharedPref identitySharedPref;

    public ProductAPIService(Context context)
    {

        this.context = context;
        identitySharedPref = new IdentitySharedPref(context);

    }

    public void GetProductDetail(String productId,final onGetProductDetail getProductDetail)
    {
        Log.i(TAG, "GetMainLists: start");
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, ClientConfigs.REST_API_BASE_URL +"/v1/products/"+productId ,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG, "onResponse: product list recived ");
                try {
                    boolean res = response.getBoolean("success");

                    if(res)
                    {
                        getProductDetail.onGetDetail(res,DetailParserResponse(response));
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

    public void GetProductList(String categoryId,final onGetProductList getProductList)
    {
        Log.i(TAG, "GetMainLists: start");
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, ClientConfigs.REST_API_BASE_URL +"/v1/categories/"+categoryId+"/products" ,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG, "onResponse: product list recived ");
                try {
                    boolean res = response.getBoolean("success");

                    if(res)
                    {
                        getProductList.onGetList(res,ProductListParserResponse(response));
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

    public void GetSearchList(String keyWord,final onGetProductList getProductList)
    {
        Log.i(TAG, "GetMainLists: start");
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, ClientConfigs.REST_API_BASE_URL +"/v1/products?search="+keyWord ,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG, "onResponse: product list recived ");
                try {
                    boolean res = response.getBoolean("success");

                    if(res)
                    {
                        getProductList.onGetList(res,ProductListParserResponse(response));
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

    public void GetProductFeatures(String productId,final onGetProductFeatures productFeatures)
    {
        Log.i(TAG, "GetMainLists: start");
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, ClientConfigs.REST_API_BASE_URL +"/v1/products/"+productId+"/details" ,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    boolean res = response.getBoolean("success");
                    if(res)
                    {
                        JSONObject object_features = response.getJSONObject("data");

                        boolean favorite = object_features.getBoolean("isFavorite");
                        boolean cart = object_features.getBoolean("isCart");
                        productFeatures.onGetFeatures(res,favorite,cart);
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




    // Parser --------------------------------------------------------------------------------------

    private ProductModel DetailParserResponse(JSONObject response)
    {
        ProductModel productModel = new ProductModel();

        try {
            JSONObject object_product = response.getJSONObject("data");

            productModel.setProductId(object_product.getString("id"));
            productModel.setTitle(object_product.getString("title"));
            productModel.setDescription(object_product.getString("description"));
            productModel.setPrice(object_product.getInt("price"));
            productModel.setViewCount(object_product.getInt("viewCount"));
            productModel.setCommentCount(object_product.getInt("commentCount"));
            productModel.setCover(object_product.getString("cover"));
            Log.i(TAG, "DetailParserResponse Cover: "+object_product.getString("cover"));

            JSONArray array_specification =  object_product.getJSONArray("specifications");
            List<ProductSpecificationModel> specificationModels = new ArrayList<>();
            for (int i = 0; i < array_specification.length(); i++) {
                ProductSpecificationModel specificationModel = new ProductSpecificationModel();
                JSONObject object_specification = array_specification.getJSONObject(i);
                specificationModel.setSpId(object_specification.getString("_id"));
                specificationModel.setName(object_specification.getString("name"));
                specificationModel.setValue(object_specification.getString("value"));
                specificationModels.add(specificationModel);
            }
            productModel.setSpecificationModelList(specificationModels);

            JSONArray array_reviews =  object_product.getJSONArray("reviews");
            List<ProductSpecificationModel> reviewsModels = new ArrayList<>();
            for (int i = 0; i < array_reviews.length(); i++) {
                ProductSpecificationModel reviewsModel = new ProductSpecificationModel();
                JSONObject object_reviews = array_reviews.getJSONObject(i);
                reviewsModel.setSpId(object_reviews.getString("_id"));
                reviewsModel.setName(object_reviews.getString("title"));
                reviewsModel.setValue(object_reviews.getString("body"));
                reviewsModels.add(reviewsModel);
            }
            productModel.setReviewsModelList(reviewsModels);

            JSONArray array_Images =  object_product.getJSONArray("images");
            List<String> Images = new ArrayList<>();
            for (int j = 0; j < array_Images.length(); j++) {
                String ImageUrl = "";
                JSONObject object_images = array_Images.getJSONObject(j);
                ImageUrl = object_images.getString("url");
                Images.add(ImageUrl);
            }
            productModel.setImagesUrl(Images);

            JSONArray array_categories =  object_product.getJSONArray("categories");
            List<CategoryModel> categoryModelList = new ArrayList<>();
            for (int j = 0; j < array_categories.length(); j++) {
                CategoryModel categoryModel = new CategoryModel();
                JSONObject object_category = array_categories.getJSONObject(j);
                categoryModel.setCategoryId(object_category.getString("_id"));
                categoryModel.setName(object_category.getString("name"));
                categoryModel.setType(object_category.getString("type"));
                categoryModel.setImage(object_category.getString("image"));
                Log.i(TAG, "DetailParserResponse: "+object_category.getString("name"));
                categoryModelList.add(categoryModel);
            }
            productModel.setCategories(categoryModelList);

            JSONObject object_brand = object_product.getJSONObject("brand");
            productModel.setBrandId(object_brand.getString("_id"));
            productModel.setBrand(object_brand.getString("name"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return productModel;
    }

    private List<ProductModel> ProductListParserResponse(JSONObject response)
    {
        List<ProductModel> productListModels = new ArrayList<>();

        try {

            JSONArray array_productList =  response.getJSONArray("data");
            for (int i = 0; i < array_productList.length(); i++) {
                ProductModel productListModel = new ProductModel();
                JSONObject object_productList = array_productList.getJSONObject(i);

                productListModel.setProductId(object_productList.getString("id"));
                productListModel.setTitle(object_productList.getString("title"));
                productListModel.setDescription(object_productList.getString("description"));
                productListModel.setPrice(object_productList.getInt("price"));
                productListModel.setCover(object_productList.getString("cover"));
                Log.i(TAG, "ProductListParserResponse: "+object_productList.getString("cover"));

                productListModels.add(productListModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return productListModels;
    }

    // Interface -----------------------------------------------------------------------------------

    public interface onGetProductDetail
    {
        void onGetDetail(boolean res, ProductModel productModel);
    }

    public interface onGetProductList
    {
        void onGetList(boolean res, List<ProductModel> productModelList);
    }

    public interface onGetProductFeatures
    {
        void onGetFeatures(boolean res,boolean favorite,boolean cart);
    }

}
