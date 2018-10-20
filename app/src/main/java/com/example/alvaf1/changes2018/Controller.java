package com.example.alvaf1.changes2018;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

interface DoRequestCallback {
    void onDataReady();
}

public class Controller {
    Context context;

    private DoRequestCallback mListener;

    public void registerOnGeekEventListener(DoRequestCallback mListener) {
        this.mListener = mListener;
    }

    public Controller(Context context){this.context=context;}

    String url=AllUrl.Server+AllUrl.Host+AllUrl.Api+AllUrl.Candidates;

    public void execute() {

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest= new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()  {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        try{

                            ArrayList<Item> items = new ArrayList();
                            JSONArray jsonArray=new JSONArray(response);
                            for(int j=0;j<jsonArray.length()-1;j++){

                                Item item=new Item();
                                JSONObject jsonObject=jsonArray.getJSONObject(j);
                                item.setId((String) jsonObject.getString("id"));
                                item.setFirstname((String)jsonObject.getString("firstname"));
                                item.setSecondname((String)jsonObject.getString("secondname"));
                                item.setThirdname((String)jsonObject.getString("thirdname"));
                                item.setVotes((String)jsonObject.getString("votes"));
                                item.setWeb((String)jsonObject.getString("web"));
                                item.setParty((String)jsonObject.getString("party"));
                                item.setDescription((String)jsonObject.getString("description"));
                                item.setImage((String)jsonObject.getString("image"));
                                items.add(item);
                            }
                            SaveListSingleton.getInstance().items = items;
                            SaveListSingleton.getInstance().total = jsonArray.getJSONObject(jsonArray.length() - 1).getInt("total");

                            if (mListener != null) {
                                mListener.onDataReady();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }catch (NullPointerException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("device_id","ID_АППАРАТА");
                params.put("device_name","НАЗВАНИЕ_АПАРАТА");
                return params;
            }
        };
        queue.add(stringRequest);


    }}







