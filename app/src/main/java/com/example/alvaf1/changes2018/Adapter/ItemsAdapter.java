package com.example.alvaf1.changes2018.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.alvaf1.changes2018.AllUrl;
import com.example.alvaf1.changes2018.Item;
import com.example.alvaf1.changes2018.R;
import com.example.alvaf1.changes2018.VolleySingletone;

import java.util.ArrayList;


public class ItemsAdapter extends BaseAdapter {

    private ItemSelectedCallback mListener;

    public void registerOnItemSelectedEventListener(ItemSelectedCallback mListener) {
        this.mListener = mListener;
    }

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
    public View getView(final int i, View view, ViewGroup viewGroup) {
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
        candidates=(ImageView)view.findViewById(R.id.candidates);

        firstName.setText(items.getFirstname());
        secondName.setText(items.getSecondname());
        thirdName.setText(items.getThirdname());
        party.setText(items.getParty());
        votes.setText(items.getVotes());
        web.setText(items.getWeb());


        checkBox=(CheckBox)view.findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    if (mListener != null) {
                        mListener.onItemSelected(i);
                    }
                }

            }
        });
        String url=AllUrl.Server+AllUrl.Host+AllUrl.Images+items.getImage();
        ImageLoader imageLoader=VolleySingletone.getInstance(this.context).getImageLoader();
        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    Log.d("Error","volley image error");
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                if (response.getBitmap() != null) {
                    candidates.setImageBitmap(response.getBitmap());
                }

            }
        });
        return view;
    }
}




































