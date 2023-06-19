package dk.kb.webhooks.api.v1.impl;

import dk.kb.webhooks.api.v1.*;
import dk.kb.webhooks.config.ServiceConfig;
import dk.kb.webhooks.model.v1.ChallengeResponseDto;
import dk.kb.webhooks.model.v1.HelloReplyDto;

import dk.kb.util.webservice.exception.ServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import dk.kb.util.webservice.ImplBase;
import javax.ws.rs.core.Response;
import org.apache.cxf.jaxrs.ext.MessageContext;


/**
 * webhooks
 *
 * <p>webhooks by the Royal Danish Library
 *
 */
public class WebhooksApiServiceImpl extends ImplBase implements WebhooksApi {
    private Logger log = LoggerFactory.getLogger(this.toString());


    /**
     * Request a Hello World message, for testing purposes
     *
     * @param alternateHello: Optional alternative to using the word &#39;Hello&#39; in the reply
     *
     * @return <ul>
      *   <li>code = 200, message = "A JSON structure containing a Hello World message", response = HelloReplyDto.class</li>
      *   </ul>
      * @throws ServiceException when other http codes should be returned
      *
      * @implNote return will always produce a HTTP 200 code. Throw ServiceException if you need to return other codes
     */
    @Override
    public HelloReplyDto getGreeting(String alternateHello) throws ServiceException {
        try {
            HelloReplyDto response = new HelloReplyDto();
            response.setMessage(ServiceConfig.getHelloLines().get(0));
        return response;
        } catch (Exception e){
            throw handleException(e);
        }
    }

    @Override
    public ChallengeResponseDto challenge(String challenge) {
        log.debug("Received challenge "+challenge);
        ChallengeResponseDto response = new ChallengeResponseDto();
        response.setChallenge(challenge);
        return response;
    }

    @Override
    public void receiveWebhookRequests(String xExlSignature,String notification) {
        log.debug("Received webhook notification "+notification);
    }

    @Override
    public Response redirect(MessageContext request) {
        return WebhooksApi.super.redirect(request);
    }
}
