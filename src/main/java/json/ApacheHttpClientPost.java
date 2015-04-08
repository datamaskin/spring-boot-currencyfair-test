package json;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;

/**
 * Created by davidb on 4/6/15.
 */
public class ApacheHttpClientPost {
    public static String httpClientPost(String URL, String entity) {
        StringBuffer sb = new StringBuffer();
        try {

            DefaultHttpClient httpClient = new DefaultHttpClient();

            HttpPost postRequest = new HttpPost(URL);
            StringEntity input = new StringEntity(entity);
            input.setContentType("application/json");
            postRequest.setEntity(input);

            HttpResponse response = httpClient.execute(postRequest);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

            String output;

//			System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }

            httpClient.getConnectionManager().shutdown();

        } catch (MalformedURLException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();

        }
        return sb.toString();
    }
    
    public static void main(String[] args) {

		String URL = "http://localhost:8080/update";
		String entity = "{ \"userId\": \"134256\", \"currencyFrom\": \"EUR\", \"currencyTo\": \"GBP\", \"amountSell\": 1000, \"amountBuy\": 747.1, \"rate\": 0.7471, \"timePlaced\": \"24-JAN-15 10:27:44\", \"originatingCountry\": \"FR\" }";
		String json = httpClientPost(URL, entity);
		System.out.println(json);


	} // END MAIN()
}
