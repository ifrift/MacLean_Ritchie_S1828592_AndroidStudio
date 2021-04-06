//MacLean_Ritchie_S1828592
package org.me.gcu.equakestartercode;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import static org.me.gcu.equakestartercode.ArrayStore.displayText;
import static org.me.gcu.equakestartercode.ArrayStore.storeTitles;
import static org.me.gcu.equakestartercode.ArrayStore.storeDescription;
import static org.me.gcu.equakestartercode.ArrayStore.storeLink;
import static org.me.gcu.equakestartercode.ArrayStore.storePubDate;
import static org.me.gcu.equakestartercode.ArrayStore.storeCategory;
import static org.me.gcu.equakestartercode.ArrayStore.storeGeoLat;
import static org.me.gcu.equakestartercode.ArrayStore.storeGeoLong;
import static org.me.gcu.equakestartercode.ArrayStore.storeMag;

import static java.lang.Float.parseFloat;

public class ItemClass {
    private String title;
    private String description;
    private String link;
    private String pubDate;
    private String category;
    private String geoLat;
    private String geoLong;
    public int monthNo;

    public ItemClass() {
        title = "";
        description = "";
        link = "";
        pubDate = "";
        category = "";
        geoLat = "";
        geoLong = "";
    }

    public ItemClass(String aTitle, String aDescription, String aLink, String aPubDate, String aCategory, String aGeoLat, String aGeoLong) {
        title = aTitle;
        description = aDescription;
        link = aLink;
        pubDate = aPubDate;
        category = aCategory;
        geoLat = aGeoLat;
        geoLong = aGeoLong;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String aTitle) {
        title = aTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String aDescription) {
        description = aDescription;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String aLink) {
        link = aLink;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String aPubDate) {
        pubDate = aPubDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String aCategory) {
        category = aCategory;
    }

    public String getGeoLat() {
        return geoLat;
    }

    public void setGeoLat(String aGeoLat) {
        geoLat = aGeoLat;
    }

    public String getGeoLong() {
        return geoLong;
    }

    public void setGeoLong(String aGeoLong) {
        geoLong = aGeoLong;
    }

    public String toString() {
        String temp;

        temp = title + " " + description + " " + link + " " + pubDate + " " + category + " " + geoLat + " " + geoLong;

        return temp;
    }

    public String onlyTitle() {
        String temp;

        temp = title;

        return temp;
    }

    public String location() {
        String temp;
        temp = description;
        String[] values = temp.split(";");
        temp = values[1] ;
        return temp;
    }

    //String Splitting---->https://javarevisited.blogspot.com/2015/12/how-to-split-comma-separated-string-in-java-example.html#axzz6prVImHvZ
    public String Dis() {
        String temp;
        String part1;
        temp = description;
        String tempLat = geoLat;
        String tempLong = geoLong;
        String[] values = temp.split(";");
        String[] mag = values[4].split(":");
        String[] depth = values[3].split(" ");
        float magTemp = parseFloat(mag[1]);

        part1 = values[1] + "\n" + values[4] /*+ "\n" + geoLat + "\n" + geoLong + "\n" + depth[2]*/;

       storeTitles.add(title);
       storeDescription.add(description);
       storeLink.add(link);
       storePubDate.add(pubDate);
       storeCategory.add(category);
       storeGeoLat.add(geoLat);
       storeGeoLong.add(geoLong);
       storeMag.add(magTemp);
       displayText.add(part1);

        return part1;
    }

    public float Mag()
    {
        String temp;
        temp = description;
        String[] values = temp.split(";");
        String[] mag = values[4].split(":");
        float magTemp = parseFloat(mag[1]);
        return magTemp;
    }

    public float Depth()
    {
        String temp;
        float tempFloat;
        temp = description;
        String[] values = temp.split(";");
        String[] depth = values[3].split(" ");
        tempFloat = parseFloat(depth[2]);
        return tempFloat;
    }

    public float Lat()
    {
        String temp;
        float tempFloat;
        temp = geoLat;
        tempFloat = parseFloat(temp);
        return tempFloat;
    }

    public float Long()
    {
        String temp;
        float tempFloat;
        temp = geoLong;
        tempFloat = parseFloat(temp);
        return tempFloat;
    }

    public Float disFloat() {
        String temp;
        String part1;
        temp = description;
        String[] values = temp.split(";");
        String[] mag = values[4].split(":");

        float magTemp = parseFloat(mag[1]);


        part1 = values[1] + "\n" + values[4];
        return magTemp;
    }

    public String[] split() {
        String temp;

        temp = pubDate;

        String[] expected = temp.split(" ");

        return expected;
    }

    public int toIntDay() {
        int tempDay;
        String[] expected = split();
        tempDay = Integer.parseInt(expected[1]);
        return tempDay;
    }

    public int toIntMonth() {
        String tempDate;
        String[] expected = split();
        tempDate = expected[2];

        switch (tempDate) {
            case "Jan":
                monthNo = 1;
                break;
            case "Feb":
                monthNo = 2;
                break;
            case "Mar":
                monthNo = 3;
                break;
            case "Apr":
                monthNo = 4;
                break;
            case "May":
                monthNo = 5;
                break;
            case "Jun":
                monthNo = 6;
                break;
            case "Jul":
                monthNo = 7;
                break;
            case "Aug":
                monthNo = 8;
                break;
            case "Sep":
                monthNo = 9;
                break;
            case "Oct":
                monthNo = 10;
                break;
            case "Nov":
                monthNo = 11;
                break;
            case "Dec":
                monthNo = 12;
                break;
        }
        return monthNo;
    }

    public String backString() {
        int temp = toIntMonth();
        String backStr;
        backStr = Integer.toString(temp);
        return backStr;
    }

    public String working() {
        Date date1 = null;
        Date date2 = null;
        String working = "Test";
        String myDate = pubDate;


        try {
            DateFormat temp = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss", Locale.ENGLISH);
            date1 = temp.parse(myDate);
            date2 = temp.parse("Wed, 17 Mar 2021 23:56:35");

            if (date1.after(date2)) {
                working = "after";
            }
            if (date1.before(date2)) {
                working = "before";
            }
            if (date1.equals(date2)) {
                working = "equal";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return working;
    }
}

