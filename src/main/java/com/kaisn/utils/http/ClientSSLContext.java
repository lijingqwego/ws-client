package com.kaisn.utils.http;

import com.kaisn.utils.PropertyUtil;
import com.kaisn.utils.encry.SymmetricEncoder;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.security.KeyStore;

public class ClientSSLContext
{

    private static final String RESOURCE_PATH = "src/main/resources";

    private static final String KEY_STORE_TYPE = "keystore.type";

    private static final String KEY_CLIENT_KEYSTORE_PATH = "key.client.keystore.path";

    private static final String KEY_CLIENT_KEYSTORE_PASSWORD = "key.client.keystore.password";

    private static final String TRUST_CLIENT_KEYSTORE_PATH = "trust.client.keystore.path";

    private static final String TRUST_CLIENT_KEYSTORE_PASSWORD = "trust.client.keystore.password";

    public SSLSocketFactory getSSLSocketFactory() throws Exception
    {

        SSLContext sslContext = SSLContext.getInstance("SSL");
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");

        //证书类型jks
        String keyStoreType = PropertyUtil.getProperty(KEY_STORE_TYPE);

        //获取证书配置kclient.keystore
        String keyClientKeystorePath = PropertyUtil.getProperty(KEY_CLIENT_KEYSTORE_PATH);
        String keyClientKeystorePassword = PropertyUtil.getProperty(KEY_CLIENT_KEYSTORE_PASSWORD);
        //解密证书密码
        keyClientKeystorePassword = SymmetricEncoder.AESDncode(keyClientKeystorePassword);

        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(new FileInputStream(RESOURCE_PATH+keyClientKeystorePath),
                keyClientKeystorePassword.toCharArray());
        kmf.init(keyStore, keyClientKeystorePassword.toCharArray());

        //获取证书配置tclient.keystore
        String trustClientKeystorePath=PropertyUtil.getProperty(TRUST_CLIENT_KEYSTORE_PATH);
        String trustClientKeystorePassword=PropertyUtil.getProperty(TRUST_CLIENT_KEYSTORE_PASSWORD);
        //解密证书密码
        trustClientKeystorePassword = SymmetricEncoder.AESDncode(trustClientKeystorePassword);

        KeyStore trustKeyStore = KeyStore.getInstance(keyStoreType);
        trustKeyStore.load(new FileInputStream(RESOURCE_PATH+trustClientKeystorePath),
                trustClientKeystorePassword.toCharArray());

        tmf.init(trustKeyStore);
        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
        HostnameVerifier hostnameVerifier = new HostnameVerifier()
        {
            public boolean verify(String arg0, SSLSession arg1)
            {
                return true;
            }
        };

        HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);

        return sslContext.getSocketFactory();
    }
}
