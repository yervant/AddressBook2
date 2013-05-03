// helpmessage.hxx
#ifndef HELPMESSAGE_H_
#define HELPMESSAGE_H_
#include<iostream>
#include<string>
#include"strops.hxx"
using namespace std;

class helpmessage
{
private:
  strops           *pstrops;
public:
  helpmessage();
  ~helpmessage();
  void             show_help_message(string cmdValue = "default");
  void             help_message_add();
  void             help_message_remove();
  void             help_message_ls();
  void             help_message_cat();
  void             help_message_cd();
  void             command_error_msg_add();
  void             command_error_run(string cmd);
  void             too_many_parameters_msg(string key, int parNo);
};
#endif
