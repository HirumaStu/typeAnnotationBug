package ru.hiruma;


public class OuterClass {
    public OuterClass() {
    }

    class Inner {
        Inner(@Deprecated @MyAnnotation String s){
            System.out.println(s);
        }
    }

    public enum Test2 {
        A("a"),
        B("b");


        private String v ;


        Test2(@Deprecated @MyAnnotation  String s){
            v=s;
        }

        public String getV() {
            return v;
        }
    }
}
