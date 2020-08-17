package com.example.kakaopay.MoneySelect;

import com.example.kakaopay.MoneyPush.MoneyPush;
import com.example.kakaopay.MoneyRecevier.MoneyReceiver;
import com.example.kakaopay.MoneyRecevier.MoneyReceiverReposity;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service("moneySelectService")
public class MoneySelectServiceImpl implements MoneySelectService {


    private final MoneySelectReposity moneySelectReposity;
    private final MoneyReceiverReposity moneyReceiverReposity;
    private static long chkRoomId = 0;

    @Autowired
    public MoneySelectServiceImpl(MoneySelectReposity moneySelectReposity, MoneyReceiverReposity moneyReceiverReposity) {
        this.moneySelectReposity = moneySelectReposity;
        this.moneyReceiverReposity = moneyReceiverReposity;
    }

    @Override
    public ResponseEntity select(MoneyPush moneyPush, Integer userId) throws Exception {

        ResponseEntity validation = ValidMoney(moneyPush, userId);

        if(validation.getStatusCode().value()!=200){
            return validation;
        }

        //돈관련 정보
        Optional<MoneyPush> moneyInfo = moneyReceiverReposity.findById(chkRoomId);

        //돈 받아간 인력정보
        List<MoneyReceiver> moneyReceiverInfo = moneySelectReposity.findByTokenValue(moneyInfo.get().getToken().getTokenValue());

        JSONObject moneySelect = new JSONObject();
        moneySelect.put("moneyPush", moneyInfo.get());
        moneySelect.put("moneyReceiver", moneyReceiverInfo);

        return new ResponseEntity<>(moneySelect.toString(),HttpStatus.OK);
    }

    public ResponseEntity ValidMoney(MoneyPush moneyPush, Integer userId){
        String roomId = moneyPush.getRoomId();
        MoneyPush chkRoom = null;


        if (moneyPush.getToken().getTokenValue().length() != 3) {
            return new ResponseEntity<>("토큰 자리수는 3자리", HttpStatus.BAD_REQUEST);
        }
        try {
            chkRoom = moneySelectReposity.findByTokenRoom(roomId, moneyPush.getToken().getTokenValue()).get();
        }catch (Exception e){
            return new ResponseEntity<>("유효한 토큰값이 아닙니다.", HttpStatus.BAD_REQUEST);
        }
        if( chkRoom.getToken().getExpireTime().minusMinutes(10).plusDays(7).isBefore(LocalDateTime.now()) ){
            return new ResponseEntity<>("토큰 시간 만료되었습니다.", HttpStatus.BAD_REQUEST);
        }
        try {
            chkRoomId = moneySelectReposity.findByReceiverId(userId, moneyPush.getRoomId(), moneyPush.getToken().getTokenValue());
        }catch (Exception e){
            chkRoomId = 0;
            System.out.println("e : " + e);
        }
        if(chkRoomId == 0){
            return new ResponseEntity<>("등록자만 조회가능합니다.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("valid ok",HttpStatus.OK);

    }

}
