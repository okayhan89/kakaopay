package com.example.kakaopay.MoneyRecevier;

import com.example.kakaopay.Common.Token;
import com.example.kakaopay.MoneySelect.MoneySelect;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Setter
@Getter
@Entity
public class MoneyReceiver extends MoneySelect {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    private String roomId;
    private int receiveUserId;
    @Embedded
    private Token token;

    public MoneyReceiver(long id, int receiveUserId, String roomId, Token token) {
        this.id = id;
        this.receiveUserId = receiveUserId;
        this.roomId = roomId;
        this.token = token;
    }
}
