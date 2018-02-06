package com.sz91online.common.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by wendajun on 2016/3/3.
 */
public class CodeUtils {
    //Content
    private static String MLContent="验证码:%s【联新移动医疗科技有限公司】";
    //MLURl
    private static String MLUrl="http://m.5c.com.cn/api/send/index.php?username=lianxin&password=lx_1234&apikey=715cbfc38f33a2fef570ac8b1f3543f1&mobile=%s&content=%s";
    public static void sendMobileCode(String code,String... mobile){
        InputStream is=null;
        try {
            //httpClient
            CloseableHttpClient closeableHttpClient =HttpClientBuilder.create().build();
            String requestUrl = makeMLUrl(code,mobile);
            HttpUriRequest uri = new HttpGet(requestUrl);
            CloseableHttpResponse response = closeableHttpClient.execute(uri);
            System.out.println(response.getStatusLine().getStatusCode());
            is = response.getEntity().getContent();
            Reader reader = new InputStreamReader(is);
            char[] buffer = new char[2048];
            int len;
            StringBuilder sb = new StringBuilder();
            while((len=reader.read(buffer))!=-1){
                sb.append(new String(buffer));
            }
            System.out.println(sb.toString());
            //HttpEntity dto = response.getEntity();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
      * @param code
     * @param mobiles
     * @return
     */
    private static String makeMLUrl(String code,String... mobiles){
        if(mobiles==null){
            return null;
        }else{
            StringBuilder sb = new StringBuilder();
            for(int i = 0;i<mobiles.length;i++){
                sb.append(mobiles[i]);
                if(i!=mobiles.length-1) {
                   sb.append(",");
                }
            }
            String content = String.format(MLContent,code);
          return String.format(MLUrl,sb.toString(),content);
        }

    }

    public static void main(String[] args) {
        sendMobileCode("1234","18681535163");
    }
}
