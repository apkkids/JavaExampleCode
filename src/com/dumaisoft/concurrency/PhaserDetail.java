package com.dumaisoft.concurrency;

import java.util.concurrent.Phaser;

/**
 * Author:      wxb
 * Project:     JavaExampleCode
 * Create Date: 2017/7/9
 * Create Time: 21:39
 * Description:
 */
public class PhaserDetail {
    private volatile long state;

    private static final int MAX_PARTIES = 0xffff;
    private static final int MAX_PHASE = Integer.MAX_VALUE;
    private static final int PARTIES_SHIFT = 16;
    private static final int PHASE_SHIFT = 32;
    private static final int UNARRIVED_MASK = 0xffff;      // to mask ints
    private static final long PARTIES_MASK = 0xffff0000L; // to mask longs
    private static final long TERMINATION_BIT = 1L << 63;

    // some special values
    private static final int ONE_ARRIVAL = 1;
    private static final int ONE_PARTY = 1 << PARTIES_SHIFT;
    private static final int EMPTY = 1;

    // The following unpacking methods are usually manually inlined

    private static int unarrivedOf(long s) {
        int counts = (int) s;
        return (counts == EMPTY) ? 0 : counts & UNARRIVED_MASK;
    }

    private static int partiesOf(long s) {
        return (int) s >>> PARTIES_SHIFT;
    }

    private static int phaseOf(long s) {
        return (int) (s >>> PHASE_SHIFT);
    }

    private static int arrivedOf(long s) {
        int counts = (int) s;
        return (counts == EMPTY) ? 0 :
                (counts >>> PARTIES_SHIFT) - (counts & UNARRIVED_MASK);
    }

    public static void main(String[] args) {
        int parties = 65536;
        int r = parties >>> PARTIES_SHIFT;
        System.out.println(Integer.toBinaryString(r));

        parties = 5;
        int phase = 3;
        long state = ((long) phase << PHASE_SHIFT) | ((long) parties << PARTIES_SHIFT) | ((long) parties-1);
        System.out.println(Long.toBinaryString(state));

        System.out.println("partiesOf:" + partiesOf(state));
        System.out.println("phase:"+phaseOf(state));
        System.out.println("arrivedOf:"+arrivedOf(state));

        System.out.println(1<<8);


    }
}
