package org.appenders.log4j2.elasticsearch.failover;

/*-
 * #%L
 * log4j2-elasticsearch
 * %%
 * Copyright (C) 2019 Rafal Foltynski
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;

import java.io.IOException;

public class ByteBufSerializer extends JsonSerializer<ByteBuf> {

    @Override
    public Class<ByteBuf> handledType() {
        return ByteBuf.class;
    }

    @Override
    public void serializeWithType(ByteBuf byteBuf, JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(byteBuf, gen ,serializers);
    }

    @Override
    public void serialize(ByteBuf byteBuf, JsonGenerator gen, SerializerProvider serializers) throws IOException {

        byteBuf.resetReaderIndex();

        ByteBufInputStream inputStream = new ByteBufInputStream(byteBuf);
        gen.writeBinary(inputStream, byteBuf.writerIndex());

    }

}
