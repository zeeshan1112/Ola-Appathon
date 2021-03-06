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

public class ListParser extends AsyncTask<Void, Void, String>
{
	ProgressDialog pd;

	Context context;
	public ListParser(Context c)
	{
		context=c;
		pd=new ProgressDialog(context);
	}
	
	protected void onPreExecute()
	{
		super.onPreExecute();
		pd.setTitle("Loading");
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.show();
	}
	@Override
	protected String doInBackground(Void... params) {
		// TODO Auto-generated method stub
		String html_response="";
		
		try
		{
			String link="http://olaapp.site50.net/eventdata.php";
			HttpClient client=new DefaultHttpClient();
			HttpGet httpGet=new HttpGet(link);
			
			Log.e("URL",link);
			HttpResponse response=client.execute(httpGet);
			
			HttpEntity entity=response.getEntity();
			html_response=EntityUtils.toString(entity);
			
			Log.e("Response Obtained",html_response.split("<")[0]);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return html_response;
	}

	protected void onPostExecute(String x)
	{
		super.onPostExecute(x);
		pd.dismiss();
		Intent i=new Intent(context,ListActivity.class);
		i.putExtra("JSON_Data",x.split("<")[0]);
		context.startActivity(i);
	}
}
