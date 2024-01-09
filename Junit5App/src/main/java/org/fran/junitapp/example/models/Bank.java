package org.fran.junitapp.example.models;

import java.math.BigDecimal;
import java.util.List;

public class Bank {

    private List<Count> counts;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Count> getCounts() {
        return counts;
    }

    public void setCounts(List<Count> counts) {
        this.counts = counts;
    }

    public void addCount(Count count) {
        counts.add(count);
    }

    public void transfer(Count origin, Count destination, BigDecimal amount){
        origin.debit(amount);
        destination.credit(amount);
    }


}
