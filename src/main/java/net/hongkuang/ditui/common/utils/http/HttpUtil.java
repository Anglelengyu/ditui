package net.hongkuang.ditui.common.utils.http;

import org.apache.commons.codec.Charsets;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.*;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class HttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    //	private static final MediaType CONTENT_TYPE_FORM = MediaType.parse("application/x-www-form-urlencoded");
    private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36";

    private HttpUtil() {
    }

    /**
     * 创建http请求，带http basic认证的
     *
     * @param username 验证时所需用户名
     * @param passowrd 验证时所需密码
     * @return
     */
    public static CloseableHttpClient createHttpClient(String username, String passowrd) {
        CredentialsProvider provider = new BasicCredentialsProvider();
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, passowrd);
        provider.setCredentials(AuthScope.ANY, credentials);
        return HttpClients.custom().setDefaultCredentialsProvider(provider).build();
    }

    /**
     * 创建http请求
     *
     * @return
     */
    public static CloseableHttpClient createHttpClient() {
        return HttpClients.custom().build();
    }

    /**
     * 发送 http post 请求
     *
     * @param url 请求地址
     * @return
     */
    public static String sendPost(String url) {
        String result = "";
        CloseableHttpClient httpClient = createHttpClient();
        HttpPost httpPost = new HttpPost(url);
        try {
            result = execute(httpClient, httpPost);
        } catch (Exception e) {
            logger.error("http post", e);
        } finally {
            httpPost.releaseConnection();
        }
        return result;
    }


    /**
     * 发送 http post 请求
     *
     * @param url    请求地址
     * @param params post请求所需参数
     * @return
     */
    public static String sendPost(String url, String params) {
        return sendPost(url, params, "utf-8");
    }

    /**
     * 发送 http post 请求
     *
     * @param url     请求地址
     * @param params  post请求所需参数
     * @param charset 编码格式
     * @return
     */
    public static String sendPost(String url, String params, String charset) {
        String result = "";
        CloseableHttpClient httpClient = createHttpClient();

        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", ContentType.APPLICATION_JSON.toString());
        httpPost.setHeader("charset", "UTF-8");
        httpPost.setHeader("Access-Token", "eyJhbGciOiJIUzI1NiJ9.eyJwYXJ0bmVyX2lkIjoiMTA2NTA5NDg4NzQyMDA3MTkzNiJ9.1ZHwIMRp6gs4cVbqWay1DcXqrV9Vk3za6Lh1N-7l7w8");
        try {
            // 设置请求参数
            httpPost.setEntity(new StringEntity(params, charset));
//            httpPost.setEntity(new UrlEncodedFormEntity(params, charset));
            result = execute(httpClient, httpPost);
        } catch (Exception e) {
            logger.error("http post", e);
        } finally {
            httpPost.releaseConnection();
        }
        return result;
    }

    /**
     * 发送带http basic认证的get请求
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param username 验证时所需用户名
     * @param userpwd  验证时所需密码
     * @return 结果
     */
    public static String sendGet(String url, String params, String username, String userpwd) {
        CloseableHttpClient httpClient = createHttpClient(username, userpwd);
        String requestUrl = params == null ? url : url + "?" + params;
        HttpGet httpGet = new HttpGet(requestUrl);
        String result = "";
        try {
            result = execute(httpClient, httpGet);
        } catch (IOException e) {
            logger.error("http get", e);
        } finally {
            httpGet.releaseConnection();
        }
        return result;
    }

    /**
     * 发送带http basic认证的get请求
     *
     * @param url      请求地址
     * @param username 验证时所需用户名
     * @param userpwd  验证时所需密码
     * @return 结果
     */
    public static String sendGet(String url, String username, String userpwd) {
        return sendGet(url, null, username, userpwd);
    }


    /**
     * 发送带http get请求
     *
     * @param url 请求地址
     * @return 结果
     */
    public static String sendGet(String url, String params) {
        CloseableHttpClient httpClient = createHttpClient();
        String requestUrl = params == null ? url : url + "?" + params;
        HttpGet httpGet = new HttpGet(requestUrl);
        String result = "";
        try {
            result = execute(httpClient, httpGet);
        } catch (IOException e) {
            logger.error("http get", e);
        } finally {
            httpGet.releaseConnection();
        }
        return result;
    }

    /**
     * 发送带http get请求
     *
     * @param url 请求地址
     * @return 结果
     */
    public static String sendGet(final String url) {
        CloseableHttpClient httpClient = createHttpClient();
        HttpGet httpGet = new HttpGet(url);
        String result = "";
        try {
            result = execute(httpClient, httpGet);
        } catch (IOException e) {
            logger.error("http get", e);
        } finally {
            httpGet.releaseConnection();
        }
        return result;
    }

    /**
     * 根据执行请求
     *
     * @param httpClient
     * @param requestBase
     * @return
     * @throws IOException
     */
    private static String execute(CloseableHttpClient httpClient, HttpRequestBase requestBase) throws IOException {
        CloseableHttpResponse httpResponse = httpClient.execute(requestBase);
        HttpEntity entity = httpResponse.getEntity();
        String result = EntityUtils.toString(entity);
        return result;
    }


    /**
     * 发送https请求
     *
     * @param url      请求地址
     * @param data     发送数据
     * @param certPath 证书地址
     * @param certPass 证书密码
     * @return
     */
    public static String postSSL(String url, String data, String certPath, String certPass) {
        HttpsURLConnection conn = null;
        OutputStream out = null;
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            KeyStore clientStore = KeyStore.getInstance("PKCS12");
            clientStore.load(new FileInputStream(certPath), certPass.toCharArray());
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(clientStore, certPass.toCharArray());
            KeyManager[] kms = kmf.getKeyManagers();
            SSLContext sslContext = SSLContext.getInstance("TLSv1");

            sslContext.init(kms, null, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            URL _url = new URL(url);
            conn = (HttpsURLConnection) _url.openConnection();

            conn.setConnectTimeout(25000);
            conn.setReadTimeout(25000);
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("User-Agent", DEFAULT_USER_AGENT);
            conn.connect();

            out = conn.getOutputStream();
            out.write(data.getBytes(Charsets.UTF_8));
            out.flush();

            inputStream = conn.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, Charsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly(inputStream);
            if (conn != null) {
                conn.disconnect();
            }
        }
    }


}
