package com.example.hardel.revin;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Notice> noticeList = new ArrayList<>();
    public final static String TITLE = "com.example.hardel.revin._TIT";
    public final static String SUBTITLE = "com.example.hardel.revin._SUB";
    public final static String DESCRIPTION = "com.example.hardel.revin._CON";
    public final static String DATE = "com.example.hardel.revin._FY";
    public final static String CATEGORY = "com.example.hardel.revin._GATO";
    public final static String IMG = "com.example.hardel.revin._IM";

    private TextView recentNews, entretainmentNews, trendNews, artNews, scienceNews, localNews;
    private ListView dailyFeed;
    private EditText searchBar;
    private Button cancelSearch;
    private Animation resize, fade;
    private MenuItem menu;
    private String searchMatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadingData();

        setContentView(R.layout.activity_main);

        searchBar = (EditText) findViewById(R.id.search_textbox);
        searchBar.setEnabled(false);

        filterCategories();
        dailyFeed = (ListView) findViewById(R.id.news_list);

        resize = AnimationUtils.loadAnimation(this,R.anim.alpha);
        fade = AnimationUtils.loadAnimation(this,R.anim.fade);

        Toolbar t = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(t);
        getSupportActionBar().setTitle("Revista Interfaz");
        t.setTitleTextColor(Color.WHITE);


        dailyFeed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Notice n = noticeList.get(position);

                Intent in = new Intent(MainActivity.this, Content.class);
                in.putExtra(TITLE, n.getTitle());
                in.putExtra(SUBTITLE, n.getDescription());
                in.putExtra(DESCRIPTION, n.getSubtitle());
                in.putExtra(DATE, n.getDate());
                in.putExtra(CATEGORY, n.getCate());
                in.putExtra(IMG, n.getImgPath());

                startActivity(in);
            }
        });
    }

    TextWatcher tw = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            searchMatch = s.toString();
            RequestQueue request = Volley.newRequestQueue(MainActivity.this);

            JsonObjectRequest jobs = new JsonObjectRequest(Request.Method.GET,
                    "https://revistainterfazgq.000webhostapp.com/scripts/guet.php",
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jsonArr = response.getJSONArray("news");
                                String title, subtitle, detail, category, content, img;
                                List<Notice> notices = new ArrayList<>();

                                for(int i = 0; i < jsonArr.length(); i++) {
                                    JSONObject jObj = jsonArr.getJSONObject(i);

                                    title = jObj.getString("Titulo");
                                    subtitle = jObj.getString("Subtitulo");
                                    detail = jObj.getString("Fecha");
                                    category = jObj.getString("Categoria");
                                    content = jObj.getString("Noticia");
                                    img = jObj.getString("Imagen");

                                    if (title.contains(searchMatch)) {
                                        notices.add(new Notice(title, subtitle, detail, category, content, img));
                                    }
                                }

                                noticeList.clear();
                                noticeList = notices;

                                ArrayAdapter<Notice> an=new customComponent();
                                dailyFeed.setAdapter(an);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

            request.add(jobs);
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_bar, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedItem = item.getItemId();
        menu = item;

        if(selectedItem == R.id.lup){
            if(!searchBar.isEnabled()){
                getSupportActionBar().setTitle("");
                cancelSearch.setVisibility(View.VISIBLE);

                resize.reset();
                cancelSearch.clearAnimation();
                cancelSearch.setAnimation(resize);

                searchBar.setEnabled(true);
                searchBar.setText("Buscar");
                searchBar.setWidth(250);

                searchBar.addTextChangedListener(tw);

                resize.reset();
                searchBar.clearAnimation();
                searchBar.setAnimation(resize);

                menu.setEnabled(false);
            }
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }

    private void filterCategories() {
        recentNews = (TextView) findViewById(R.id.recent_added);
        entretainmentNews = (TextView) findViewById(R.id.entretainment);
        trendNews = (TextView) findViewById(R.id.trends);
        artNews = (TextView) findViewById(R.id.art);
        scienceNews = (TextView) findViewById(R.id.science);
        localNews = (TextView) findViewById(R.id.local);

        cancelSearch = (Button) findViewById(R.id.close);
        cancelSearch.setVisibility(View.GONE);

        View.OnClickListener vow=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v == recentNews){
                    changeSize(recentNews, entretainmentNews, trendNews, artNews, scienceNews, localNews);
                    loadingData();
                }else if(v == entretainmentNews){
                    changeSize(entretainmentNews, recentNews, trendNews, artNews, scienceNews, localNews);
                    loadingData("entretenimiento");
                }else if(v == trendNews){
                    changeSize(trendNews, entretainmentNews, recentNews, artNews, scienceNews, localNews);
                    loadingData("tendencias");
                }else if(v == artNews){
                    changeSize(artNews, entretainmentNews, recentNews, trendNews, scienceNews, localNews);
                    loadingData("arte");
                }else if(v == scienceNews){
                    changeSize(scienceNews, artNews, entretainmentNews, recentNews, trendNews, localNews);
                    loadingData("ciencia");
                }else if(v == localNews){
                    changeSize(localNews, artNews, entretainmentNews, recentNews, trendNews, scienceNews);
                    loadingData("interfaz");
                }else if(v == cancelSearch){
                    fade.reset();
                    searchBar.clearAnimation();
                    searchBar.setAnimation(fade);

                    searchBar.setText("");
                    searchBar.removeTextChangedListener(tw);
                    searchBar.setEnabled(false);
                    searchBar.setWidth(0);

                    fade.reset();
                    cancelSearch.clearAnimation();
                    cancelSearch.setAnimation(fade);

                    cancelSearch.setVisibility(View.GONE);
                    menu.setEnabled(true);
                    getSupportActionBar().setTitle("Revista Interfaz");

                    loadingData();
                }else if(v == searchBar){
                    if(searchBar.getText().toString().equals("Buscar")){
                        searchBar.setText("");
                    }
                }
            }
        };

        recentNews.setOnClickListener(vow);
        entretainmentNews.setOnClickListener(vow);
        trendNews.setOnClickListener(vow);
        artNews.setOnClickListener(vow);
        scienceNews.setOnClickListener(vow);
        localNews.setOnClickListener(vow);
        cancelSearch.setOnClickListener(vow);
        searchBar.setOnClickListener(vow);
    }

    private void changeSize(TextView selected, TextView tv1, TextView tv2, TextView tv3, TextView tv4, TextView tv5){
        selected.setTextSize(30f);
        tv1.setTextSize(15f);
        tv2.setTextSize(15f);
        tv3.setTextSize(15f);
        tv4.setTextSize(15f);
        tv5.setTextSize(15f);
    }

    private void loadingData() {
        RequestQueue request = Volley.newRequestQueue(this);

        JsonObjectRequest jobs = new JsonObjectRequest(Request.Method.GET,
                "https://revistainterfazgq.000webhostapp.com/scripts/guet.php",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArr = response.getJSONArray("news");
                            String title, subtitle, detail, category, content, img;
                            List<Notice> notices = new ArrayList<>();

                            for(int i = 0; i < jsonArr.length(); i++){
                                JSONObject jObj = jsonArr.getJSONObject(i);

                                title = jObj.getString("Titulo");
                                subtitle = jObj.getString("Subtitulo");
                                detail = jObj.getString("Fecha");
                                category = jObj.getString("Categoria");
                                content = jObj.getString("Noticia");
                                img = jObj.getString("Imagen");

                                notices.add(new Notice(title, subtitle, detail, category, content, img));
                            }

                            noticeList.clear();
                            noticeList = notices;

                            ArrayAdapter<Notice> an = new customComponent();
                            dailyFeed.setAdapter(an);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        request.add(jobs);
    }

    private void loadingData(final String condition) {
        RequestQueue request = Volley.newRequestQueue(this);

        JsonObjectRequest jobs = new JsonObjectRequest(Request.Method.GET,
                "https://revistainterfazgq.000webhostapp.com/scripts/guet.php",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArr = response.getJSONArray("news");
                            String title, subtitle, detail, category, content, img;
                            List<Notice> notices = new ArrayList<>();

                            for(int i = 0; i < jsonArr.length(); i++){
                                JSONObject jObj = jsonArr.getJSONObject(i);

                                title = jObj.getString("Titulo");
                                subtitle = jObj.getString("Subtitulo");
                                detail = jObj.getString("Fecha");
                                category = jObj.getString("Categoria");
                                content = jObj.getString("Noticia");
                                img = jObj.getString("Imagen");

                                if(category.equalsIgnoreCase(condition)){
                                    notices.add(new Notice(title, subtitle, detail, category, content, img));
                                }
                            }

                            noticeList.clear();
                            noticeList = notices;

                            ArrayAdapter<Notice> an = new customComponent();        //Array of notices
                            dailyFeed.setAdapter(an);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        request.add(jobs);
    }

    private class customComponent extends ArrayAdapter<Notice>{
        public customComponent() {
            super(MainActivity.this,R.layout.base_mini, noticeList);
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.base_mini,parent,false);
            }

            Notice n = noticeList.get(position);

            ImageView preview = (ImageView) convertView.findViewById(R.id.img_preview);
            TextView tw = (TextView) convertView.findViewById(R.id.title_preview);
            TextView tw2 = (TextView) convertView.findViewById(R.id.subtitle_preview);

            Picasso.with(MainActivity.this).load(n.getImgPath()).into(preview);
            tw.setText(n.getTitle());
            tw2.setText(n.getDescription());

            return convertView;
        }
    }
}
