package com.kaisn.utils.http;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Iterator;
import java.util.Map;


public class HttpsRequest {

    private static Logger logger = Logger.getLogger(HttpsRequest.class);

    private static final String CHARSET_UTF_8 = "UTF-8";

    /**
     * 处理https GET/POST请求
     * @param requestUrl 请求地址
     * @param requestMethod 请求方法
     * @param requestStr 请求参数
     * @return
     */
    public static String httpsRequest(String requestUrl, String requestMethod, String requestStr)
    {
        String result = null;
        HttpsURLConnection httpsConnection = null;
        try {
            HostnameVerifier hostnameVerifier = new HostnameVerifier() {
                public boolean verify(String s, SSLSession sslsession) {
                    logger.warn("WARNING: Hostname is not matched for cert.");
                    return true;
                }
            };
            URL url = new URL(requestUrl);
            httpsConnection = (HttpsURLConnection) url.openConnection();
            httpsConnection.setHostnameVerifier(hostnameVerifier);
            //单向认证
            /*SSLContext sslContext = SSLContext.getInstance("SSL","SunJSSE");
            sslContext.init(null, new TrustManager[]{new ClientTrustManager()}, new SecureRandom());
            httpsConnection.setSSLSocketFactory(sslContext.getSocketFactory());*/
            //双向认证
            httpsConnection.setSSLSocketFactory(ClientSSLContext.getSSLSocketFactory());
            httpsConnection.setDoOutput(true);
            httpsConnection.setDoInput(true);
            httpsConnection.setConnectTimeout(3000);
            httpsConnection.setReadTimeout(15000);
            httpsConnection.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpsConnection.setRequestMethod(requestMethod);
            if ("GET".equalsIgnoreCase(requestMethod)){
                httpsConnection.connect();
            }
            // 当有数据需要提交时
            if (null != requestStr) {
                OutputStream outputStream = httpsConnection.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(requestStr.getBytes(CHARSET_UTF_8));
                outputStream.close();
            }
            int responseCode = httpsConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                result = getReturn(httpsConnection);
            } else {
                logger.error("响应失败，响应码："+responseCode);
            }
        } catch (Exception e) {
            logger.error("请求失败",e);
        }
        return result;
    }

    /**
     * 处理http GET/POST请求
     * @param requestUrl 请求地址
     * @param requestMethod 请求方法
     * @param requestStr 请求参数
     * @return
     */
    public static String httpRequest(String requestUrl,String requestMethod,String requestStr)
    {
        String result = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(requestUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(15000);
            connection.setUseCaches(false);
            // 设置请求方式（GET/POST）
            connection.setRequestMethod(requestMethod);
            if ("GET".equalsIgnoreCase(requestMethod)){
                connection.connect();
            }
            // 当有数据需要提交时
            if (null != requestStr) {
                OutputStream outputStream = connection.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(requestStr.getBytes(CHARSET_UTF_8));
                outputStream.close();
            }
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                result = getReturn(connection);
            } else {
                logger.error("响应失败，响应码："+responseCode);
            }
        } catch (Exception e) {
            logger.error("请求失败",e);
        }
        return result;
    }

    /**
     * 请求url获取返回的内容
     * @param connection
     * @return
     * @throws IOException
     */
    public static String getReturn(HttpURLConnection connection) {
        StringBuffer buffer = new StringBuffer();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        //将返回的输入流转换成字符串
        try {
            inputStream = connection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream,CHARSET_UTF_8);
            bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
        } catch (Exception e) {
            logger.error("获取响应流失败",e);
        } finally {
            IOUtils.closeQuietly(bufferedReader);
            IOUtils.closeQuietly(inputStreamReader);
            IOUtils.closeQuietly(inputStream);
            if (connection != null) {
                connection.disconnect();
            }
        }
        return buffer.toString();
    }

    /**
     * 将参数化为 body
     * @param params
     * @param urlEncode
     * @return
     */
    public static String getRequestBody(Map<String, String> params, boolean urlEncode) {
        StringBuilder body = new StringBuilder();
        Iterator<String> iteratorHeader = params.keySet().iterator();
        while (iteratorHeader.hasNext()) {
            String key = iteratorHeader.next();
            String value = params.get(key);
            if (urlEncode) {
                try {
                    body.append(key + "=" + URLEncoder.encode(value, CHARSET_UTF_8) + "&");
                } catch (UnsupportedEncodingException e) {
                     e.printStackTrace();
                }
            } else {
                body.append(key + "=" + value + "&");
            }
        }

        if (body.length() == 0) {
            return "";
        }
        return body.substring(0, body.length() - 1);
    }

    public static void main(String[] args)  {
        String requestUrl = "https://localhost:8444/ws-client/emp/list";
        String post = httpsRequest(requestUrl, "POST", "page=1&limit=10");
        System.out.println(post);
    }
}
