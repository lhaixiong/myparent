package lhxbase;

import com.lhxbase.pattern.behaviour.observe.House;
import com.lhxbase.pattern.behaviour.observe.HousePriceObserver;
import org.junit.Test;

public class PatternTest {
    @Test
    public void testObserve(){
        House house1=new House(15000f,"万科");
        House house2=new House(10000f,"碧桂园");
        System.out.println(house1);
        System.out.println(house2);

        HousePriceObserver observer1=new HousePriceObserver("观察者a");
        HousePriceObserver observer2=new HousePriceObserver("观察者b");
        HousePriceObserver observer3=new HousePriceObserver("观察者c");

        house1.addObserver(observer1);
        house1.addObserver(observer2);
        house1.addObserver(observer3);

        house2.addObserver(observer2);
        house2.addObserver(observer3);
        house1.setPrice(18000f);
        house2.setPrice(13000f);
    }
}
