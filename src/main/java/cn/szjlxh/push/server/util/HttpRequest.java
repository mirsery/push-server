package cn.szjlxh.push.server.util;

import cn.szjlxh.push.server.broadcast.message.ResponseMsg;
import cn.szjlxh.push.server.broadcast.message.ThreeDMsg;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by mirsery on 28/12/2016.
 */
public class HttpRequest {

    private static final Logger log = LoggerFactory.getLogger(HttpRequest.class);

    private static Gson gson = new Gson();

    public void sendPost(String url, ThreeDMsg content, CallBack callBack) {
        BufferedReader in = null;
        DataOutputStream out = null;
        String result = "";
        HttpURLConnection conn = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            conn = (HttpURLConnection) realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("hh", "hh");
            conn.setRequestMethod("POST");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setReadTimeout(10000);

            out = new DataOutputStream(conn.getOutputStream());

            out.write(("data=" + URLEncoder.encode(content.toString(), "utf8")).toString().getBytes());

            out.flush();
            conn.connect();

            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }

            log.info("receive the result :" + result);

            ResponseMsg responseMsg = gson.fromJson(result, ResponseMsg.class);

            log.info("receive the responseMsg :" + responseMsg);

            if ("1".equals(responseMsg.getStatus())) {//成功
                callBack.onSuccess(content);
            } else {
                callBack.onError(content);
            }

        } catch (Exception e) {
            log.error("发送 POST 请求出现异常！" + e);
            callBack.onError(content);
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                conn.disconnect();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}