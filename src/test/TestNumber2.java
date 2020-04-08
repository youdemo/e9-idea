package test;

public class TestNumber2 {

    public static final String ZERO = "zero";
    public static final String NEGATIVE = "negative";
    public static final String SPACE = " ";
    public static final String MILLION = "million";
    public static final String THOUSAND = "thousand";
    public static final String HUNDRED = "hundred";
    public static final String[] INDNUM = {"zero", "one", "two", "three", "four", "five", "six",
            "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen",
            "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"};
    public static final String[] DECNUM = {"twenty", "thirty", "forty", "fifty", "sixty",
            "seventy", "eighty", "ninety"};

    //数字转换英文
    public static String format(int i) {

        StringBuilder sb = new StringBuilder();

        if(i == 0) {
            return ZERO;
        }

        if(i < 0) {
            sb.append(NEGATIVE).append(SPACE);
            i *= -1;
        }


        if(i >= 1000000) {
            sb.append(numFormat(i / 1000000)).append(SPACE).append(MILLION).append(SPACE);
            i %= 1000000;

        }

        if(i >= 1000) {
            sb.append(numFormat(i / 1000)).append(SPACE).append(THOUSAND).append(SPACE);

            i %= 1000;
        }

        if(i < 1000){
            sb.append(numFormat(i));
        }

        return sb.toString();
    }
    public static String numFormat(int i) {

        StringBuilder sb = new StringBuilder();

        if(i >= 100) {
            sb.append(INDNUM[i / 100]).append(SPACE).append(HUNDRED).append(SPACE);
        }

        i %= 100;

        if(i != 0) {
            if(i >= 20) {
                sb.append(DECNUM[i / 10 -2]).append(SPACE);
                if(i % 10 != 0) {
                    sb.append(INDNUM[i % 10]);
                }
            }else {
                sb.append(INDNUM[i]);
            }
        }

        return sb.toString();
    }
    public static void main(String[] args) {
        System.out.println(format(1123123));

    }

}
