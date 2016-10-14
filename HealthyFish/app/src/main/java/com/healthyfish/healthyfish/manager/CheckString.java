package com.healthyfish.healthyfish.manager;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * Created by RohiniAjith on 6/11/2016.
 */
public class CheckString {


    public boolean isemptyString(String str)
    {
      if(str.equals(""))
      {
          return  true;
      }
        return false;

    }
    public boolean isAlpha(String str)
    {
         return str.matches("[a-zA-Z]+");
    }
    public  boolean isNumbers(String str)
    {
        System.out.println("Number: "+str.matches("^[0-9]{10}$"));
        return str.matches("[0-9]+");
    }
    public  boolean isEmail(String str)
    {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(str);
                 emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
    public int lengthofString(String str)
    {

        return  str.length();
    }
    public boolean isNull(String str)
    {
        if(str.equals(null)||str.equals("null"))
        {
            return true;
        }
        return false;
    }
}
