package com.example.chalokhoj;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class ListAdapterClass extends BaseAdapter implements OnClickListener {

	private Activity activity;
    private ArrayList<EventClass> data;
    private static LayoutInflater inflater=null;
    public Resources res;
    EventClass tempValues=null;
    int i=0;
	SharedPreferences sp;
	public ListAdapterClass(Activity a,ArrayList<EventClass> e,Resources resLocal)
	{
		activity=a;
		data=e;
		res=resLocal;
		sp=a.getSharedPreferences("CurrentLocationData", Context.MODE_PRIVATE);
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(data.size()<=0)
            return 1;
        return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	 public static class ViewHolder{
         
         public TextView name;
         public TextView distance;
         public TextView time;
     }
	 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi = convertView;
        ViewHolder holder;
         
        if(convertView==null){
             
            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.event_list_item, null);
             
            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.name = (TextView) vi.findViewById(R.id.name);
            holder.distance=(TextView)vi.findViewById(R.id.distance);
            holder.time=(TextView)vi.findViewById(R.id.time);
             
           /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else 
            holder=(ViewHolder)vi.getTag();
         
        if(data.size()<=0)
        {
            holder.name.setText("No Data");
             
        }
        else
        {
            /***** Get each Model object from Arraylist ********/
            tempValues=null;
            tempValues = (EventClass) data.get( position );
             
            /************  Set Model values in Holder elements ***********/

             holder.name.setText(tempValues.getName());
             holder.distance.setText(Double.toString(distanceFrom(Double.parseDouble(sp.getString("latitude","0")),Double.parseDouble(sp.getString("longitude","0")),Double.parseDouble(tempValues.getLatitude()),Double.parseDouble(tempValues.getLongitude())))+ " kms");		//we are supposed to calculate distance here
             holder.time.setText(tempValues.getEndTime());
              
             /******** Set Item Click Listener for LayoutInflater for each row *******/

            vi.setOnClickListener(new OnItemClickListener(position));
        }
        return vi;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	
	private class OnItemClickListener  implements OnClickListener{           
        private int mPosition;
         
        OnItemClickListener(int position){
             mPosition = position;
        }
         
        @Override
        public void onClick(View arg0) {

   
          ListActivity sct = (ListActivity)activity;

         /****  Call  onItemClick Method inside CustomListViewAndroidExample Class ( See Below )****/

            sct.onItemClick(mPosition);
        }               
    }   
	
	public double distanceFrom(double lat1, double lng1, double lat2, double lng2) {
	    double earthRadius = 3958.75;
	    double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(lng2-lng1);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLng/2) * Math.sin(dLng/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    double dist = earthRadius * c;
	    double meterConversion = 1.609;
	    double final1=(dist * meterConversion);
	    return (double)Math.round(final1*100)/100;
	}

}
