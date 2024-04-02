/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package org.apache.hc.client5.http.impl;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.jupiter.api.Test;

/**
 * Tests {@link InMemoryDnsResolver}.
 */
public class InMemoryDnsResolverTest {

    @Test
    void resolve() throws UnknownHostException {
        final InMemoryDnsResolver resolver = new InMemoryDnsResolver();
        final String hostString = "127.0.0.1";
        final byte[] address = new byte[] { 127, 0, 0, 1 };
        resolver.add(hostString, InetAddress.getByAddress(hostString, address));
        final InetAddress[] result1 = resolver.resolve(hostString);
        assertEquals(1, result1.length);
        assertArrayEquals(address, result1[0].getAddress());
    }

    @Test
    void resolveIPv6ZoneId() throws UnknownHostException {
        final InMemoryDnsResolver resolver = new InMemoryDnsResolver();
        final String hostStr = "[fe80::221:b7ff:fe8a:57d5%en4]";
        final byte[] address = new byte[] { (byte) 0xfe, (byte) 0x80, 0, 0, 0, 0, 0, 0, 0x02, 0x21, (byte) 0xb7, (byte) 0xff, (byte) 0xfe, (byte) 0x8a, 0x57,
                (byte) 0xd5 };
        resolver.add(hostStr, InetAddress.getByAddress(hostStr, address));
        // This is an IPv6 address literal with zone ID
        final InetAddress[] result = resolver.resolve(hostStr);
        assertEquals(1, result.length);
        assertArrayEquals(address, result[0].getAddress());

    }

}
