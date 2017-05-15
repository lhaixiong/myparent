package com.lhxbase.enumeration;

public enum  Season{
    SPRING("春天","春分又绿江南岸"),
    SUMMER("夏天","映日荷花别样红"),
    AUTUMN("秋天","秋水共长天一色"),
    WINTER("冬天","窗含西岭千秋雪");

    private final String seasonName;
    private final String seasonDesription;
    private Season(String seasonName,String seasonDesription){
        this.seasonName=seasonName;
        this.seasonDesription=seasonDesription;
    }

    @Override
    public String toString() {
        return "Season{" +
                "seasonName='" + seasonName + '\'' +
                ", seasonDesription='" + seasonDesription + '\'' +
                '}';
    }
}
