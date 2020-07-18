package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeIntValue extends RuntimeValue {
  long intValue;

  public RuntimeIntValue(long v) {
    intValue = v;
  }

  @Override
  protected String typeName() {
    return "int";
  }

  @Override
  public String toString() {
    return Long.toString(intValue);
  }

  @Override
  public boolean getBoolValue(String what, AspSyntax where) {
    if (intValue == 0) {
      return false;
    } else {
      return true;
    }
  }

  @Override
  public double getFloatValue(String what, AspSyntax where) {
    return (double)intValue;
  }

  @Override
  public long getIntValue(String what, AspSyntax where) {
    return intValue;
  }

  @Override
  public String getStringValue(String what, AspSyntax where) {
    return Long.toString(intValue);
  }

// tabell 2.3
  @Override
  public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("+ operand",where);
      return new RuntimeIntValue(intValue + v2);
    } else if (v instanceof RuntimeFloatValue) {
      double v2 = v.getFloatValue("+ operand", where);
      return new RuntimeFloatValue(intValue + v2);
    } else {
      runtimeError("Type error for +.", where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("/ operand", where);
      return new RuntimeIntValue(intValue / v2);
    } else if (v instanceof RuntimeFloatValue) {
      double v2 = v.getFloatValue("/ operand", where);
      return new RuntimeFloatValue(intValue / v2);
    } else {
      runtimeError("Type error //.", where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeFloatValue) {
      double v2 = v.getFloatValue("== operand", where);
      return new RuntimeBoolValue(intValue == v2);
    } else if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("== operand", where);
      return new RuntimeBoolValue(intValue == v2);
    } else if (v instanceof RuntimeNoneValue) {
      return new RuntimeBoolValue(false);
    } else {
      runtimeError("Type error for ==.", where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeFloatValue) {
       double v2 = v.getFloatValue("> operand", where);
       return new RuntimeBoolValue(intValue > v2);
    } else if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("> operand",where);
          return new RuntimeBoolValue(intValue > v2);
    } else {
      runtimeError("Type error for >.", where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeFloatValue) {
       double v2 = v.getFloatValue(">= operand", where);
       return new RuntimeBoolValue(intValue >= v2);
    } else if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue(">= operand",where);
          return new RuntimeBoolValue(intValue >= v2);
    } else {
      runtimeError("Type error for >=.", where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("// operand", where);
      return new RuntimeFloatValue(Math.floor(intValue / v2));
    } else if (v instanceof RuntimeFloatValue) {
      double v2 = v.getFloatValue("// operand", where);
      return new RuntimeFloatValue(Math.floor(intValue / v2));
    } else {
      runtimeError("Type error //.", where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeFloatValue) {
       double v2 = v.getFloatValue("< operand", where);
       return new RuntimeBoolValue(intValue < v2);
    } else if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("< operand",where);
          return new RuntimeBoolValue(intValue < v2);
    } else {
      runtimeError("Type error for <.", where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeFloatValue) {
       double v2 = v.getFloatValue("<= operand", where);
       return new RuntimeBoolValue(intValue <= v2);
    } else if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("<= operand",where);
          return new RuntimeBoolValue(intValue <= v2);
    } else {
      runtimeError("Type error for <=.", where);
    }
    return null;
  }


  @Override
  public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("* operand", where);
      return new RuntimeIntValue(Math.floorMod(intValue, v2));
    } else if (v instanceof RuntimeFloatValue) {
      double v2 = v.getFloatValue("* operand",where);
      return new RuntimeFloatValue(intValue-v2*Math.floor(intValue/v2));
    } else {
      runtimeError("Type error *.", where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("* operand", where);
      return new RuntimeIntValue(intValue * v2);
    } else if (v instanceof RuntimeFloatValue) {
      double v2 = v.getFloatValue("* operand",where);
      return new RuntimeFloatValue(intValue * v2);
    } else {
      runtimeError("Type error *.", where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalNegate(AspSyntax where) {
    return new RuntimeIntValue(-1 * intValue);
  }

  @Override
  public RuntimeValue evalNot(AspSyntax where) {
    if(intValue == 0) {
      return new RuntimeBoolValue(true);
    } else {
      return new RuntimeBoolValue(false);
    }
  }

  @Override
  public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeFloatValue) {
      double v2 = v.getFloatValue("!= operand",where);
      return new RuntimeBoolValue(intValue != v2);
    } else if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("!= operand",where);
      return new RuntimeBoolValue(intValue != v2);
    } else {
      runtimeError("Type error for !=.", where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalPositive(AspSyntax where) {
    return new RuntimeIntValue(intValue);
  }

  @Override
  public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("- operand",where);
      return new RuntimeIntValue(intValue - v2);
    } else if (v instanceof RuntimeFloatValue) {
      double v2 = v.getFloatValue("- operand",where);
      return new RuntimeFloatValue(intValue - v2);
    } else {
      runtimeError("Type error for -.", where);
    }
    return null;
  }

}
