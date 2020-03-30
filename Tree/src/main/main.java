package main;

import tree.BinaryTree;
import tree.Tree;

public class main {
    public static void main(String[] args) {
        Tree t = new BinaryTree();
        t.insert(10);
        System.out.println(t.size());
    }
}
