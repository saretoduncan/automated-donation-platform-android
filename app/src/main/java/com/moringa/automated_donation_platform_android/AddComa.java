package com.moringa.automated_donation_platform_android;

public class AddComa {
    public String addComaToAmount(String amount){
        String result = "";
        if(Integer.parseInt(amount) < 1000)
        {
            return amount;
        }

        StringBuilder sb = new StringBuilder();

        int count = 0;
        for(int i = amount.length() -1; i>= 0; i--)
        {
            count++;

            if(count % 3 == 0 && i != 0)
            {
                sb.append(amount.charAt(i));
                sb.append(",");
                count = 0;
            }
            else
            {
                sb.append(amount.charAt(i));
            }
        }
        String str = sb.toString();

        String reversedStr = reverse(str);
        return "Ksh. "+reversedStr;
    }

    public static String reverse(String str)
    {
        StringBuilder sb = new StringBuilder(str);

        return sb.reverse().toString();
    }
}

