package com.example.kakaopay.MoneySelect;

import com.example.kakaopay.MoneyPush.MoneyPush;
import com.example.kakaopay.MoneyRecevier.MoneyReceiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface MoneySelectReposity extends JpaRepository<MoneyReceiver, Long> {

    List<MoneyReceiver> findByRoomId(String id);

    @Query(value = "select * from MONEY_RECEIVER as mr where mr.TOKEN_VALUE = ?1 " , nativeQuery = true)
    List<MoneyReceiver> findByTokenValue(String tokenValue);

    @Query("select rt from MoneyPush as rt where rt.roomId = ?1 and rt.token.tokenValue = ?2")
    Optional<MoneyPush> findByTokenRoom(String roomId, String tokenId);

    @Query(value = "select mr.ID from MONEY_PUSH mr where mr.PUSH_USER_ID = ?1 and mr.ROOM_ID = ?2 and mr.TOKEN_VALUE = ?3" , nativeQuery = true)
    long findByReceiverId(@Param("receiveUserId")int receiveUserId, @Param("roomid") String roomid, @Param("tokenValue") String tokenValue );
}
