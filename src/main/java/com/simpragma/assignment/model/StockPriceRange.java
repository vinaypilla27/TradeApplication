package com.simpragma.assignment.model;

public class StockPriceRange {

    private final String symbol;
    private final double highest;
    private final double lowest;

    public StockPriceRange(final String symbol, final double highest, final double lowest) {
        this.symbol = symbol;
        this.highest = highest;
        this.lowest = lowest;
    }

    public double getLowest() {
        return lowest;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getHighest() {
        return highest;
    }

}
