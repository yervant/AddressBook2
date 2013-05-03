// main.cpp
// the JSON list processing main program
#include<iostream>
#include<string>
#include"command.hxx"
#include"JSONNode.hxx"
#include"JSONList.hxx"
using namespace std;
int main (int argc, char** argv)
{
  string name(argv[0]);
  string::size_type loc = name.find_last_of("/");
  if (loc == string::npos) {
    command cmd;  
    cmd.run();
  } else {
    string  tmp(name.substr(loc + 1, name.size() - loc));
    command cmd(tmp);
    cmd.run();
  }
  return 0;
}
