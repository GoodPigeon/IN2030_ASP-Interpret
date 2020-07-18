package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspIfStmt extends AspCompoundStmt{
  ArrayList<AspExpr> exprs = new ArrayList<>();
  ArrayList<AspSuite> suites = new ArrayList<>();

  AspIfStmt(int n){
    super(n);
  }

  static AspIfStmt parse(Scanner s){
    enterParser("if stmt");
    AspIfStmt ais = new AspIfStmt(s.curLineNum());
    skip(s, ifToken);
    ais.exprs.add(AspExpr.parse(s));
    skip(s, colonToken);
    ais.suites.add(AspSuite.parse(s));

    while(s.curToken().kind == elifToken){
      skip(s, elifToken);
      ais.exprs.add(AspExpr.parse(s));
      skip(s, colonToken);
      ais.suites.add(AspSuite.parse(s));
    }

    if(s.curToken().kind == elseToken){
      skip(s, elseToken);
      skip(s, colonToken);
      ais.suites.add(AspSuite.parse(s));
    }

    leaveParser("if stmt");
    return ais;
  }

  @Override
  public void prettyPrint() {
     prettyWrite("if ");
     exprs.get(0).prettyPrint();
     prettyWrite(":");
     suites.get(0).prettyPrint();
     int i = 1;
     while(exprs.size()>i){
       prettyWrite("elif ");
       exprs.get(i).prettyPrint();
       prettyWrite(":");
       suites.get(i).prettyPrint();
       i++;
     }

     if(exprs.size() < suites.size()){
       prettyWrite("else");
       prettyWrite(":");
       suites.get(i).prettyPrint();

     }

  }


  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue v = null;
    for(int i = 0; i < exprs.size(); i++){
      v = exprs.get(i).eval(curScope);
      if(exprs.get(i).eval(curScope).getBoolValue("if test.", this)){
        suites.get(i).eval(curScope);
        return v;
      }
    }
    if(suites.size() > exprs.size()){
      suites.get(suites.size()-1).eval(curScope);
    }

    trace("if" + v.showInfo());
    return v;
  }

}
