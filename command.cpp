// command.cpp
#include"command.hxx"

command::command ()
{
  string defaultname("jsonshell");
  pstrops               = new strops();
  this->name            = defaultname;
  string const cmdstr[] = {"ls","cd","cat","add","remove",
			   "!help","!quit","#debug"};
  int    const cmdno    = sizeof(cmdstr)/sizeof(string *);
  for (int i=0; i < cmdno; ++i)
    this->cmdset.insert(cmdstr[i]);
  initial();

}

command::command (string const name)
{
  string const cmdstr[] = {"ls","cd","cat","add","remove",
			   "!help","!quit","#debug"};
  int    const cmdno    = sizeof(cmdstr)/sizeof(string *);
  pstrops               = new strops();
  for (int i=0; i < cmdno; ++i)
    this->cmdset.insert(cmdstr[i]);
  this->name = name;
  initial();
}

command::~command ()
{
  delete   pstrops;
}

void command::initial()
{
  ifstream fin;
  string content;
  string saveName = "default.json";
  fin.open(saveName.c_str(),ios::in);
  if (fin) {
    getline(fin, content);
    this->jlist.initial(content);
  } else {
    this->jlist.toy(); 
  }
  fin.close();
}
/*** 
 *the main interface program
 *  between human and computer
 */
bool command::run ()
{
  string input_string = "";
  string cur_cmd      = "";                        
  while (true)
  {
    pstrops->setcolor("program");
    cout << this->name;
    pstrops->setcolor("default");
    cout << ">";
    getline(cin,input_string);
    if (0 == input_string.size())      
      continue;
    this->cmd_vec = pstrops->cmdstring2word(input_string);
    cur_cmd       = cmd_vec[0];                    // the current cmd
    if ( 0 == cmd_vec.size())          
      continue;                                    // input nothing
    if (!cur_cmd.compare("!quit")) {               // input "!quit"
      cout << "save the memory content into files?" << endl;
      cout << " y     -- save it! (<enter> default)" << endl;
      cout << " n     -- discard it!" << endl;
      cout << "-->";
      getline(cin,input_string);
      if (!input_string.compare("y") 
	  || !input_string.compare("yes")
	  || input_string.size() == 0) {
	cout << "input file name"<<endl;
	cout << "<enter> as default name (default.json):";
	getline(cin, input_string);
	if (input_string.size() == 0)
	  this->save();
	else
	  this->save(input_string);
      }
      else
	cout << "the content was discarded!"<< endl;
      return true;
    }
    if (!cur_cmd.compare("!help")) {             // input "!help"
      if (1 == cmd_vec.size())
	helpmsg.show_help_message();
      else if (2 == cmd_vec.size())
	helpmsg.show_help_message(cmd_vec[1]);
      else {
	helpmsg.too_many_parameters_msg(cmd_vec[1], 1);
	helpmsg.show_help_message();
      }
    } 
    if (this->cmdset.count(cmd_vec[0]) == 1) {   // input correct command
      if(!run_command(cmd_vec)) 
	helpmsg.command_error_run(cur_cmd);
    }else{                                       // input wrong command
      pstrops->setcolor("error");
      cout << "No command ";
      pstrops->setcolor("mark");
      cout << cur_cmd;
      pstrops->setcolor("error");
      cout << " found!" << endl;
      pstrops->setcolor();
      helpmsg.show_help_message();
    }
  } // end of while
  return true;
}

/***
 * excute a specific command 
 * the command content in cmdVec
 */
bool command::run_command (vector<string> cmdVec)
{ 
  string cmd = cmdVec[0];
  if (!cmd.compare("ls"))
    return jlist.ls(jlist.cur);
  if (!cmd.compare("cd") && 1 == cmdVec.size())
    jlist.cur = jlist.cd(jlist.cur);
  if (!cmd.compare("cd") && 2 == cmdVec.size())
    jlist.cur = jlist.cd(jlist.cur, cmdVec[1]);
  if (!cmd.compare("cd") && cmdVec.size() > 2) { 
    helpmsg.too_many_parameters_msg(cmd, 1);
    return false;
  }

  if (!cmd.compare("cat") && 1 == cmdVec.size())
    helpmsg.help_message_cat();
  if (!cmd.compare("cat") && 2 == cmdVec.size())
    return jlist.cat(jlist.cur, cmdVec[1]);
  if (!cmd.compare("cat") && cmdVec.size() > 2) {
    helpmsg.too_many_parameters_msg(cmd, 1);
    return false;
  }

  if (!cmd.compare("add") && 1 == cmdVec.size()) {
    string corStr = this->correct_input_for_add();
    if(!jlist.add(jlist.cur, corStr))
      helpmsg.command_error_msg_add();
    return true;
  }
  if (!cmd.compare("add") && 2 == cmdVec.size()) {
    if (!jlist.add(jlist.cur, cmdVec[1]))
      helpmsg.command_error_msg_add();
    return true;
  }
  if (!cmd.compare("add") && cmdVec.size() > 2) {
    string tmpStr = "";
    for (int i = 1; i < cmdVec.size(); i++)
      tmpStr += cmdVec[i];
    if (!jlist.add(jlist.cur, tmpStr))
      helpmsg.command_error_msg_add();
    return true;
  }

  if (!cmd.compare("remove") && 1 == cmdVec.size()) {
    vector<string> corStrVec = this->correct_input_for_remove();
    for (int i = 0; i < corStrVec.size(); ++i)
      jlist.remove(jlist.cur, corStrVec[i]);
  }
  if (!cmd.compare("remove") && 2 == cmdVec.size())
    return jlist.remove(jlist.cur, cmdVec[1]);
  if (!cmd.compare("remove") &&  cmdVec.size() > 3) {
    for (int i = 1; i < cmdVec.size(); ++i)
      jlist.remove(jlist.cur, cmdVec[i]);
  }
  if (!cmd.compare("#debug") && 2 == cmdVec.size())
    return jlist.debug(jlist.cur, cmdVec[1]);
  return true;
}

/***
 * get the needed remove items' name
 */
vector<string> command::correct_input_for_remove ()
{
  vector<string> outputVec;
  string         input_string;
  cout << "please give the key:";
  getline(cin, input_string);
  outputVec = pstrops->cmdstring2word(input_string);
  return outputVec;
}

/***
 * get a correct format input string
 * return the string which satisfy the jlist.add
 */
string command::correct_input_for_add ()
{
  string         rs;
  string         key;
  string         content;
  vector<string> inputVec;
  string         input_string;
  while(true) {
    cout << "key:";
    getline(cin, input_string);
    inputVec = pstrops->cmdstring2word(input_string);
    if (0 == inputVec.size()) {
      continue;
    } else if (1 == inputVec.size()) {
      key = "\"" + inputVec[0] + "\"";
      break;
    } else {
      pstrops->setcolor("error");
      cout << "format error, key string without space." << endl;
      pstrops->setcolor();
      continue;
    }
  }
  cout << "value:";
  getline(cin, content);
  content = pstrops->delete_content_space(content);
  rs = key + ":" + content;
  return rs;
}

/***
 * save the current memory content.
 */
bool command::save (string saveName)
{
  string content;
  string tmpName = saveName;
  string input_string;
  content = this->jlist.get_allcontent();  
  //cout << "###" << content << "###" << endl;
  ofstream fout;
  ifstream fin;
  fin.open(saveName.c_str(),ios::in);
    
  if (fin) {
    cout << "Warning: the file ";
    pstrops->setcolor("mark");
    cout << saveName;
    pstrops->setcolor();
    cout << " exist! Do you rewrite it?" << endl;
    cout << " y/yes     -- rewrite it! (default)" << endl;
    cout << " n/no      -- save another name it!" << endl;
    cout << "-->";
    getline(cin, input_string);
   
    if (!input_string.compare("n") 
	|| !input_string.compare("no"))
    {
      cout<<"input another file name:";
      getline(cin,input_string);
      if (input_string.size() > 0)
	tmpName = input_string;
    }
    fout.open(tmpName.c_str());    
    fout << content;
  } else {
    fout.open(tmpName.c_str());
    fout << content;
  }
  cout << "the content has been saved in \"";
  pstrops->setcolor("mark");
  cout << saveName;
  pstrops->setcolor();
  cout <<"\"!" << endl;
  fout.close();
  fin.close();
  return true;
}
