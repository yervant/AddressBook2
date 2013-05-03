//strops.cpp  string opration class
// this class used to oprate the string
#include "strops.hxx"

strops::strops ()
{
  default_color  = "\033[0m";
  errormsg_color = "\033[1;31m";
  folder_color   = "\033[1;34m";
  program_color  = "\033[1;32m";
  mark_color     = "\033[1;36m";
  formatTag = true;
}

strops::strops (string str)
{
  default_color  = "\033[0m";
  errormsg_color = "\033[1;31m";
  folder_color   = "\033[1;34m";
  program_color  = "\033[1;32m";
  mark_color     = "\033[1;36m";
  this->strVec = this->content2vector(str);
  if (this->strVec.size() == 0) {
    this->formatTag = false;
  } else if (this->strVec.size() == 1 && strVec[0].size() == 0) {
    this->formatTag = false;
  } else {
    this->formatTag = true;
  }
}

strops::~strops()
{}

/*** 
 * convert the content to the needed vector format
 * return empty vector if the content isn't
 * satisfy the needed format, for exmample the content:
 * "key3":{"key31":"value31","key32":"value32"},"key4":"vaule4"
 * become [key, "key31":"value31","key32":"value32", key4, value4]
 */
vector<string> strops::content2vector (string content)
{
  vector<string>   rsVector;
  vector<string>   errVector;  // it's empty
  if (0 == content.size()) {
    return  errVector;
    this->formatTag = false;
  }
  string nospacestr = delete_content_space(content);
  int keybpos       = 0;     // key begin position
  int pos           = 0;
  bool subTag       = false;

  for (int i = 0; i < nospacestr.size();)
  {
    if (nospacestr[i] == ':') {    
      if (nospacestr[keybpos] == ',')   keybpos++;
      string key(nospacestr.substr(keybpos, i-keybpos));
      rsVector.push_back(key);
      if ((i+1) == nospacestr.size()) { 
	return errVector;
	this->formatTag = false;
      }
      pos = find_match_pos(nospacestr, nospacestr[i+1], i+1);
      if (pos == -1) {
	return errVector;
	this->formatTag = false;
      }
      string value(nospacestr.substr(i+1, pos - i));
      rsVector.push_back(value);
      i = pos + 1;
      keybpos = i;
      continue;
    } else {
      i++;
    }
  } // end for
  this->strVec = rsVector;
  this->formatTag = check_format();
  return  rsVector;
}

/***
 * convert cmmand string to word seperated by space
 * return  the vector
 */
vector< string > strops::cmdstring2word (string const str)  
{  
  int start = 0;  
  int count = 0;  
  int i     = 0;
  vector< string > eachline;  
  if (0 == str.size()) {
    return eachline;
  }
  // delete the start useless space 
  while (isspace(str[i])) { i++; }
  string s(str.substr(i, str.size() - i));
  // s make sure the first char is non-null
  for (int j = 0; j < s.size();)     
  {  
    if (s[j] == '"') {
      if (count > 0) {
	string tmp(s.substr(start, count));
	eachline.push_back(tmp);
	count = 0;
      }
      int pos = find_match_pos(s, s[j], j+1);
      if (pos == -1) {
	string tmp(s.substr(j, s.size() - j));
	eachline.push_back(tmp);
	return eachline;
      }
      string tmp(s.substr(j, (pos-j) + 1));
      eachline.push_back(tmp);
      j = pos + 1;
      start = j;
      count = 0; 
      continue;
    } else if (isspace(s[j]) && count > 0) {
      string tmp(s.substr(start, count));
      eachline.push_back(tmp);
      count = 0;
    } else if (!isspace(s[j]) && count == 0) {
      start = j;
      count ++;
    } else if (!isspace(s[j]) && count > 0) {
      count ++;
    }
    if ( (s.size() - 1) == j && count > 0) {
      string tmp(s.substr(start, count));
      eachline.push_back(tmp);
    }
    j++;
  }
  return eachline;  
}  

/***
 * find the matched pattern from the pos+1 in content
 * the pattern can be assigned '{' '"'
 */
int strops::find_match_pos (string content, char pattern, int pos)
{
  int bracket = 1;
  if ((pos + 1) == content.size())
    return -1;
  for (int i = pos + 1; i < content.size(); ++i)
  {
    if ((pattern == '"') && content[i] == '"')
      return i;
    if ((pattern == '{') && content[i] == '}' && bracket == 1)
      return i;
    if ((pattern == '{') && content[i] == '}' && bracket != 1) 
      bracket --;
    if ((pattern == '{') && content[i] == '{') 
      bracket ++;
  }
  return -1;
}

/**
 * print the vector tmpVec content
 */
void strops::print_vector (vector<string> tmpVec)
{
  cout << "vector size=" << tmpVec.size() << endl;
  if (tmpVec.size() == 0) {
    cout <<"this is a empty vector"<<endl;
    return;
  }
  if (tmpVec.size() == 1 && tmpVec[0].size() == 0) {
    cout <<"this is a empty vector"<<endl;
    return;
  }
  for (int i = 0; i < tmpVec.size(); ++i)
  {
    if ( i%2 == 0)
      cout<<"["<<(i/2+i%2)<<"] "<< tmpVec[i];
    else
      cout<<":"<< tmpVec[i]<<endl;
  }
}

/***
 * delete the useless space in content string
 * the useless space indicates the outside of ""
 */
string strops::delete_content_space (string content)
{
  string         nospacestr = "";
  bool           subTag     = false;
  int            i          = 0;
  while (isspace(content[i])) { i++; }
  string s(content.substr(i, content.size() - i));
  for (int j = 0; j < s.size();)
  { 
    if (s[j] == '"') {
      int    pos = find_match_pos(s, s[j], j+1);
      if (pos == -1){
	string tmp(s.substr(j, s.size() - j));
	nospacestr += tmp;
	return nospacestr;
      } else {
	string tmp(s.substr(j, (pos-j) + 1));
	nospacestr += tmp;
	j           = pos + 1;
      }
    } else {
      nospacestr += content[j];
      ++j;
    }
  }
  return nospacestr;
}

/***
 * delete all space in content any position
 */
string strops::delete_all_space (string content)
{
  string rs = "";
  for (int i = 0; i < content.size(); ++i)
  {
    if (!isspace(content[i]))
      rs += content[i];
  }
  return rs;
}

/*** 
 * set the cout text color
 * set "default" color if no input 
 * the color_type can be assigned 
 * "default", "error", "folder", "program"
 */
void strops::setcolor (string color_type)
{ 
  string color;
  if (!color_type.compare("default"))
    color = default_color;
  else if (!color_type.compare("error"))
    color = errormsg_color;
  else if (!color_type.compare("folder"))
    color = folder_color;
  else if (!color_type.compare("program"))
    color = program_color;
  else if (!color_type.compare("mark"))
    color = mark_color;
  else 
    color = default_color;
  cout<<color;
  return;
}

/***
 * check whether input key is repeat
 * the key in inVec (input vector)'s even item
 * return the repeated string name if we find repeat pattern
 * else  return the empty string.
 */
string strops::check_repeat (vector<string> inVec)
{
  string rs = "";
  set<string> keyset;
  for (int i = 0; i < inVec.size() - 1; i += 2)
  {
    if (keyset.count(inVec[i]) == 1) {
      return inVec[i];
    } else {
      keyset.insert(inVec[i]);
    }
  }
  return rs;
}

/***
 * check the input string whether pair-wise
 */
bool strops::check_format ()
{
  string key = "";
  string value = "";
  if (0 == strVec.size())
    return true;
  if (1 == (strVec.size()%2)) { 
    return false;
  }
  for (int i=0; i < strVec.size()-1; i += 2)
  {
    key       = strVec[i];
    int lposk = key.size() - 1;      // last position key
    vector<string> tmpVec;
    string key_without_sem(key.substr(1, key.size() - 2 ));
    tmpVec = cmdstring2word(key_without_sem);
    if (tmpVec.size() > 1) {
      return false;
    }

    value     = strVec[i+1];
    int lposv = value.size() - 1;    // last position value
    if ((key[0] != '"') || (key[lposk] != '"'))
      return false;
    if (value[0] == '"' && (value[lposv] != '"'))
      return false;
    if (value[lposv] == '"' && (value[0] != '"'))
      return false;
    if (value[0] == '{' && (value[lposv] != '}'))
      return false;
    if (value[0] != '{' && (value[lposv] == '}'))
      return false;
  }
  return true;
}

