package com.lhxa.service.comm.imp;

import com.lhxa.service.comm.BookShopService;
import com.lhxa.service.comm.CashierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("cashierService")
public class CashierServiceImp implements CashierService {
    @Autowired
    private BookShopService bookShopService;
    @Override
    public void batchPurchase(int accountId, int[] bookIds) {
        for (int id:bookIds){
            bookShopService.purchase(accountId,id);
        }
    }
}
