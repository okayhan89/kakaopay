package com.example.kakaopay.MoneySelect;

import com.example.kakaopay.MoneyPush.MoneyPush;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Data
@Setter
@Getter
public class MoneySelect {
    private MoneyPush moneyPush;

    @OneToMany(fetch= FetchType.EAGER, cascade = CascadeType.ALL) // (1)
    @JoinColumn(name="room_id")
    private List moneySelect;
}
