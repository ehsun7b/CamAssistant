package com.ehsunbehravesh.dropbox;

import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

/**
 *
 * @author Ehsun Behravesh
 */
public class DropboxLogin {

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(DropboxLogin.class);
    
    private String appKey;
    private String appSecret;
    private String userAgent;
    private DbxWebAuthNoRedirect webAuth;

    public DropboxLogin() {
        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("dropbox.properties")) {
            Properties dropboxConfig = new Properties();
            dropboxConfig.load(is);
            appKey = dropboxConfig.getProperty("app_key");
            appSecret = dropboxConfig.getProperty("app_secret");
            userAgent = dropboxConfig.getProperty("user_agent");
        } catch (Exception ex) {
            log.error("Error in loading Dropbox app config parameters. File dropbox.properties must be in the classpath.", ex);
        }
    }

    public String start() {
        DbxAppInfo appInfo = new DbxAppInfo(appKey, appSecret);

        DbxRequestConfig config = new DbxRequestConfig(userAgent, Locale.getDefault().toString());
        webAuth = new DbxWebAuthNoRedirect(config, appInfo);
        return webAuth.start();
    }
    
    public String finish(String code) throws DbxException {
        DbxAuthFinish finish = webAuth.finish(code);
        return finish.accessToken;
    }
    
    public static void main(String[] args) {
        DropboxLogin dropboxLogin = new DropboxLogin();
        System.out.println(dropboxLogin.start());
    }
}
