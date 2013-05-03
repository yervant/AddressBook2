//command.hxx
// this class use as terminal interface to users
#ifndef COMMAND_H_
#define COMMAND_H_
#include<iostream>
#include<string>
#include<fstream>
#include<vector>
#include<set>
#include<cctype>
#include"JSONList.hxx"
#include"strops.hxx"
#include"helpmessage.hxx"
using namespace std;
class command
{
private:
  string           name;        // the terminal name shows to user
  JSONList         jlist;       // saved json list
  helpmessage      helpmsg;
  vector< string > cmd_vec;     // command vector format
  set< string >    cmdset;      // all commands
  strops           *pstrops;
  void             initial();
  string           correct_input_for_add();
  vector< string > correct_input_for_remove();
public:
  command();
  explicit command(string const);
  ~command();
  bool              run();
  bool              save(string saveName = "default.json");
  bool              run_command(vector<string> cmd);      
                    // run the specific command
};
#endif
