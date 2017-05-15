package com.lhxa.service.comm.imp;

import com.lhxa.dao.account.AccountDao;
import com.lhxa.dao.book.BookDao;
import com.lhxa.dao.bookstock.BookStockDao;
import com.lhxa.service.comm.BookShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "bookShopService")
public class BookShopServiceImp implements BookShopService{
    @Autowired
    private BookDao bookDao;
    @Autowired
    private BookStockDao bookStockDao;
    @Autowired
    private AccountDao accountDao;

    @Override
    public void purchase(int accountId, int bookId) {
        int price=bookDao.findBookPrice(bookId);
        bookStockDao.updateBookStock(bookId);
        accountDao.updateAccount(accountId,price);
    }
}
