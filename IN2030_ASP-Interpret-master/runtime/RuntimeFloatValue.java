package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFloatValue extends RuntimeValue {
  double floatValue;


  public RuntimeFloatValue(double v) {
    floatValue = v;
  }

  @Override
  protected String typeName() {
    return "float";
  }

  @Override
  public String toString() {
    return Double.toString(floatValue);
  }

  @Override
  public boolean getBoolValue(String what, AspSyntax where) {
    if (floatValue == 0.0) {
      return false;
    } else {
      return true;
    }
  }

  @Override
  public double getFloatValue(String what, AspSyntax where) {
    return floatValue;
  }

  @Override
  public long getIntValue(String what, AspSyntax where) {
    return (long)floatValue;
  }

  @Override
  public String getStringValue(String what, AspSyntax where) {
    return Double.toString(floatValue);
  }

// tabell 2.3
  @Override
  public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("+ operand",where);
      return new RuntimeFloatValue(floatValue + v2);
    } else if (v instanceof RuntimeFloatValue) {
      double v2 = v.getFloatValue("+ operand", where);
      return new RuntimeFloatValue(floatValue + v2);
    } else {
      runtimeError("Type error for +.", where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("/ operand", where);
      return new RuntimeFloatValue(floatValue / v2);
    } else if (v instanceof RuntimeFloatValue) {
      double v2 = v.getFloatValue("/ operand", where);
      return new RuntimeFloatValue(floatValue / v2);
    } else {
      runtimeError("Type error //.", where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeFloatValue) {
      double v2 = v.getFloatValue("== operand", where);
      return new RuntimeBoolValue(floatValue == v2);
    } else if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("== operand", where);
      return new RuntimeBoolValue(floatValue == v2);
    } else if (v instanceof RuntimeNoneValue) {
      boolean v2 = v.getBoolValue("== operand", where);
      return new RuntimeBoolValue(v.getBoolValue("== operand", where) == v2);
    } else {
      runtimeError("Type error for ==.", where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeFloatValue) {
      double v2 = v.getFloatValue("> operand", where);
      return new RuntimeBoolValue(floatValue > v2);
    } else if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("> operand",where);
      return new RuntimeBoolValue(floatValue > v2);
    } else {
      runtimeError("Type error for >.", where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeFloatValue) {
       double v2 = v.getFloatValue(">= operand", where);
       return new RuntimeBoolValue(floatValue > v2);
    } else if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue(">= operand",where);
          return new RuntimeBoolValue(floatValue >= v2);
    } else {
      runtimeError("Type error for >=.", where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("// operand", where);
      return new RuntimeFloatValue(Math.floor(floatValue / v2));
    } else if (v instanceof RuntimeFloatValue) {
      double v2 = v.getFloatValue("// operand", where);
      return new RuntimeFloatValue(Math.floor(floatValue / v2));
    } else {
      runtimeError("Type error //.", where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeFloatValue) {
       double v2 = v.getFloatValue("< operand", where);
       return new RuntimeBoolValue(floatValue < v2);
    } else if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("< operand",where);
          return new RuntimeBoolValue(floatValue < v2);
    } else {
      runtimeError("Type error for <.", where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeFloatValue) {
       double v2 = v.getFloatValue("<= operand", where);
       return new RuntimeBoolValue(floatValue <= v2);
    } else if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("<= operand",where);
          return new RuntimeBoolValue(floatValue <= v2);
    } else {
      runtimeError("Type error for <=.", where);
    }
    return null;
  }


  @Override
  public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("* operand", where);
      return new RuntimeFloatValue(floatValue-v2*Math.floor(floatValue/v2));
    } else if (v instanceof RuntimeFloatValue) {
      double v2 = v.getFloatValue("* operand",where);
      return new RuntimeFloatValue(floatValue-v2*Math.floor(floatValue/v2));
    } else {
      runtimeError("Type error *.", where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("* operand", where);
      return new RuntimeFloatValue(floatValue * v2);
    } else if (v instanceof RuntimeFloatValue) {
      double v2 = v.getFloatValue("* operand",where);
      return new RuntimeFloatValue(floatValue * v2);
    } else {
      runtimeError("Type error *.", where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalNegate(AspSyntax where) {
    return new RuntimeFloatValue(-1.0 * floatValue);
  }

  @Override
  public RuntimeValue evalNot(AspSyntax where) {
    if(floatValue == 0.0) {
      return new RuntimeBoolValue(true);
    } else {
      return new RuntimeBoolValue(false);
    }
  }

  @Override
  public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeFloatValue) {
      double v2 = v.getFloatValue("!= operand",where);
      return new RuntimeBoolValue(floatValue != v2);
    } else if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("!= operand",where);
      return new RuntimeBoolValue(floatValue != v2);
    } else {
      runtimeError("Type error for !=.", where);
    }
    return null;
  }

  @Override
  public RuntimeValue evalPositive(AspSyntax where) {
    return new RuntimeFloatValue(floatValue);
  }

  @Override
  public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      long v2 = v.getIntValue("- operand", where);
      return new RuntimeFloatValue(floatValue - v2);
    } else if (v instanceof RuntimeFloatValue) {
      double v2 = v.getFloatValue("- operand", where);
      return new RuntimeFloatValue(floatValue - v2);
    } else {
      runtimeError("Type error for -.", where);
    }
    return null;
  }

}
