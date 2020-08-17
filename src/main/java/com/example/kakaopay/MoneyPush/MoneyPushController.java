package com.example.kakaopay.MoneyPush;

import com.example.kakaopay.Common.Token;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

@RestController
@RequestMapping("/push")
@Api(value = "뿌리기Api", description = "뿌리기api")
public class MoneyPushController {

    private final MoneyPushService moneyPushService;

    @Autowired
    public MoneyPushController(MoneyPushService moneyPushService) {
        this.moneyPushService = moneyPushService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value="뿌리기api", notes="헤더값 : X-USER-ID 는 뿌리고자하는 사람id 숫자만됨. X-ROOM-ID 는 roomid, moneyPush는 pushMoney 값과 pushDivideCount 값만 입력부탁드릴게요.")
    public String pushMoney(
            @RequestHeader(value="X-USER-ID") Integer userId,
            @RequestHeader(value="X-ROOM-ID") String roomId,
            //@RequestHeader(value="test") Integer test,
            @RequestBody MoneyPush moneyPush
            //@RequestParam Integer pushMoney,
            //@RequestParam Integer pushDivideCount
    ) throws Exception {
        moneyPush.setPushUserId(userId);
        moneyPush.setRoomId(roomId);
        moneyPush.setPerMoney(moneyPush.getPushMoney()/moneyPush.getPushDivideCount());
        moneyPush.setOriginPushMoney(moneyPush.getPushMoney());

        moneyPush.setInsertTime(LocalDateTime.now());
        Token token = new Token();

        moneyPush.setToken(token);
        moneyPushService.add(moneyPush);

        JSONObject res = new JSONObject();
        res.put("tokenValue",token.getTokenValue());
        return res.toString();
    }
}
