// JSONList.hxx root
#ifndef JSONLIST_H_
#define JSONLIST_H_
#include<iostream>
#include<string>
#include<sstream>
#include<vector>
#include"JSONNode.hxx"         // using json node class
#include"strops.hxx"
using namespace std;
class JSONList
{
private:
  strops         *pstrops;     // to deal with string
  string         sub_content(JSONNode *node);
  JSONNode*      findkey(JSONNode *node, string key);
  bool           removesub(JSONNode *subnode);
  bool           remove_first_node(JSONNode *firstNode);
  bool           update_parNode_value(JSONNode *firstNode, JSONNode *par);
  string         get_pointer_value(JSONNode *node);
  bool           addone(JSONNode *firstNode, string key, string content);

public:
  JSONList();
  ~JSONList();
  JSONNode       *root;         // the root pointer.
  JSONNode       *cur;          // the current pointer which points
                                // to the first node in the current level
  void           toy();         // just a toy for debugging.
  void           initial(string content);
  bool           ls(JSONNode* node);
  bool           cat(JSONNode* node, string key);
  bool           add(JSONNode* node, string content);
  bool           remove(JSONNode* node, string key);
  bool           debug(JSONNode* node, string key);
  JSONNode*      cd(JSONNode *node, string key = ".");
  template       <typename T>
  string         to_string(T const& number);
  string         get_allcontent();
  bool           addsub(JSONNode *preLevel, JSONNode *curNode, string content); 
};
#endif
