package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspCompOpr extends AspSyntax{
  String opr;
  TokenKind kind;


  AspCompOpr(int n){
    super(n);
  }

  static AspCompOpr parse(Scanner s){
    enterParser("comp opr");
    AspCompOpr aco = new AspCompOpr(s.curLineNum());
    aco.kind = s.curToken().kind;

    if(s.curToken().kind == lessToken){
      skip(s, lessToken);
      aco.opr = "<";
      leaveParser("comp opr");
      return aco;
    }
    else if(s.curToken().kind == greaterToken){
      skip(s, greaterToken);
      aco.opr = ">";
      leaveParser("comp opr");
      return aco;
    }
    else if(s.curToken().kind == doubleEqualToken){
      skip(s, doubleEqualToken);
      aco.opr = "==";
      leaveParser("comp opr");
      return aco;
    }
    else if(s.curToken().kind == greaterEqualToken){
      skip(s, greaterEqualToken);
      aco.opr = ">=";
      leaveParser("comp opr");
      return aco;
    }
    else if(s.curToken().kind == lessEqualToken){
      skip(s, lessEqualToken);
      aco.opr = "<=";
      leaveParser("comp opr");
      return aco;
    }
    else if(s.curToken().kind == notEqualToken){
      skip(s, notEqualToken);
      aco.opr = "!=";
      leaveParser("comp opr");
      return aco;
    }

    return null;
  }

  @Override
  public void prettyPrint() {
    prettyWrite(opr);
  }


  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    return null;
  }

}
