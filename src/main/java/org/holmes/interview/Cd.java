package org.holmes.interview;

import java.util.concurrent.Callable;

import org.json.JSONException;
import org.json.JSONObject;
/**
 * Cd class implements Runnable class
 * constructor need one param which means the key 
 * you want to open.
 * 
 * @author holmes
 *
 */
public class Cd implements Callable<String>{
	private String directory;
	public Cd(String directory){
		this.directory = directory;
	}
	/**
	 *when the thread start, run() will start working;
	 *It will change the jsonbuffer. 
	 */
	public String call(){
		String errormsg = null;
		try{
		    JSONObject jo = MainThread.jsonbuffer.getJSONObject(directory);
		    try{
		    	jo.getJSONObject((String)jo.keys().next());
		    	MainThread.jsonbuffer = jo;
		    	return errormsg;
		    }catch(JSONException e){
		    	return errormsg = "already at bottom!\n";
		    }
		}catch(JSONException je){
			return errormsg = "No such key under this directory!\n";
		}
	}
}
