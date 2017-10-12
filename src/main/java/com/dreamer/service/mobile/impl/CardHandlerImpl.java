package com.dreamer.service.mobile.impl;

import com.dreamer.domain.user.Card;
import com.dreamer.repository.mobile.CardDao;
import com.dreamer.service.mobile.CardHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by huangfei on 02/07/2017.
 */
@Service
public class CardHandlerImpl extends BaseHandlerImpl<Card> implements CardHandler {


    private CardDao cardDao;

    public CardDao getCardDao() {
        return cardDao;
    }

    @Autowired
    public void setCardDao(CardDao cardDao) {
        setBaseDao(cardDao);
        this.cardDao = cardDao;
    }
}
