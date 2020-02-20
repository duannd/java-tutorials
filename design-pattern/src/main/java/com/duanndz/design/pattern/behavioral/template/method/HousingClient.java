package com.duanndz.design.pattern.behavioral.template.method;

public class HousingClient {

    public static void main(String[] args) {
        HouseTemplate woodenHouse = new WoodenHouse();
        woodenHouse.buildHouse();
        System.out.println("********************************************");
        HouseTemplate glassHouse = new GlassHouse();
        glassHouse.buildHouse();
    }

}
