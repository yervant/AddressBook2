package org.holmes.interview;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(value=Parameterized.class)
public class CdTest {
	private String key;
	private String value;
	private String expected;
	private static int inittime = 0;
	private ExecutorService exec = Executors.newCachedThreadPool();
	@Parameters
	public static Collection<String[]> getParam(){
		return Arrays.asList(new String[][]{
			{"entries",null,null},
			{"hanmeimei",null,"already at bottom!\n"},
			{"lilei",null,"already at bottom!\n"},
			{"xiaoming",null,"No such key under this directory!\n"},
			{null,null,"No such key under this directory!\n"}//这里第二个参数用不到
		});
	}
	
	public CdTest(String key, String value, String expected){
		this.key = key;
		this.value = value;
		this.expected = expected;
	}
	@Before//do only once
	public void init() throws JSONException{
		if(inittime==0){
		MainThread.jsonbuffer = new JSONObject("{\"entries\":{\"hanmeimei\":{\"address\":" +
				"\"Earth somewhere else\",\"age\":26,\"mobile\":\"13700000001\"}" +
				",\"lilei\":{\"address\":\"Earth somewhere\",\"age\":27,\"mobile\":" +
				"\"13700000000\"}}}");
		inittime++;
		}
	}
	@Test
	public void testCd(){
		String ans = null;
		try {
			ans = exec.submit(new Cd(key)).get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(expected,ans);
	}
	/*@Test
	public void testAdd(){
		
	}
	@Test
	public void testCat(){
		
	}
	@Test
	public void testRemove(){
		
	}*/

}
