package implementations;

import utilities.BSTreeADT;
import utilities.Iterator;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class BSTree<E extends Comparable<? super E>> implements BSTreeADT<E> {

    private BSTreeNode<E> root;
    private int size;

    // Empty constructor
    public BSTree() {
        root = null;
        size = 0;
    }

    // Constructor with initial root element
    public BSTree(E element) {
        if (element == null) throw new NullPointerException();
        root = new BSTreeNode<>(element);
        size = 1;
    }

    @Override
    public BSTreeNode<E> getRoot() throws NullPointerException {
        if (root == null) throw new NullPointerException();
        return root;
    }

    @Override
    public int getHeight() {
        return height(root);
    }

    private int height(BSTreeNode<E> node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.getLeft()), height(node.getRight()));
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public boolean contains(E entry) throws NullPointerException {
        if (entry == null) throw new NullPointerException();
        return search(entry) != null;
    }

    @Override
    public BSTreeNode<E> search(E entry) throws NullPointerException {
        if (entry == null) throw new NullPointerException();
        return searchNode(root, entry);
    }

    private BSTreeNode<E> searchNode(BSTreeNode<E> node, E entry) {
        if (node == null) return null;

        int cmp = entry.compareTo(node.getElement());
        if (cmp == 0) return node;
        if (cmp < 0) return searchNode(node.getLeft(), entry);
        return searchNode(node.getRight(), entry);
    }

    @Override
    public boolean add(E newEntry) throws NullPointerException {
        if (newEntry == null) throw new NullPointerException();

        if (root == null) {
            root = new BSTreeNode<>(newEntry);
            size = 1;
            return true;
        }

        BSTreeNode<E> current = root;
        while (true) {
            int cmp = newEntry.compareTo(current.getElement());

            if (cmp == 0) {
                return false; // reject duplicates
            }
            else if (cmp < 0) {
                if (current.getLeft() == null) {
                    current.setLeft(new BSTreeNode<>(newEntry));
                    size++;
                    return true;
                }
                current = current.getLeft();
            }
            else {
                if (current.getRight() == null) {
                    current.setRight(new BSTreeNode<>(newEntry));
                    size++;
                    return true;
                }
                current = current.getRight();
            }
        }
    }

    @Override
    public BSTreeNode<E> removeMin() {
        if (root == null) return null;

        if (root.getLeft() == null) {
            BSTreeNode<E> min = root;
            root = root.getRight();
            size--;
            return min;
        }

        BSTreeNode<E> parent = root;
        BSTreeNode<E> current = root.getLeft();

        while (current.getLeft() != null) {
            parent = current;
            current = current.getLeft();
        }

        parent.setLeft(current.getRight());
        size--;
        return current;
    }

    @Override
    public BSTreeNode<E> removeMax() {
        if (root == null) return null;

        if (root.getRight() == null) {
            BSTreeNode<E> max = root;
            root = root.getLeft();
            size--;
            return max;
        }

        BSTreeNode<E> parent = root;
        BSTreeNode<E> current = root.getRight();

        while (current.getRight() != null) {
            parent = current;
            current = current.getRight();
        }

        parent.setRight(current.getLeft());
        size--;
        return current;
    }

    // ----------- ITERATORS -----------

    @Override
    public Iterator<E> inorderIterator() {
        ArrayList<E> list = new ArrayList<>();
        inorder(root, list);
        return new ListIterator<>(list);
    }

    private void inorder(BSTreeNode<E> node, ArrayList<E> list) {
        if (node == null) return;
        inorder(node.getLeft(), list);
        list.add(node.getElement());
        inorder(node.getRight(), list);
    }

    @Override
    public Iterator<E> preorderIterator() {
        ArrayList<E> list = new ArrayList<>();
        preorder(root, list);
        return new ListIterator<>(list);
    }

    private void preorder(BSTreeNode<E> node, ArrayList<E> list) {
        if (node == null) return;
        list.add(node.getElement());
        preorder(node.getLeft(), list);
        preorder(node.getRight(), list);
    }

    @Override
    public Iterator<E> postorderIterator() {
        ArrayList<E> list = new ArrayList<>();
        postorder(root, list);
        return new ListIterator<>(list);
    }

    private void postorder(BSTreeNode<E> node, ArrayList<E> list) {
        if (node == null) return;
        postorder(node.getLeft(), list);
        postorder(node.getRight(), list);
        list.add(node.getElement());
    }

    // ----------- INTERNAL LIST ITERATOR -----------

    private static class ListIterator<E> implements Iterator<E> {
        private ArrayList<E> list;
        private int index = 0;

        public ListIterator(ArrayList<E> list) {
            this.list = list;
        }

        @Override
        public boolean hasNext() {
            return index < list.size();
        }

        @Override
        public E next() throws NoSuchElementException {
            if (!hasNext()) throw new NoSuchElementException();
            return list.get(index++);
        }
    }
}
