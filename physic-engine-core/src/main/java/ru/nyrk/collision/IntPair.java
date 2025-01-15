package ru.nyrk.collision;

class IntPair {
    public int first;
    public int second;
    public IntPair(int first, int second) {
        this.first = Math.min(first, second);
        this.second = Math.max(first, second);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof IntPair pair && pair.first == first && pair.second == second;
    }
}
