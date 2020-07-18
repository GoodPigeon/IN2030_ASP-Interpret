package no.uio.ifi.asp.runtime;

import java.util.ArrayList;

//import com.sun.deploy.util.SystemUtils;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;


public class RuntimeDictValue extends RuntimeValue{

  ArrayList<RuntimeValue> number = new ArrayList<>();
  ArrayList<RuntimeValue> name = new ArrayList<>();

  public RuntimeDictValue (ArrayList<RuntimeValue> number, ArrayList<RuntimeValue> name){
    this.number = number;
    this.name = name;
  }

  @Override
  protected String typeName() {
    return "dict";
  }

  @Override
  public String showInfo() {
    String str = "";

    if(number.size() == 0 || name.size() == 0){
      str = "{" + str + "}";
    } else {
      int i = 0;
      str += "{";
      for(RuntimeValue rtv : number){
        str += rtv.showInfo();
        str += " : ";
        str += name.get(i++).showInfo();
        str += " , ";
      }
      str = str.substring(0, str.length() - 2);
      str += "}";
    }
    return str;
  }

  @Override
  public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
    if(v instanceof RuntimeStringValue){
      String v2 = v.getStringValue("[...] operand", where);
      int pos = 0;

      for (RuntimeValue x : number) {
        if(x.getStringValue("String", where).equals(v2)){
          return name.get(pos);
        }
        pos++;
      }
      runtimeError("Key not found", where);
    } else {
      runtimeError("Type error for dictionary {...}", where);
    }
    return null;
  }
}
