package no.uio.ifi.asp.parser;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspFuncDef extends AspCompoundStmt{
  public ArrayList<AspName> names = new ArrayList<>();
  public AspSuite as;

  AspFuncDef(int n){
    super(n);
  }

  static AspFuncDef parse(Scanner s){
    enterParser("func def");
    AspFuncDef funcDef = new AspFuncDef(s.curLineNum());
    skip(s, defToken);
    funcDef.names.add(AspName.parse(s));
    skip(s, leftParToken);
    while(s.curToken().kind != rightParToken){
      funcDef.names.add(AspName.parse(s));
      if(s.curToken().kind != commaToken){
        break;
      }
      skip(s, commaToken);
    }
    skip(s, rightParToken);
    skip(s, colonToken);
    funcDef.as = AspSuite.parse(s);


    leaveParser("func def");
    return funcDef;
  }


  @Override
  public void prettyPrint() {
    int count = 0;
    prettyWrite("def ");
    if(!names.isEmpty()){
      names.get(0).prettyPrint();
    }
    prettyWrite("( ");
    if(names.size()>0){
      for(int i = 0; i < names.size(); i++){
        if(count > 0){
          prettyWrite(", ");
        }
        names.get(i).prettyPrint();

        count++;
      }
    }
    prettyWrite(") ");
    prettyWrite(" : ");
    as.prettyPrint();
  }

  public RuntimeValue runFunction(RuntimeScope s)throws RuntimeReturnValue{
    try{
		    RuntimeValue suite = as.eval(s);
      }catch(RuntimeReturnValue rrv){
        return rrv.value;
      }
      return null;
	}

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue v = null;
    AspName n = names.get(0);
    v  = new RuntimeFunc(this, curScope, n.name);
    curScope.assign(n.name, v);
    trace("def " + v.showInfo());
    return v;
  }

}
