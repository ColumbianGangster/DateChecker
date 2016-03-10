package com.datechecker.datechecker.utils;


import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Bertrand Brompton.
 */
public class MyValidator {
    private Context context;
    public MyValidator(Context c){
        this.context = c;
    }

    public Boolean isValid(String s){
        Boolean result = false;
        if(s.contains("-")){
            StringTokenizer st = new StringTokenizer(s, "-");
            result = parse(st,s);
        } else if (s.contains("/")){
            StringTokenizer st = new StringTokenizer(s, "/");
            result = parse(st,s);
        } else { // do nothing. any other token fails
        }
        return result;
    }
    private Boolean parse(StringTokenizer st, String s){
        ArrayList<String> al = new ArrayList<>();
        while(st.hasMoreElements()){
            al.add(st.nextToken());
        }
        return checkParsed(al);
    }
    private Boolean checkParsed(ArrayList<String> al){
        Boolean result = false;
        if(al.size() == 3){
            try{
                int first = Integer.parseInt(al.get(0));
                int second = Integer.parseInt(al.get(1));
                int third = Integer.parseInt(al.get(2));
                if(Locale.getDefault() == Locale.US){ // format is MM/dd/yyyy
                    if(checkIfMonth(first)){
                        if(checkIfDay(second, first)){
                            if(checkIfYear(third)){
                                result = true;
                            }
                        }
                    }
                } else { // format is dd/MM/yyyy
                    if(checkIfMonth(second)){
                        if(checkIfDay(first, second)){
                            if(checkIfYear(third)){
                                result = true;
                            }
                        }
                    }
                }
            } catch(NumberFormatException e){
                Log.i("Test", "checkParsed: "+e);
            }
        }
        return result;
    }
    private Boolean checkIfYear(int alleged_year){
        if(alleged_year < 1800 || alleged_year > 2100){
            return false;
        } else {
            return true;
        }
    }
    private Boolean checkIfMonth(int alleged_month){
        if(alleged_month < 1 || alleged_month > 12){ return false; } else { return true; }
    }
    private Boolean checkIfDay(int alleged_day, int month){
        // bad months must be checked by CheckIfMonth, used in wrapper function
        // January (1), February (2), March (3), April (4), May (5), June (6), July (7), August (8),
        // September (9), October (10), November (11), December (12)
        int days_of_month = 31;
        if(month == 2){
            days_of_month = 28;
        }else if(month == 4 || month == 6 || month == 9 || month == 11){ // so, April, June, Sept, November
            days_of_month = 30;
        } // any other month defaults to 31 days
        if(alleged_day < 1 || alleged_day > days_of_month){ return false; } else { return true; }
    }
}
