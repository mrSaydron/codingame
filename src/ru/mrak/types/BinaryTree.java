package ru.mrak.types;

public interface BinaryTree<T extends Comparable> {
    void add(T element);
    void delete(T element);
    void contains(T element);
}
