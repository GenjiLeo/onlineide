package edu.tum.ase.teststarter;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class SuperMathTest {
// JAacoco used for code coverage
    @Test
    public void testFindFactors() {
        // given /
        /// Integer is bad because we're using a Long value in the function
        // at max_int this will fail
        ArrayList<Integer> expectedResult = new ArrayList<Integer>();
        expectedResult.add(1);
        expectedResult.add(2);
        expectedResult.add(4);
        expectedResult.add(5);
        expectedResult.add(10);
        expectedResult.add(20);

        // when / act
        ArrayList<Integer> actualResult = SuperMath.findFactors(20);

        // then / assert
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testFullAdder(){
        // given // when //then
        assertArrayEquals(SuperMath.fullAdder(false,false,false), new Boolean[]{false,false});
        assertArrayEquals(SuperMath.fullAdder(false,false,true), new Boolean[]{true,false});
        assertArrayEquals(SuperMath.fullAdder(false,true ,false), new Boolean[]{true,false});
        assertArrayEquals(SuperMath.fullAdder(false,true,true), new Boolean[]{false,true});
        assertArrayEquals(SuperMath.fullAdder(true,false,false), new Boolean[]{true,false});
        assertArrayEquals(SuperMath.fullAdder(true,false,true), new Boolean[]{false,true});
        assertArrayEquals(SuperMath.fullAdder(true,true,false), new Boolean[]{false,true});
        assertArrayEquals(SuperMath.fullAdder(true,true,true), new Boolean[]{true,true});
    }
        // Cannot be test completely because of the Input domain!

    @Test
    public void testBinaryAddition(){
        //given

        //when

        //then
    }
    @Test
    public void testBinaryAddition_throwsArrayLengthMismatchException(){
        //given
        Boolean[] a = new Boolean[]{false,false,false};
        Boolean[] b = new Boolean[]{false,false};
        //when
        try{
            Boolean[] res = SuperMath.BinaryAddition(a,b);
            fail("Exception expected");
        }catch( Exception e){
            assertThat(e,instanceOf(SuperMath.ArrayLengthsMismatchException.class));
        }

        //then
    }

    @Test
    public void testSomeCaller_handlesException(){
        //given
        Boolean[] a = new Boolean[]{false,false,false};
        Boolean[] b = new Boolean[]{false,false};
        Boolean[] expectedResult = new Boolean[]{false,false,false};

        //when
        //capture output
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        try{
            TestStarterApp.someCaller(a,b);
            // then
            assertEquals(Arrays.toString(expectedResult), out.toString());
        }catch(Exception e){
            // if exception
            fail();
        }
    }
}
