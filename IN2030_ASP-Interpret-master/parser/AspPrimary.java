package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspPrimary extends AspSyntax{
  ArrayList<AspPrimarySuffix> primarySuffixes = new ArrayList<>();
  AspAtom aa;

  AspPrimary(int n){
    super(n);
  }

  static AspPrimary parse(Scanner s){
    enterParser("primary");
    AspPrimary ap = new AspPrimary(s.curLineNum());
    ap.aa = AspAtom.parse(s);
    if(s.curToken().kind == leftBracketToken || s.curToken().kind == leftParToken){
      while(s.curToken().kind == leftBracketToken || s.curToken().kind == leftParToken){
        ap.primarySuffixes.add(AspPrimarySuffix.parse(s));
      }
    }
    leaveParser("primary");
    return ap;
  }


  @Override
  public void prettyPrint() {
    aa.prettyPrint();
    for(AspPrimarySuffix ps : primarySuffixes){
      ps.prettyPrint();
    }
  }

  @Override
	RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue v = null;
    v = aa.eval(curScope);
    ArrayList<RuntimeValue> rtvs;

    if(!primarySuffixes.isEmpty()){
      for(int i = 0; i<primarySuffixes.size();i++){
        if(primarySuffixes.get(i) instanceof AspArguments){
          rtvs = primarySuffixes.get(i).eval(curScope).getList("Primaries get list", this);
          v = v.evalFuncCall(rtvs, this);
        }
        else if(primarySuffixes.get(i) instanceof AspSubscription){
          v = v.evalSubscription(primarySuffixes.get(i).eval(curScope), this);
        }
      }
    }
    return v;
  }

}
