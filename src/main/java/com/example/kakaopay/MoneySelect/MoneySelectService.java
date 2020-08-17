package com.example.kakaopay.MoneySelect;

import com.example.kakaopay.Common.Token;
import com.example.kakaopay.MoneyPush.MoneyPush;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;

public interface MoneySelectService {
    ResponseEntity select(MoneyPush moneyPush, Integer userId) throws Exception;
}
