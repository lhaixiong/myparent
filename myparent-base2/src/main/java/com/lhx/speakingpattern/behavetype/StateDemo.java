package com.lhx.speakingpattern.behavetype;

/**
 * 无尽加班何时休----状态模式
 */
public class StateDemo {
    public static void main(String[] args) {
        Task task=new Task(new MarchState());
        while (task.getTaskTime()<10){
            task.request();
        }
    }
    /**
     * 状态抽象类
     */
    static abstract class State{
        abstract void handle(Task task);
    }

    /**
     * 行军状态
     */
    static class MarchState extends State{
        @Override
        void handle(Task task) {
            System.out.println(String.format("时间[%s]:行军状态，正在行军....",task.getTaskTime()));
            task.addTaskTime(1);
            if (task.getTaskTime()>3) {
                task.setNextState(new AttackState());
            }
        }
    }
    /**
     * 攻击状态
     */
    static class AttackState extends State{
        @Override
        void handle(Task task) {
            System.out.println(String.format("时间[%s]:攻击状态，正在攻击....",task.getTaskTime()));
            task.addTaskTime(1);
            if(task.getTaskTime()>5){
                task.setNextState(new BackState());
            }
        }
    }
    /**
     * 返回状态
     */
    static class BackState extends State{
        @Override
        void handle(Task task) {
            System.out.println(String.format("时间[%s]:返回状态，正在返回....",task.getTaskTime()));
            task.addTaskTime(1);
        }
    }

    /**
     * 任务上下文，持有状态引用，若果任务属性（time）发生变化，,相应状态做处理
     */
    static class Task{
        private State current;
        private int taskTime;
        public void request(){
            current.handle(this);
        }

        public void setNextState(State next) {
            this.current = next;
        }

        public int getTaskTime() {
            return taskTime;
        }

        public void addTaskTime(int taskTime) {
            this.taskTime += taskTime;
        }

        public Task(State initState) {
            this.current = initState;
        }
    }
}
