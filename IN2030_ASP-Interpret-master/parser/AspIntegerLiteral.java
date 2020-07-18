package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspIntegerLiteral extends AspAtom{
  long integer;

  AspIntegerLiteral(int n){
    super(n);
  }

  static AspIntegerLiteral parse(Scanner s){
    AspIntegerLiteral ail = new AspIntegerLiteral(s.curLineNum());
    enterParser("integer literal");
    ail.integer = s.curToken().integerLit;
    skip(s, integerToken);
    leaveParser("integer literal");
    return ail;
  }

  @Override
  public void prettyPrint() {
    prettyWrite(Long.toString(integer));
  }


  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
     return new RuntimeIntValue(integer);
  }
}
