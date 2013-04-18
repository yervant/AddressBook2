package org.holmes.interview;

import java.io.*;
import java.util.concurrent.*;

import org.json.*;

public class MainThread {
	
	public static JSONObject json = new JSONObject();      //holding the complete json
	public static JSONObject jsonbuffer = new JSONObject();//holding the present directory
	
	public static void main(String[] args){
			//initialize:
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
			String path = null;
			while(true){
			    try{
			    	System.out.print("please enter the path of json file\n");
			        path = stdin.readLine();
			        String jsonString;
			        jsonString = readFile(path);
			        json = new JSONObject(jsonString);
			        jsonbuffer = new JSONObject(jsonString);
			        break;
			    }catch(JSONException je){
			    	System.out.print("wrong file because it isn't a json file!\n");
			    }catch(IOException ie){
			    	System.out.print("wrong path dear!\n");
			    }
			}
			ExecutorService exec = Executors.newCachedThreadPool();
		try{
			//listening on system in:
			while(true){
				String sin = stdin.readLine();
				String[] slist = sin.split(" ");
				if(sin.equals("ls")){
				    exec.execute(new Ls());
				}else if(slist[0].equals("cd")&& slist.length==2){
					String temp = exec.submit(new Cd(slist[1])).get();
					if(!(temp==null))
					    System.out.print(temp);
				}else if(slist[0].equals("cat")&& slist.length==2){
					System.out.print(exec.submit(new Cat(slist[1])).get());
				}else if(sin.equals("add")){
					System.out.print("key: ");
					String addkey = stdin.readLine();
					System.out.print("values: ");
					String addval = stdin.readLine();
					System.out.print(exec.submit(new Add(addkey,addval)).get());
				}else if(sin.equals("remove")){
					System.out.print("enter the key(s): ");
					String keys = stdin.readLine();
					System.out.print(exec.submit(new Remove(keys)).get());
				}else if(sin.equals("!quit")){
					exec.shutdownNow();
					json.put((String)json.keys().next(),jsonbuffer);
					writeFile(path);
					return;
				}else{
					showhelp();
				}
			}
		} catch(IOException e){
			System.out.println(e.getMessage());
		} catch(JSONException je){
			System.out.println(je.getMessage());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			exec.shutdownNow();
		}
	}
	
	private static String readFile(String fileName) throws IOException{
		BufferedReader in = new BufferedReader(new FileReader(fileName));
		String s;
		StringBuilder sb = new StringBuilder();
		while((s = in.readLine()) != null){
			sb.append(s);
		}
		in.close();
		return sb.toString();
	}
	
	private static void writeFile(String fileName) throws IOException, JSONException{
		BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
		json.write(out);
		out.close();
	}
	
	private static void showhelp(){
		String helpmsg = new String();
		helpmsg = "Welcome to use this program!\n"+
		"I will show you all the commands and some simple usage\n"+
		"ls\n"+
		"ls command will list all elements in the curent directory\n" +
		"cd\n" +
		"cd command need one parameter then it will open this directory\n" +
		"cat\n" +
		"cat command need one parameter then it will open and show the value of this item\n" +
		"add\n" +
		"add command will add new item into entries, key should not be duplicate\n" +
		"remove\n" +
		"remove command will remove one or more item(s) from entries, key should be existed!\n" +
		"!quit\n" +
		"!quit command is used to quit from this application.\n" +
		"!help\n" +
		"!help command will show you this help doc.\n";
		System.out.print(helpmsg);		
	}
}
