package com.example.alvaf1.changes2018;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

public class AboutCandidates extends AppCompatActivity {
    TextView name, secondName, thirdName, description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_candidates);

        name=(TextView)findViewById(R.id.name);
        secondName=(TextView)findViewById(R.id.secondName);
        thirdName=(TextView)findViewById(R.id.thirdName);
        description=(TextView)findViewById(R.id.description);
        ImageView candidat=(ImageView)findViewById(R.id.candidat);
        Item item=new Item();
        Bundle extras=getIntent().getExtras();
        String firstN=extras.getString("firstN");
        String secondN=extras.getString("secondN");
        String thirdN=extras.getString("thirdN");
        String des=extras.getString("description");
        name.setText(firstN+" ");
        secondName.setText(secondN);
        thirdName.setText(thirdN);
        description.setText(des);

        String url=AllUrl.Server+AllUrl.Host+AllUrl.Images+'/'+item.getImage();
        downloadImage(url, candidat);
    }
    private void downloadImage(String url, final ImageView candidat) {
        ImageLoader imageLoader=VolleySingletone.getInstance(this).getImageLoader();
        imageLoader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {

            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

    }

    public void onClick(View view) {
        Intent intent=new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

  /*  private class CheckImage extends AsyncTask<Object, Void, Boolean> {
        public CheckImage(ImageView candidat, String image) {
        }

        @Override
        protected Boolean doInBackground(Object[] objects) {
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }*/
}
