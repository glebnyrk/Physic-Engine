package ru.nyrk.BVH;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class BVHIterator implements Iterator<BVHChild> {
    private final Stack<BVHChild> stack = new Stack<>();
    public BVHIterator(BVHChild root) {
        if (root != null) {
            stack.push(root);
        }
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public BVHChild next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        BVHChild current = stack.pop();

        // Если узел — это BVHTreePart, добавляем детей в стек
        if (current instanceof BVHTreePart part) {
            if (part.getRightChild() != null) {
                stack.push(part.getRightChild());
            }
            if (part.getLeftChild() != null) {
                stack.push(part.getLeftChild());
            }
        }

        return current;
    }
}
