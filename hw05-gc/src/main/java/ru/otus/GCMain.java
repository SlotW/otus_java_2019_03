package ru.otus;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.MBeanServer;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import java.lang.management.*;
import java.util.List;
import java.util.Map;

/**
 * Created by Alexandr Byankin on 19.05.2019
 */

/*
0)
-Xms512m
-Xmx512m
Thread.sleep(0)
Serial Collector = 1 m 14 s
Parallel Collector = 35 s
G1 = 40 s

1)
-Xms512m
-Xmx512m
Thread.sleep(2)
Serial Collector = 6 m 1 s
Parallel Collector = 5 m 4 s
G1 = 5 m 18 s

2)
-Xms1024m
-Xmx1024m
Thread.sleep(2)
Serial Collector = 11 m 24 s
Parallel Collector = 10 m 9 s
G1 = 10 m 54 s
 */

public class GCMain {

    static Benchmark mbean;

    public static void main(String[] args) throws Exception {

        switchOnMonitoring();
        mbean = new Benchmark();
        mbean.run();
    }

    private static void switchOnMonitoring(){
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name:" + gcbean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    String gcName = info.getGcName();
                    String gcAction = info.getGcAction();
                    String gcCause = info.getGcCause();

                    long startTime = info.getGcInfo().getStartTime();
                    long duration = info.getGcInfo().getDuration();

                    System.out.println("start:" + startTime + " countAdd: " + String.valueOf(Benchmark.countAdd) + " Name: " + gcName + ", action:" + gcAction + ", gcCause:" + gcCause + "(" + duration + " ms)");

                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }

}
