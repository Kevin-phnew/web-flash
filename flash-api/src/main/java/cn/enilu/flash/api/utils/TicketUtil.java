package cn.enilu.flash.api.utils;

import com.crystaldecisions.sdk.framework.CrystalEnterprise;
import com.crystaldecisions.sdk.framework.IEnterpriseSession;
import com.crystaldecisions.sdk.framework.ISessionMgr;

import javax.net.ssl.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URL;
import java.security.KeyStore;

public class TicketUtil {
    public static void main(String[] args) {
        String qlikTicket = TicketUtil.getQlikTicket("aaa", "xxx");
    }


    public static String getQlikTicket(String UserId, String UserDirectory) {
        //todo 配置到文件中或字典中
        String xrfkey = "7rBHABt65vFflaZ7"; //Xrfkey to prevent cross-site issues
        String host = "QlikSenseServerHostName"; //Enter the Qlik Sense Server hostname here
        String vproxy = "VirtualProxyPrefix"; //Enter the prefix for the virtual proxy configured in Qlik Sense Steps Step 1
        try {

            /************** BEGIN Certificate Acquisition **************/
            String certFolder = "c:\\javaTicket\\"; //This is a folder reference to the location of the jks files used for securing ReST communication
            String proxyCert = certFolder + "client.jks"; //Reference to the client jks file which includes the client certificate with private key
            String proxyCertPass = "secret"; //This is the password to access the Java Key Store information
            String rootCert = certFolder + "root.jks"; //Reference to the root certificate for the client cert. Required in this example because Qlik Sense certs are used.
            String rootCertPass = "secret"; //This is the password to access the Java Key Store information
            /************** END Certificate Acquisition **************/

            /************** BEGIN Certificate configuration for use in connection **************/
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream(new File(proxyCert)), proxyCertPass.toCharArray());
            KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            kmf.init(ks, proxyCertPass.toCharArray());
            SSLContext context = SSLContext.getInstance("SSL");
            KeyStore ksTrust = KeyStore.getInstance("JKS");
            ksTrust.load(new FileInputStream(rootCert), rootCertPass.toCharArray());
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(ksTrust);
            context.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            SSLSocketFactory sslSocketFactory = context.getSocketFactory();
            /************** END Certificate configuration for use in connection **************/


            /************** BEGIN HTTPS Connection **************/
            System.out.println("Browsing to: " + "https://" + host + ":4243/qps/" + vproxy + "/ticket?xrfkey=" + xrfkey);
            URL url = new URL("https://" + host + ":4243/qps/" + vproxy + "/ticket?xrfkey=" + xrfkey);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setSSLSocketFactory(sslSocketFactory);
            connection.setRequestProperty("x-qlik-xrfkey", xrfkey);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("POST");
            /************** BEGIN JSON Message to Qlik Sense Proxy API **************/


            String body = "{ 'UserId':'" + UserId + "','UserDirectory':'" + UserDirectory + "',";
            body += "'Attributes': [],";
            body += "}";
            System.out.println("Payload: " + body);
            /************** END JSON Message to Qlik Sense Proxy API **************/


            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(body);
            wr.flush(); //Get the response from the QPS BufferedReader
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                builder.append(inputLine);
            }
            in.close();
            String data = builder.toString();
            System.out.println("The response from the server is: " + data);
            return data;
            /************** END HTTPS Connection **************/
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return null;
        }
    }

    public static String getWcaToken(HttpServletRequest request) throws Exception {
        String wcaToken = "";
        HttpSession session = request.getSession();
        ISessionMgr sessionMgr;
        IEnterpriseSession entsession = (IEnterpriseSession) request.getSession().getAttribute("boesession");
        if (entsession == null) {
            sessionMgr = CrystalEnterprise.getSessionMgr();
            //以下userName,password需要改为自己bo登录页面的用户名和密码,ip也要修改为bo所在服务器的ip；
            String userName = "？？？", passWord = "？？？", ip = "138.6.4.170";
            entsession = sessionMgr.logon(userName, passWord, ip + ":6400", "secEnterprise");
            session.setAttribute("boesession", entsession);
        }
        request.getSession().setAttribute("boesession", entsession);
		/* 其中createLogonToken(java.lang.String clientComputerName, int validMinutes, int validNumOfLogons)
		   clientComputerName为使用这个token的客户端计算机名，空字符串表示该token可被任何客户端使用；
		   validMinutes为token的有效时间（分钟）；
		   validNumOfLogons 表示该token允许被使用的最大次数。*/
        wcaToken = entsession.getLogonTokenMgr().createLogonToken("", 1000, 1000);
//		wcaToken = entsession.getLogonTokenMgr().createWCAToken("", 1000,1000);//既然提示过期的方法，就可能有问题
        return wcaToken;
    }
}