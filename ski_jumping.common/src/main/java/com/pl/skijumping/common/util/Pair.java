package com.pl.skijumping.common.util;

public class Pair <t,k> {
    private t left;
    private k right;

    public Pair(t left, k right) {
        this.left = left;
        this.right = right;
    }

    public t getLeft() {
        return left;
    }

    public void setLeft(t left) {
        this.left = left;
    }

    public k getRight() {
        return right;
    }

    public void setRight(k right) {
        this.right = right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair<?, ?> pair = (Pair<?, ?>) o;

        if (left != null ? !left.equals(pair.left) : pair.left != null) return false;
        return right != null ? right.equals(pair.right) : pair.right == null;
    }

    @Override
    public int hashCode() {
        int result = left != null ? left.hashCode() : 0;
        result = 31 * result + (right != null ? right.hashCode() : 0);
        return result;
    }
}
