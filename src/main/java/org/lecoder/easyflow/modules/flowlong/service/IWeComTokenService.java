package org.lecoder.easyflow.modules.flowlong.service;


import org.lecoder.easyflow.common.entity.WeComTokenRequest;

/**
 * @author Administrator
 */
public interface IWeComTokenService {

    String getTokenByRequest(WeComTokenRequest tokenRequest);

    String getToken(String secret);

}
