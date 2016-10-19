package com.ictvn.erp.wip.wipclosing.managers;

import com.ictvn.erp.wip.wipclosing.network.RequestPackage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpManager {
    public static String getData(RequestPackage p){
        BufferedReader reader = null;
        String uri = p.getUri();

        if (p.getMethod().equals("GET")){
            uri+="?" + p.getEncodedParams();
        }

        try{
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(p.getMethod());
            if (p.getMethod().equals("POST")){
                conn.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                writer.write(p.getEncodedParams());
                writer.flush();
            }
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
