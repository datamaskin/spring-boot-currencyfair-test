package jPhant_Test;


import JPhant.JPhant;
import json.JSONStreamParser;

import java.io.IOException;

/**
 * Created by davidb on 3/25/15.
 */
public class JPhant_Currency_Fair_Test {

    public static String replaceCharAt(String s, int i, char c) {
        StringBuffer buf = new StringBuffer(s);
        buf.setCharAt(i, c);
        return buf.toString();
    }
    
    public static void main(String[] sArgs) {
        // Configure
        final JPhant jPhant1 = new JPhant(new JPhant_Config_currency_fair());
        JSONStreamParser jsp = null;
//        String json = "{ \"userId\": \"134256\", \"currencyFrom\": \"EUR\", \"currencyTo\": \"GBP\", \"amountSell\": 1000, \"amountBuy\": 747.1, \"rate\": 0.7471, \"timePlaced\": \"24-JAN-15 10:27:44\", \"originatingCountry\": \"FR\" }";
        try {
            jsp = new JSONStreamParser("http://localhost:8080/");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Manual URL: "+jPhant1.getsPhantURL()+"streams/"+jPhant1.getsPublicKey());

        System.out.println("---------------------------------");
        System.out.println("Test Clear & Stats");
        jPhant1.clear();
        System.out.println("\tStatus="+jPhant1.getStatus().toString());
        System.out.println("\tVerify PageCount==0 and Used==0 above...");
        System.out.println();

        System.out.println("---------------------------------");
        System.out.println("AddData and Verify");

        /*final String[] SDATA = {
            "134256",
            "EUR",
            "GBP",
            String.valueOf(1000),
            String.valueOf(747.1),
            String.valueOf(0.7471),
            "24-JAN-15 10:27:44",
            "FR"
        };*/
        
        String[] SDATA = jsp.getSDATA();
        String[] SFIELDS = jsp.getSFIELDS();
//        final String[] SFIELDS = new String[] { "userid", "currencyfrom", "currencyto", "amountsell", "amountbuy", "rate", "timeplaced", "originatingcountry" };

//        String[][] aasAddData = jPhant1.toStringArrayOfStringArrays(null);
        
        /*String[][] aasAddData = new String[][] {{ "userid", "currencyfrom", "currencyto", "amountsell", "amountbuy", "rate", "timeplaced", "originatingcountry" },
                                                { "134256", "EUR", "GBP", String.valueOf(1000), String.valueOf(747.1), String.valueOf(0.7471), "24-JAN-15 10:27:44", "FR" }};*/

        String[][] aasAddData = new String[2][SDATA.length];

        for (int j=0; j<SDATA.length; j++) {
            aasAddData[0][j] = SFIELDS[j];
            aasAddData[1][j] = SDATA[j];
        }

        boolean bTest = jPhant1.addData(aasAddData);
        System.out.println("\tbTest="+bTest);
//		aasAddData[1] = new String[] { sDATA, sID };
        System.out.println("\tAddData: "+jPhant1.toStringArrayListOfStringArrayLists(aasAddData).toString());
        jPhant1.addData(aasAddData);
        String[][] aasReturnData = jPhant1.getData();
        System.out.println("\tRcvd:'"+jPhant1.toStringArrayListOfStringArrayLists(aasReturnData).toString()+"'");
        /*System.out.println("\tSent:'"+sTest+"'");
        System.out.println("\tRecv:'"+aasReturnData[1][0]+"'");
        System.out.println("\t"+sTest.equals(aasReturnData[1][0]));
        System.out.println();*/
//        final String[][] aasGetData = jPhant1.getData();
        System.out.println("\tGetData: "+jPhant1.toStringArrayListOfStringArrayLists(aasReturnData).toString());
        System.out.println("\tVerify AddData & GetData are identical above (w/ addition of timstamp)...");
        System.out.println("\tData Identical="+(aasAddData[0][0].equals(aasReturnData[0][0]) && aasAddData[0][1].equals(aasReturnData[0][1]) && aasAddData[1][0].equals(aasReturnData[1][0]) && aasAddData[1][1].equals(aasReturnData[1][1])));
        //System.out.println("\tData Identical="+(aasAddData[0][0].equals(aasGetData[0][0])+", "+aasAddData[0][1].equals(aasGetData[0][1])+", "+aasAddData[1][0].equals(aasGetData[1][0])+", "+aasAddData[1][1].equals(aasGetData[1][1])));
        System.out.println("\tStatus="+jPhant1.getStatus().toString());
        System.out.println("\tVerify PageCount==1 and Used>0 above...");

        System.out.println("\tTest Get Rate Limits...");
        System.out.println("\t\tgetXRateLimitLimit()="+jPhant1.getXRateLimitLimit());
        System.out.println("\t\tgetXRateLimitRemaining()="+jPhant1.getXRateLimitRemaining());
        System.out.println("\t\tgetXRateLimitReset()="+jPhant1.getXRateLimitReset());
        System.out.println();
        
        
//        try {
            for (int i=0; i<50; i++) {
                pushDataPoints(jPhant1, jsp);
//                Thread.sleep(2000);
            }
        /*} catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        
        /*String json1 = jPhant1.getData(JPhant.Format.json);
        String[] temp;
        String delims = "[\\[\\],\r\n]+";
        String json2 = null;
        StringBuilder sb = new StringBuilder();
        if (json1 != null) {
            temp = json1.split(delims);
            int i = 1;
            while (i<temp.length/2) { // camel case so the POJO doesn't barf
                if (temp[i].contains("amountbuy")) {
                    String s = replaceCharAt(temp[i], 7, 'B');
                    temp[i] = s;
                } else if (temp[i].contains("amountsell")) {
                    String s = replaceCharAt(temp[i], 7, 'S');
                    temp[i] = s;
                } else if (temp[i].contains("currencyfrom")) {
                    String s = replaceCharAt(temp[i], 9, 'F');
                    temp[i] = s;
                } else if (temp[i].contains("currencyto")) {
                    String s = replaceCharAt(temp[i], 9, 'T');
                    temp[i] = s;
                } else if (temp[i].contains("originatingcountry")) {
                    String s = replaceCharAt(temp[i], 12, 'C');
                    temp[i] = s;
                } else if (temp[i].contains("timeplaced")) {
                    String s = replaceCharAt(temp[i], 5, 'P');
                    temp[i] = s;
                } else if (temp[i].contains("userid")) {
                    String s = replaceCharAt(temp[i], 6, 'I');
                    temp[i] = s;
                }
//                        case "timestamp" :
                sb.append(temp[i]);
                sb.append(",");
                System.out.println(temp[i]);
                System.out.println(sb.toString());
                i++;
            }
            sb.replace(sb.length()-1, sb.length()-1, "}");
            
            json2 = sb.toString().substring(0, sb.length()-1);
        }

        for(String[] asRow : aasReturnData) {
            for(String sField : asRow) {
                System.out.print(sField+", ");
            }
            System.out.println();
        }

        System.out.println("---------------------------------");
        System.out.println("AddData until PageCount==2 and Verify Page2");
        String sBigData = "";
        *//*while(sBigData.length()<10000) {
            sBigData += SDATA[0];
        }*//*
        try {
            jsp = new JSONStreamParser("http://localhost:8080/update", json2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SDATA = jsp.getSDATA();
        SFIELDS = jsp.getSFIELDS();

        aasAddData = new String[2][SDATA.length];
        for (int j=0; j<SDATA.length; j++) {
            aasAddData[0][j] = SFIELDS[j];
            aasAddData[1][j] = SDATA[j];
        }

        bTest = jPhant1.addData(aasAddData);
        System.out.println("\tbTest="+bTest);
        System.out.println("\tAddData: "+jPhant1.toStringArrayListOfStringArrayLists(aasAddData).toString());

        for(String[] asRow : aasReturnData) {
            for(String sField : asRow) {
                System.out.print(sField+", ");
            }
            System.out.println();
        }

        JPhant.Stats sStats = jPhant1.getStatus();
        System.out.println("\tTest stats: " + sStats);

        aasReturnData = jPhant1.getData();
        System.out.println("\tRcvd:'"+jPhant1.toStringArrayListOfStringArrayLists(aasReturnData).toString()+"'");

        System.out.println("\tGetData: "+jPhant1.toStringArrayListOfStringArrayLists(aasReturnData).toString());
        System.out.println("\tVerify AddData & GetData are identical above (w/ addition of timstamp)...");
        System.out.println("\tData Identical="+(aasAddData[0][0].equals(aasReturnData[0][0]) && aasAddData[0][1].equals(aasReturnData[0][1]) && aasAddData[1][0].equals(aasReturnData[1][0]) && aasAddData[1][1].equals(aasReturnData[1][1])));

        System.out.println("\tStatus="+jPhant1.getStatus().toString());
        System.out.println("\tVerify PageCount==1 and Used>0 above...");
        
        System.out.println("\tTest Get Rate Limits...");
        System.out.println("\t\tgetXRateLimitLimit()="+jPhant1.getXRateLimitLimit());
        System.out.println("\t\tgetXRateLimitRemaining()="+jPhant1.getXRateLimitRemaining());
        System.out.println("\t\tgetXRateLimitReset()="+jPhant1.getXRateLimitReset());
        System.out.println();

        System.out.println("---------------------------------");
        *//*System.out.println("Clear & Stats");
        jPhant1.clear();*//*
        System.out.println("\tStatus="+jPhant1.getStatus().toString());
        System.out.println();*/
    }
    
    private static void pushDataPoints(JPhant jPhant, JSONStreamParser jsp) {

        String json1 = jPhant.getData(JPhant.Format.json);
        String[] temp;
        String delims = "[\\[\\],\r\n]+";
        String json2 = null;
        StringBuilder sb = new StringBuilder();
        if (json1 != null) {
            temp = json1.split(delims);
            int i = 1;
            while (i<9) { // camel case so the POJO doesn't barf
                if (temp[i].contains("amountbuy")) {
                    String s = replaceCharAt(temp[i], 7, 'B');
                    temp[i] = s;
                } else if (temp[i].contains("amountsell")) {
                    String s = replaceCharAt(temp[i], 7, 'S');
                    temp[i] = s;
                } else if (temp[i].contains("currencyfrom")) {
                    String s = replaceCharAt(temp[i], 9, 'F');
                    temp[i] = s;
                } else if (temp[i].contains("currencyto")) {
                    String s = replaceCharAt(temp[i], 9, 'T');
                    temp[i] = s;
                } else if (temp[i].contains("originatingcountry")) {
                    String s = replaceCharAt(temp[i], 12, 'C');
                    temp[i] = s;
                } else if (temp[i].contains("timeplaced")) {
                    String s = replaceCharAt(temp[i], 5, 'P');
                    temp[i] = s;
                } else if (temp[i].contains("userid")) {
                    String s = replaceCharAt(temp[i], 6, 'I');
                    temp[i] = s;
                }
//                        case "timestamp" :
                sb.append(temp[i]);
                sb.append(",");
//                System.out.println(temp[i]);
//                System.out.println(sb.toString());
                i++;
            }
            sb.replace(sb.length()-1, sb.length()-1, "}");

            json2 = sb.toString().substring(0, sb.length()-1);
        }

        System.out.println("---------------------------------");
        System.out.println("AddData until PageCount==2 and Verify Page2");
        
        try {
            jsp = new JSONStreamParser("http://localhost:8080/update", json2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] SDATA = jsp.getSDATA();
        String[] SFIELDS = jsp.getSFIELDS();

        String[][] aasAddData = new String[2][SDATA.length];
        for (int j=0; j<SDATA.length; j++) {
            aasAddData[0][j] = SFIELDS[j];
            aasAddData[1][j] = SDATA[j];
        }

        boolean bTest = jPhant.addData(aasAddData);
        System.out.println("\tbTest="+bTest);
        System.out.println("\tAddData: "+jPhant.toStringArrayListOfStringArrayLists(aasAddData).toString());

        String[][] aasReturnData = jPhant.getData();
        for(String[] asRow : aasReturnData) {
            for(String sField : asRow) {
                System.out.print(sField+", ");
            }
            System.out.println();
        }

        JPhant.Stats sStats = jPhant.getStatus();
        System.out.println("\tTest stats: " + sStats);

        aasReturnData = jPhant.getData();
        System.out.println("\tRcvd:'"+jPhant.toStringArrayListOfStringArrayLists(aasReturnData).toString()+"'");

        System.out.println("\tGetData: "+jPhant.toStringArrayListOfStringArrayLists(aasReturnData).toString());
        System.out.println("\tVerify AddData & GetData are identical above (w/ addition of timstamp)...");
        System.out.println("\tData Identical="+(aasAddData[0][0].equals(aasReturnData[0][0]) && aasAddData[0][1].equals(aasReturnData[0][1]) && aasAddData[1][0].equals(aasReturnData[1][0]) && aasAddData[1][1].equals(aasReturnData[1][1])));

        System.out.println("\tStatus="+jPhant.getStatus().toString());
        System.out.println("\tVerify PageCount==1 and Used>0 above...");

        System.out.println("\tTest Get Rate Limits...");
        System.out.println("\t\tgetXRateLimitLimit()="+jPhant.getXRateLimitLimit());
        System.out.println("\t\tgetXRateLimitRemaining()="+jPhant.getXRateLimitRemaining());
        System.out.println("\t\tgetXRateLimitReset()="+jPhant.getXRateLimitReset());
        System.out.println();

        System.out.println("---------------------------------");
        /*System.out.println("Clear & Stats");
        jPhant1.clear();*/
        System.out.println("\tStatus="+jPhant.getStatus().toString());
        System.out.println();

    }
}
