package no.uio.ifi.asp.runtime;

import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;


public class RuntimeListValue extends RuntimeValue{

  ArrayList<RuntimeValue> listObject = new ArrayList<>();

  public RuntimeListValue (){}

  public RuntimeListValue (ArrayList<RuntimeValue> listparam){
    listObject = listparam;
  }

  public void addToList(RuntimeValue v){
    listObject.add(v);
  }
  @Override
  public ArrayList<RuntimeValue> getList(String what, AspSyntax Where){
    return getList();
  }
  public ArrayList<RuntimeValue> getList(){
    return listObject;
  }

  public RuntimeValue getElem(int pos){
    return listObject.get(pos);

  }

  @Override
  protected String typeName() {
    return "list";
  }

@Override
  public RuntimeValue evalLen(AspSyntax where){
    RuntimeIntValue v = new RuntimeIntValue(listObject.size());

    return v;
  }

public int getSize(){
  return listObject.size();
}


  @Override
  public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {

    RuntimeValue res = null;
    if(v instanceof RuntimeIntValue){
      long v2 = v.getIntValue("[...] operand", where);
      int v3 = (int)v2;

      if(v3 > listObject.size()-1){
        Main.error("Array size is " + listObject.size() + " but you're trying to get element at position " + v3);
      }else{
        res = listObject.get(v3);
      }
    }else{
      runtimeError("Type error for [...].", where);
    }
    return res;
  }


  @Override
  public String getStringValue(String what, AspSyntax where) {
    String listString = "";
    for(RuntimeValue r : listObject){
      listString += r.showInfo();
      listString += ", ";
    }
    if(listObject.size() == 0){
      listString = "[" + listString;
      listString = listString + "]";
    }
    listString = listString.substring(0,(listString.length()-2));
    listString = "[" + listString;
    listString = listString + "]";
    return listString;
  }

  @Override
  public String toString() {
    String listString = "";
    for(RuntimeValue r : listObject){
      listString += r.toString();
      listString += ", ";
    }
    if(listObject.size() == 0){
      listString = "[" + listString;
      listString = listString + "]";
    }
    listString = listString.substring(0,(listString.length()-2));
    listString = "[" + listString;
    listString = listString + "]";
    return listString;
  }

  @Override
  public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where){

    RuntimeValue res = null;
    if(v instanceof RuntimeIntValue){
      long v2 = v.getIntValue("* operand", where);
      res = new RuntimeListValue(multiplyList(listObject, v2));

    } else{
      runtimeError("Type error for *.", where);
    }
    return res;
  }


  public ArrayList multiplyList(ArrayList v, long d){
    ArrayList<RuntimeValue> temp = new ArrayList<RuntimeValue>();
    temp.addAll(v);
    for(int i = 0; i < d-1; i++){
      v.addAll(temp);
    }
    return v;
  }

  @Override
  public boolean getBoolValue(String what, AspSyntax where){
    if(listObject.isEmpty()){
      return false;
    }else {
      return true;
    }
  }

@Override
  public void evalAssignElem(RuntimeValue inx, RuntimeValue val, AspSyntax where) {
    long v2 = inx.getIntValue("dict operand", where);
    int v3 = (int)v2;

    listObject.remove(v3);
    listObject.add(v3, val);
  }

  public int myLength(){
    return listObject.size();
  }


}
