package com.fline.exception;

public class TestException {

    private String printA() throws Exception {
        System.out.println("aaaa");
        throw new Exception("出错了");
    }

    private String printB() throws Exception {
        return printA();
    }

    private void printC() throws Exception {
        try {
            System.out.println(printB());
        } catch (Exception e) {
            throw new Exception("bbb");
        }
    }

    public static void main(String[] args) throws Exception {
        TestException te = new TestException();
        te.printC();
    }
}
