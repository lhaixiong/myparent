package com.lhxs3.controller;

public enum Branch {
    GD("GD", "广东", "0"),
    GZ("GZ", "广州", "200"),
    SZ("SZ", "深圳", "755"),
    DG("DG", "东莞", "769"),
    FS("FS", "佛山", "757"),
    ST("ST", "汕头", "754"),
    JM("JM", "江门", "750"),
    SG("SG", "韶关", "751"),
    QY("QY", "清远", "763"),
    ZQ("ZQ", "肇庆", "758"),
    YF("YF", "云浮", "766"),
    MZ("MZ", "梅州", "753"),
    HY("HY", "河源", "762"),
    HZ("HZ", "惠州", "752"),
    ZH("ZH", "珠海", "756"),
    ZS("ZS", "中山", "760"),
    JY("JY", "揭阳", "663"),
    CZ("CZ", "潮州", "768"),
    SW("SW", "汕尾", "660"),
    MM("MM", "茂名", "668"),
    YJ("YJ", "阳江", "662"),
    ZJ("ZJ", "湛江", "759");

    private String code;
    private String describe;
    private String areaCode;

    private Branch(String var3, String var4, String var5) {
        this.code = var3;
        this.describe = var4;
        this.areaCode = var5;
    }

    public String getCode() {
        return this.code;
    }

    public String getDescribe() {
        return this.describe;
    }

    public String getAreaCode() {
        return this.areaCode;
    }


    public Branch[] getValues() {
        return values();
    }
}
