package com.example.chalokhoj;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class IncrementDriverCountClass extends AsyncTask<Void,Void, String> {

	ProgressDialog pd;
	Context context;
	String driver_number;
	Double lati,longi;
	String event_id;
	String coordinates;
	
	public IncrementDriverCountClass(Context c, Double latitude,Double longitude,String id)
	{
		context=c;
		lati=latitude;
		longi=longitude;
		event_id=id;
		//pd=new ProgressDialog(context);
	}
	
	protected void onPreExecute()
	{
		/*pd.setTitle("Confirming...");
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.show();*/
	}
	
	@Override
	protected String doInBackground(Void... params) {
		// TODO Auto-generated method stub
		String html_response="";
		
		
		try
		{
			
			String link="http://olaapp.site50.net/drivercount.php?id=" + event_id;
			
			HttpClient client=new DefaultHttpClient();
			HttpGet httpGet=new HttpGet(link);
			
			Log.e("URL",link);
			HttpResponse response=client.execute(httpGet);
			
			HttpEntity entity=response.getEntity();
			html_response=EntityUtils.toString(entity);
			
			coordinates=lati.toString()+","+longi.toString();
			Log.e("Response Obtained",html_response.split("<")[0]);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return coordinates;
	}
	
	protected void onPostExecute(String x)
	{
		
		super.onPostExecute(x);
		
		Intent i=new Intent(context,FinalActivity.class);
		i.putExtra("coordinates",x);
		context.startActivity(i);
	}
}
