package com.example.kakaopay.MoneyPush;

import com.example.kakaopay.Common.Token;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Setter
@Getter
@Entity
public class MoneyPush {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int pushUserId;
    private String roomId;
    private int pushMoney;
    private int pushDivideCount;
    private int perMoney;
    private int originPushMoney;
    private LocalDateTime insertTime;
    @Embedded
    private Token token;

    public MoneyPush(long id, int pushUserId, String roomId, int pushMoney, int originPushMoney, int pushDivideCount, LocalDateTime insertTime, Token token) {
        this.id = id;
        this.pushUserId = pushUserId;
        this.roomId = roomId;
        this.pushMoney = pushMoney;
        this.pushDivideCount = pushDivideCount;
        this.insertTime = insertTime;
        this.originPushMoney = originPushMoney;
        this.token = token;
    }
}
