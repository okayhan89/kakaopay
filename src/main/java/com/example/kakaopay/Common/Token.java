package com.example.kakaopay.Common;

import lombok.*;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;
import java.util.Random;

//@Embeddable
//@NoArgsConstructor(access = AccessLevel.PUBLIC)
//@AllArgsConstructor
@Getter
@Setter
public class Token {

    private String tokenValue;
    private LocalDateTime expireTime;

    public Token() {

        Random rnd =new Random();

        StringBuffer buf =new StringBuffer();

        for(int i=0;i<3;i++){
            if(rnd.nextBoolean()){
                buf.append((char)((int)(rnd.nextInt(26))+97));
            }else{
                buf.append((rnd.nextInt(10)));
            }
        }

        //token.setTokenValue(buf.substring(0));
        //token.setExpireTime(LocalDateTime.now().plusMinutes(10));
        this.tokenValue = buf.substring(0);
        this.expireTime = LocalDateTime.now().plusMinutes(10);
    }

}
