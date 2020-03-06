package com.example.botver2;

import android.os.StrictMode;
import android.os.SystemClock;
import android.util.Log;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClientBuilder;
import java.io.IOException;



import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.SQLOutput;

/**
 * Copyright Pandorabots, Inc. &copy; 2014.
 * All Rights Reserved.
 */
public class PandorabotsTalkAPI {

    public String defaultCustid = "0";
    public String sessionid = defaultCustid;
    public String responseFailed = "RESPONSE FAILED";
    public String defaultBotKey = "UodYLeoGwXhODsz0AWhPYVyM1D6TW9laohdcbSxxA5mv3Sa85caZnxg-3SY9L1ZAV4WROq-9b3o~";
    public String defaultHost = "api.pandorabots.com";
    public String askPandorabots(String input) {
        return askPandorabots(input, defaultHost, defaultBotKey);
    }
    public String askPandorabots(String input, String host, String botkey) {
        System.out.println("Entering SRAIX with input="+input+" host ="+host+" botid="+botkey);
        String responseContent = pandorabotsRequest(input, host, botkey);
        if (responseContent == null) return responseFailed;
        else return pandorabotsResponse(responseContent, host, botkey);
    }
    public String responseContent(final String URL) throws Exception {

        /*
        URL url = new URL(URL);
        URLConnection con = url.openConnection();
        InputStream in = con.getInputStream();
        String encoding = con.getContentEncoding();
        encoding = encoding == null ? "UTF-8" : encoding;
        String body = IOUtils.toString(in, encoding);
        System.out.println(body);
        return body;
        */
        RetrieveFeedTask asincTask = new RetrieveFeedTask();
       // asincTask.execute(URL).get();
        //Log.d("vedem",str_result );
        //return asincTask.getStringResponse();
        return asincTask.execute(URL).get();
        //HttpClient httpClient = HttpClientBuilder.create().build();
        //HttpGet httpget = new HttpGet(url);
        //System.out.println(httpget);






        /*HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet();
        request.setURI(new URI(url));
        InputStream is = client.execute(request).getEntity().getContent();
        BufferedReader inb = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder("");
        String line;
        String NL = System.getProperty("line.separator");
        while ((line = inb.readLine()) != null) {
            sb.append(line).append(NL);
        }
        inb.close();
        return sb.toString();
        */

    }


    public String spec(String host, String botkey, String sessionid, String input) {
        System.out.println("--> custid = "+sessionid);
        String spec = "";
        try {
            if (sessionid.equals("0"))      // get custid on first transaction with Pandorabots
                spec =    String.format("%s?botkey=%s&input=%s",
                        "https://" + host + "/talk",
                        botkey,
                        URLEncoder.encode(input, "UTF-8"));
            else spec =                 // re-use custid on each subsequent interaction
                    String.format("%s?botkey=%s&input=%s&input=%s&sessionid=%s",
                            "https://" + host + "/talk",
                            botkey,
                            sessionid,
                            input);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //System.out.println(spec);
        return spec;
        //return  "https://api.pandorabots.com/talk?botkey=UodYLeoGwXhODsz0AWhPYVyM1D6TW9laohdcbSxxA5mv3Sa85caZnxg-3SY9L1ZAV4WROq-9b3o~&input=hello";
    }

    public String pandorabotsRequest(String input, String host, String botkey) {
        try {

            String spec = spec(host, botkey, sessionid, input);
            System.out.println("Spec* = "+spec);
            String responseContent = responseContent(spec);
            System.out.println("returned responseContent as string");
            System.out.println(responseContent);
            return responseContent;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public String pandorabotsResponse (String sraixResponse, String host, String botid) {
        String botResponse = responseFailed;
        try {
            System.out.println("Straix response is: " + sraixResponse);
            int n1 = sraixResponse.indexOf("[\"");
            int n2 = sraixResponse.indexOf("\"]");

            if (n2 > n1)
                botResponse = sraixResponse.substring(n1+"[\"".length(), n2);
            n1 = sraixResponse.indexOf("sessionid=");
            if (n1 > 0) {
                sessionid = sraixResponse.substring(n1+"sessionid=\"".length(), sraixResponse.length());
                n2 = sessionid.indexOf("\"");
                if (n2 > 0) sessionid = sessionid.substring(0, n2);
                else sessionid = defaultCustid;
                }
            if (botResponse.endsWith(".")) botResponse = botResponse.substring(0, botResponse.length()-1);   // snnoying Pandorabots extra "."
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Bot response in pandorabotsResponse() are : "+ botResponse.toString());
        return botResponse;

    }

}
