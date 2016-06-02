package com.ksa.service.finance.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.StringUtils;

import com.ksa.dao.finance.AccountCurrencyRateDao;
import com.ksa.dao.finance.AccountDao;
import com.ksa.model.ModelUtils;
import com.ksa.model.bd.Currency;
import com.ksa.model.bd.CurrencyRate;
import com.ksa.model.finance.Account;
import com.ksa.model.finance.AccountCurrencyRate;
import com.ksa.model.finance.AccountState;
import com.ksa.model.finance.Charge;
import com.ksa.model.logistics.BookingNote;
import com.ksa.service.finance.AccountService;


public class AccountServiceImpl implements AccountService {
    
    private AccountDao accountDao;
    private AccountCurrencyRateDao accountCurrencyRateDao;

    @Override
    public int querySimilarAccountCodeCount( String partnerCode ) throws RuntimeException {
        return accountDao.querySimilarAccountCodeCount( partnerCode );
    }

    @Override
    public Account loadAccountById( String accountId ) throws RuntimeException {
        Account account = accountDao.selectAccountById( accountId );
        if( account == null ) {
            throw new IllegalArgumentException( String.format( "标识为 '%s' 的结算/对账单不存在。", accountId ) );
        }
        return account;
    }
    
    @Override
    public Account removeInvoice( Account account ) throws RuntimeException {
        Account temp = accountDao.selectAccountById( account.getId() );
        if( temp == null ) {
            throw new IllegalArgumentException( String.format( "标识为 '%s' 的结算/对账单不存在。", account.getId() ) );
        }
        
        // 当发票拿去销账的对应结算单已经确认结算完毕后，不能删除
        if( !AccountState.isNone( temp.getState() ) ) {
            throw new IllegalStateException( String.format( "结算单已审核通过，无法删除。", temp.getCode() ) );
        }
        
        // 删除
        int length = accountDao.deleteAccount( temp );
        if( length != 1 ) {
            throw new IllegalStateException( String.format( "删除结算单发生异常，期望删除 1 条数据，实际删除了 %d 条数据。", length ) );
        }
        
        return temp;
    }

    @Override
    public Account saveAccount( Account account, List<AccountCurrencyRate> rates ) throws RuntimeException {
        if( StringUtils.hasText( account.getId() ) ) {
            Account temp = accountDao.selectAccountById( account.getId() );
            if( temp == null ) {
                throw new IllegalArgumentException( String.format( "标识为 '%s' 的结算/对账单不存在。", account.getId() ) );
            }
            if( AccountState.isNone( temp.getState() ) || AccountState.isProcessing( temp.getState() ) ) {
                // 审核前的结算单，还未进入流程前 可以任意修改
                temp.setCreatedDate( account.getCreatedDate() );
                temp.setDeadline( account.getDeadline() );
                temp.setPaymentDate( account.getPaymentDate() );
                temp.setNote( account.getNote() );
                temp.setCode( account.getCode() );
                temp.setTarget( account.getTarget() );
                accountDao.updateAccount( temp );

                // 更新费用清单
                List<Charge> oldCharges = temp.getCharges();
                Set<String> oldChargeSet = generateChargeIdSet( oldCharges );
                List<Charge> newCharges = account.getCharges();
                Set<String> newChargeSet = generateChargeIdSet( newCharges );
                if( newCharges != null && newCharges.size() > 0 ) {
                    List<Charge> needInsertedCharges = new ArrayList<Charge>();
                    for( Charge newCharge : newCharges ) {
                        if( ! oldChargeSet.contains( newCharge.getId() ) ) {
                            // 新增的费用
                            needInsertedCharges.add( newCharge );
                        }
                    }
                    if( needInsertedCharges.size() > 0 ) {
                        accountDao.insertAccountCharges( temp, needInsertedCharges );
                    }
                }
                
                if( oldCharges != null && oldCharges.size() > 0 ) {
                    List<Charge> needDeletedCharges = new ArrayList<Charge>();
                    for( Charge oldCharge : oldCharges ) {
                        if( ! newChargeSet.contains( oldCharge.getId() ) ) {
                            // 删除的费用
                            needDeletedCharges.add( oldCharge );
                        }
                    }
                    if( needDeletedCharges.size() > 0 ) {
                        accountDao.deleteAccountCharges( temp, needDeletedCharges );
                    }
                }
                
                // 更新汇率
                if( rates != null && rates.size() > 0 ) {    // 保存汇率
                    for( AccountCurrencyRate rate : rates ) {
                        rate.setAccount( temp );
                        if( ! StringUtils.hasText( rate.getId() ) ) {
                            rate.setId( ModelUtils.generateRandomId() );
                            accountCurrencyRateDao.insertRate( rate );
                        } else {
                            accountCurrencyRateDao.updateRate( rate );
                        }
                    }
                }
            } else {
                // 已进入审核流程的结算单 只能修改有限的数据
                // TODO 具体可以修改什么 暂时待定
                temp.setDeadline( account.getDeadline() );
                temp.setPaymentDate( account.getPaymentDate() );
                temp.setNote( account.getNote() );
                accountDao.updateAccount( temp );
            }
        } else {
            // 新建            
            account.setId( ModelUtils.generateRandomId() );
            accountDao.insertAccount( account );    // 保存结算单
            List<Charge> charges = account.getCharges();
            if( charges != null && charges.size() > 0 ) {
                accountDao.insertAccountCharges( account, charges );    // 保存结算单中的费用清单
            }
            if( rates != null && rates.size() > 0 ) {    // 保存汇率
                for( AccountCurrencyRate rate : rates ) {
                    rate.setAccount( account );
                    rate.setId( ModelUtils.generateRandomId() );
                    accountCurrencyRateDao.insertRate( rate );
                }
            }
        }
        return accountDao.selectAccountById( account.getId() );
    }
    
    private Set<String> generateChargeIdSet( List<Charge> charges ) {
        Set<String> set = new HashSet<String>();
        if( charges != null && charges.size() > 0 ) {
            for( Charge charge : charges ) {
                set.add( charge.getId() );
            }
        }
        return set;
    }

    @Override
    public Account updateAccountState( Account account ) throws RuntimeException {
        Account temp = accountDao.selectAccountById( account.getId() );
        if( temp == null ) {
            throw new IllegalArgumentException( String.format( "标识为 '%s' 的结算单不存在。", account.getId() ) );
        }
        // FIXME 需要判断状态改变的合理性
        temp.setState( account.getState() );
        accountDao.updateAccountState( account );
        return temp;
    }

    @Override
    public List<BookingNote> loadAccountBookingNotes( Account account ) throws RuntimeException {
        return accountDao.selectBookingNoteByAccountId( account.getId() );
    }

    @Override
    public List<? extends CurrencyRate> loadAccountCurrencyRates( Account account ) throws RuntimeException {
        if( account == null ) {
            return accountCurrencyRateDao.selectLatestRates();
        }
        
        // 获取最新汇率
        List<CurrencyRate> latestRates = accountCurrencyRateDao.selectLatestRates();
        Map<String, CurrencyRate> latestRateMap = generateRateMap( latestRates );

        // 获取系统已有的货币清单，为清单中的货币查询相应的汇率
        List<Currency> currencies = accountCurrencyRateDao.selectAllCurrency();
        if( currencies == null || currencies.size() <= 0 ) {
            return Collections.emptyList();
        }
        
        // 获取结算单对应的汇率
        List<AccountCurrencyRate> rates = accountCurrencyRateDao.selectRatesByAccountId( account.getId() );
        Map<String, CurrencyRate> rateMap = generateRateMap( rates );
        
        // 获取客户对应的汇率
        Map<String, CurrencyRate> partnerRateMap = new HashMap<String, CurrencyRate>();
        if( account.getTarget() != null && StringUtils.hasText( account.getTarget().getId() ) ) {
            List<CurrencyRate> partnerRates = accountCurrencyRateDao.selectRateByPartner( account.getTarget().getId() );
            partnerRateMap = generateRateMap( partnerRates );
        }
        
        List<CurrencyRate> result = new ArrayList<CurrencyRate>( currencies.size() );
        for( Currency c : currencies ) {
            String currencyId = c.getId();
            if( rateMap.containsKey( currencyId ) ) {
                result.add( rateMap.get( currencyId ) );
            } else { 
                AccountCurrencyRate rate = new AccountCurrencyRate();
                rate.setAccount( account );
                rate.setCurrency( c );
                if( partnerRateMap.containsKey( currencyId ) ) {
                    rate.setRate( partnerRateMap.get( currencyId ).getRate() );
                } else if( latestRateMap.containsKey( currencyId ) ) {
                    rate.setRate( latestRateMap.get( currencyId ).getRate() );
                } else {
                    rate.setRate( c.getDefaultRate() );
                }
                result.add( rate );
            }
        }
        return result;
    }
    
    private Map<String, CurrencyRate> generateRateMap( List<? extends CurrencyRate> rates ) {
        Map<String, CurrencyRate> rateMap = new HashMap<String, CurrencyRate>();
        if( rates != null && rates.size() > 0 ) {
            for( CurrencyRate rate : rates ) {
                rateMap.put( rate.getCurrency().getId(), rate );
            }
        }
        return rateMap;
    }
    
    public void setAccountDao( AccountDao accountDao ) {
        this.accountDao = accountDao;
    }

    public void setAccountCurrencyRateDao( AccountCurrencyRateDao accountCurrencyRateDao ) {
        this.accountCurrencyRateDao = accountCurrencyRateDao;
    }



}
