package dk.kb.webhooks.api.v1.impl;

import dk.kb.webhooks.api.v1.*;
import dk.kb.webhooks.config.ServiceConfig;
import dk.kb.webhooks.model.v1.BookDto;
import dk.kb.webhooks.model.v1.ErrorDto;
import java.io.File;
import dk.kb.webhooks.model.v1.HelloReplyDto;
import java.util.List;
import java.util.Map;

import dk.kb.util.webservice.exception.ServiceException;
import dk.kb.util.webservice.exception.InternalServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.io.File;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dk.kb.util.webservice.ImplBase;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Providers;
import javax.ws.rs.core.MediaType;
import org.apache.cxf.jaxrs.model.wadl.Description;
import org.apache.cxf.jaxrs.model.wadl.DocTarget;
import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.cxf.jaxrs.ext.multipart.*;

import io.swagger.annotations.Api;

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


}
