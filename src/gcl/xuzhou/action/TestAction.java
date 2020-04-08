package gcl.xuzhou.action;

public class TestAction {
    public static void main(String[] args) {
        String aaa = "000000500000";

        if(aaa.length()>6 &&aaa.substring(0,6).equals("000000")){
            System.out.println(aaa.substring(6,aaa.length()));
            aaa = aaa.substring(6,aaa.length());
            if(aaa.substring(0,1).equals("5")){

            }

        }
    }
}
