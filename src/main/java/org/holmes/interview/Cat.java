package org.holmes.interview;

import java.util.concurrent.Callable;
/**
 * Cat class implements Runnable class.
 * need one param to construct, the item name
 * in addressbook.
 * function: show the detail of the item.
 * 
 * @author holmes
 *
 */
public class Cat implements Callable<String>{
	private String itemname;
	public Cat(String itemname){
		this.itemname = itemname;
	}
	/**
	 * when thread start, run() will get to work.
	 * simply show the details of item
	 */
	public String call(){
		String msg;
		if(MainThread.jsonbuffer.has(itemname)){
			String s = MainThread.jsonbuffer.optString(itemname);
			msg = s+"\n";
		}else{
			msg = "No such item exist!\n";
		}
		return msg;
	}

}
