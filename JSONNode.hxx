// JSONNode.hxx head 
#ifndef JSONNODE_H_
#define JSONNODE_H_
#include<iostream>
#include<set>
#include<map>
#include<string>
using namespace std;
class JSONNode
{
public:
  JSONNode ();
  explicit JSONNode (string str);
  ~JSONNode();
  string    key;               // each node has a identical in same level.
  union     valueType          // the key's value struct.
  {                            // can be int, double, and string.
    int     intV;
    double  douV;
    string  *strP;
  };
  valueType  value;            // the key's value.
  string     value_att;        // the key's value attribute.
  JSONNode  *subNode;          // son node, node == null when this is an atom.
  JSONNode  *parNode;          // parents  node, point to previous node
                               // Note:point to previous level first node if -
                               // this node is first node of the cur. level.
  JSONNode  *nextNode;         // point to next node in the same level.
  bool      assign(int    intV);
  bool      assign(double douV);
  bool      assign(string *strP);
};
#endif
