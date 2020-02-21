package com.duanndz.design.pattern.behavioral.chain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ATMDispenseChain {

    private DispenseChain chain;

    public ATMDispenseChain() {
        // initialize the chain
        this.chain = new Dollar50Dispenser();
        DispenseChain chain2 = new Dollar20Dispenser();
        DispenseChain chain3 = new Dollar10Dispenser();

        // set the chain of responsibility
        chain.setNextChain(chain2);
        chain2.setNextChain(chain3);
    }

}
