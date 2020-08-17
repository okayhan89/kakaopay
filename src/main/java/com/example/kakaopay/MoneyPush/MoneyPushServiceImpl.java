package com.example.kakaopay.MoneyPush;

import com.example.kakaopay.Common.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("moneyPushService")
public class MoneyPushServiceImpl implements MoneyPushService{

    private final MoneyPushReposity moneyPushReposity;

    @Autowired
    public MoneyPushServiceImpl(MoneyPushReposity moneyPushReposity) {
        this.moneyPushReposity = moneyPushReposity;
    }

    @Override
    public MoneyPush add(MoneyPush moneyPush) throws Exception {
        return moneyPushReposity.save(moneyPush);
    }
}
