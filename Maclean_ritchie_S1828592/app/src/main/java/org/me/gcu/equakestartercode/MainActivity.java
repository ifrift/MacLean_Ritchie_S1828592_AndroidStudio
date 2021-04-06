//MacLean_Ritchie_S1828592

package org.me.gcu.equakestartercode;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.me.gcu.equakestartercode.R;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.widget.Spinner;
import android.widget.Toast;

import static org.me.gcu.equakestartercode.ArrayStore.storeGeoLat;

public class MainActivity extends AppCompatActivity implements OnClickListener, AdapterView.OnItemSelectedListener {
    private Context mContext;
    private int id;
    private List <String>items ;
    private int i = 0;
    private ArrayList<String> list;
    private ArrayAdapter<String> arrayAdapter;
    private ListView listView;
    private Button startButton;
    private String result = "";
    private String url1="";
    private String urlSource="http://quakes.bgs.ac.uk/feeds/MhSeismology.xml";
    private int num = 1;
    private EditText startDate;
    private EditText endDate;
    private TextView minMaxTextView;
    private Button searchDate;
    private Button mapButton;
    private String[] days = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};
    private String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    private Spinner spinnerStartDate;
    private Spinner spinnerStartDateMonth;
    private Spinner spinnerEndDate;
    private Spinner spinnerEndDateMonth;
    private String whichButton;
    private String maxMagStr;
    private float maxMag = -100f;
    private String maxDepthStr;
    private float maxDepth = -100f;
    private String minDepthStr;
    private float minDepth = 100f;
    private String maxLatStr;
    private float maxLat = -100f;
    private String minLatStr;
    private float minLat = 100f;
    private String minLongStr;
    private float minLong = 100f;
    private String maxLongStr;
    private float maxLong = -100f;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.myList);
        list = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
        searchDate = (Button)findViewById(R.id.searchDateButton);
        startButton = (Button)findViewById(R.id.startButton);
        mapButton = (Button)findViewById(R.id.mapButton);
        minMaxTextView = (TextView)findViewById(R.id.minMax);
        mapButton.setOnClickListener(this);
        startButton.setOnClickListener(this);
        searchDate.setOnClickListener(this);


        spinnerStartDate = (Spinner)findViewById(R.id.spinnerStartDate);
        spinnerStartDateMonth = (Spinner)findViewById(R.id.spinnerStartDateMonth);
        spinnerEndDate = (Spinner)findViewById(R.id.spinnerEndDate);
        spinnerEndDateMonth = (Spinner)findViewById(R.id.spinnerEndDateMonth);
        spinnerStartDate.setOnItemSelectedListener(this);
        spinnerStartDateMonth.setOnItemSelectedListener(this);
        spinnerEndDate.setOnItemSelectedListener(this);
        spinnerEndDateMonth.setOnItemSelectedListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (whichButton == "normal") {
                    minMaxTextView.setText(ArrayStore.storeDescription.get(i));
                }
            }
        });

        ArrayAdapter dayAA = new ArrayAdapter(this,android.R.layout.simple_spinner_item, days);
        ArrayAdapter monthAA = new ArrayAdapter(this,android.R.layout.simple_spinner_item, months);
        dayAA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthAA.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerStartDate.setAdapter(dayAA);
        spinnerStartDateMonth.setAdapter(monthAA);
        spinnerEndDate.setAdapter(dayAA);
        spinnerEndDateMonth.setAdapter(monthAA);
    }

    public void onClick(View aview)
    {

        if (aview == startButton)
        {
            clearArray();
            minMaxTextView.setText("");
            whichButton = "normal";
            startProgress();
        }

        else if (aview == searchDate)
        {
            clearArray();
            minMaxTextView.setText("");
            whichButton = "date";
            maxMag = 0f;
            startProgress();
        }


        if (aview == mapButton)
        {
            Intent mapIntent = new Intent(this, MyMapClass.class);
            startActivity(mapIntent);
        }
    }

    public void clearArray()
    {
        ArrayStore.storeTitles.clear();
        ArrayStore.storeDescription.clear();
        ArrayStore.storeCategory.clear();
        ArrayStore.storeLink.clear();
        ArrayStore.storePubDate.clear();
        ArrayStore.storeGeoLat.clear();
        ArrayStore.storeGeoLong.clear();
        ArrayStore.storeMag.clear();
    }


    public void startProgress()
    {

        new Thread(new Task(urlSource)).start();
    } //

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {



    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    private class Task implements Runnable
    {
        private String url;

        private String theStartDay = spinnerStartDate.getSelectedItem().toString();
        private String theEndDay = spinnerEndDate.getSelectedItem().toString();
        private String theStartMonth = spinnerStartDateMonth.getSelectedItem().toString();
        private String theEndMonth = spinnerEndDateMonth.getSelectedItem().toString();

        int startDay = Integer.parseInt(theStartDay);
        int startMonth = Integer.parseInt(theStartMonth);
        int endDay = Integer.parseInt(theEndDay);
        int endMonth = Integer.parseInt(theEndMonth);

        public Task(String aurl)
        {
            url = aurl;
        }
        @Override
        public void run()
        {

            URL aurl;
            URLConnection yc;
            BufferedReader in = null;
            String inputLine = "";




            try
            {

                aurl = new URL(url);
                yc = aurl.openConnection();
                in = new BufferedReader(new InputStreamReader(yc.getInputStream()));


                while ((inputLine = in.readLine()) != null)
                {
                    result = result + inputLine;

                }
                in.close();
            }
            catch (IOException ae)
            {

            }

            MainActivity.this.runOnUiThread(new Runnable()
            {
                public void run() {
                    parseData(result);
                }
            });
        }

        ArrayAdapter<String>arrayAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.for_list_array, ArrayStore.displayText)
        {
            @NonNull
            @Override
            public View getView(int position, @Nullable View changeView, @NonNull ViewGroup parent)
            {
                View myView = super.getView(position, changeView, parent);
                for(int i = 0; i < ArrayStore.displayText.size(); i++)
                {
                    if(ArrayStore.storeMag.get(position) >= 0 && ArrayStore.storeMag.get(position) < 1)
                    {
                        myView.setBackgroundColor(getResources().getColor(R.color.lime));
                    }
                    if(ArrayStore.storeMag.get(position) >= 1 && ArrayStore.storeMag.get(position) < 2)
                    {
                        myView.setBackgroundColor(getResources().getColor(R.color.yellow));
                    }
                    if(ArrayStore.storeMag.get(position) >= 2 && ArrayStore.storeMag.get(position) < 3)
                    {
                        myView.setBackgroundColor(getResources().getColor(R.color.orange));
                    }
                    if(ArrayStore.storeMag.get(position) >= 3 && ArrayStore.storeMag.get(position) < 4)
                    {
                        myView.setBackgroundColor(getResources().getColor(R.color.red));
                    }
                }
                return myView;
            }
        };


        private void parseData(String dataToParse)
        {
            ItemClass item = new ItemClass();
            list.clear();
            ArrayStore.storeMag.clear();
            ArrayStore.displayText.clear();
            try
            {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( new StringReader( dataToParse ) );
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT)
                {

                    if(eventType == XmlPullParser.START_TAG)
                    {
                        if(xpp.getName().equalsIgnoreCase("item"))
                        {
                            item = new ItemClass();
                        }

                        if (xpp.getName().equalsIgnoreCase("title"))
                        {
                            String text = xpp.nextText();
                            item.setTitle(text);
                        }

                        else if (xpp.getName().equalsIgnoreCase("description"))
                        {
                            String text = xpp.nextText();
                            item.setDescription(text);
                        }

                        else if (xpp.getName().equalsIgnoreCase("link"))
                        {
                            String text = xpp.nextText();
                            item.setLink(text);
                        }

                        else if (xpp.getName().equalsIgnoreCase("pubDate"))
                        {
                            String text = xpp.nextText();
                            item.setPubDate(text);
                        }

                        else if(xpp.getName().equalsIgnoreCase("category"))
                        {
                            String text = xpp.nextText();
                            item.setCategory(text);
                        }

                        else if(xpp.getName().equalsIgnoreCase("lat"))
                        {
                            String text = xpp.nextText();
                            item.setGeoLat(text);
                        }

                        else if(xpp.getName().equalsIgnoreCase("long"))
                        {
                            String text = xpp.nextText();
                            item.setGeoLong(text);
                        }
                    }

                    else if(eventType == XmlPullParser.END_TAG)
                    {

                        if (xpp.getName().equalsIgnoreCase("item")) {

                            if (whichButton == "normal") {
                                list.add(item.Dis());
                                listView.setAdapter(arrayAdapter);
                                arrayAdapter.notifyDataSetChanged();
                            }

                            else if (whichButton == "date") {

                                int month = item.toIntMonth();
                                int day = item.toIntDay();
                                float currentDepth = item.Depth();
                                float currentMag = item.Mag();
                                float currentLat = item.Lat();
                                float currentLong = item.Long();

                                if (month > startMonth && month < endMonth) {
                                        list.add(item.Dis());
                                        listView.setAdapter(arrayAdapter);
                                        arrayAdapter.notifyDataSetChanged();
                                        minMax(currentDepth, currentMag, currentLat, currentLong, item.location());
                                    minMaxTextView.setText("Most Northernly " + maxLatStr + "\n" + "Most Southernly " + minLatStr + "\n" + "Most Easternly " + maxLongStr + "\n" + "Most Westernly " + minLongStr + "\n" + "Deepest " + maxDepthStr + "\n" + "Shallowest " + minDepthStr + "\n" + "Greatest Magnitude " + maxMagStr);

                                }
                                else if (month == startMonth && day >= startDay && month < endMonth) {
                                        list.add(item.Dis());
                                        listView.setAdapter(arrayAdapter);
                                        arrayAdapter.notifyDataSetChanged();
                                        minMax(currentDepth, currentMag, currentLat, currentLong, item.location());
                                    minMaxTextView.setText("Most Northernly " + maxLatStr + "\n" + "Most Southernly " + minLatStr + "\n" + "Most Easternly " + maxLongStr + "\n" + "Most Westernly " + minLongStr + "\n" + "Deepest " + maxDepthStr + "\n" + "Shallowest " + minDepthStr + "\n" + "Greatest Magnitude " + maxMagStr);

                                }
                                else if (month > startMonth && month <= endMonth && day <= endDay) {
                                        list.add(item.Dis());
                                        listView.setAdapter(arrayAdapter);
                                        arrayAdapter.notifyDataSetChanged();
                                        minMax(currentDepth, currentMag, currentLat, currentLong, item.location());
                                    minMaxTextView.setText("Most Northernly " + maxLatStr + "\n" + "Most Southernly " + minLatStr + "\n" + "Most Easternly " + maxLongStr + "\n" + "Most Westernly " + minLongStr + "\n" + "Deepest " + maxDepthStr + "\n" + "Shallowest " + minDepthStr + "\n" + "Greatest Magnitude " + maxMagStr);

                                }
                                else if (month == startMonth && month == endMonth && day <= endDay && day >= startDay) {
                                        list.add(item.Dis());
                                        listView.setAdapter(arrayAdapter);
                                        arrayAdapter.notifyDataSetChanged();
                                        minMax(currentDepth, currentMag, currentLat, currentLong, item.location());
                                        minMaxTextView.setText("Most Northernly " + maxLatStr + "\n" + "Most Southernly " + minLatStr + "\n" + "Most Easternly " + maxLongStr + "\n" + "Most Westernly " + minLongStr + "\n" + "Deepest " + maxDepthStr + "\n" + "Shallowest " + minDepthStr + "\n" + "Greatest Magnitude " + maxMagStr);

                                }
                                else {
                                    list.clear();
                                    listView.setAdapter(arrayAdapter);
                                    arrayAdapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }

                    eventType = xpp.next();

                }

            }
            catch (XmlPullParserException ae1)
            {

            }
            catch (IOException ae1)
            {

            }



        }

        public void minMax(float currentDepth, float currentMag, float currentLat, float currentLong, String location)
        {
            if (currentDepth > maxDepth)
            {
                maxDepth = currentDepth;
                maxDepthStr = location;
            }

            if (currentDepth < minDepth)
            {
                minDepth = currentDepth;
                minDepthStr = location;
            }

            if (currentMag > maxMag)
            {
                maxMag = currentMag;
                maxMagStr = location;
            }

            if (currentLat > maxLat)
            {
                maxLat = currentLat;
                maxLatStr = location;
            }

            if (currentLat < minLat)
            {
                minLat = currentLat;
                minLatStr = location;
            }

            if (currentLong > maxLong)
            {
                maxLong = currentLong;
                maxLongStr = location;
            }

            if (currentLong < minLong)
            {
                minLong = currentLong;
                minLongStr = location;
            }
        }


    }

}