package org.holmes.interview;

import java.util.concurrent.Callable;

/**
 * Remove class implements Runnable class
 * only have one method which is used to remove
 * pair of key-value from jsonbuffer.
 * 
 * @author holmes
 *
 */
public class Remove implements Callable<String>{
	private String s;
	public Remove(String keys){
		this.s = keys;
	}
	public String call(){
		String msg = "";
		try{
		    String[] keylist = s.split(" ");
		    if(keylist.length == 0)
			    msg = "no key enter!\n";
		    for(int i=0;i<keylist.length;i++){
			//System.out.print(keylist[i]);
			    if(MainThread.jsonbuffer.has(keylist[i])){
				    MainThread.jsonbuffer.remove(keylist[i]);
				    msg += "remove "+keylist[i]+" success!\n";
			    }else{
				    msg += keylist[i]+" is not found!\n";
			    }
		    }
		}catch(Exception e){
			msg = "no key enter!\n";
		}
		return msg;
	}
}
