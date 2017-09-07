package com.lhx.speakingpattern.mix;

public class StrategeDemoMix {
    public static void main(String[] args) {
        StrategeManager manager=new StrategeManager("d");
        Stratege stratege = manager.getStratege();
        stratege.algorithm();
    }
    private static class StrategeManager{
        private Stratege stratege;
        public StrategeManager(String type){
            switch (type){//这里switch可以利用反射来解决类型判断的问题
                case "a":{
                    this.stratege=new StrategeA();
                    break;
                }
                case "b":{
                    this.stratege=new StrategeB();
                    break;
                }
                case "c":{
                    this.stratege=new StrategeC();
                    break;
                }
                default:this.stratege=new StrategeA();
            }
        }

        public Stratege getStratege() {
            return stratege;
        }
    }
    private static abstract class Stratege{
        abstract void algorithm();
    }
    private static class StrategeA extends Stratege{

        @Override
        void algorithm() {
            System.out.println("a策略实现算法族");
        }
    }
    private static class StrategeB extends Stratege{

        @Override
        void algorithm() {
            System.out.println("b策略实现算法族");
        }
    }
    private static class StrategeC extends Stratege{

        @Override
        void algorithm() {
            System.out.println("c策略实现算法族");
        }
    }
}

