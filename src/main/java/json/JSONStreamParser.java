package json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;
import java.net.URL;

/**
 * Created by davidb on 4/5/15.
 */
public class JSONStreamParser {

    public String[] getSDATA() {
        return SDATA;
    }

    public String[] getSFIELDS() {
        return SFIELDS;
    }

    String[] SDATA = new String[8];
    String[] SFIELDS = new String[8];

    public JSONStreamParser(String ... strings) throws IOException {
        
//        String url = "http://localhost:8080/";
        String url = null;
//        String json = "{ \"userId\": \"134256\", \"currencyFrom\": \"EUR\", \"currencyTo\": \"GBP\", \"amountSell\": 1000, \"amountBuy\": 747.1, \"rate\": 0.7471, \"timePlaced\": \"24-JAN-15 10:27:44\", \"originatingCountry\": \"FR\" }";
        String json = null;
        JsonFactory factory = new JsonFactory();
        String result = null;
        JsonParser parser = null;
        
        for (int i=0; i<strings.length; i++) {
            if (strings[i].startsWith("http://"))
                url = strings[i];
            else if (strings[i].startsWith("{") || strings[i].startsWith("["))
                json = strings[i];
        }
        
        if (strings.length > 1) {
            result = ApacheHttpClientPost.httpClientPost(url, json);
            parser = factory.createParser(result);
        }
        else if (strings.length == 1)
            parser = factory.createParser(new URL(url));
        

        JsonToken token;
        while (!parser.isClosed()) {
            // get the token
            token = parser.nextToken();
            System.out.println(token);
            System.out.println(parser.getCurrentToken());
            System.out.println(parser.getCurrentName());

            // if its the last token then we are done

            // each element of the array is an currency items so the next token
            // should be {

            int i = 0;
            while (token != null && !JsonToken.END_ARRAY.equals(token)) {
                token = parser.nextToken();
                if (token == null || JsonToken.END_OBJECT.equals(token))
                    break;

                if (JsonToken.FIELD_NAME.equals(token)) {
                    if (JsonToken.VALUE_STRING.equals(token))
                        token = parser.nextToken();
                    System.out.println(parser.getCurrentToken());
                    String currentName = parser.getCurrentName().toLowerCase();
                    System.out.println(currentName);
                    switch (currentName) {
                        case "amountbuy" :
                        case "amountsell" :
                        case "currencyfrom" :
                        case "currencyto" :
                        case "originatingcountry" :
                        case "rate" :
                        case "timeplaced" :
                        case "userid" :
//                        case "timestamp" :
                            SFIELDS[i] = currentName;
                            token = parser.nextToken();
                            String text = parser.getText();
                            SDATA[i] = text;
                            i++;
                            System.out.println(text);
                            break;
                    }
                }
            }
        }

    }
}
