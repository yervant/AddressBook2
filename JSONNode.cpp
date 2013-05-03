//JSONNode.cpp json node class
// this class act as JSONNode linked list node
#include "JSONNode.hxx"

JSONNode::JSONNode ()
{
  this->subNode  = NULL;
  this->parNode  = NULL;
  this->nextNode = NULL;
}

JSONNode::JSONNode (string str)
{
  this->subNode  = NULL;
  this->parNode  = NULL;
  this->nextNode = NULL;
  this->key      = str;
}
           
JSONNode::~JSONNode ()
{}

bool JSONNode::assign (int    intV)
{
  this->value_att  = "int";
  this->value.intV = intV;
}

bool JSONNode::assign (double douV)
{
  this->value_att  = "double";
  this->value.douV = douV;
}

bool JSONNode::assign (string *strP)
{
  this->value_att = "string";
  this->value.strP = strP;
}


