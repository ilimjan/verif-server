package ru.tkoinform.ppkverificationserver.configuration.atjwt;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.SecurityContext;

import java.security.Key;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CustomJWSAlgorithmMapJWSKeySelector<C extends SecurityContext> implements JWSKeySelector<C> {
    private Map<JWSAlgorithm, JWSKeySelector<C>> jwsKeySelectors;

    public CustomJWSAlgorithmMapJWSKeySelector(Map<JWSAlgorithm, JWSKeySelector<C>> jwsKeySelectors) {
        this.jwsKeySelectors = jwsKeySelectors;
    }

    public List<? extends Key> selectJWSKeys(JWSHeader header, C context) throws KeySourceException {
        JWSKeySelector<C> keySelector = (JWSKeySelector)this.jwsKeySelectors.get(header.getAlgorithm());
        if (keySelector == null) {
            throw new IllegalArgumentException("Unsupported algorithm of " + header.getAlgorithm());
        } else {
            return keySelector.selectJWSKeys(header, context);
        }
    }

    public Set<JWSAlgorithm> getExpectedJWSAlgorithms() {
        return this.jwsKeySelectors.keySet();
    }
}
