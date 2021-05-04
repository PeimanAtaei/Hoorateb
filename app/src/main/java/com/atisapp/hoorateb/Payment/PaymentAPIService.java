package com.atisapp.hoorateb.Payment;

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
import com.atisapp.hoorateb.Comment.CommentAPIService;
import com.atisapp.hoorateb.Comment.CommentModel;
import com.atisapp.hoorateb.Core.ClientConfigs;
import com.atisapp.hoorateb.MainFragment.ui.basket.ItemOrderModel;
import com.atisapp.hoorateb.ModelsAndAdapters.ProductModel;
import com.atisapp.hoorateb.Storage.SharedPre.IdentitySharedPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaymentAPIService {

    private static final String TAG = "CommentAPIService";
    private Context context;
    private IdentitySharedPref identitySharedPref;

    public PaymentAPIService(Context context)
    {
        this.context = context;
        identitySharedPref = new IdentitySharedPref(context);
    }

    public void GetPaymentList(final onGetPaymentList getPaymentList)
    {
        Log.i(TAG, "GetPaymentList: ");
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, ClientConfigs.REST_API_BASE_URL +"/v1/payments",null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.i(TAG, "onResponse: product list received");

                try {
                    boolean res = response.getBoolean("success");
                    //productPrefs.set_product_msg(response.getString("message"));
                    if(res)
                    {
                        getPaymentList.onGet(res,PaymentListParsResponse(response));
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
                            Log.e(TAG, error.getMessage() );
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

    public void GetPaymentDetail(String paymentId,final onGetPaymentDetail getPaymentDetail)
    {

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, ClientConfigs.REST_API_BASE_URL +"/v1/payments/"+paymentId,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.i(TAG, "onResponse: product list received");

                try {
                    boolean res = response.getBoolean("success");
                    //productPrefs.set_product_msg(response.getString("message"));
                    if(res)
                    {
                        getPaymentDetail.onGet(res,PaymentDetailParsResponse(response));
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
                            Log.e(TAG, error.getMessage() );
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

    private List<PaymentModel> PaymentListParsResponse(JSONObject response)
    {
        List<PaymentModel> paymentModelList = new ArrayList<>();

        try {

            JSONArray array_payments =  response.getJSONArray("data");

            for (int i = 0; i < array_payments.length(); i++) {


                PaymentModel paymentModel = new PaymentModel();
                JSONObject object_payment = array_payments.getJSONObject(i);

                paymentModel.setPaymentId(object_payment.getString("id"));
                paymentModel.setPaymentPrice(object_payment.getInt("price"));
                paymentModel.setPaymentPaid(object_payment.getBoolean("paid"));
                paymentModel.setPaymentStatus(object_payment.getString("status"));
                paymentModel.setPaymentNumber(object_payment.getString("trackNumber"));
                paymentModel.setPaymentDate(object_payment.getString("createdAt"));

                paymentModelList.add(paymentModel);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return paymentModelList;
    }

    private PaymentModel PaymentDetailParsResponse(JSONObject response)
    {
        PaymentModel paymentModel = new PaymentModel();
        try {
            JSONObject object_payment = response.getJSONObject("data");
            paymentModel.setPaymentId(object_payment.getString("id"));
            paymentModel.setPaymentPrice(object_payment.getInt("price"));
            paymentModel.setPaymentPaid(object_payment.getBoolean("paid"));
            paymentModel.setPaymentStatus(object_payment.getString("status"));
            paymentModel.setPaymentNumber(object_payment.getString("trackNumber"));
            paymentModel.setPaymentDate(object_payment.getString("createdAt"));

            JSONArray array_orders =  object_payment.getJSONArray("orders");
            List<ItemOrderModel> itemOrderModelList = new ArrayList<>();

            for (int i = 0; i < array_orders.length(); i++) {


                ItemOrderModel itemOrderModel = new ItemOrderModel();
                JSONObject object_order = array_orders.getJSONObject(i);

                itemOrderModel.setItemId(object_order.getString("id"));
                itemOrderModel.setPrice(object_order.getInt("price"));
                itemOrderModel.setQuantity(object_order.getInt("quantity"));

                ProductModel productModel = new ProductModel();
                JSONObject object_product = object_order.getJSONObject("product");
                productModel.setProductId(object_product.getString("id"));
                productModel.setTitle(object_product.getString("title"));
                productModel.setDescription(object_product.getString("description"));
                productModel.setPrice(object_product.getInt("price"));
                productModel.setCover(object_product.getString("cover"));
                itemOrderModel.setProductModel(productModel);

                itemOrderModelList.add(itemOrderModel);

            }
            paymentModel.setOrderList(itemOrderModelList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return paymentModel;
    }


    // Interfaces ----------------------------------------------------------------------------------

    public interface onGetPaymentList
    {
        void onGet(boolean msg, List<PaymentModel> paymentModelList);
    }

    public interface onGetPaymentDetail
    {
        void onGet(boolean msg, PaymentModel paymentModel);
    }

}
