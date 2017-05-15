package com.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("cashierService")
public class CashierServiceImp implements CashierService {
    @Autowired
    @Qualifier("bookShopService")
    private BookShopService bookShopService;
    @Override
    public void batchPurchase(int userId, int[] bookids) {
        for (int id:bookids){
            bookShopService.purchase(userId,id);
        }
    }
}
