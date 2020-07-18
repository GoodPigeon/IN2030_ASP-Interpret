package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspFactorPrefix extends AspSyntax{
  String symbol;
  TokenKind kind;

  AspFactorPrefix(int n){
    super(n);
  }

  static AspFactorPrefix parse(Scanner s){
    enterParser("factor prefix");
    AspFactorPrefix afp = new AspFactorPrefix(s.curLineNum());

    if(s.curToken().kind == plusToken){
      skip(s, plusToken);
      afp.symbol = "+";
      afp.kind = plusToken;
      leaveParser("factor prefix");
      return afp;
    }
    else if(s.curToken().kind == minusToken){
      skip(s, minusToken);
      afp.symbol = "-";
      afp.kind = minusToken;
      leaveParser("factor prefix");
      return afp;
    }
    return null;
  }



  @Override
  public void prettyPrint() {
    prettyWrite(symbol);
  }


  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      return null;
  }

}
