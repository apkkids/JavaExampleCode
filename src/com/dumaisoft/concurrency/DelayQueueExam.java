package com.dumaisoft.concurrency;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/5/15
 * Create Time: 21:39
 * Description:
 */
public class DelayQueueExam {
    public static void main(String[] args) throws InterruptedException {
        DelayQueue<DelayElement> queue = new DelayQueue<>();
        for (int i = 0; i < 10; i++) {
            queue.put(new DelayElement(1000 * i, "DelayElement" + i));
        }
        while (!queue.isEmpty()) {
            DelayElement delayElement = queue.take();
            System.out.println(delayElement.getName());
        }
    }

    static class DelayElement implements Delayed {
        private final long delay;
        private long expired;
        private final String name;

        DelayElement(int delay, String name) {
            this.delay = delay;
            this.name = name;
            expired = System.currentTimeMillis() + delay;
        }

        public String getName() {
            return name;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(expired - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            long d = (getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS));
            return (d == 0) ? 0 : ((d < 0) ? -1 : 1);
        }
    }
}
