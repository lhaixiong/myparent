package com.tx;

public interface BookShopDao {
    public int findBookPriceByBookId(int bookId);
    public void updateBookStock(int bookId);
    public void updateUserAccount(int userId,int price);
}
