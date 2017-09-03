package com.dumaisoft.zhihu;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/7/1
 * Create Time: 15:43
 * Description:
 */
public class Demo5 {
    public static void main(String[] args) {
        Product p = new Product();
        Producer producer = new Producer(p);
        Consumer consumer = new Consumer(p);
        producer.start();
        consumer.start();
    }

    private static class Product {
        String name;
        double price;
        boolean flag = false;
    }

    private static class Consumer extends Thread {
        Product p;

        public Consumer(Product p) {
            this.p = p;
        }

        @Override
        public void run() {
            int i =0;
            while (true) {
                synchronized (p) {
                    if (p.flag == true) {
                        System.out.println("Consumer has consumed " + p.name + ", price = " + p.price);
                        p.flag = false;
                        i++;
                        p.notifyAll();
                        if (i>=10)
                            return;
                    } else {
                        try {
                            p.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private static class Producer extends Thread {
        Product p;

        public Producer(Product p) {
            this.p = p;
        }

        @Override
        public void run() {
            int i = 0;
            while (true) {
                synchronized (p) {
                    if (p.flag == false) {
                        if (i % 2 == 0) {
                            p.name = "Apple";
                            p.price = 6.5;
                        } else {
                            p.name = "Banana";
                            p.price = 2.0;
                        }
                        System.out.println("Producer has produced " + p.name + ", price = " + p.price);
                        p.flag = true;
                        i++;
                        p.notifyAll();
                        if (i == 10) {
                            return;
                        }
                    } else {
                        try {
                            p.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
