package com.kaisn.utils.http;

import com.kaisn.utils.PropertyUtil;
import com.kaisn.utils.encry.SymmetricEncoder;
import org.apache.commons.io.IOUtils;

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

    public static SSLSocketFactory getSSLSocketFactory() throws Exception
    {
        // 实例化密钥库
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        // 获取证书配置
        String keyClientKeystorePath = PropertyUtil.getProperty(KEY_CLIENT_KEYSTORE_PATH);
        String keyClientKeystorePassword = PropertyUtil.getProperty(KEY_CLIENT_KEYSTORE_PASSWORD);
        // 解密证书密码
        keyClientKeystorePassword = SymmetricEncoder.AESDncode(keyClientKeystorePassword);
        // 获得密钥库kclient.keystore
        KeyStore keyStore = getKeyStore(RESOURCE_PATH+keyClientKeystorePath,keyClientKeystorePassword);
        // 初始化密钥工厂
        kmf.init(keyStore, keyClientKeystorePassword.toCharArray());

        // 实例化信任库
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        // 获取证书配置tclient.keystore
        String trustClientKeystorePath=PropertyUtil.getProperty(TRUST_CLIENT_KEYSTORE_PATH);
        String trustClientKeystorePassword=PropertyUtil.getProperty(TRUST_CLIENT_KEYSTORE_PASSWORD);
        // 解密证书密码
        trustClientKeystorePassword = SymmetricEncoder.AESDncode(trustClientKeystorePassword);
        // 获得信任库
        KeyStore trustKeyStore = getKeyStore(RESOURCE_PATH+trustClientKeystorePath,trustClientKeystorePassword);
        // 初始化信任库
        tmf.init(trustKeyStore);

        // 实例化SSL上下文
        SSLContext sslContext = SSLContext.getInstance("TLS");
        // 初始化SSL上下文
        sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

        return sslContext.getSocketFactory();
    }

    /**
     * 获得KeyStore.
     * @param keyStorePath 密钥库路径
     * @param password  密码
     * @return 密钥库
     * @throws Exception
     */
    public static KeyStore getKeyStore(String keyStorePath, String password)
            throws Exception {
        // 实例化密钥库
        KeyStore keyStore = KeyStore.getInstance("JKS");
        // 获得密钥库文件流
        FileInputStream inputStream = new FileInputStream(keyStorePath);
        // 加载密钥库
        keyStore.load(inputStream, password.toCharArray());
        // 关闭密钥库文件流
        IOUtils.closeQuietly(inputStream);
        return keyStore;
    }
}
