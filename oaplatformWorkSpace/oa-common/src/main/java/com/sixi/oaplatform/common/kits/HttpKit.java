package com.sixi.oaplatform.common.kits;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sixi.oaplatform.common.utils.ResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.X509Certificate;
import java.util.Map;

/**
 * HttpKit
 * @author Administrator
 */
public class HttpKit {

    private static Logger logger = LoggerFactory.getLogger(HttpKit.class);

    private HttpKit() {}

    /**
     * https 域名校验
     */
    private class TrustAnyHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    /**
     * https 证书管理
     */
    private class TrustAnyTrustManager implements X509TrustManager {
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)  {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) {
        }
    }

    private static final String GET = "GET";
    private static final String POST = "POST";
    private static String CHARSET = "UTF-8";

    private static final SSLSocketFactory sslSocketFactory = initSSLSocketFactory();
    private static final TrustAnyHostnameVerifier trustAnyHostnameVerifier = new HttpKit().new TrustAnyHostnameVerifier();

    private static SSLSocketFactory initSSLSocketFactory() {
        try {
            TrustManager[] tm = {new HttpKit().new TrustAnyTrustManager()};
            // ("TLS", "SunJSSE");
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tm, new java.security.SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 设置编码格式
     * 默认UTF-8
     * @param charSet 编码格式
     */
    public static void setCharSet(String charSet) {
        if (StrKit.isBlank(charSet)) {
            throw new IllegalArgumentException("charSet can not be blank.");
        }
        HttpKit.CHARSET = charSet;
    }

    /**
     * 得到http连接 并且设置头文件
     *
     * @param url 地址
     * @param method 请求方式
     * @param headers 需要加入的头信息
     * @return HttpURLConnection
     * @throws IOException io操作异常
     */
    private static HttpURLConnection getHttpConnection(String url, String method, Map<String, String> headers) throws IOException {
        URL _url = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) _url.openConnection();
        if (conn instanceof HttpsURLConnection) {
            ((HttpsURLConnection) conn).setSSLSocketFactory(sslSocketFactory);
            ((HttpsURLConnection) conn).setHostnameVerifier(trustAnyHostnameVerifier);
        }

        conn.setRequestMethod(method);
        conn.setDoOutput(true);
        conn.setDoInput(true);

        conn.setConnectTimeout(19000);
        conn.setReadTimeout(19000);

        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("User-Agent",
                                "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");

        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }

        return conn;
    }

    /**
     * 带参数的get请求
     * @param url 地址
     * @param queryParas 请求参数
     * @return ResultData
     */
    public static ResultData get(String url, Map<String, String> queryParas) {
        return get(url, queryParas, null);
    }

    /**
     * 不带参数的get请求
     * @param url 地址
     * @return ResultData
     */
    public static ResultData get(String url) {
        return get(url, null, null);
    }

    /**
     * 发送get请求调用远程服务
     * @param url 请求地址
     * @param queryParas 请求拼接参数
     * @param headers 请求头文件
     * @return ResultData
     */
    public static ResultData get(String url, Map<String, String> queryParas, Map<String, String> headers) {
        HttpURLConnection conn = null;
        try {
            conn = getHttpConnection(buildUrlWithQueryString(url, queryParas), GET, headers);
            conn.connect();
            String s = readResponseString(conn);
            ResultData<JSONObject> resultData = JSON.parseObject(s, ResultData.class);
            //如果请求返回的结果为失败
            /*if (ResultData.STATUS_SUCCESS!=resultData.getStatus()){
                throw new Exception(resultData.getMsg());
            }*/

            return resultData;
        }catch (IOException e){
            e.printStackTrace();
            logger.error("io 操作异常: {}",e.getMessage());
            return ResultData.error("io 操作异常 msg:"+e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            logger.error("远程 服务调用失败: {}",e.getMessage());
            return ResultData.error("远程 服务调用失败 msg:"+e.getMessage());
        }
        finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * 默认头文件的post 远程请求函数
     *
     * @param url 地址
     * @param queryParas 拼接参数
     * @param data json格式对象参数
     * @return ResultData
     */
    public static ResultData post(String url, Map<String, String> queryParas, String data) {
        return post(url, queryParas, data, null);
    }

    /**
     * 不带拼接参数的 post 远程请求函数
     *
     * @param url 地址
     * @param data json格式请求对象
     * @param headers 头文件
     * @return ResultData
     */
    public static ResultData post(String url, String data, Map<String, String> headers) {
        return post(url, null, data, headers);
    }

    /**
     * 不带头文件与拼接参数的 post 远程请求函数
     *
     * @param url 请求地址
     * @param data json格式的请求对象
     * @return ResultData
     */
    public static ResultData post(String url, String data) {
        return post(url, null, data, null);
    }

    /**
     * 发送post请求调用远程服务最终方法
     * @param url 地址
     * @param queryParas 请求参数
     * @param data 请求数据
     * @param headers 头文件
     * @return ResultData
     */
    public static ResultData post(String url, Map<String, String> queryParas, String data, Map<String, String> headers) {
        HttpURLConnection conn = null;
        try {
            conn = getHttpConnection(buildUrlWithQueryString(url, queryParas), POST, headers);
            conn.connect();

            OutputStream out = conn.getOutputStream();
            out.write(data != null ? data.getBytes(CHARSET) : null);
            out.flush();
            out.close();

            String s = readResponseString(conn);

            logger.info("请求询盘后端: " + url + " Data: " + data);
            logger.info("询盘返回数据: " + s);

            return JSON.parseObject(s, ResultData.class);
        }catch (IOException e){
            e.printStackTrace();
            logger.error("io 操作异常:{}",e.getMessage());
            return ResultData.error("io 操作异常 msg:"+e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            logger.error("远程 服务调用失败:{}",e.getMessage());
            return ResultData.error("远程 服务调用失败 msg:"+e.getMessage());
        }
        finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    /**
     * IO操作读取返回的结果存入String中
     * @param conn http连接
     * @return String
     * @throws Exception Io异常 或者 远程服务异常
     */
    private static String readResponseString(HttpURLConnection conn) throws Exception {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        try {
            inputStream = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, CHARSET));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            logger.error("读取远程返回结果错误:--- {}",e.getMessage());
            throw new Exception(e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ignored) {
                    logger.error("IO 关闭操作异常");
                }
            }
        }
    }

    /**
     * 拼接带参数的url地址
     *
     * @param url 地址
     * @param queryParas 参数
     * @return String
     */
    private static String buildUrlWithQueryString(String url, Map<String, String> queryParas) {
        if (queryParas == null || queryParas.isEmpty()) {
            return url;
        }

        StringBuilder sb = new StringBuilder(url);
        boolean isFirst;
        if (!url.contains("?")) {
            isFirst = true;
            sb.append("?");
        }
        else {
            isFirst = false;
        }

        for (Map.Entry<String, String> entry : queryParas.entrySet()) {
            if (isFirst) {
                isFirst = false;
            }
            else {
                sb.append("&");
            }

            String key = entry.getKey();
            String value = entry.getValue();
            if (StrKit.notBlank(value)) {
                try {
                    value = URLEncoder.encode(value, CHARSET);
                }
                catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
            sb.append(key).append("=").append(value);
        }
        return sb.toString();
    }

    /**
     * 数据读取无效
     * @param request HttpServletRequest
     * @return String
     */
    @Deprecated
    public static String readData(HttpServletRequest request) {
        BufferedReader br = null;
        try {
            StringBuilder result = new StringBuilder();
            br = request.getReader();
            for (String line; (line = br.readLine()) != null; ) {
                if (result.length() > 0) {
                    result.append("\n");
                }
                result.append(line);
            }
            return result.toString();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            if (br != null) {
                try {
                    br.close();
                }catch (IOException ignored) {
                    logger.error("IO 关闭操作异常");
                }
            }
        }
    }

    /**
     * 读取请求数据无效
     * @param request HttpServletRequest
     * @return String
     */
    @Deprecated
    public static String readIncommingRequestData(HttpServletRequest request) {
        return readData(request);
    }
}