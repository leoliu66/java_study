package com.example.demo.easygongchang;

/**
 * @ClassName HumanFactory
 * @Description 工厂
 * @Author liulu_leo
 * @Date 2021/2/22
 * @Version 1.0
 */
public class HumanFactory {

    public <T extends Human> T createHuman(Class<T> c) {
        Human human = null;
        try {
            System.out.println(c.getName());
            human = (Human) Class.forName(c.getName()).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {

        }
        return (T) human;
    }
}
