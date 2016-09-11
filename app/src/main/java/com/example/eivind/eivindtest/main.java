package com.example.eivind.eivindtest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class main extends AppCompatActivity {
    EditText message, info;
    public ListView listView;
    private TextView dinnerId;
    Spinner days;
    DbHelper dbHelper;
    Button save;

    private Dinner dinner;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<CharSequence> daysAdapter;
    public static ArrayList<String> arrayList, list2;
    private static ArrayList<Integer> dinIds = new ArrayList<Integer>();
    public static String [] prgmNameList={"Let Us C","c++","JAVA","Jsp","Microsoft .Net","Android","PHP","Jquery","JavaScript"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(main.this,settings.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void init() {
        days = (Spinner) findViewById(R.id.days);
        save = (Button) findViewById(R.id.save);
        message = (EditText) findViewById(R.id.editText);
        info = (EditText) findViewById(R.id.etInfo);
        listView = (ListView) findViewById(R.id.listView);
        dinnerId = (TextView) findViewById(R.id.dinnerId);
        arrayList = new ArrayList<String>();
        list2 = new ArrayList<String>();
        String[] c = getResources().getStringArray(R.array.days_array);
        daysAdapter = new ArrayAdapter(this,R.layout.list_black_text,new ArrayList(Arrays.asList(c)));
        daysAdapter.setDropDownViewResource(R.layout.list_black_text);
        days.setAdapter(daysAdapter);
        // Adapter: You need three parameters 'the context, id of the layout (it will be where the data is shown),
        // and the array that contains the data
        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_black_text, arrayList);
        // Here, you set the data in your ListView
        listView.setAdapter(new CustomAdapter(this,arrayList, list2));
        //arrayList.clear();
        //Fyller listview med data
        Dinner din = new Dinner(getApplicationContext());
        List<Dinner> dinners = din.getDinners();
        for(Dinner d : dinners){
            arrayList.add(d.toString());
            list2.add(d.day);
            dinIds.add(Integer.parseInt(d.id));
            daysAdapter.remove(d.day);
        }
        daysAdapter.notifyDataSetChanged();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String din_id = dinnerId.getText().toString();


                dinner = new Dinner(getApplicationContext());
                dinner.id = din_id;
                dinner.dinner = message.getText().toString();
                dinner.info = info.getText().toString();
                dinner.day = String.valueOf(days.getSelectedItem());
                // this line adds the data of your EditText and puts in your array
                if(dinner.dinner.isEmpty()){
                    Toast toast = Toast.makeText(getApplicationContext(),"Du må skrive en matrett!", Toast.LENGTH_LONG);
                    toast.show();
                    toast.setGravity(Gravity.TOP|Gravity.CENTER, 0 ,500);
                    return;
                }
                if(dinner.save()){
                    Toast t = Toast.makeText(getApplicationContext(), "Lagret!",Toast.LENGTH_LONG);
                    t.show();
                    t.setGravity(Gravity.TOP|Gravity.CENTER, 0 ,500);
                    message.setText("");
                    info.setText("");
                }
                else {
                    Toast.makeText(getApplicationContext(), "Feil ved lagring!",Toast.LENGTH_LONG).show();

                }
                arrayList.clear();
                dinIds.clear();
                List<Dinner> dinners = dinner.getDinners();
                for(Dinner d : dinners){
                    arrayList.add(d.toString());
                    dinIds.add(Integer.parseInt(d.id));
                    daysAdapter.remove(d.day);
                }
                daysAdapter.notifyDataSetChanged();
                // next thing you have to do is check if your adapter has changed
                adapter.notifyDataSetChanged();
            }
        });
        /*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View arg1, int pos, long arg3) {
                String value = (String)adapter.getItemAtPosition(pos);

                int id = dinIds.get(pos);

                Dinner din = new Dinner(getApplicationContext());
                din.id = String.valueOf(id);
                din = din.getDinner();
                message.setText(din.dinner);
                info.setText(din.info);
                dinnerId.setText(din.id);
                cleanDays();
                daysAdapter.add(din.day);
                days.setSelection(getDayInt(din.day));
                daysAdapter.notifyDataSetChanged();
                //Toast.makeText(getApplicationContext(), "Id: " + text + " " + din.dinner, Toast.LENGTH_SHORT).show();
            }
        }); */

    }
    private int getDayInt(String day){
        int pos = 0;
        switch (day){
            case "Tirsdag":
                pos = 1;
                break;
            case "Onsdag":
                pos = 2;
                break;
            case "Torsdag":
                pos = 3;
                break;
            case "Fredag":
                pos = 4;
                break;
            case "Lørdag":
                pos = 5;
                break;
            case "Søndag":
                pos = 6;
                break;
        }
        return pos;
    }
    public void clearArr(){
        listView = (ListView) findViewById(R.id.listView);

    }

    private void cleanDays(){
        Dinner din = new Dinner(getApplicationContext());
        List<Dinner> dinners = din.getDinners();
        for(Dinner d : dinners){
            daysAdapter.remove(d.day);
        }
        daysAdapter.notifyDataSetChanged();
    }


}
