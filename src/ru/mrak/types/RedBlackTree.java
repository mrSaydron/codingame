package ru.mrak.types;

public class RedBlackTree<T extends Comparable> implements BinaryTree<T> {

    public static void main(String[] args) {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        tree.add(50);
        tree.add(40);
        tree.add(30);
        tree.add(20);
        tree.add(35);
        tree.add(35);
        tree.add(45);
        tree.add(45);
    }

    private Node<T> root;

    public RedBlackTree() {
        root = null;
    }

    @Override
    public void add(T element) {
        Node<T> newNode = addLeaf(element);
        resolution(newNode);
    }

    @Override
    public void delete(T element) {

    }

    @Override
    public void contains(T element) {
        Node node = new Node();
        node.isRed = false;


    }

    private void resolution(Node<T> node) {
        Case aCase = defineCase(node);
        Node<T> grandParent;
        Node<T> uncle;
        Node<T> parent;
        switch (aCase) {
            case ONE:
                node.isRed = false;
                break;
            case TWO:
                break;
            case THERE:
                node.parent.isRed = false;
                grandParent = node.getGrandParent();
                uncle = node.getUncle();
                grandParent.isRed = true;
                uncle.isRed = false;
                resolution(grandParent);
                break;
            case FOUR_LEFT:
                grandParent = node.getGrandParent();
                parent = node.parent;

                grandParent.lowChild = node;

                parent.parent = node;
                parent.highChild = null;

                node.parent = grandParent;
                node.lowChild = parent;

                resolution(parent);
                break;
            case FOUR_RIGHT:
                grandParent = node.getGrandParent();
                parent = node.parent;

                grandParent.highChild = node;

                parent.parent = node;
                parent.lowChild = null;

                node.parent = grandParent;
                node.highChild = parent;

                resolution(parent);
                break;
            case FIVE_LEFT:
                grandParent = node.getGrandParent();
                parent = node.parent;
                if(grandParent.isRoot()) {
                    parent.parent = null;
                    root = parent;
                } else {
                    Node<T> greatGrandParent = grandParent.parent;
                    greatGrandParent.changeChild(grandParent, parent);
                    parent.parent = greatGrandParent;
                }
                grandParent.parent = parent;
                grandParent.highChild = null;
                grandParent.isRed = true;

                parent.highChild = grandParent;
                parent.isRed = false;
                break;
            case FIVE_RIGHT:
                grandParent = node.getGrandParent();
                parent = node.parent;
                if(grandParent.isRoot()) {
                    parent.parent = null;
                    root = parent;
                } else {
                    Node<T> greatGrandParent = grandParent.parent;
                    greatGrandParent.changeChild(grandParent, parent);
                    parent.parent = greatGrandParent;
                }
                grandParent.parent = parent;
                grandParent.lowChild = null;
                grandParent.isRed = true;

                parent.highChild = grandParent;
                parent.isRed = false;
        }
    }

    private Case defineCase(Node node) {
        if (node == null || node.isRoot()) {
            return Case.ONE;
        }
        if (!node.parent.isRed) {
            return Case.TWO;
        }
        if (node.parent.isRed && node.getUncle() != null && node.getUncle().isRed) {
            return Case.THERE;
        }
        if (node.parent.isRed && (node.getUncle() == null || !node.getUncle().isRed)) {
            Node parent = node.parent;
            Node grandParent = node.getGrandParent();
            if(((parent.lowChild == node) != (grandParent.lowChild == parent)) && !(parent.lowChild == node)) {
                return Case.FOUR_LEFT;
            }
            if((parent.lowChild == node) != (grandParent.lowChild == parent)) {
                return Case.FOUR_RIGHT;
            }
            if(!(parent.lowChild == node)) {
                return Case.FIVE_LEFT;
            }
            return Case.FIVE_RIGHT;
        }
        throw new RuntimeException("Не предусмотренный случай поворота");
    }

    private Node<T> addLeaf(T element) {
        Node<T> find = root;
        Node<T> child;
        if(find != null) {
            while(true) {
                if(element.compareTo(find.element) < 0) {
                    if(find.lowChild == null) {
                        child = new Node<>();
                        child.element = element;
                        child.parent = find;
                        child.isRed = true;
                        find.lowChild = child;
                        break;
                    }
                    find = find.lowChild;
                } else {
                    if(find.highChild == null) {
                        child = new Node<>();
                        child.element = element;
                        child.parent = find;
                        child.isRed = true;
                        find.highChild = child;
                        break;
                    }
                    find = find.highChild;
                }
            }
        } else {
            child = new Node<>();
            child.element = element;
            child.isRed = true;
            root = child;
        }
        return child;
    }

    private static class Node<T> {
        T element;
        Node<T> parent;
        Node<T> lowChild;
        Node<T> highChild;
        boolean isRed;

        boolean isRoot() {
            return parent == null;
        }

        boolean hasGrandParent() {
            return parent != null && parent.parent != null;
        }

        Node<T> getParent() {
            return parent;
        }

        Node<T> getUncle() {
            Node<T> uncle = null;
            if(hasGrandParent()) {
                Node grand = parent.parent;
                if(grand.lowChild == parent) {
                    uncle = grand.highChild;
                } else {
                    uncle = grand.lowChild;
                }
            }
            return uncle;
        }

        Node<T> getGrandParent() {
            Node<T> grand = null;
            if(hasGrandParent()) {
                grand = parent.parent;
            }
            return grand;
        }

        Node<T> getSibling() {
            Node<T> sibling = null;
            if(parent != null) {
                if(parent.lowChild == this) {
                    sibling = parent.highChild;
                } else {
                    sibling = parent.lowChild;
                }
            }
            return sibling;
        }

        void changeChild(Node<T> oldChild, Node<T> newChild) {
            if(oldChild == lowChild) {
                lowChild = newChild;
            } else if(oldChild == highChild) {
                highChild = newChild;
            } else {
                throw new RuntimeException("Нет такого ребенка");
            }
        }
    }

    private enum Case{
        ONE,
        TWO,
        THERE,
        FOUR_LEFT,
        FOUR_RIGHT,
        FIVE_LEFT,
        FIVE_RIGHT
    }
}
