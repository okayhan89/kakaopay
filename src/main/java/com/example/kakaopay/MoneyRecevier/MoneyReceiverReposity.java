package com.example.kakaopay.MoneyRecevier;

import com.example.kakaopay.Common.Token;
import com.example.kakaopay.MoneyPush.MoneyPush;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface MoneyReceiverReposity extends JpaRepository<MoneyPush, Long> {

    Optional<MoneyPush> findById(long id);

    @Query("select rt from MoneyPush as rt where rt.roomId = ?1 and rt.token.tokenValue = ?2")
    Optional<MoneyPush> findByTokenRoom(String roomId, String tokenId);

    @Transactional
    @Modifying
    @Query(value = "insert into MONEY_RECEIVER (ROOM_ID, RECEIVE_USER_ID, TOKEN_VALUE) values (:roomid, :receiveUserId, :tokenValue)" , nativeQuery = true)
    int saveMoneyReciver(@Param("roomid") String roomid, @Param("receiveUserId") int receiveUserId,  @Param("tokenValue") String tokenValue);

    @Query(value = "select mr.RECEIVE_USER_ID from MONEY_RECEIVER mr where mr.RECEIVE_USER_ID = ?1 and mr.TOKEN_VALUE = ?2" , nativeQuery = true)
    int findByReceiverId(@Param("receiveUserId")int receiveUserId, @Param("tokenValue")String tokenValue);
}
