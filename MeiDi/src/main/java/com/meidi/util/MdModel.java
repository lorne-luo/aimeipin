package com.meidi.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * Created by luanpeng on 16/3/18.
 */
public class MdModel extends HashMap<String, Object> implements MdConstants {

    public MdModel(HttpServletRequest request) {
        HttpSession session = request.getSession();
        UserSession userSession = (UserSession) session.getAttribute(USER_SESSION);
        if (!MdCommon.isEmpty(userSession)) {
            this.put("wx_openid", userSession.getWx_openid());
            this.put("account", userSession.getAccount());
            this.put("bu_flag", userSession.getBu_flag());
        }
        
//        this.put("wx_openid", "obfnSs5n7HU9nRzfGwdJ0NEM3blQ"); //luotao
//        this.put("wx_openid", "obfnSs1ml4_otdFAZuc0-urgWwbA");
//        this.put("wx_openid", "obfnSs6hVB7yTVpeSLX9t7M0A2zE");
//        this.put("wx_openid", "dfgerhrthsh");

        this.put("PATH", PATH);
        this.put("IMAGE_FORMAL_URL", IMAGE_FORMAL_URL);
    }

    public String get(String key) {
        return super.get(key) == null ? "" : super.get(key).toString();
    }
}
