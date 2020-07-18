package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspAssignment extends AspSmallStmt{
  ArrayList<AspSubscription> subscriptions = new ArrayList<>();
  AspName an;
  AspExpr ae;
  Boolean hasSub;

  AspAssignment(int n) {
    super(n);
  }

  static AspAssignment parse(Scanner s) {
    enterParser("assignment");
    AspAssignment aa = new AspAssignment(s.curLineNum());
    aa.an = AspName.parse(s);
      while(s.curToken().kind != equalToken) {
        aa.subscriptions.add(AspSubscription.parse(s));
      }
    skip(s, equalToken);
    aa.ae = AspExpr.parse(s);
    if(aa.subscriptions.size()>0){
      aa.hasSub = true;
    }
    leaveParser("assignment");
    return aa;
  }


  @Override
  public void prettyPrint() {
    an.prettyPrint();
    int nPrinted = 0;
    for(AspSubscription as : subscriptions){
        as.prettyPrint();
    }
    ++nPrinted;
    prettyWrite("=");
    ae.prettyPrint();
  }


  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
	  RuntimeValue v = ae.eval(curScope);
    RuntimeValue k = null;

    if(subscriptions.isEmpty()){
      curScope.assign(an.name, v);
      trace("subscription: " + an.name + "=" + v);
      return v;
    }
    else{
      k = an.eval(curScope);
      for(int i = 0; i<subscriptions.size()-1; i++){
        k = k.evalSubscription(subscriptions.get(i).eval(curScope), this);
      }
      RuntimeValue f = subscriptions.get(subscriptions.size()-1).eval(curScope);
      k.evalAssignElem(f, v, this);
      return v;
    }
  }

}
