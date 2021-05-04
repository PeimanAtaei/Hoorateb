package com.atisapp.hoorateb.MainFragment.ui.basket;

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
import com.atisapp.hoorateb.Comment.CommentModel;
import com.atisapp.hoorateb.Core.ClientConfigs;
import com.atisapp.hoorateb.MainFragment.ui.category.CategoryModel;
import com.atisapp.hoorateb.MainFragment.ui.home.HomeAPIService;
import com.atisapp.hoorateb.ModelsAndAdapters.ProductModel;
import com.atisapp.hoorateb.Storage.SharedPre.IdentitySharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardAPIService {

    private static final String TAG = "CardAPIService";
    private Context context;
    private IdentitySharedPref identitySharedPref;

    public CardAPIService(Context context)
    {
        this.context = context;
        identitySharedPref = new IdentitySharedPref(context);
    }

    public void GetUserCard(final onGetUserCard getUserCard)
    {

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, ClientConfigs.REST_API_BASE_URL +"/v1/carts" ,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    boolean res = response.getBoolean("success");

                    if(res)
                    {
                        getUserCard.onGet(res,OrderParsResponse(response));
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

    public void GetUseFavorite(final onGetUserFavorite userFavorite)
    {

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, ClientConfigs.REST_API_BASE_URL +"/v1/favorites" ,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    boolean res = response.getBoolean("success");

                    if(res)
                    {
                        userFavorite.onGet(res,FavoriteParsResponse(response));
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

    public void AddToCard(String productId,final onAddToCard addToCard)
    {

        JSONObject cardObj = new JSONObject();
        try {
            cardObj.put("productId",productId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "GetMainLists: start");
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, ClientConfigs.REST_API_BASE_URL +"/v1/carts" ,cardObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG, "onResponse: product list recived ");
                try {
                    boolean res = response.getBoolean("success");
                    Log.i(TAG, "onResponse: "+res);
                    if(res)
                    {
                        addToCard.onGet(res);
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

    public void DeleteFromCard(String productId,final onDeleteFromCard delete)
    {

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.DELETE, ClientConfigs.REST_API_BASE_URL +"/v1/orders/"+productId ,null , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    boolean res = response.getBoolean("success");

                    if(res)
                    {
                        delete.onGet(res);
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

    public void AddToFavorite(String productId,final onAddToFavorite addToCard)
    {

        JSONObject cardObj = new JSONObject();
        try {
            cardObj.put("productId",productId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "GetMainLists: start");
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, ClientConfigs.REST_API_BASE_URL +"/v1/favorites" ,cardObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG, "onResponse: product list recived ");
                try {
                    boolean res = response.getBoolean("success");
                    Log.i(TAG, "onResponse: "+res);
                    if(res)
                    {
                        addToCard.onGet(res);
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

    public void DeleteFromFavorite(String productId,final onDeleteFromFavorite delete)
    {
        Log.i(TAG, "DeleteFromFavorite: "+productId);
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.DELETE, ClientConfigs.REST_API_BASE_URL +"/v1/favorites/"+productId ,null , new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    boolean res = response.getBoolean("success");

                    if(res)
                    {
                        delete.onGet(res);
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

    public void UpdateQuantity(String productId,int count,final onUpdateQuantity updateQuantity)
    {

        JSONObject cardObj = new JSONObject();
        try {
            cardObj.put("quantity",count);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.PATCH, ClientConfigs.REST_API_BASE_URL +"/v1/orders/"+productId+"/quantity" ,cardObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    boolean res = response.getBoolean("success");

                    if(res)
                    {
                        updateQuantity.onGet(res);
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

    public void DeleteOrder(String orderId,final onDeleteOrder deleteOrder)
    {

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.DELETE, ClientConfigs.REST_API_BASE_URL +"/v1/orders/"+orderId,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    boolean res = response.getBoolean("success");

                    if(res)
                    {
                        deleteOrder.onDelete(res);
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

    public void PaymentOrder(String orderId,final onStartPayment startPayment)
    {

        JSONObject paymentObj = new JSONObject();
        try {
            paymentObj.put("cartId",orderId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, ClientConfigs.REST_API_BASE_URL +"/v1/payments",paymentObj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    boolean res = response.getBoolean("success");
                    String url = response.getString("data");

                    if(res)
                    {
                        startPayment.onStart(res,url);
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

    public void CheckUserDetail(final onCheckUserDetail userDetail)
    {

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, ClientConfigs.REST_API_BASE_URL +"/v1/auth/checkUserDetails",null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    boolean res = response.getBoolean("success");
                    boolean data = response.getBoolean("data");

                    if(res)
                    {
                        userDetail.onCheck(data);
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

    private TotalOrderModel OrderParsResponse(JSONObject response)
    {
        TotalOrderModel totalOrderModel = new TotalOrderModel();

        try {

            totalOrderModel.setTotalCount(response.getInt("length"));
            JSONObject obj_order = response.getJSONObject("data");

            totalOrderModel.setOrderId(obj_order.getString("id"));
            totalOrderModel.setTotalPrice(obj_order.getInt("total"));

            JSONArray array_items =  obj_order.getJSONArray("orders");
            List<ItemOrderModel> itemOrderModels = new ArrayList<>();
            for (int i = 0; i < array_items.length(); i++) {

                ItemOrderModel itemOrderModel = new ItemOrderModel();
                ProductModel productModel = new ProductModel();
                JSONObject object_items = array_items.getJSONObject(i);

                itemOrderModel.setQuantity(object_items.getInt("quantity"));
                itemOrderModel.setPrice(object_items.getInt("price"));
                itemOrderModel.setItemId(object_items.getString("id"));

                JSONObject obj_product = object_items.getJSONObject("product");
                productModel.setProductId(obj_product.getString("id"));
                productModel.setTitle(obj_product.getString("title"));
                productModel.setDescription(obj_product.getString("description"));
                productModel.setCover(obj_product.getString("cover"));
                productModel.setPrice(obj_product.getInt("price"));

                JSONArray array_categories =  obj_product.getJSONArray("categories");
                List<CategoryModel> categoryModelList = new ArrayList<>();
                for (int j = 0; j < array_categories.length(); j++) {
                    CategoryModel categoryModel = new CategoryModel();
                    JSONObject object_category = array_categories.getJSONObject(j);
                    categoryModel.setCategoryId(object_category.getString("id"));
                    categoryModel.setName(object_category.getString("name"));
                    categoryModelList.add(categoryModel);

                }
                productModel.setCategories(categoryModelList);

                JSONObject obj_brand = obj_product.getJSONObject("brand");
                productModel.setBrandId(obj_brand.getString("id"));
                productModel.setBrand(obj_brand.getString("name"));

                itemOrderModel.setProductModel(productModel);

                itemOrderModels.add(itemOrderModel);


            }
            totalOrderModel.setItemsList(itemOrderModels);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return totalOrderModel;
    }

    private List<ProductModel> FavoriteParsResponse(JSONObject response)
    {
        List<ProductModel> productModelList = new ArrayList<>();

        try {

            JSONArray array_items =  response.getJSONArray("data");

            for (int i = 0; i < array_items.length(); i++) {

                ProductModel productModel = new ProductModel();
                JSONObject object_items = array_items.getJSONObject(i);

                productModel.setProductId(object_items.getString("id"));
                productModel.setTitle(object_items.getString("title"));
                productModel.setDescription(object_items.getString("description"));
                productModel.setPrice(object_items.getInt("price"));
                productModel.setCover(object_items.getString("cover"));

                productModelList.add(productModel);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return productModelList;
    }


    // Interface -----------------------------------------------------------------------------------4


    public interface onGetUserCard
    {
        void onGet(boolean msg,TotalOrderModel totalOrderModel);
    }

    public interface onGetUserFavorite
    {
        void onGet(boolean msg,List<ProductModel> productModelList);
    }

    public interface onAddToCard
    {
        void onGet(boolean msg);
    }

    public interface onDeleteFromCard
    {
        void onGet(boolean msg);
    }

    public interface onAddToFavorite
    {
        void onGet(boolean msg);
    }

    public interface onDeleteFromFavorite
    {
        void onGet(boolean msg);
    }

    public interface onUpdateQuantity
    {
        void onGet(boolean msg);
    }

    public interface onDeleteOrder
    {
        void onDelete(boolean msg);
    }

    public interface onStartPayment
    {
        void onStart(boolean msg,String url);
    }

    public interface onCheckUserDetail
    {
        void onCheck(boolean msg);
    }

}
