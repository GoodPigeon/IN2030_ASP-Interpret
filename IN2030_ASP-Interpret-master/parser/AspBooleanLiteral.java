package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspBooleanLiteral extends AspAtom{
  String boolStr;
  boolean value;

  AspBooleanLiteral(int n){
    super(n);
  }

  static AspBooleanLiteral parse(Scanner s){
    AspBooleanLiteral abl = new AspBooleanLiteral(s.curLineNum());
    enterParser("boolean literal");
    if(s.curToken().kind == trueToken){
      abl.boolStr = "True";
      abl.value = true;
      skip(s, trueToken);
      leaveParser("boolean literal");
      return abl;
    }
    else if(s.curToken().kind == falseToken){
      abl.boolStr = "False";
      abl.value = false;
      skip(s, falseToken);
      leaveParser("boolean literal");
      return abl;
    }
    return null;
  }

  @Override
  public void prettyPrint() {
    prettyWrite(boolStr);
  }


  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    return new RuntimeBoolValue(value);
  }
}
