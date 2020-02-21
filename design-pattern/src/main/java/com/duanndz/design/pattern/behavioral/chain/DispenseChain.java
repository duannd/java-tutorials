package com.duanndz.design.pattern.behavioral.chain;

public interface DispenseChain {

    void setNextChain(DispenseChain nextChain);

    void dispense(Currency currency);

}
