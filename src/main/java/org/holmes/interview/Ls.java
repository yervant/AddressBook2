package org.holmes.interview;

import java.util.Iterator;
import org.json.JSONObject;
/**
 * Ls class implements Runnable class
 * simply show all keys under this catalog
 * 
 * @author holmes
 *
 */
public class Ls implements Runnable{
	public Ls(){}
	public void run(){
		Iterator it = MainThread.jsonbuffer.keys();
		while(it.hasNext())
			System.out.print(it.next()+"\n");
	}
}
