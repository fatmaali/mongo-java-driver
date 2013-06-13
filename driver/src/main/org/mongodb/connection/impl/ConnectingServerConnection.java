/*
 * Copyright (c) 2008 - 2013 10gen, Inc. <http://10gen.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mongodb.connection.impl;

import org.bson.ByteBuf;
import org.mongodb.connection.Connection;
import org.mongodb.connection.ResponseBuffers;
import org.mongodb.connection.ResponseSettings;
import org.mongodb.connection.ServerAddress;
import org.mongodb.connection.ServerConnection;
import org.mongodb.connection.ServerConnectionState;
import org.mongodb.connection.ServerDescription;
import org.mongodb.connection.ServerType;

import java.util.List;

import static org.mongodb.assertions.Assertions.notNull;

class ConnectingServerConnection implements ServerConnection {
    private final Connection connection;
    private final ServerDescription serverDescription;

    public ConnectingServerConnection(final Connection connection) {
        this.connection = notNull("connection", connection);
        this.serverDescription = ServerDescription.builder().state(ServerConnectionState.Connecting).type(ServerType.Unknown)
                .address(connection.getServerAddress()).build();
    }

    @Override
    public ServerDescription getDescription() {
        return serverDescription;
    }

    @Override
    public void open() {
        connection.open();
    }

    @Override
    public void sendMessage(final List<ByteBuf> byteBuffers) {
        connection.sendMessage(byteBuffers);
    }

    @Override
    public ResponseBuffers receiveMessage(final ResponseSettings responseSettings) {
        return connection.receiveMessage(responseSettings);
    }

    @Override
    public void close() {
        connection.close();
    }

    @Override
    public boolean isClosed() {
        return connection.isClosed();
    }

    @Override
    public ServerAddress getServerAddress() {
        return connection.getServerAddress();
    }
}
