package com.dumaisoft.zhihu;

/**
 * Created by wxb on 2019/1/11 0011.
 */
class producer implements Runnable {
    private Message msg;
    public producer(Message msg) {
        this.msg = msg;
    }

    @Override
    public void run() {
        for (int x = 0; x < 1000; x++) {
            if (x % 2 == 0) {
                this.msg.set("11", "aa");
                try {
                    Thread.sleep(100); //延时操作
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                this.msg.set("22", "bb");
            }
        }
    }
}

class consumer implements Runnable {
    private Message msg;
    public consumer(Message msg) {
        this.msg = msg;
    }

    @Override
    public void run() {
        for (int x = 0; x < 100; x++) {
            try {
                Thread.sleep(100); //延时操作
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(this.msg.get());
        }
    }
}

class Message {
    private String title;
    private String content;
    private boolean flag = true;
    public synchronized void set(String title, String content) {
        if (this.flag == false) {
            try {
                super.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.title = title;
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.content = content;
        this.flag = false;
        super.notify();
    }

    public synchronized String get() {
        if (this.flag = true) {
            try {
                super.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            return this.title + "--" + this.content;
        } finally {
            this.flag = true;
            super.notify();
        }
    }
}

public class TestDemo {
    public static void main(String[] args) throws Exception {
        Message msg = new Message();
        new Thread(new producer(msg)).start();
        new Thread(new consumer(msg)).start();
    }
}