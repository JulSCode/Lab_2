import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Mathematical formulas calculation class
 */
public class Formula {
    /**
     * Mathematical formula in the form of a string
     */
    private String strFormula;
    /**
     * The value of a mathematical formula (or the type of error) in the form of a string
     */
    private String result;
    /**
     * Index of the current character
     */
    private int index;
    /**
     * Current symbol
     */
    private char ch;
    /**
     * Dictionary of values of variables in the form Map
     */
    private Map variables;
    /**
     * Constructor with the parameter
     */
    public Formula(String str)
    {
        this.strFormula = str;
        this.index =-1;
        variables = new HashMap<>();
    }
    /**
     * Default Constructor
     */
    public Formula()
    {
        this.strFormula = "";
        this.index =-1;
        variables = new HashMap<>();
    }
    /**
     * The method shifts the index of the current character to the right by 1 and changes the current character
     */
    private void currentCh()
    {
        this.ch = ++this.index < this.strFormula.length() ? this.strFormula.charAt(this.index) : '@';
    }
    /**
     * The method shifts the index of the current character to the left by 1 and changes the current character
     */
    private void previousCh()
    {
        this.ch = --this.index >= 0 ? this.strFormula.charAt(this.index) : '@';
    }

    /**
     * The method adds (modifies) the value of the variable
     * @param key name of the variable to be added (modified)
     * @param value value of the variable to be added (modified)
     */
    public void setVariable(char key, double value)
    {
        variables.put(key,value);
    }
    /**
     * The method set new mathematical formula in class
     * @param strFormula new mathematical formula
     */
    public void setStrFormula(String strFormula) {
        this.strFormula = strFormula;
    }
    /**
     * The method set new Dictionary of Values of variables in class
     * @param variables new Dictionary of Values of variables
     */
    public void setVariables(Map variables) {
        this.variables = variables;
    }
    /**
     * The method return Dictionary of Values of variables stored in a class
     * @return Dictionary of Values of variables stored in a class
     */
    public Map getVariables() {
        return variables;
    }
    /**
     * The method return mathematical expression stored in a class
     * @return mathematical expression stored in a class
     */
    public String getStrFormula() {
        return strFormula;
    }
    /**
     * The method calculates the value of a mathematical formula
     * written in the class after clearing the dictionary of variables
     * @return the calculated formula (8 decimal places) or the type of the first error that occurred during the calculation
     */
    public String calculateFormula()
    {
        return calculateFormula(null, true);
    }
    /**
     * The method calculates the value of the mathematical formula passed to the function
     * @param st mathematical formula to be calculated
     * @param clearMap is true if you need to clear the dictionary of variables already in the class
     * @return the calculated formula (8 decimal places) or the type of the first error that occurred during the calculation
     */
    public String calculateFormula(String st, boolean clearMap)
    {
        if (st!=null)
            strFormula = st;
        if (clearMap)
            variables.clear();
        if (strFormula != null && !strFormula.equals(""))
        {
            if(checkFormula(null))
            {
                result = "";
                index = -1;
                double meanExpression;
                currentCh();
                meanExpression = secondPriority();
                if (result.equals(""))
                {
                    result = String.format("%.8f", meanExpression);
                    result = result.replaceAll(",", ".");
                    double res = Double.parseDouble(result);
                    result = Double.toString(res);
                }
            }
            else
                result = "Syntax error in the formula!";
        }
        else
            result = "The formula is not set!";
        return this.result;
    }
    /**
     * The method checks (and places in the class) the mathematical formula passed to the function for syntax errors
     * @param str mathematical formula to check
     * @return is true if the mathematical formula being tested does not contain syntax errors
     */
    public boolean checkFormula(String str)
    {
        if (str!=null)
            strFormula = str;
        boolean result = true;
        int numberPoint = 0;
        int numberStaples = 0;
        currentCh();
        while (ch!='@' && result)
        {
            if (ch =='.')
            {
                numberPoint++;
                currentCh();
                if (ch >= '9' || ch <= '0' || numberPoint>1)
                    result = false;
                else
                    previousCh();
            }
            else if (ch >= 'a' && ch <= 'z')
            {
                numberPoint = 0;
                int startInd = this.index;
                while (ch >= 'a' && ch <= 'z' && this.index < startInd + 4)
                    currentCh();
                String function = this.strFormula.substring(startInd, this.index);
                if (function.equals("sqrt"))
                {
                    if (!(ch <= '9' && ch >= '0') && !(ch <= 'z' && ch >= 'a') && ch!='(' && ch!='-')
                        result = false;
                    else
                        previousCh();
                }
                else
                {
                    if (function.length() == 4)
                        previousCh();
                    function = this.strFormula.substring(startInd, this.index);
                    if (function.equals("sin") || function.equals("cos") || function.equals("tan") || function.equals("abs"))
                    {
                        if (!(ch <= '9' && ch >= '0') && !(ch <= 'z' && ch >= 'a') && ch!='(' && ch!='-')
                            result = false;
                        else
                            previousCh();
                    }
                    else
                    {
                        this.index = startInd;
                        ch = this.strFormula.charAt(this.index);
                        currentCh();
                        if (ch == '.')
                            result = false;
                        else
                            previousCh();
                    }
                }
            }
            else if (ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^' || ch == '(')
            {
                numberPoint=0;
                if (ch == '(')
                    if (numberStaples>=0)
                        numberStaples++;
                    else
                        result = false;
                currentCh();
                if (!(ch <= '9' && ch >= '0') && !(ch <= 'z' && ch >= 'a') && ch!='(' && ch!='-')
                    result = false;
                else
                    previousCh();
            }
            else if (ch == ')')
            {
                numberPoint=0;
                if (numberStaples>0)
                {
                    numberStaples--;
                    currentCh();
                    if (ch == '.')
                        result = false;
                    else
                        previousCh();
                }
                else
                    result = false;
            }
            else if (ch < '0' || ch > '9')
                result = false;
            currentCh();
        }
        if (numberStaples!=0)
            result = false;
        return result;
    }
    /**
     * The method checks the mathematical formula written in the class for syntax errors
     * @return is true if the mathematical formula being tested does not contain syntax errors
     */
    public boolean checkFormula()
    {
        return checkFormula(null);
    }
    /**
     * The method performs recursive descent on the formula (while maintaining the priority of operations)
     * by calculating the product and quotient of already calculated formulas
     * @return calculated value
     */
    private double firstPriority()
    {
        double result = calculate();
        for (;;)
        {
            if (ch == '*' && this.result.equals(""))
            {
                currentCh();
                result *= calculate();
            }
            else
            if (ch == '/' && this.result.equals(""))
            {
                currentCh();
                double divider = calculate();
                if (divider!=0)
                    result /= divider;
                else if (this.result.equals(""))
                    this.result = "Error in calculation (division by zero)!";
            }
            else
                return result;
        }
    }
    /**
     * The method performs recursive descent on the formula (while maintaining the priority of operations)
     * by calculating the sum and difference of already calculated formulas
     * @return calculated value
     */
    private double secondPriority()
    {
        double result = firstPriority();
        for (;;)
        {
            if (ch == '+' && this.result.equals(""))
            {
                currentCh();
                result += firstPriority();
            }
            else
            if (ch == '-' && this.result.equals(""))
            {
                currentCh();
                result -= firstPriority();
            }
            else
                return result;
        }
    }
    /**
     * The method calculates the value of the expression starting from the current index
     * @return calculated value (if it is impossible to calculate, it will write the error type in the result and return 0.0)
     */
    private double calculate()
    {
        if (ch == '+')
        {
            currentCh();
            return calculate();
        }
        if (ch == '-')
        {
            currentCh();
            return -calculate();
        }
        Double result = null;
        if (ch == '(')
        {
            currentCh();
            result = secondPriority();
            currentCh();
        }
        else
        if ((ch >= '0' && ch <= '9'))
        {
            int startInd = this.index;
            int numberpoint = 0;
            while ((ch >= '0' && ch <= '9') || ch =='.')
            {
                if (ch =='.')
                    numberpoint++;
                currentCh();
            }
            if (numberpoint<=1)
                result = Double.parseDouble(this.strFormula.substring(startInd, this.index));
            else
            {
                if (this.result.equals(""))
                    this.result = "Error in calculation (incorrect number format)!";
                result = 0.0;
            }
        }
        else
        if (ch >= 'a' && ch <= 'z')
        {
            int startInd = this.index;
            while (ch >= 'a' && ch <= 'z' && this.index < startInd + 4)
                currentCh();
            String function = this.strFormula.substring(startInd, this.index);
            if (function.equals("sqrt"))
            {
                double sq = calculate();
                if (sq>=0)
                    result = Math.sqrt(sq);
                else
                {
                    if (this.result.equals(""))
                        this.result = "Error in calculation (the value in sqrt is less than 0)!";
                    result = 0.0;
                }
            }
            else
            {
                if (function.length() == 4)
                    previousCh();
                function = this.strFormula.substring(startInd, this.index);

                switch (function)
                {
                    case "sin":
                        result = Math.sin(calculate());
                        break;
                    case "cos":
                        result = Math.cos(calculate());
                        break;
                    case "tan":
                        result = Math.tan(calculate());
                        break;
                    case "abs":
                        result = Math.abs(calculate());
                        break;
                    default:
                        this.index = startInd;
                        ch = this.strFormula.charAt(this.index);
                        double currentVariable;
                        if (variables.containsKey(ch))
                        {
                            currentVariable = (double) variables.get(ch);
                        }
                        else
                        {
                            Scanner in = new Scanner(System.in);
                            System.out.print("Value " + ch + " -> ");
                            currentVariable = in.nextDouble();
                            setVariable(ch, currentVariable);
                        }
                        result = currentVariable;
                        currentCh();
                        break;
                }
            }
        }
        if (ch >= '0' && ch <= '9' || ch >= 'a' && ch <= 'z' || ch == '(')// || ch == '.' || ch == ',')
            result *= calculate();
        if (ch =='^')
        {
            currentCh();
            double degree = calculate();
            if (result<0 && degree <1)
            {
                if (this.result.equals(""))
                    this.result = "Error in calculation (it is impossible to raise to a degree)!";
                result = 0.0;
            }
            else
                result = Math.pow(result,degree);
        }
        return result;
    }
}