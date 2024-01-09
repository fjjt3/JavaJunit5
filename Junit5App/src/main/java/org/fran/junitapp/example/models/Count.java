package org.fran.junitapp.example.models;

import org.fran.junitapp.example.exceptions.RedNumbersException;

import java.math.BigDecimal;

public class Count {
    private String person;
    private BigDecimal balance;
    private Bank bank;

    public Count(String person, BigDecimal balance) {
        this.balance = balance;
        this.person = person;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public void debit(BigDecimal amount) {
        BigDecimal newBalance= this.balance.subtract(amount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0){
            throw new RedNumbersException("insufficient money");
        }
        this.balance = newBalance;
    }

    public void credit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Count )){
            return false;
        }

        Count c = (Count) obj;
        if (this.person == null || this.balance == null ){
            return false;
        }

        return this.person.equals(c.getPerson()) && this.balance.equals(c.getBalance());
    }
}
