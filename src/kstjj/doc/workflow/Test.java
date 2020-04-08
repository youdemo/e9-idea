package kstjj.doc.workflow;

public class Test {
    public static void main(String[] args) {
        String result = "";
        String flag = "";
        String aaa="17_51,17_52,17_53,17_54,17_55,17_56,17_57,17_58,17_59,17_60,17_61,17_62,17_63,17_64,17_65,17_66,17_67,17_68,17_69,17_70,17_71";
        String arr[]=aaa.split(",");
        for(String aid:arr){
            result=result+flag+aid.substring(aid.lastIndexOf("_")+1);
            flag = ",";
        }
        System.out.println(result);
    }
}
