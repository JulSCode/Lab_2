import org.junit.jupiter.api.BeforeEach;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FormulaTest {
    /**
     * value of class
     */
    Formula fr;
    /**
     * template for tests
     */
    @BeforeEach
    void prepareData() {
        fr = new Formula();
        fr.setVariable('a',-1.1);
        fr.setVariable('b',-2.2);
        fr.setVariable('c',3.3);
    }

    /**
     * Unit tests for methods of the Formula class
     */
    @org.junit.jupiter.api.Test
    void setVariableTest() {
        fr.setVariable('d',4.4);
        assertEquals(4.4,fr.getVariables().get('d'));
    }

    @org.junit.jupiter.api.Test
    void setStrFormulaTest() {
        fr.setStrFormula("a+b");
        assertEquals("a+b",fr.getStrFormula());
    }

    @org.junit.jupiter.api.Test
    void setVariablesTest() {
        Map<String, Double> tmp = new HashMap<>();
        tmp.put("x", 6.0);
        fr.setVariables(tmp);
        assertEquals(6.0,fr.getVariables().get("x"));
    }

    @org.junit.jupiter.api.Test
    void getVariablesTest() {
        Map<String, Double> tmp = new HashMap<>();
        tmp.put("x", 6.0);
        fr.setVariables(tmp);
        assertEquals(6.0,fr.getVariables().get("x"));
    }

    @org.junit.jupiter.api.Test
    void getStrFormulaTest() {
        fr.setStrFormula("a+b");
        assertEquals("a+b",fr.getStrFormula());
    }

    @org.junit.jupiter.api.Test
    void calculateFormulaTest1() {
        assertEquals("-1.43", fr.calculateFormula("a*c-b",false));
    }

    @org.junit.jupiter.api.Test
    void calculateFormulaTest2() {
        assertEquals("Error in calculation (the value in sqrt is less than 0)!",fr.calculateFormula("sqrta",false));
    }

    @org.junit.jupiter.api.Test
    void calculateFormulaTest3() {
        assertEquals("4.15199936", fr.calculateFormula("sin(sqrt(abs-3.5+a)-1)+a3a", false));
    }

    @org.junit.jupiter.api.Test
    void calculateFormulaTest4() {
        assertEquals("Error in calculation (division by zero)!", fr.calculateFormula("a/0", false));
    }

    @org.junit.jupiter.api.Test
    void calculateFormulaTest5() {
        assertEquals("Syntax error in the formula!", fr.calculateFormula("2+2)))", false));
    }
    @org.junit.jupiter.api.Test
    void checkFormulaTest1() {
        assertEquals(true,fr.checkFormula("a+c*b"));
    }

    @org.junit.jupiter.api.Test
    void checkFormulaTest2() {
        assertEquals(false,fr.checkFormula("a+*b"));
    }

    @org.junit.jupiter.api.Test
    void checkFormulaTest3() {
        assertEquals(true,fr.checkFormula("a+(c*b)"));
    }

    @org.junit.jupiter.api.Test
    void checkFormulaTest4() {
        assertEquals(false,fr.checkFormula("a+((c*b)"));
    }
}