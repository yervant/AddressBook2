package org.holmes.interview;

import java.util.concurrent.Callable;

import org.json.JSONException;
/**
 * Add class implements Runnable class.
 * Add class need two param to construct.
 * first param is key in json and
 * second param is value in json
 * this class will add a pair of key-value to jsonbuffer.
 * 
 * @author holmes
 *
 */
public class Add implements Callable<String>{
	private String key;
	private String value;
	public Add(String key,String value){
		this.key = key;
		this.value = value;
	}
	/**method description
	 * implement function void run() in Runnable
	 * make sure the key is non-duplicate and
	 * the value has a correct structure
	 */
	public String call(){
		String msg = null;
		try{
		if(MainThread.jsonbuffer.has(key)){
			msg = "the key has already existed!\n";
		}else{
			if(value.startsWith("{")&&value.endsWith("}")){
			    MainThread.jsonbuffer.put(key, value);
			    msg = "add success!\n";
			}else{
				msg = "please enter a value surrounded by {}\n";
			}
		}
		}catch (Exception e) {
			msg = "add failed!\n";
		}
		return msg;
	}

}
