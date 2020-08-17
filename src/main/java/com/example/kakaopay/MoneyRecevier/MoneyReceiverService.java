package com.example.kakaopay.MoneyRecevier;

import com.example.kakaopay.MoneyPush.MoneyPush;
import org.springframework.http.ResponseEntity;

public interface MoneyReceiverService {
    ResponseEntity update(MoneyPush moneyPush, Integer userId) throws Exception;
}
