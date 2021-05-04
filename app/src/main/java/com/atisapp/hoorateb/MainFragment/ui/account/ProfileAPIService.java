package com.atisapp.hoorateb.MainFragment.ui.account;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

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

public class ProfileAPIService {

    private static final String TAG = "ProfileAPIService";
    private Context context;
    private IdentitySharedPref identitySharedPref;

    public ProfileAPIService(Context context)
    {
        this.context = context;
        identitySharedPref = new IdentitySharedPref(context);
    }


    public void GetUserInfo(final onGetUserInfo getUserInfo)
    {

        Log.i(TAG, "GetMainLists: start");
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, ClientConfigs.REST_API_BASE_URL +"/v1/auth/me" ,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG, "onResponse: product list recived ");
                try {
                    boolean res = response.getBoolean("success");
                    Log.i(TAG, "onResponse: "+res);
                    if(res)
                    {
                        getUserInfo.onGet(res,GetUserParserResponse(response));
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

    public void SendUserInfo(UserModel userModel, final onSendUserInfo getUserInfo)
    {

        JSONObject profile_object = new JSONObject();
        try {
            profile_object.put("firstName",userModel.getFirstName());
            profile_object.put("lastName",userModel.getLastName());
            profile_object.put("birthday","");
            profile_object.put("nationalCode",userModel.getNationalCode());
            profile_object.put("cardNumber",userModel.getCardNumber());
            profile_object.put("email","");
            profile_object.put("phoneNumber",userModel.getPhoneNumber());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "GetMainLists: start");
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.PUT, ClientConfigs.REST_API_BASE_URL +"/v1/auth/updateDetails" ,profile_object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG, "onResponse: product list recived ");
                try {
                    boolean res = response.getBoolean("success");
                    Log.i(TAG, "onResponse: "+res);
                    if(res)
                    {
                        getUserInfo.onGet(res);
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

    public void GetUserAddresses(final onGetUserAddress userAddress)
    {

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, ClientConfigs.REST_API_BASE_URL +"/v1/addresses" ,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    boolean res = response.getBoolean("success");
                    if(res)
                    {
                        int length = response.getInt("length");
                        userAddress.onGet(res,GetAddressParserResponse(response),length);
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

    public void UpdateUserAddress(AddressModel addressModel, final onUpdateUserAddress sendUserAddress)
    {

        JSONObject address_object = new JSONObject();
        try {
            address_object.put("address",addressModel.getFullAddress());
            address_object.put("postalCode",addressModel.getPostalCode());
            address_object.put("unit",addressModel.getUnit());
            address_object.put("plate",addressModel.getPlate());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "GetMainLists: start");
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.PUT, ClientConfigs.REST_API_BASE_URL +"/v1/addresses/"+addressModel.getAddressId() ,address_object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG, "onResponse: product list recived ");
                try {
                    boolean res = response.getBoolean("success");
                    Log.i(TAG, "onResponse: "+res);
                    if(res)
                    {
                        sendUserAddress.onGet(res);
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

    public void CreateUserAddress(AddressModel addressModel, final onSendUserAddress sendUserAddress)
    {

        JSONObject address_object = new JSONObject();
        try {
            address_object.put("address",addressModel.getFullAddress());
            address_object.put("postalCode",addressModel.getPostalCode());
            address_object.put("unit",addressModel.getUnit());
            address_object.put("plate",addressModel.getPlate());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "GetMainLists: start");
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, ClientConfigs.REST_API_BASE_URL +"/v1/addresses" ,address_object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG, "onResponse: product list recived ");
                try {
                    boolean res = response.getBoolean("success");
                    Log.i(TAG, "onResponse: "+res);
                    if(res)
                    {
                        sendUserAddress.onGet(res);
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

    public void DeleteAddress(String id, final onDeleteAddress sendUserAddress)
    {

        Log.i(TAG, "GetMainLists: start");
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.DELETE, ClientConfigs.REST_API_BASE_URL +"/v1/addresses/"+id ,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    boolean res = response.getBoolean("success");
                    if(res)
                    {
                        sendUserAddress.onGet(res);
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

    public void ChangeAddress(String id, final onChangeAddress changeAddress)
    {

        Log.i(TAG, "GetMainLists: start");
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, ClientConfigs.REST_API_BASE_URL +"/v1/addresses/"+id+"/change" ,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    boolean res = response.getBoolean("success");
                    changeAddress.onGet(res);



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

    public void UploadAvatar(String avatar, final onUploadAvatar uploadAvatar)
    {

        JSONObject avatar_object = new JSONObject();
        try {
            avatar_object.put("file",avatar);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "GetMainLists: start");
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, ClientConfigs.REST_API_BASE_CDN +"/files?type=images" ,avatar_object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    boolean res = response.getBoolean("success");
                    Log.i(TAG, "onResponse: "+res);
                    if(res)
                    {
                        String url = response.getString("data");
                        uploadAvatar.onGet(res,url);
                    }
                    else
                        Toast.makeText(context,"اختلال در بارگذاری تصویر",Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onErrorResponse: erore"+error.getMessage());

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
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap< String, String > params = new HashMap < String, String > ();
                params.put("type","images");
                return params;
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

    private UserModel GetUserParserResponse(JSONObject response)
    {
        UserModel userModel = new UserModel();
        AddressModel addressModel = new AddressModel();
        try {
            Log.i(TAG, "GetUserParserResponse: start");
            JSONObject object_product = response.getJSONObject("data");

            userModel.setUserRole(object_product.getString("role"));
            userModel.setUserId(object_product.getString("id"));
            userModel.setMobileNumber(object_product.getString("mobileNumber"));
            userModel.setFirstName(object_product.getString("firstName"));
            userModel.setLastName(object_product.getString("lastName"));
            userModel.setNationalCode(object_product.getString("nationalCode"));
            userModel.setCardNumber(object_product.getString("cardNumber"));
            userModel.setPhoneNumber(object_product.getString("phoneNumber"));

            Log.i(TAG, "GetUserParserResponse: "+userModel.getLastName());

            JSONObject object_address = object_product.getJSONObject("address");
            if(object_address != null)
            {
                addressModel.setAddressId(object_address.getString("_id"));
                addressModel.setFullAddress(object_address.getString("address"));
                addressModel.setPostalCode(object_address.getString("postalCode"));
                addressModel.setUnit(object_address.getInt("unit"));
                addressModel.setPlate(object_address.getInt("plate"));
                addressModel.setActive(object_address.getBoolean("active"));
                userModel.setAddressModel(addressModel);

                Log.i(TAG, "GetUserParserResponse: "+addressModel.getFullAddress());

            }

            Log.i(TAG, "GetUserParserResponse: "+userModel.getFirstName());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userModel;
    }

    private List<AddressModel> GetAddressParserResponse(JSONObject response)
    {
        List<AddressModel> addressModelList = new ArrayList<>();

        try {

            JSONArray array_address =  response.getJSONArray("data");
            for (int i = 0; i < array_address.length(); i++) {
                AddressModel addressModelModel = new AddressModel();
                JSONObject object_address = array_address.getJSONObject(i);

                addressModelModel.setAddressId(object_address.getString("id"));
                addressModelModel.setFullAddress(object_address.getString("address"));
                addressModelModel.setPostalCode(object_address.getString("postalCode"));
                addressModelModel.setUnit(object_address.getInt("unit"));
                addressModelModel.setPlate(object_address.getInt("plate"));
                addressModelModel.setActive(object_address.getBoolean("active"));

                addressModelList.add(addressModelModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return addressModelList;
    }

    // Interface -----------------------------------------------------------------------------------


    public interface onGetUserInfo
    {
        void onGet(boolean msg, UserModel userModel);
    }

    public interface onSendUserInfo
    {
        void onGet(boolean msg);
    }

    public interface onGetUserAddress
    {
        void onGet(boolean msg, List<AddressModel> addressModelList,int length);
    }

    public interface onSendUserAddress
    {
        void onGet(boolean msg);
    }

    public interface onUpdateUserAddress
    {
        void onGet(boolean msg);
    }

    public interface onDeleteAddress
    {
        void onGet(boolean msg);
    }

    public interface onChangeAddress
    {
        void onGet(boolean msg);
    }

    public interface onUploadAvatar
    {
        void onGet(boolean msg,String url);
    }

}
