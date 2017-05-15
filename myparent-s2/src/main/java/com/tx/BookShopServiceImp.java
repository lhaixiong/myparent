package com.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("bookShopService")
public class BookShopServiceImp implements BookShopService {
    @Autowired
    @Qualifier("bookShopDao")
    private BookShopDao dao;
//    @Transactional
    @Override
    public void purchase(int userId, int bookId) {
        int price=dao.findBookPriceByBookId(bookId);
        dao.updateBookStock(bookId);
        dao.updateUserAccount(userId,price);
    }
}
