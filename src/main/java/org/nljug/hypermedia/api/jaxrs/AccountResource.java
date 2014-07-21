package org.nljug.hypermedia.api.jaxrs;

import com.theoryinpractise.halbuilder.api.Representation;
import com.theoryinpractise.halbuilder.api.RepresentationFactory;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.UriBuilder;
import org.nljug.hypermedia.api.resource.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Chris Quach
 */
@Path("account")
public class AccountResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountResource.class);
    private final Map<String, Account> resources = new HashMap<>();
    @Autowired
    private RepresentationFactory representationFactory;

    public AccountResource() {
        resources.put("1234", new Account("1234", "Willem van Oranje", 10125d));
        resources.put("5678", new Account("5678", "Balthasar Gerards", 100d));
    }

    @GET
    @Path("/{accountNumber}")
    @Produces(RepresentationFactory.HAL_JSON)
    public Representation get(@PathParam("accountNumber") String accountNumber) {
        final Account account = resources.get(accountNumber);
        if (account == null) {
            throw new NotFoundException();
        }

        return representationFactory.newRepresentation(getSelf(accountNumber))
                .withLink("deposit", "/account/deposit/" + accountNumber)
                .withLink("withdraw", "/account/withdraw/" + accountNumber)
                .withBean(account);
    }

    private URI getSelf(String... params) {
        try {
            return UriBuilder
                    .fromResource(AccountResource.class)
                    .path(AccountResource.class.getMethod("get", String.class))
                    .build((Object[]) params);
        } catch (NoSuchMethodException ex) {
            LOGGER.error("exception building URI", ex);
            throw new WebApplicationException();
        }

    }
}
