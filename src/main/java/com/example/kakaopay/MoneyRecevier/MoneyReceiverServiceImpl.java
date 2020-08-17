package com.example.kakaopay.MoneyRecevier;

import com.example.kakaopay.MoneyPush.MoneyPush;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service("moneyReceiverService")
public class MoneyReceiverServiceImpl implements MoneyReceiverService {

    private final MoneyReceiverReposity moneyReceiverReposity;
    private static MoneyPush chkRoom = null;

    @Autowired
    public MoneyReceiverServiceImpl(MoneyReceiverReposity moneyReceiverReposity) {
        this.moneyReceiverReposity = moneyReceiverReposity;
    }


    @Override
    public ResponseEntity update(MoneyPush moneyPush, Integer userId) throws Exception {

        ResponseEntity validation = ValidMoney(moneyPush, userId);

        if(validation.getStatusCode().value()!=200){
            return validation;
        }

        if(chkRoom.getToken().getTokenValue().equals(moneyPush.getToken().getTokenValue()) ){
            if(chkRoom.getPushMoney() - chkRoom.getPerMoney() > 0 && chkRoom.getPushDivideCount() > 0 ){

                chkRoom.setPushMoney(chkRoom.getPushMoney() - chkRoom.getPerMoney());
                chkRoom.setPushDivideCount(chkRoom.getPushDivideCount()-1);
                chkRoom = moneyReceiverReposity.save(chkRoom);

//                MoneyReceiver moneyReceiver = new MoneyReceiver();
//                moneyReceiver.setReceiveUserId(userId);
//                moneyReceiver.setRoomId(chkRoom.getRoomId());
//                moneyReceiver.setToken(chkRoom.getToken());
                try {
                    moneyReceiverReposity.saveMoneyReciver(chkRoom.getRoomId(), userId, chkRoom.getToken().getTokenValue());
                }catch (Exception e ){
                    return new ResponseEntity<>("돈받는인력추가시에러", HttpStatus.BAD_REQUEST);
                }

            }
            else{
                return new ResponseEntity<>("잔액이 없습니다.", HttpStatus.BAD_REQUEST);
            }

        }
        else {
            return new ResponseEntity<>("토큰이 다릅니다.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("성공",HttpStatus.OK);
    }



    public ResponseEntity ValidMoney(MoneyPush moneyPush, Integer userId){
        String roomId = moneyPush.getRoomId();

        if (moneyPush.getToken().getTokenValue().length() != 3) {
            return new ResponseEntity<>("토큰 자리수는 3자리", HttpStatus.BAD_REQUEST);
        }
        try {
            chkRoom = moneyReceiverReposity.findByTokenRoom(roomId, moneyPush.getToken().getTokenValue()).get();
        }catch (Exception e){
            return new ResponseEntity<>("유효한 토큰값이 아닙니다.", HttpStatus.BAD_REQUEST);
        }
        if( chkRoom.getToken().getExpireTime().isBefore(LocalDateTime.now()) ){
            return new ResponseEntity<>("토큰 시간 만료되었습니다.", HttpStatus.BAD_REQUEST);
        }
        if (chkRoom.getPushUserId() == userId  ){
            return new ResponseEntity<>("뿌린 사람은 돈을 받을수 없습니다.", HttpStatus.BAD_REQUEST);
        }

        int receiverChk = 0;
        try {
            System.out.println("ngin : "+moneyPush.getToken().getTokenValue());
            System.out.println("aefni : "+chkRoom.getToken().getTokenValue());
            receiverChk = moneyReceiverReposity.findByReceiverId(userId, moneyPush.getToken().getTokenValue());
            System.out.println("receiverChk : "+receiverChk);
        }catch (Exception e){
            receiverChk = 0;

            System.out.println("receiverChk e: "+e);
        }

        if(receiverChk != 0){
            return new ResponseEntity<>("한번 가지고 간 사람입니다.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("valid ok",HttpStatus.OK);
    }

}
