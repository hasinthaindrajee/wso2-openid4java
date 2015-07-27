/*
 * Copyright 2006-2008 Sxip Identity Corporation
 */

package org.openid4java.consumer;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Marius Scurtescu
 */
public class InMemoryConsumerAssociationStoreTest extends ConsumerAssociationStoreTest {
    public InMemoryConsumerAssociationStoreTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(InMemoryConsumerAssociationStoreTest.class);
    }

    protected ConsumerAssociationStore createStore() {
        return new InMemoryConsumerAssociationStore();
    }

    public void testCleanup() throws InterruptedException {
        super.testCleanup();

        InMemoryConsumerAssociationStore inMemoryAssociationStore = (InMemoryConsumerAssociationStore) _associationStore;
        assertEquals(1, inMemoryAssociationStore.size());
    }
}
