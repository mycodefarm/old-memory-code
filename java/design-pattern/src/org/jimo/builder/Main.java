package org.jimo.builder;

/**
 * Created by root on 17-6-17.
 */
public class Main {
    public static void main(String[] args) {
        TextBuilder textBuilder = new TextBuilder();
        new Director(textBuilder).construct();
        System.out.println(textBuilder.getResult());

        HTMLBuilder htmlBuilder = new HTMLBuilder();
        new Director(htmlBuilder).construct();
        System.out.println(htmlBuilder.getResult() + " 文档编写完成");

        /**
         ==============================
         [Greeting]

         **从早到晚

         -Good Morning
         -Good Afternoon

         **Night

         -Good Night
         -Night
         -Bye

         ==============================

         Greeting.html 文档编写完成
         */
    }
}
