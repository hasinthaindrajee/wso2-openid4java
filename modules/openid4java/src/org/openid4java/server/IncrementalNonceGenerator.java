/*
 * Copyright 2006-2008 Sxip Identity Corporation
 */

package org.openid4java.server;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openid4java.util.InternetDateFormat;

import java.util.Date;

/**
 * @author Marius Scurtescu, Johnny Bufu
 */
public class IncrementalNonceGenerator implements NonceGenerator {
    private static Log _log = LogFactory.getLog(IncrementalNonceGenerator.class);
    private static final boolean DEBUG = _log.isDebugEnabled();

    private static InternetDateFormat _dateFormat = new InternetDateFormat();

    private String _timestamp = "";
    private String _nodeTimestamp = "";

    private int _counter = 0;

    public IncrementalNonceGenerator() {
        _nodeTimestamp = Long.toString(new Date().getTime());

    }


    public synchronized String next() {
        String currentTimestamp = getCurrentTimpestamp();

        if (_timestamp.equals(currentTimestamp)) {
            _counter++;
        } else {
            _timestamp = currentTimestamp;
            _counter = 0;
        }

        String nonce = _timestamp + Integer.toString(_counter) + _nodeTimestamp;

        if (DEBUG) {
            _log.debug("Generated nonce: " + nonce);
        }

        return nonce;

    }

    private String getCurrentTimpestamp() {
        Date now = new Date();

        return _dateFormat.format(now);
    }
}
