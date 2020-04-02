package tree;

public class TrieTree implements Tree {
    int size = 0;
    Node root;
    
    private class Node {
        int value;
        Node[] childs = new Node[3];
    }
    
    @Override
    public boolean insert(int e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean remove(int e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean search(int e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
