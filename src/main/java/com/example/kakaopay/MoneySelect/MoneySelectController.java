package com.example.kakaopay.MoneySelect;

import com.example.kakaopay.Common.Token;
import com.example.kakaopay.MoneyPush.MoneyPush;
import com.example.kakaopay.MoneyRecevier.MoneyReceiverService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/select")
@Api(value = "조회api", description = "조회api")
public class MoneySelectController {


    private final MoneySelectService moneySelectService;

    @Autowired
    public MoneySelectController(MoneySelectService moneySelectService) {
        this.moneySelectService = moneySelectService;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ApiOperation(value="조회api", notes="헤더값 : X-USER-ID 는 뿌리고자하는 사람id 숫자만됨. X-ROOM-ID 는 roomid, token 값에는 tokenValue만 입력부탁드릴게요.")
    public ResponseEntity selectMoney(
            @RequestHeader(value="X-USER-ID") Integer userId,
            @RequestHeader(value="X-ROOM-ID") String roomId,
            @RequestBody Token token
    ) throws Exception {
        MoneyPush moneyPush = new MoneyPush();
        moneyPush.setRoomId(roomId);
        moneyPush.setToken(token);

        System.out.println("select Money");
        ResponseEntity res = moneySelectService.select(moneyPush, userId);
        return res;
    }

}
