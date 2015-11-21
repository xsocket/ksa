package com.ksa.model.finance;

import com.ksa.model.bd.CurrencyRate;

public class AccountCurrencyRate extends CurrencyRate {

    private static final long serialVersionUID = 7598185185140077626L;

    /** 所属结算单 */
    protected Account account = new Account();

    public Account getAccount() {
        return account;
    }

    public void setAccount( Account account ) {
        this.account = account;
    }
}
