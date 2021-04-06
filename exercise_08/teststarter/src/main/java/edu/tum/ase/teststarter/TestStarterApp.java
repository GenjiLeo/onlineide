package edu.tum.ase.teststarter;

import java.util.Arrays;

import static edu.tum.ase.teststarter.SuperMath.calculationString;

public class TestStarterApp {
    public static void main(String[] args) {
        Boolean[] a = new Boolean[]{false, true, true};
        Boolean[] b = new Boolean[]{false, false, true};
        try {
            Boolean[] res = SuperMath.BinaryAddition(a, b);
            System.out.println(calculationString(a, b, res));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void someCaller(Boolean[] a, Boolean[] b){
        Boolean[] res = null;
        try{
            res = SuperMath.BinaryAddition(a,b);
        }catch(SuperMath.ArrayLengthsMismatchException e){
            res = new Boolean[]{false,false,false};
        }
        System.out.print(Arrays.toString(res));
    }

}
