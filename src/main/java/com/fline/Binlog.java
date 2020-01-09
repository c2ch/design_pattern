package com.fline;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Binlog {

    static BinaryLogClient.EventListener eventListener = new BinaryLogClient.EventListener() {
        public void onEvent(Event event) {
            EventData data = event.getData();
            Instant instant = Instant.ofEpochMilli(event.getHeader().getTimestamp());
            ZoneId zone = ZoneId.systemDefault();
            System.out.println("执行日期：" + LocalDateTime.ofInstant(instant, zone));
            System.out.println("string:" + data.toString());

        }
    };
    static BinaryLogClient client = new BinaryLogClient("172.16.23.199", 3308, "fline", "flineCloud@2019root");

    public static void connect() throws Exception {
        EventDeserializer eventDeserializer = new EventDeserializer();
        eventDeserializer.setCompatibilityMode(
                EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG,
                EventDeserializer.CompatibilityMode.CHAR_AND_BINARY_AS_BYTE_ARRAY
        );
        client.setEventDeserializer(eventDeserializer);
        client.registerEventListener(eventListener);
        client.connect();

    }

    public static void main(String[] args) throws Exception {
        connect();
    }

//    public static void disconnect() throws Exception {
//        System.out.println(client);
//        client.unregisterEventListener(eventListener);
//        client.disconnect();
//    }
}
