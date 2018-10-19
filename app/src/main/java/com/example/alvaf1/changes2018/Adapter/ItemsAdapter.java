package com.example.alvaf1.changes2018.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alvaf1.changes2018.AllUrl;
import com.example.alvaf1.changes2018.FileUtils;
import com.example.alvaf1.changes2018.Item;
import com.example.alvaf1.changes2018.R;
import com.example.alvaf1.changes2018.SaveListSingleton;
import com.example.alvaf1.changes2018.VolleySingletone;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ItemsAdapter extends BaseAdapter {

    Context context;
    ArrayList<Item> arrayList;
    LayoutInflater inflater;

    public void setItemsArray(ArrayList<Item> arrayList) {this.arrayList=arrayList;}

    public ItemsAdapter(Context context){
        this.context=context;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.arrayList=new ArrayList<>();
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView firstName, secondName, thirdName, party, votes, web;
        final ImageView candidates;
        CheckBox checkBox;
        Item items = (Item) getItem(i);
        view=inflater.inflate(R.layout.base_adapter,viewGroup,false);

        firstName=(TextView)view.findViewById(R.id.firstName);
        secondName=(TextView)view.findViewById(R.id.secondName);
        thirdName=(TextView)view.findViewById(R.id.thirdName);
        party=(TextView)view.findViewById(R.id.party);
        votes=(TextView)view.findViewById(R.id.votes);
        web=(TextView)view.findViewById(R.id.web);
        candidates=(ImageView)view.findViewById(R.id.candidat);

        firstName.setText(items.getFirstname());
        secondName.setText(items.getSecondname());
        thirdName.setText(items.getThirdname());
        party.setText(items.getParty());
        votes.setText(items.getVotes());
        web.setText(items.getWeb());
        String url="http://adlibtech.ru/elections/upload_images/"+SaveListSingleton.getInstance().items.get(i).getImage();

        checkBox=(CheckBox)view.findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    String url=AllUrl.Server+AllUrl.Host+AllUrl.Api+AllUrl.Check;
                    RequestQueue queue = Volley.newRequestQueue(context);
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>()
                            {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("ResponseCheck", response);

                                }

                                },
                            new Response.ErrorListener()
                            {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.d("Error.ResponseCheck", error.toString());
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams()
                        {
                            Map<String, String>  params = new HashMap<String, String>();
                            params.put("device_id","ID_АППАРАТА");
                            params.put("device_name", "НАЗВАНИЕ_АПАРАТА");
                            params.put("candidate_id","ID_КАНДИДАТА");
                            params.put("last_id","ID_КАНДИДАТА_КОТОРЫЙ_БЫЛ_ОТМЕЧЕН_ДО_ЭТОГО");
                            return params;
                        }
                    };
                }

            }
        });
        ImageLoader imageLoader=VolleySingletone.getInstance(this.context).getImageLoader();
        imageLoader.get(url, new ImageLoader.ImageListener() {

            @Override
            public void onErrorResponse(VolleyError error) {


            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {

            }
        });

        return view;
    }
}




































