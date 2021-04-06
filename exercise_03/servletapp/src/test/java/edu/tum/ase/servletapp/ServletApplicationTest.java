package edu.tum.ase.servletapp;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class ServletApplicationTest extends TestCase {
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ServletApplicationTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(ServletApplicationTest.class);
    }

    /**
     * Rigorous Test :-)
     */
    public void testServletApplication() {
        assertTrue(true);
    }
}
