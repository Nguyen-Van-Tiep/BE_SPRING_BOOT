package com.example.demo.model.utilities;

import com.example.demo.config.exception.BusinessException;
import com.example.demo.enums.ErrorCode;
import com.example.demo.model.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.net.ssl.*;
import java.security.cert.X509Certificate;

public class SercurityUtils {
    public static void disableSSLVerification() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            TrustManager[] trustManager = new TrustManager[] { new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            } };
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustManager, null);
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
        }
    }

    public static String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
    public static Long getCurrentUserId() throws BusinessException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserPrincipal) {
            return ((UserPrincipal) principal).getId();
        }
        throw new BusinessException(ErrorCode.USER_NOT_FOUND);
    }
    public static UserPrincipal getPrincipal() {
        return (UserPrincipal) (SecurityContextHolder.getContext()).getAuthentication().getPrincipal();
    }
}

