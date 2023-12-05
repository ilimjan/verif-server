package envers;

import org.hibernate.envers.RevisionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import ru.tkoinform.ppkverificationserver.model.CustomRevisionEntity;

public class CustomRevisionListener implements RevisionListener {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void newRevision(Object o) {
        CustomRevisionEntity entity = (CustomRevisionEntity) o;
        SecurityContext ctx = SecurityContextHolder.getContext();
        Authentication auth = ctx.getAuthentication();

        String userName = null;
        Object principal = auth.getPrincipal();
        if (principal instanceof Jwt) {
            Jwt jwt = (Jwt) auth.getPrincipal();
            userName = getResponsible(jwt);
        }

        entity.setUserName(userName);
    }

    private String getResponsible(Jwt jwt) {

        if (jwt != null) {

            /*String middleName = "";
            if (jwt.getClaimAsString("middle_name") != null) {
                middleName = jwt.getClaimAsString("middle_name");
            }*/

            String givenName = "";
            if (jwt.getClaimAsString("given_name") != null) {
                givenName = jwt.getClaimAsString("given_name");
            }

            String familyName = "";
            if (jwt.getClaimAsString("family_name") != null) {
                familyName = jwt.getClaimAsString("family_name");
            }
            return givenName
                    + " " + familyName;
        } else
            return null;
    }
}
