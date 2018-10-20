package com.example.alvaf1.changes2018;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.alvaf1.changes2018.Adapter.ItemSelectedCallback;
import com.example.alvaf1.changes2018.Adapter.ItemsAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity  implements DoRequestCallback, ItemSelectedCallback {
    ItemsAdapter itemsAdapter;
    TextView total;
    ListView listView;
    int selectedItemIndex = -1;
    boolean isParticipantChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Log.d("TAG","onCreate");
    Controller controller=new Controller(this);
    controller.registerOnGeekEventListener(this);
    controller.execute();


    }

    @Override
    protected void onResume() {
        super.onResume();
        Controller controller=new Controller(this);
        controller.registerOnGeekEventListener(this);
        controller.execute();
    }

    private void updateItems() {

    }

    @Override
    public void onDataReady() {
        itemsAdapter=new ItemsAdapter(this);
        itemsAdapter.registerOnItemSelectedEventListener(this);
        ArrayList<Item> item =  SaveListSingleton.getInstance().items;
        itemsAdapter.setItemsArray(SaveListSingleton.getInstance().items);

        listView=(ListView)findViewById(R.id.listView);
        listView.setAdapter(itemsAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Item item=new Item();
                item=SaveListSingleton.getInstance().items.get(i);
                Intent intent=new Intent();
                intent.setClass(getApplicationContext(),AboutCandidates.class);
                intent.putExtra("firstN",item.getFirstname());
                intent.putExtra("secondN",item.getSecondname());
                intent.putExtra("thirdN",item.getThirdname());
                intent.putExtra("description",item.getDescription());
                intent.putExtra("image", item.getImage());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemSelected(final int index) {
        String url = AllUrl.Server+AllUrl.Host+AllUrl.Api+AllUrl.Check;
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()  {
                    @Override
                    public void onResponse(String response) {

                        if (!isParticipantChecked) {

                            SaveListSingleton.getInstance().total++;


                        }

                        isParticipantChecked = true;

                        if (selectedItemIndex >= 0) {
                            View selectedView = getViewByPosition(selectedItemIndex, listView);
                            View oldView = getViewByPosition(index, listView);
                            CheckBox checkBox = selectedView.findViewById(R.id.checkBox);
                            checkBox.setChecked(false);
                            //SaveListSingleton.getInstance().;
                            //selectedView.votes++;
                        }

                        selectedItemIndex = index;
                        Log.d("Response", response);
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
                Item item=SaveListSingleton.getInstance().items.get(index);
                Map<String, String>  params = new HashMap<String, String>();
                params.put("device_id","ID_АППАРАТА");
                params.put("device_name", "НАЗВАНИЕ_АПАРАТА");
                params.put("candidate_id", item.getId());

                if (selectedItemIndex >= 0) {
                    Item oldItem = SaveListSingleton.getInstance().items.get(selectedItemIndex);
                    params.put("last_id",oldItem.getId());
                }

                return params;
            }
        };
        queue.add(postRequest);
        total=(TextView)findViewById(R.id.total);
        total.setText("Голосов: "+String.valueOf(SaveListSingleton.getInstance().total));



    }
    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

}

