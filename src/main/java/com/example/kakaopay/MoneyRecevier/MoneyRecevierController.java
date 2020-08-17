package com.example.kakaopay.MoneyRecevier;

import com.example.kakaopay.Common.Token;
import com.example.kakaopay.MoneyPush.MoneyPush;
import com.example.kakaopay.MoneyPush.MoneyPushService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/recevier")
@Api(value = "받기api", description = "받기api")
public class MoneyRecevierController {

    private final MoneyReceiverService moneyReceiverService;

    @Autowired
    public MoneyRecevierController(MoneyReceiverService moneyReceiverService) {
        this.moneyReceiverService = moneyReceiverService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value="받기api", notes="헤더값 : X-USER-ID 는 뿌리고자하는 사람id 숫자만됨. X-ROOM-ID 는 roomid, token 값에는 tokenValue만 입력부탁드릴게요.")
    public ResponseEntity recevieMoney(
            @RequestHeader(value="X-USER-ID") Integer userId,
            @RequestHeader(value="X-ROOM-ID") String roomId,
            @RequestBody Token token
    ) throws Exception {
        MoneyPush moneyPush = new MoneyPush();
        moneyPush.setRoomId(roomId);
        moneyPush.setToken(token);

        ResponseEntity res = moneyReceiverService.update(moneyPush, userId);

        return res;
    }

}
