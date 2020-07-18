package no.uio.ifi.asp.runtime;
import java.util.ArrayList;
import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.parser.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFunc extends RuntimeValue{
  AspFuncDef def;
  RuntimeScope defScope;
  String funcName;

  public RuntimeFunc(AspFuncDef def, RuntimeScope defScope, String name){
    this.def = def;
    this.defScope = defScope;
    this.funcName = name;
  }
  public  RuntimeFunc(String name){
    this.funcName = name;
  }

  public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {
    int formalsParams = def.names.size()-1;
    //(a) Sjekk at antallet aktuelle parametre er det samme som antallet formelle parametre.
    if(formalsParams == actualParams.size()){
      RuntimeScope skop = new RuntimeScope(defScope);
      //(b) Opprett et nytt RuntimeScope-objekt. Dette skopets outer skal være det skopet der funksjonen ble deklarert.

      //(c) Gå gjennom parameterlisten og tilordne alle de aktuelle parameterverdiene til de formelle parametrene.
      for (int i = 0 ; i < actualParams.size(); i++){
        skop.assign(def.names.get(i+1).name, actualParams.get(i));

      }
      try {
        //(d) Kall funksjonens runFunction (med det nye skopet som parameter) slik at den kan utføre innmaten av funksjonen.
        //def.runFunction(skop);
        def.as.eval(skop);
      } catch (RuntimeReturnValue rrv) {
        return rrv.value;
      }
    }else{

      RuntimeValue.runtimeError("Wrong " + funcName + "!",where);
    }
    return null;
  }

  @Override
  public String toString(){
    return funcName;
  }

  @Override
  protected String typeName() {
    return "func";
  }

  RuntimeScope returnScope(){
    return defScope;
  }
}
