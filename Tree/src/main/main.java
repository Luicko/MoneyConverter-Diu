package main;

import tree.BinaryTree;

public class main {
    public static void main(String[] args) {
        BinaryTree t = new BinaryTree();
        t.insert(10);
        System.out.println(t.search(10));
    }
}
