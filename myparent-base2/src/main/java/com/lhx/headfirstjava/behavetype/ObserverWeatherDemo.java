package com.lhx.headfirstjava.behavetype;

import java.util.ArrayList;
import java.util.List;

public class ObserverWeatherDemo {
    public static void main(String[] args) {
        WeatherData weatherSubJect=new WeatherData();
        Observer currentConditionObserver=new CurrentConditionDisplay();
        Observer forecastObserver=new ForecastDisplay();
        weatherSubJect.registerObserver(currentConditionObserver);
        weatherSubJect.registerObserver(forecastObserver);

        weatherSubJect.setMessureData(23.4f,33.3f,444.4f);
        weatherSubJect.setMessureData(123.4f,133.3f,1444.4f);
        weatherSubJect.setMessureData(223.4f,233.3f,2444.4f);
    }

    interface Subject{
        void registerObserver(Observer o);
        void removeObserver(Observer o);
        void notifyAllObservers();
    }
    interface Observer{
        void update(Subject subject,Object otherData);
    }
    static class WeatherData implements Subject{
        private List<Observer> observerList;
        private float temperature;
        private float humility;
        private float pressure;

        public void setMessureData(float temperature,float humility,float pressure){
            System.out.println("温度数据变化了");
            this.temperature=temperature;
            this.humility=humility;
            this.pressure=pressure;
            notifyAllObservers();
        }

        @Override
        public void registerObserver(Observer o) {
            observerList.add(o);
        }

        @Override
        public void removeObserver(Observer o) {
            observerList.remove(o);
        }

        @Override
        public void notifyAllObservers() {
            observerList.stream()
                    .forEach(x->x.update(this,null));
        }

        public WeatherData(){
            this.observerList=new ArrayList<>();
        }

        public float getTemperature() {
            return temperature;
        }

        public float getHumility() {
            return humility;
        }

        public float getPressure() {
            return pressure;
        }
    }
    interface DisplayElement{
        void display();
    }
    static class CurrentConditionDisplay implements Observer,DisplayElement{
        private float temperature;
        private float humility;
        private float pressure;
        @Override
        public void display() {
            System.out.println("CurrentConditionObserver Current Condition is：");
            System.out.println("temperature："+this.temperature);
            System.out.println("humility："+this.humility);
            System.out.println("pressure："+this.pressure);
        }

        @Override
        public void update(Subject weatherSub, Object otherData) {
            WeatherData weatherData= (WeatherData) weatherSub;
            this.temperature=weatherData.getTemperature();
            this.humility=weatherData.getHumility();
            this.pressure=weatherData.getPressure();
            display();
        }
    }
    static class ForecastDisplay implements Observer,DisplayElement{
        private float temperature;
        private float humility;
        private float pressure;
        @Override
        public void display() {
            System.out.println("ForecastDisplay is：");
            System.out.println("temperature："+this.temperature);
            System.out.println("humility："+this.humility);
            System.out.println("pressure："+this.pressure);
        }

        @Override
        public void update(Subject weatherSub, Object otherData) {
            WeatherData weatherData= (WeatherData) weatherSub;
            this.temperature=weatherData.getTemperature();
            this.humility=weatherData.getHumility();
            this.pressure=weatherData.getPressure();
            display();
        }
    }
}
