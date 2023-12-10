package edu.project4.models;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Pixel {
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);
    private int r = 0;
    private int g = 0;
    private int b = 0;
    private int hitCount = 0;

    public void addColor(int r, int g, int b) {
        lock.writeLock().lock();

        try {
            if (hitCount == 0) {
                this.r = r;
                this.g = g;
                this.b = b;
            } else {
                this.r = (this.r + r) / 2;
                this.g = (this.g + g) / 2;
                this.b = (this.b + b) / 2;
            }

            hitCount++;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void correctColor(double brightness) {
        lock.writeLock().lock();

        try {
            this.r = (int) (this.r * brightness);
            this.g = (int) (this.g * brightness);
            this.b = (int) (this.b * brightness);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public int getR() {
        lock.readLock().lock();

        try {
            return r;
        } finally {
            lock.readLock().unlock();
        }
    }

    public int getG() {
        lock.readLock().lock();

        try {
            return g;
        } finally {
            lock.readLock().unlock();
        }
    }

    public int getB() {
        lock.readLock().lock();

        try {
            return b;
        } finally {
            lock.readLock().unlock();
        }
    }

    public int getHitCount() {
        lock.readLock().lock();

        try {
            return hitCount;
        } finally {
            lock.readLock().unlock();
        }
    }
}
