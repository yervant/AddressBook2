// helpmessage.cpp
#include"helpmessage.hxx"

helpmessage::helpmessage()
{
  pstrops               = new strops();
}

helpmessage::~helpmessage()
{}

void helpmessage::show_help_message (string cmdValue)
{
  if (!cmdValue.compare("default") ||
      !cmdValue.compare("!help")) {
    cout<<"------------------------------------------------------------"<<endl;
    cout<<"   !help  --- shows this help message!"<<endl;
    cout<<"              example: !help cat"<<endl;
    cout<<"              -- shows the useage of the command cat"<<endl;
    cout<<"------------------------------------------------------------"<<endl;
    cout<<" only follow cmmand can be used:"<<endl;
    cout<<"------------------------------------------------------------"<<endl;
    cout<<"   ls     --- list the all objects in current";
    cout<<" level (directory)!"<<endl;
    cout<<"   cd     --- change into another sub level(directory)"<<endl;
    cout<<"   cat    --- shows the content of the items"<<endl;
    cout<<"   add    --- add an intems into the current level last node"<<endl;
    cout<<"   remove --- remove an items"<<endl;
    cout<<"   !quit  --- quit this program.." <<endl;
    cout<<"------------------------------------------------------------"<<endl;
  } else if (!cmdValue.compare("add")) {
    help_message_add();
  } else if (!cmdValue.compare("ls")) {
    help_message_ls();
  } else  if (!cmdValue.compare("remove")) {
    help_message_remove();
  } else if (!cmdValue.compare("cat")) {
    help_message_cat();
  } else if (!cmdValue.compare("cd")){
    help_message_cd();
  } else {
    show_help_message("default");
    return;
  }
}

void helpmessage::help_message_add ()
{
  cout<<"   add    --- add an intems into the current level last node"<<endl;
  cout<<"------------------------------------------------------------"<<endl;
  cout<<"          --- there are two right format"<<endl;
  cout<<"          1> add \"key\":value"<<endl;
  cout<<"             example i): add \"key1\":\"value1\"";
  cout<<",\"key2\":\"value2\""<<endl;
  cout<<"                    ii): add \"key3\":{\"key31\":\"value31\"";
  cout<<",\"key32\":\"value32\"}"<< endl;
  cout<<endl;
  cout<<"          2> add <enter>" << endl;
  cout<<"             key:key1"<<endl;
  cout<<"             value:\"value1\""<<endl;
  cout<<"          !!note:any key should without space"<<endl;
  cout<<"------------------------------------------------------------"<<endl;
}

void helpmessage::help_message_ls ()
{
  cout<<"   ls     --- list the all objects in current";
  cout<<" level (directory)!"<<endl;
  cout<<"------------------------------------------------------------"<<endl;
}

void helpmessage::help_message_remove ()
{
  cout<<"   remove --- remove an items"<<endl;
  cout<<"------------------------------------------------------------"<<endl;
  cout<<"          --- there are two right format"<<endl;
  cout<<"          1> remove key"<<endl;
  cout<<"          2> remove <enter>" << endl;
  cout <<"            please give the key:key"<<endl;
  cout<<"------------------------------------------------------------"<<endl;
}

void helpmessage::help_message_cat ()
{
  cout<<"   cat    --- shows the content of the items"<<endl;
  cout<<"------------------------------------------------------------"<<endl;
  cout<<"          useage: cat key "<<endl;
  cout<<"            esp.: cat ."<<endl;
  cout<<"                  shows the current level all keys' contents."<<endl;
  cout<<"------------------------------------------------------------"<<endl;
}

void helpmessage::help_message_cd ()
{
  cout<<"   cd     --- change into another sub level(directory)"<<endl;
  cout<<"------------------------------------------------------------"<<endl;
  cout<<"          useage: cd key "<<endl;
  cout<<"            esp.: cd .. "<<endl;
  cout<<"                  to previous level."<<endl;
  cout<<"------------------------------------------------------------"<<endl;
}

void helpmessage::command_error_msg_add()
{
  pstrops->setcolor("error");
  cout << "Error usage: input error string format!" << endl;
  pstrops->setcolor("mark");
  cout << " add";
  pstrops->setcolor();
  cout << " command usage as follows." << endl;
  help_message_add();
  return;
}
void helpmessage::command_error_run(string cmd)
{
  pstrops->setcolor("error");
  cout << "The command";
  pstrops->setcolor("mark");
  cout << " " << cmd;
  pstrops->setcolor("error");
  cout<< " run failed!"<< endl;
  pstrops->setcolor();
  help_message_add();
}

void helpmessage::too_many_parameters_msg (string key, int parNo)
{
  pstrops->setcolor("error");
  cout << "Error usage: too many parameters!" << endl;
  pstrops->setcolor("mark");
  cout << " " << key;
  pstrops->setcolor();
  if (1 == parNo)
    cout << " only accept a parameters." << endl;
  else
    cout << " only accept " << parNo << " parameters." << endl;
  return;
}
