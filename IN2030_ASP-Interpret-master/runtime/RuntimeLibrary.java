package no.uio.ifi.asp.runtime;

import java.util.ArrayList;
import java.util.Scanner;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeLibrary extends RuntimeScope {
    private Scanner keyboard = new Scanner(System.in);

    public RuntimeLibrary() {

      //float
      assign("float", new RuntimeFunc("float"){
        @Override
        public RuntimeValue evalFuncCall(
          ArrayList<RuntimeValue> actualParams,
          AspSyntax where){
        checkNumParams(actualParams, 1, "float", where);
        return new RuntimeFloatValue(actualParams.get(0).getFloatValue("double", where));
      }});

      //len
      assign("len", new RuntimeFunc("len"){
        @Override
        public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where){
          checkNumParams(actualParams, 1, "len", where);
          System.out.println("hello");
          return actualParams.get(0).evalLen(where);
        }
      });

      //input
      assign("input", new RuntimeFunc("input"){
        @Override
        public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where){
          checkNumParams(actualParams, 1, "input", where);
          System.out.println(actualParams.get(0));
          return new RuntimeStringValue(keyboard.nextLine());
        }
      });

      //str
      assign("str", new RuntimeFunc("str"){
        @Override
        public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where){
          checkNumParams(actualParams, 1, "str", where);
          return new RuntimeStringValue(actualParams.get(0).toString());
        }});

        //print
        assign("print", new RuntimeFunc("print"){
          @Override
          public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where){
            for(RuntimeValue v: actualParams){
              System.out.print(v.toString());
              System.out.print(" ");

            }
            System.out.println();
            return new RuntimeNoneValue();
          }});

          //int
          assign("int", new RuntimeFunc("int"){
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where){
              checkNumParams(actualParams, 1, "int", where);
              return new RuntimeIntValue(actualParams.get(0).getIntValue("get int", where));
            }
          });

          //float
          assign("float", new RuntimeFunc("float"){
            @Override
            public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where){
              checkNumParams(actualParams, 1, "float", where);
              return new RuntimeFloatValue(actualParams.get(0).getFloatValue("float", where));

            }

          });

    }


    private void checkNumParams(ArrayList<RuntimeValue> actArgs,
				int nCorrect, String id, AspSyntax where) {
	         if (actArgs.size() != nCorrect)
	          RuntimeValue.runtimeError("Wrong number of parameters to "+id+"!",where);
    }
}
