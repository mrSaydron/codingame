package ru.mrak.types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Tree <T> {

    private Element root;
    private List<Element> elements;
    private Map<String, Predicate<T>> filters;

    public void addFilter(String name, Predicate<T> filter) {
        if(filters == null) {
            filters = new HashMap<>();
        }
        filters.put(name, filter);
    }

    public List<T> filtred(String filterName) {
        if(filters.containsKey(filterName)) {
            return elements.stream().map(Element::get).filter(filters.get(filterName)).collect(Collectors.toList());
        }
        return null;
    }

    public Element getRoot() {
        return root;
    }

    public List<Element> getElements() {
        return elements;
    }

    public Tree(T root) {
        this.root = new Element(root, null);
        elements = new ArrayList<>();
        elements.add(this.root);
    }

    public class Element {
        private List<Element> child;
        private T container;
        private Element parent;

        private Element(T container, Element parent) {
            this.container = container;
            this.parent = parent;
        }

        public void add(T element) {
            if (child == null) {
                child = new ArrayList<>();
            }
            Element newElement = new Element(element, this);
            child.add(newElement);
            Tree.this.elements.add(newElement);
        }

        public List<Element> getChild() {
            return child;
        }

        public T get() {
            return container;
        }

        public Element getParent() {
            return parent;
        }
    }
}
