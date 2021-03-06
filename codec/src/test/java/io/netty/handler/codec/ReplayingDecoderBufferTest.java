/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package io.netty.handler.codec;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import io.netty.util.Signal;

import org.junit.Test;

public class ReplayingDecoderBufferTest {
    
    /**
     * See https://github.com/netty/netty/issues/445
     */
    @Test
    public void testGetUnsignedByte() {
        ReplayingDecoderBuffer buffer = new ReplayingDecoderBuffer(Unpooled.copiedBuffer("TestBuffer", CharsetUtil.ISO_8859_1));

        boolean error;
        int i = 0;
        try {
            for (;;) {
                buffer.getUnsignedByte(i);
                i++;
            }
        } catch (Signal e) {
            error = true;
        }

        assertTrue(error);
        assertEquals(10, i);
    }

    /**
     * See https://github.com/netty/netty/issues/445
     */
    @Test
    public void testGetByte() {
        ReplayingDecoderBuffer buffer = new ReplayingDecoderBuffer(Unpooled.copiedBuffer("TestBuffer", CharsetUtil.ISO_8859_1));

        boolean error;
        int i = 0;
        try {
            for (;;) {
                buffer.getByte(i);
                i++;
            }
        } catch (Signal e) {
            error = true;
        }

        assertTrue(error);
        assertEquals(10, i);
    }
    
    /**
     * See https://github.com/netty/netty/issues/445
     */
    @Test
    public void testGetBoolean() {
        ByteBuf buf = Unpooled.buffer(10);
        while(buf.writable()) {
            buf.writeBoolean(true);
        }
        ReplayingDecoderBuffer buffer = new ReplayingDecoderBuffer(buf);

        boolean error;
        int i = 0;
        try {
            for (;;) {
                buffer.getBoolean(i);
                i++;
            }
        } catch (Signal e) {
            error = true;
        }

        assertTrue(error);
        assertEquals(10, i);
    }

}
