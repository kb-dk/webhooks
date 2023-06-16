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
     * Add or update a single book
     *
     * @param BookDto: Add or update a single book
     *
     * @return <ul>
      *   <li>code = 200, message = "If the book was added successfully", response = BookDto.class</li>
      *   </ul>
      * @throws ServiceException when other http codes should be returned
      *
      * @implNote return will always produce a HTTP 200 code. Throw ServiceException if you need to return other codes
     */
    @Override
    public BookDto addBook(BookDto bookDto) throws ServiceException {
        // TODO: Implement...


        try {
            BookDto response = new BookDto();
        response.setId("DfBu3");
        response.setTitle("J38VG");
        response.setPages(1916529628);
        return response;
        } catch (Exception e){
            throw handleException(e);
        }

    }

    /**
     * Perform some image processing and return the result as an image
     *
     * @param image: The image to use as source for the colorization
     *
     * @param method: The algorithm used to colorize the image
     *
     * @param intensity: The intensity of the colorization
     *
     * @return <ul>
      *   <li>code = 200, message = "The colorized image", response = String.class</li>
      *   </ul>
      * @throws ServiceException when other http codes should be returned
      *
      * @implNote return will always produce a HTTP 200 code. Throw ServiceException if you need to return other codes
     */
    @Override
    public javax.ws.rs.core.StreamingOutput colorize( Attachment imageDetail, String method, Double intensity) throws ServiceException {
        // TODO: Implement...


        try {
            // Show download link in Swagger UI, inline when opened directly in browser
            setFilename("somefile", true, false);
            return output -> output.write("Magic".getBytes(java.nio.charset.StandardCharsets.UTF_8));
        } catch (Exception e){
            throw handleException(e);
        }

    }

    /**
     * Deletes metadata for a single book
     *
     * @param id: The ID for the book to delete
     *
     * @return <ul>
      *   <li>code = 200, message = "OK", response = String.class</li>
      *   <li>code = 404, message = "Not found", response = String.class</li>
      *   </ul>
      * @throws ServiceException when other http codes should be returned
      *
      * @implNote return will always produce a HTTP 200 code. Throw ServiceException if you need to return other codes
     */
    @Override
    public String deleteBook(String id) throws ServiceException {
        // TODO: Implement...


        try {
            String response = "Tgd5gI";
        return response;
        } catch (Exception e){
            throw handleException(e);
        }

    }

    /**
     * Sample OpenAPI definition for a service that constructs a PDF and delivers it
     *
     * @param id: The ID of the article to process
     *
     * @return <ul>
      *   <li>code = 200, message = "OK", response = String.class</li>
      *   <li>code = 404, message = "Article ID is unknown", response = ErrorDto.class</li>
      *   </ul>
      * @throws ServiceException when other http codes should be returned
      *
      * @implNote return will always produce a HTTP 200 code. Throw ServiceException if you need to return other codes
     */
    @Override
    public javax.ws.rs.core.StreamingOutput getArticle(String id) throws ServiceException {
        // TODO: Implement...


        try {
            // Show download link in Swagger UI, inline when opened directly in browser
            setFilename("somefile", true, false);
            return output -> output.write("Magic".getBytes(java.nio.charset.StandardCharsets.UTF_8));
        } catch (Exception e){
            throw handleException(e);
        }

    }

    /**
     * Retrieves metadata for a single book
     *
     * @param id: The ID for the book to retrieve
     *
     * @return <ul>
      *   <li>code = 200, message = "Structured representation of the Book.", response = BookDto.class</li>
      *   <li>code = 404, message = "Not found", response = String.class</li>
      *   </ul>
      * @throws ServiceException when other http codes should be returned
      *
      * @implNote return will always produce a HTTP 200 code. Throw ServiceException if you need to return other codes
     */
    @Override
    public BookDto getBook(String id) throws ServiceException {
        // TODO: Implement...


        try {
            BookDto response = new BookDto();
        response.setId("XLoN9");
        response.setTitle("QRMypQ");
        response.setPages(579145062);
        return response;
        } catch (Exception e){
            throw handleException(e);
        }

    }

    /**
     * Delivers metadata on books
     *
     * @param query: Search query for the books
     *
     * @param max: The maximum number of books to return
     *
     * @param format: The delivery format. This can also be specified using headers, as seen in the Responses section. If both headers and format are specified, format takes precedence.  * JSONL: Newline separated single-line JSON representations of Books * JSON: Valid JSON in the form of a single array of Books * XML: Valid XML in the form of a single container with Books * CSV: Comma separated, missing values represented with nothing, strings encapsulated in quotes
     *
     * @return <ul>
      *   <li>code = 200, message = "OK", response = String.class</li>
      *   <li>code = 400, message = "HTTP 400: Bad request", response = String.class</li>
      *   </ul>
      * @throws ServiceException when other http codes should be returned
      *
      * @implNote return will always produce a HTTP 200 code. Throw ServiceException if you need to return other codes
     */
    @Override
    public javax.ws.rs.core.StreamingOutput getBooks(String query, Long max, String format) throws ServiceException {
        // TODO: Implement...


        try {
            // Show download link in Swagger UI, inline when opened directly in browser
            setFilename("somefile", true, false);
            return output -> output.write("Magic".getBytes(java.nio.charset.StandardCharsets.UTF_8));
        } catch (Exception e){
            throw handleException(e);
        }

    }

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
        // TODO: Implement...


        try {
            HelloReplyDto response = new HelloReplyDto();
            response.setMessage(ServiceConfig.getHelloLines().get(0));
        return response;
        } catch (Exception e){
            throw handleException(e);
        }

    }


}
