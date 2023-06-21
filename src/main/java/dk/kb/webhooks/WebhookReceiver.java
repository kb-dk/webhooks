package dk.kb.webhooks;

import dk.kb.alma.gen.webhooks.Notification;
import dk.kb.webhooks.config.ServiceConfig;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class WebhookReceiver {
    private static Logger log = LoggerFactory.getLogger(WebhookReceiver.class);
    public static void validateRequestSignature(String body, String signature) {
        String hash = createHash(body, ServiceConfig.getWebhookSecret());
        if(!hash.equals(signature)){
            //TODO handle error
        }
    }

    public static void handleNotification(String notificationString) {

        Notification notification = getNotificationObjectFromString(notificationString);
        switch (notification.getAction()) {
            case "REQUEST":
                handleRequestNotification(notification);
                break;
            default:
                log.info("Webhook action '{}' not supported.",notification.getAction());
        }
    }

    public static Notification getNotificationObjectFromString(String notificationString) {
        //TODO: implement
        Notification notification = new Notification();
        return notification;
    }

    public static void handleRequestNotification(Notification notification) {
        /* TODO
            if shouldSomethingBeCreatedInMaestro(notification)
                createSomethingInMaestro(notification)
            if shouldSomethingBeClosedInMaestro(notification)
                closeSomethingInMaestro(notification)
            else
                do nothing
         */
    }

    private static String createHash(String body, String secret) {
 /*
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            mac.init(key);
            return Base64.encodeBase64String(mac.doFinal(body.getBytes()));
        } catch (Exception e) {
            //TODOhandle error
            return "error";
        }
        */
        return "todo";
    }

}
