package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeStringValue extends RuntimeValue {
  String strValue;


  public RuntimeStringValue(String v) {
    strValue = v;
  }

  @Override
  protected String typeName() {
    return "string";
  }

  @Override
  public String toString() {
    return "'" + strValue + "'";
  }

  @Override
  public boolean getBoolValue(String what, AspSyntax where) {
    if(strValue == ""){
      return false;
    } else {
      return true;
    }
  }

  @Override
  public double getFloatValue(String what, AspSyntax where) {
    return Double.parseDouble(strValue);
  }

  @Override
  public long getIntValue(String what, AspSyntax where) {
    return Long.parseLong(strValue);
  }

  @Override
  public String getStringValue(String what, AspSyntax where) {
    return strValue;
  }

// tabell 2.3
  @Override
  public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeStringValue) {
      String v2 = v.getStringValue("+ operand", where);
      return new RuntimeStringValue(strValue.concat(v2));
    } else {
      runtimeError("Type error for +.", where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("* operand", where);

      String result = "";

      for (int i = 0; i < v2; i++) {
        result += strValue;
      }

      return new RuntimeStringValue(result);
    } else {
      runtimeError("Type error *.", where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeStringValue) {
      String v2 = v.getStringValue("== operand",where);
      return new RuntimeBoolValue((strValue.equals(v2)));
    } else if (v instanceof RuntimeNoneValue) {
      return new RuntimeBoolValue(false);
    } else {
     runtimeError("Type error for ==.", where);
   }
   return null;
 }

  @Override
  public RuntimeValue evalLen(AspSyntax where) {
    return new RuntimeIntValue(strValue.length());
  }

  @Override
  public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
    if(v instanceof RuntimeIntValue) {
      long vl = v.getIntValue("[...] operand", where);
      int vi = (int)vl;
      return new RuntimeStringValue(Character.toString(strValue.charAt(vi)));
    } else {
      runtimeError("Type error for [...].", where);
    }
    return null;
  }

 @Override
 public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
   if (v instanceof RuntimeStringValue) {
     String v2 = v.getStringValue("< operand",where);
     int temp = strValue.compareTo(v2);
     if (temp < 0) {
       return new RuntimeBoolValue(true);
     } else {
       return new RuntimeBoolValue(false);
     }
   } else {
     runtimeError("Type error for <.", where);
   }
   return null;
 }

 @Override
 public RuntimeValue evalNot(AspSyntax where) {
   if (strValue == "") {
     return new RuntimeBoolValue(true);
   } else {
     return new RuntimeBoolValue(false);
   }
 }
}
