// strops.hxx string opration class head
// this class used to oprate the string
#ifndef STROPS_H_
#define STROPS_H_
#include<iostream>
#include<string>
#include<vector>
#include<sstream>
#include<set>
using namespace std;
class strops
{
private:
  vector<string> strVec;
  bool           formatTag;
public:
  strops();
  explicit strops(string str);
  ~strops();
  bool           debug();
  vector<string> content2vector(string content);
  void           print_vector(vector<string> tmpVec);
  string         delete_content_space(string content);
  string         delete_all_space (string content);
  int            find_match_pos(string content, char pattern, int pos);
  bool           check_format();
  vector<string> get_vector(){return this->strVec;};
  void           setcolor(string color = "default");
  string         check_repeat(vector<string> inVec);
  vector<string> cmdstring2word(string const str);
  string         default_color;
  string         errormsg_color;
  string         folder_color;
  string         program_color;
  string         mark_color;  
};
#endif
