package com.mstefanwalker.model;

import java.util.*;

public class Tree {

    private Node root;

    private Map<Node, Node> allNodes = new HashMap<>();

    public Tree( Node root ) {
        this.root = root;
    }

    public Collection<Node> getAllNodes() {
        return allNodes.values();
    }

    public void grow() {
        growRecursively( root, allNodes );
    }

    private void growRecursively( Node node, Map<Node, Node> visited ) {

        visited.put( node, node );

        int[][] state = node.getState();

        // all zero, game end, don't grow, return now
        if ( state[0][0] == 0 && state[0][1] == 0 )
            return;

        // one zero, other even, split!
        if ( state[0][0] == 0 && state[0][1] % 2 == 0 ) {
            int half = state[0][1] / 2;
            Node child = new Node( half, half, state[1][0], state[1][1], node );
            if ( visited.get( child ) == null ) {
                node.addChild( child );
                growRecursively( child, visited );
            } else {
                node.addChild( visited.get( child ) );
            }
        }

        // now try all combinations
        for ( int h0 = 0; h0 < 2; h0++ ) {
            if ( state[0][h0] == 0 ) continue;
            for ( int h1 = 0; h1 < 2; h1++ ) {
                if ( state[1][h1] == 0 ) continue;
                int[] changed = Arrays.copyOf( state[1], 2 );
                changed[h1] = changed[h1] + state[0][h0];
                Node child = new Node( state[0][0], state[0][1], changed, node );
                if ( visited.get( child ) == null ) {
                    node.addChild( child );
                    growRecursively( child, visited );
                } else {
                    node.addChild( visited.get( child ) );
                }
            }
        }
    }

    @Override
    public String toString() {
        return "" + root;
    }
}
