package com.example.alvaf1.changes2018;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.example.alvaf1.changes2018.Adapter.ItemsAdapter;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity  implements DoRequestCallback {
    ItemsAdapter itemsAdapter;
    TextView total;
    ListView listView;
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
    }

    private void updateItems() {

    }

    @Override
    public void onDataReady() {
        itemsAdapter=new ItemsAdapter(this);
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
                startActivity(intent);
            }
        });
    }

    }

