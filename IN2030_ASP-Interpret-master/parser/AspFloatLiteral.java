package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspFloatLiteral extends AspAtom{
  double floatNum;

  AspFloatLiteral(int n){
    super(n);
  }

  static AspFloatLiteral parse(Scanner s){
    AspFloatLiteral afl = new AspFloatLiteral(s.curLineNum());
    enterParser("float literal");
    afl.floatNum = s.curToken().floatLit;
    skip(s, floatToken);
    leaveParser("float literal");
    return afl;
  }

  @Override
  public void prettyPrint() {
    prettyWrite(floatNum + "");
  }


  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
     return new RuntimeFloatValue(floatNum);
  }
}
