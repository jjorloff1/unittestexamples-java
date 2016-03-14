package com.example.unittest;

/**
 * AdderImpl is a class that provides simple addition functionality.  Of course, it only support adding to
 * results between 0 and 100, but who would need to add anything larger?
 */
public class AdderImpl implements Adder {
    private int augend;
    private int addend;

    public AdderImpl(int augend, int addend) {
        this.augend = augend;
        this.addend = addend;
    }

    /**
     * Add two numbers.
     *
     * If the result is negative throw an UnderLimitResultException.  If it is greater than 100
     * throw an OverLimitResultException.
     *
     * @return the sum
     */
    public int add() {
        int result = augend + addend;

        if (result < 0) {
            throw new UnderLimitResultException();
        }
        if (result > 100) {
            throw new OverLimitResultException();
        }

        return result;
    }
}
