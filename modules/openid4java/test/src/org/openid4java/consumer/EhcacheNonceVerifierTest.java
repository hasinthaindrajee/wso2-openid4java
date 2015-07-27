/*
 * Copyright 2006-2008 Sxip Identity Corporation
 */

package org.openid4java.consumer;

import junit.framework.Test;
import junit.framework.TestSuite;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

/**
 * @author Marius Scurtescu, Johnny Bufu
 */
public class EhcacheNonceVerifierTest extends AbstractNonceVerifierTest {
    private CacheManager _cacheManager;

    public EhcacheNonceVerifierTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(EhcacheNonceVerifierTest.class);
    }

    public void setUp() throws Exception {
        _cacheManager = new CacheManager();

        super.setUp();
    }

    public void tearDown() throws Exception {
        super.tearDown();

        _cacheManager = null;
    }

    public NonceVerifier createVerifier(int maxAge) {
        _cacheManager.removalAll();
        _cacheManager.addCache(new Cache("testCache", 100, false, false, maxAge, maxAge));

        EhcacheNonceVerifier nonceVerifier = new EhcacheNonceVerifier(maxAge);
        nonceVerifier.setCache(_cacheManager.getCache("testCache"));

        return nonceVerifier;
    }
}
