package com.posco.erp.wipapp.managers;

import com.posco.erp.wipapp.network.RequestPackage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpManager {
    public static String getData(RequestPackage p){
        BufferedReader reader = null;
        String uri = p.getUri();

        if (p.getMethod().equals("GET") || p.getMethod().equals("POST")){
            uri+="?" + p.getEncodedParams();
        }

        try{
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(p.getMethod());
            StringBuilder sb = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while((line = reader.readLine()) != null){
                sb.append(line + "\n");
            }
            return sb.toString();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        } finally {
            if (reader != null)
            {
                try{
                    reader.close();
                } catch (Exception e){
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }
}
