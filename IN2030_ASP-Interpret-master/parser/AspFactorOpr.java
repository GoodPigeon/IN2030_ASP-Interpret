package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspFactorOpr extends AspSyntax{
  String symbol;
  TokenKind tokenKind;


  AspFactorOpr(int n){
    super(n);
  }

  static AspFactorOpr parse(Scanner s){
    enterParser("factor opr");
    AspFactorOpr afo = new AspFactorOpr(s.curLineNum());
    afo.tokenKind = s.curToken().kind;

    if(s.curToken().kind == astToken){
      skip(s, astToken);
      afo.symbol = " * ";
      leaveParser("factor opr");
      return afo;
    }
    else if(s.curToken().kind == slashToken){
      skip(s, slashToken);
      afo.symbol = " / ";
      leaveParser("factor opr");
      return afo;
    }
    else if(s.curToken().kind == percentToken){
      skip(s, percentToken);
      afo.symbol = " % ";
      leaveParser("factor opr");
      return afo;
    }
    else if(s.curToken().kind == doubleSlashToken){
      skip(s, doubleSlashToken);
      afo.symbol = " // ";
      leaveParser("factor opr");
      return afo;
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
