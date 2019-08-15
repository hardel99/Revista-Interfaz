package com.example.hardel.revin;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<nius> nuev=new ArrayList<nius>();
    public final static String TIL="com.example.hardel.revin._TIT";
    public final static String SUBT="com.example.hardel.revin._SUB";
    public final static String CONT="com.example.hardel.revin._CON";
    public final static String FAI="com.example.hardel.revin._FY";
    public final static String CATER="com.example.hardel.revin._GATO";
    public final static String IMA="com.example.hardel.revin._IM";

    private TextView novo,dive,tren,arto,sayens,yey;
    private ListView aja;
    private EditText edt;
    private Button claus;
    private Animation resai,puf;
    private MenuItem mi;
    private String ha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadining();

        setContentView(R.layout.activity_main);

        edt= (EditText) findViewById(R.id.eti);
        edt.setEnabled(false);

        textagorias();
        aja = (ListView) findViewById(R.id.weno);

        resai= AnimationUtils.loadAnimation(this,R.anim.alpha);
        puf= AnimationUtils.loadAnimation(this,R.anim.puff);

        Toolbar tu = (Toolbar) findViewById(R.id.atles);
        setSupportActionBar(tu);
        getSupportActionBar().setTitle("Revista Interfaz");
        tu.setTitleTextColor(Color.WHITE);


        //getActuali(dut,"SELECT * FROM news ORDER BY ID DESC");

        aja.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                nius ni=nuev.get(position);

                Intent in=new Intent(MainActivity.this,conten.class);
                in.putExtra(TIL,ni.getTit());
                in.putExtra(SUBT,ni.getmimid());
                in.putExtra(CONT,ni.getDescr());
                in.putExtra(FAI,ni.getFecha());
                in.putExtra(CATER,ni.getCate());
                in.putExtra(IMA,ni.getPata());
                startActivity(in);
            }
        });
    }

    TextWatcher tw=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            ha=s.toString();
            RequestQueue requ= Volley.newRequestQueue(MainActivity.this);

            JsonObjectRequest jobs=new JsonObjectRequest(Request.Method.GET,
                    "https://revistainterfazgq.000webhostapp.com/scripts/guet.php",
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONArray jara=response.getJSONArray("news");
                                String taitai,subtaitai,deit,cateo,noti,ima;
                                List<nius> suin= new ArrayList<nius>();

                                for(int i=0;i<jara.length();i++){
                                    JSONObject gor=jara.getJSONObject(i);

                                    taitai=gor.getString("Titulo");
                                    subtaitai=gor.getString("Subtitulo");
                                    deit=gor.getString("Fecha");
                                    cateo=gor.getString("Categoria");
                                    noti=gor.getString("Noticia");
                                    ima=gor.getString("Imagen");

                                    if(taitai.contains(ha)){
                                        suin.add(new nius(taitai,subtaitai,deit,cateo,noti,ima));
                                    }
                                }

                                nuev.clear();
                                nuev=suin;

                                ArrayAdapter<nius> an=new custom();
                                aja.setAdapter(an);
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

            requ.add(jobs);
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.serchiar,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int ifi=item.getItemId();
        mi=item;

        if(ifi==R.id.lup){
            if(!edt.isEnabled()){
                getSupportActionBar().setTitle("");
                claus.setVisibility(View.VISIBLE);

                resai.reset();
                claus.clearAnimation();
                claus.setAnimation(resai);

                edt.setEnabled(true);
                edt.setText("Buscar");
                edt.setWidth(250);

                edt.addTextChangedListener(tw);

                resai.reset();
                edt.clearAnimation();
                edt.setAnimation(resai);

                mi.setEnabled(false);
            }
            return true;
        }else{
            return super.onOptionsItemSelected(item);
        }
    }

    private void textagorias(){
        novo = (TextView) findViewById(R.id.novo);
        dive = (TextView) findViewById(R.id.dive);
        tren = (TextView) findViewById(R.id.tren);
        arto = (TextView) findViewById(R.id.arto);
        sayens = (TextView) findViewById(R.id.sayens);
        yey = (TextView) findViewById(R.id.yey);

        claus = (Button) findViewById(R.id.claus);
        claus.setVisibility(View.GONE);

        View.OnClickListener vow=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v==novo){
                    cambiarTa(novo,dive,tren,arto,sayens,yey);
                    loadining();
                }else if(v==dive){
                    cambiarTa(dive,novo,tren,arto,sayens,yey);
                    loadining("entretenimiento");
                }else if(v==tren){
                    cambiarTa(tren,dive,novo,arto,sayens,yey);
                    loadining("tendencias");
                }else if(v==arto){
                    cambiarTa(arto,dive,novo,tren,sayens,yey);
                    loadining("arte");
                }else if(v==sayens){
                    cambiarTa(sayens,arto,dive,novo,tren,yey);
                    loadining("ciencia");
                }else if(v==yey){
                    cambiarTa(yey,arto,dive,novo,tren,sayens);
                    loadining("interfaz");
                }else if(v==claus){
                    puf.reset();
                    edt.clearAnimation();
                    edt.setAnimation(puf);

                    edt.setText("");
                    edt.removeTextChangedListener(tw);
                    edt.setEnabled(false);
                    edt.setWidth(0);

                    puf.reset();
                    claus.clearAnimation();
                    claus.setAnimation(puf);

                    claus.setVisibility(View.GONE);
                    mi.setEnabled(true);
                    getSupportActionBar().setTitle("Revista Interfaz");

                    loadining();
                }else if(v==edt){
                    if(edt.getText().toString().equals("Buscar")){
                        edt.setText("");
                    }
                }
            }
        };

        novo.setOnClickListener(vow);
        dive.setOnClickListener(vow);
        tren.setOnClickListener(vow);
        arto.setOnClickListener(vow);
        sayens.setOnClickListener(vow);
        yey.setOnClickListener(vow);
        claus.setOnClickListener(vow);
        edt.setOnClickListener(vow);
    }

    private void cambiarTa(TextView selc,TextView nyui,TextView nyui2,TextView nyui3,TextView nyui4,TextView nyui5){
        selc.setTextSize(30f);
        nyui.setTextSize(15f);
        nyui2.setTextSize(15f);
        nyui3.setTextSize(15f);
        nyui4.setTextSize(15f);
        nyui5.setTextSize(15f);
    }

    /**         metodo para que al actualizar no c++ en tu data, que lastimosamente ya no voy a usar pero si
     *          lo voy a reciclar
     *
     *      private void getActuali(SQLiteDatabase sqld,String catigory){
                Cursor ur=sqld.rawQuery(catigory,null);

                List<nius> suin= new ArrayList<nius>();

                 if(ur.moveToFirst()){
                     String ti,subt,niuses,dei,donde,aqui;

                     do{
                         ti=ur.getString(ur.getColumnIndex("Titulo"));
                         subt=ur.getString(ur.getColumnIndex("Subtitulo"));
                         niuses=ur.getString(ur.getColumnIndex("Noticia"));
                         dei=ur.getString(ur.getColumnIndex("Fecha"));
                         aqui=ur.getString(ur.getColumnIndex("Categoria"));
                         donde=ur.getString(ur.getColumnIndex("Imagen"));

                         suin.add(new nius(ti,subt,niuses,dei,aqui,donde));
                     } while(ur.moveToNext());
                 }

                 nuev.clear();
                 nuev=suin;

                 ArrayAdapter<nius> ne=new custom();
                 aja.setAdapter(ne);
            }
    **/

    private void loadining(){
        RequestQueue requ= Volley.newRequestQueue(this);

        JsonObjectRequest jobs=new JsonObjectRequest(Request.Method.GET,
                "https://revistainterfazgq.000webhostapp.com/scripts/guet.php",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jara=response.getJSONArray("news");
                            String taitai,subtaitai,deit,cateo,noti,ima;
                            List<nius> suin= new ArrayList<nius>();

                            for(int i=0;i<jara.length();i++){
                                JSONObject gor=jara.getJSONObject(i);

                                taitai=gor.getString("Titulo");
                                subtaitai=gor.getString("Subtitulo");
                                deit=gor.getString("Fecha");
                                cateo=gor.getString("Categoria");
                                noti=gor.getString("Noticia");
                                ima=gor.getString("Imagen");

                                suin.add(new nius(taitai,subtaitai,deit,cateo,noti,ima));
                            }

                            nuev.clear();
                            nuev=suin;

                            ArrayAdapter<nius> an=new custom();
                            aja.setAdapter(an);
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

        requ.add(jobs);
    }

    private void loadining(final String condishon){
        RequestQueue requ= Volley.newRequestQueue(this);

        JsonObjectRequest jobs=new JsonObjectRequest(Request.Method.GET,
                "https://revistainterfazgq.000webhostapp.com/scripts/guet.php",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jara=response.getJSONArray("news");
                            String taitai,subtaitai,deit,cateo,noti,ima;
                            List<nius> suin= new ArrayList<nius>();

                            for(int i=0;i<jara.length();i++){
                                JSONObject gor=jara.getJSONObject(i);

                                taitai=gor.getString("Titulo");
                                subtaitai=gor.getString("Subtitulo");
                                deit=gor.getString("Fecha");
                                cateo=gor.getString("Categoria");
                                noti=gor.getString("Noticia");
                                ima=gor.getString("Imagen");

                                if(cateo.equalsIgnoreCase(condishon)){
                                    suin.add(new nius(taitai,subtaitai,deit,cateo,noti,ima));
                                }
                            }

                            nuev.clear();
                            nuev=suin;

                            ArrayAdapter<nius> an=new custom();
                            aja.setAdapter(an);
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

        requ.add(jobs);
    }

    private class custom extends ArrayAdapter<nius>{
        public custom() {
            super(MainActivity.this,R.layout.base_mini,nuev);
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView==null){
                convertView=getLayoutInflater().inflate(R.layout.base_mini,parent,false);
            }

            nius ni=nuev.get(position);

            ImageView awebo = (ImageView) convertView.findViewById(R.id.qli);
            TextView t = (TextView) convertView.findViewById(R.id.titu);
            TextView t2 = (TextView) convertView.findViewById(R.id.subtitu);

            Picasso.with(MainActivity.this).load(ni.getPata()).into(awebo);
            t.setText(ni.getTit());
            t2.setText(ni.getmimid());

            return convertView;
        }
    }
}
