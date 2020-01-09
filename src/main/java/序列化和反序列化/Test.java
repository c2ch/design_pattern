package 序列化和反序列化;

import java.io.*;

public class Test {

    //序列化
    private static void SerializePerson() {
        Person person =new Person();
        person.setAge(30);
        person.setName("SerializePerson");
        ObjectOutputStream outputStream = null;
        try {
            outputStream=new ObjectOutputStream(new FileOutputStream("D:/Person.ser"));
            outputStream.writeObject(person);
            System.out.println("序列化成功。");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    //反序列化
    private static Person DeserializePerson() {

        Person person=null;
        ObjectInputStream inputStream=null;
        try {
            inputStream=new ObjectInputStream(new FileInputStream("E:/hello.txt"));
            try {
                person=(Person)inputStream.readObject();
                System.out.println("执行反序列化过程成功。");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return person;
    }

    public static void main(String[] args) {
        SerializePerson();
    }
}
