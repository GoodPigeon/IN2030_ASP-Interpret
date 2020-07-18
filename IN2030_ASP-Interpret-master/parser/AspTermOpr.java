package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspTermOpr extends AspSyntax{
  String symbol;
  TokenKind kind;


  AspTermOpr(int n){
    super(n);
  }

  static AspTermOpr parse(Scanner s){
    enterParser("term opr");
    AspTermOpr ato = new AspTermOpr(s.curLineNum());
    if(s.curToken().kind == plusToken){
      skip(s, plusToken);
      ato.symbol = " + ";
      ato.kind = plusToken;
      leaveParser("term opr");
      return ato;
    }
    else if(s.curToken().kind == minusToken){
      skip(s, minusToken);
      ato.symbol = " - ";
      ato.kind = minusToken;
      leaveParser("term opr");
      return ato;
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
