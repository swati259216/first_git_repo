package com.zemoso.rideapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by zemoso on 13/1/16.
 */
public class MyCustomBaseAdapter extends BaseAdapter {


    public static ArrayList<Display> displayArrayList;
    private LayoutInflater mInflater;

    public MyCustomBaseAdapter(Context context,ArrayList<Display>displays){
        displayArrayList = displays;
        mInflater = LayoutInflater.from(context);
    }
    public int getCount(){
        return displayArrayList.size();

    }

    public Object getItem(int position){
        return displayArrayList.get(position);
    }

    public long getItemId(int postion){
        return  postion;
    }
    public View getView(int position, View convertView,ViewGroup parent){
        ViewHolder holder;
        if (convertView == null){
            convertView = mInflater.inflate(R.layout.custon_row_view,null);
            holder = new ViewHolder();
            holder.txtUsername = (TextView) convertView.findViewById(R.id.Username);
            holder.txtMobilenumber = (TextView) convertView.findViewById(R.id.Mobilenumber);
            holder.txtStartingplace = (TextView) convertView.findViewById(R.id.Startingplace);



//            holder.txtRidedistance = (TextView) convertView.findViewById(R.id.Ridedistance);
//            holder.txtTimetaken = (TextView) convertView.findViewById(R.id.Timetaken);
//            holder.txtWaitingtime = (TextView)convertView.findViewById(R.id.Waitingtime);
//            holder.txtCabtype = (TextView)convertView.findViewById(R.id.Cabtype);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String username = displayArrayList.get(position).getUsername();
        String mobilenumber = displayArrayList.get(position).getMobilenumber();
        String startingplace =displayArrayList.get(position).getStartingplace();
        String ridedistance = displayArrayList.get(position).getRidedistance();
        String timetaken  = displayArrayList.get(position).getTimetaken();
        String cabtype = displayArrayList.get(position).getCabtype();
        Timestamp bookingTime = displayArrayList.get(position).getBoookingTime();
         // GET CURRENT TIME BY USING TIME STAMP
        long currentTimeMillis = System.currentTimeMillis();
        long timeDiffBooking = currentTimeMillis - bookingTime.getTime();
        //FROM NOW
        long hoursDifference = (timeDiffBooking/1000)/3600;
        if(hoursDifference>24){
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(bookingTime.getTime());
            int hours = 0;
            int minutes = 0;
            String ampm = "";

            minutes = calendar.get(Calendar.MINUTE);
            if (calendar.get(Calendar.AM_PM)==Calendar.PM) {
                hours = calendar.get(Calendar.HOUR_OF_DAY) - 12;
                ampm = "PM";
            }
            else {
                hours = calendar.get(Calendar.HOUR_OF_DAY);
                ampm = "AM";
            }
            int month = calendar.get(Calendar.MONTH) +1;
            int date = calendar.get(Calendar.DATE);

            String monthname = " ";
            if (month == 1){
                monthname = "January";
            }
            else if (month == 2){
                monthname = "February";
            }
            else if (month == 3)
            {
                monthname = "March";
            }
            else if (month == 4){
                monthname = "April";
            }
            else if (month == 5){

                monthname = "May";
            }
            else if (month == 6){
                monthname = "June";
            }
            else if (month == 7){
                monthname = "July";
            }
            else if (month == 8){
                monthname = "August";
            }
            else if (month == 9){
                monthname = "September";
            }
            else if (month == 10){
                monthname = "October";
            }
            else if (month == 11){
                monthname = "November";
            }
            else if (month == 12)
            {
                monthname = "December";
            }

            String properdate = hours + ":" + minutes + " " + ampm + " " + date + " -" + monthname;
            holder.txtMobilenumber.setText(properdate+ " with cab type " + cabtype);
        }
        else {

            holder.txtMobilenumber.setText(hoursDifference + " hours from now "+ " with cab type " + cabtype);
            if(hoursDifference<1){
                long minutes = (timeDiffBooking/1000)/60;
                holder.txtMobilenumber.setText(minutes + " minutes from now "+ " with cab type " + cabtype);
                if(minutes<1){
                    long seconds = timeDiffBooking/1000;
                    holder.txtMobilenumber.setText(seconds + " seconds from now "+ " with cab type " + cabtype);
                }


            }

        }

        //holder.txtUsername.setText( bookingTime + " from " + startingplace + " with cab type "  + cabtype);
        //holder.txtMobilenumber.setText("Time Taken : " + timetaken +" and ride distance " + ridedistance);
        holder.txtUsername.setText(startingplace);

        holder.txtStartingplace.setText("Time Taken " + timetaken + " and Ride distance " + ridedistance);



        //     holder.txtStartingplace.setText(displayArrayList.get(position).getStartingplace());
//        holder.txtRidedistance.setText(displayArrayList.get(position).getRidedistance());
//        holder.txtTimetaken.setText(displayArrayList.get(position).getTimetaken());
//        holder.txtWaitingtime.setText(displayArrayList.get(position).getWaitingtime());
//        holder.txtCabtype.setText(displayArrayList.get(position).getCabtype());


        return convertView;
    }
    
    static class ViewHolder {
        TextView txtUsername;
        TextView txtMobilenumber;
        TextView txtStartingplace;
        TextView txtRidedistance;
        TextView txtTimetaken;
        TextView txtWaitingtime;
        TextView txtCabtype;


    }
}
