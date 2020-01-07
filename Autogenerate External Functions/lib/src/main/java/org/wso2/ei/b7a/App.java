package org.wso2.ei.b7a;
import java.lang.reflect.*;
import java.io.IOException;
import java.io.FileWriter;

public class App {
    static String classFullName;

    public static void main(String...args){
        classFullName=giveFullClass();
        String classSimpleName=giveSimpleClass();
        writeMethod(classSimpleName,classFullName);
    }

    public static String giveFullClass() {

        MyClass obj = new MyClass();
        Class cls = obj.getClass();
        classFullName=cls.getName();
        return classFullName;
    }

    public static String giveSimpleClass() {

        int firstChar= classFullName.lastIndexOf('.') + 1;
        String classSimpleName = classFullName.substring(firstChar);
        return classSimpleName;
    }

    public static String writeMethod(String classSimpleName,String classFullName){

        try {
            FileWriter wObj = new FileWriter("../Bal_Project/src/module_bal/external_functions.bal");
            wObj.write("import ballerinax/java; \n");
            MyClass obj = new MyClass();
            Class cls = obj.getClass();
            Method[] methods = cls.getDeclaredMethods();
            for (Method method:methods) {
                wObj.write("function ");
                wObj.write(method.getName());
                wObj.write("() ");
                Class returnParam = method.getReturnType();
                String returnType=returnParam.getName();
                if( returnType!= "void"){
                    wObj.write("returns handle");
                }
                wObj.write(" = @java:Method { name: \" ");
                wObj.write(method.getName());
                wObj.write(" \", class : \" ");
                wObj.write(classFullName);
                wObj.write("\" } \nexternal; \n");

            }
            wObj.close();


        }
        catch (IOException e){
            System.out.println("An error occured");
            e.printStackTrace();
        }

        return null;
    }

}