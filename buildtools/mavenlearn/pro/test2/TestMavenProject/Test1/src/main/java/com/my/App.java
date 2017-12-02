package com.my;

import java.util.UUID;

/**
 * Hello world!
 *
 */
public class App 
{
    public String generateUniqueKey(){

        String id = UUID.randomUUID().toString();
        System.out.println("he  dff");
        return id;

    }
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}
