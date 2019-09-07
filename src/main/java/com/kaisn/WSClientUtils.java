package com.kaisn;

import com.kaisn.utils.PropertiesUtil;
import com.kaisn.utils.encry.SymmetricEncoder;
import com.kaisn.ws.EmployeeService;
import org.apache.commons.io.IOUtils;
import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.log4j.Logger;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.Properties;

public class WSClientUtils {


    private static Logger logger = Logger.getLogger(WSClientUtils.class);

    private static Object object = null;

    private static final String RESOURCE_PATH = "src/main/resources";

    public static Object getInstance(String serviceName,Class<?> serviceClass) {

        try {
            if(object != null){
                return object;
            }

            //读取WSDL的URL配置
            Properties properties = PropertiesUtil.getProperties("/properties/config.properties", PropertiesUtil.defaultType);
            String wsdlUrl=properties.getProperty("wsdl.url")+serviceName;

            JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
            factoryBean.setAddress(wsdlUrl);
            factoryBean.setServiceClass(serviceClass);
            object = factoryBean.create();
            Client proxy = ClientProxy.getClient(object);
            HTTPConduit conduit = (HTTPConduit) proxy.getConduit();
            TLSClientParameters tlsParams = conduit.getTlsClientParameters();
            if (tlsParams == null) {
                tlsParams = new TLSClientParameters();
            }
            tlsParams.setDisableCNCheck(true);
            //设置keystore
            tlsParams.setKeyManagers(getKeyManagers(properties));
            // 设置信任证书
            tlsParams.setTrustManagers(getTrustManagers(properties));
            conduit.setTlsClientParameters(tlsParams);

            return object;
        }catch (Exception e){
            logger.error("Failed to create webservice interface instance.",e);
            return null;
        }
    }

    public static KeyManager[] getKeyManagers(Properties properties) throws Exception {
        InputStream inputStream = null;
        try {
            // 获取默认的 X509算法
            String alg = KeyManagerFactory.getDefaultAlgorithm();
            // 创建密钥管理工厂
            KeyManagerFactory factory = KeyManagerFactory.getInstance(alg);

            //获取证书配置
            String keyStorePath=properties.getProperty("key.client.keystore.path");
            String keyStorePassword=properties.getProperty("key.client.keystore.password");
            //解密证书密码
            keyStorePassword = SymmetricEncoder.AESDncode(keyStorePassword);

            inputStream = new FileInputStream(RESOURCE_PATH+keyStorePath);

            // 构建以证书相应格式的证书仓库
            KeyStore ks = KeyStore.getInstance("JKS");
            // 加载证书
            ks.load(inputStream, keyStorePassword.toCharArray());
            factory.init(ks, keyStorePassword.toCharArray());
            KeyManager[] keyms = factory.getKeyManagers();
            return keyms;
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    public static TrustManager[] getTrustManagers(Properties properties) throws Exception {
        // 读取证书仓库输入流
        InputStream inputStream = null;
        try {
            // 信任仓库的默认算法X509
            String alg = TrustManagerFactory.getDefaultAlgorithm();
            // 获取信任仓库工厂
            TrustManagerFactory factory = TrustManagerFactory.getInstance(alg);

            //获取证书配置
            String keyStorePath=properties.getProperty("trust.client.keystore.path");
            String keyStorePassword=properties.getProperty("trust.client.keystore.password");
            //解密证书密码
            keyStorePassword = SymmetricEncoder.AESDncode(keyStorePassword);

            // 读取信任仓库
            inputStream = new FileInputStream(RESOURCE_PATH+keyStorePath);
            // 密钥类型
            KeyStore ks = KeyStore.getInstance("JKS");
            // 加载密钥
            ks.load(inputStream, keyStorePassword.toCharArray());
            factory.init(ks);
            TrustManager[] tms = factory.getTrustManagers();
            return tms;
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }
}
