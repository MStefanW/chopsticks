package com.mstefanwalker.model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Node {

    private int[][] state; // [player][hand], [0][?] always to move, always size [2][2]

    private Node parent;

    private List<Node> children = new LinkedList<>();

    public Node( int[][] state, Node parent ) {
        this.state = state;
        this.parent = parent;
        remainder();
        sort();
        swap();
    }

    public Node( int h00, int h01, int h10, int h11, Node parent ) {
        this( new int[][]{{h00,h01},{h10,h11}}, parent );
    }

    public Node( int h00, int h01, int h10, int h11 ) {
        this( h00, h01, h10, h11, null );
    }

    public Node( int h00, int h01, int[] h1, Node parent ) {
        this( h00, h01, h1[0], h1[1], parent );
    }

    private void remainder() {
        for ( int h0 = 0; h0 < 2; h0++ )
            for ( int h1 = 0; h1 < 2; h1++ )
                state[h0][h1] %= 5;
    }

    private void sort() {
        for ( int[] player : state )
            Arrays.sort( player );
    }

    private void swap() {
        int[] swap = state[ 0 ];
        state[ 0 ] = state[ 1 ];
        state[ 1 ] = swap;
    }

    public int[][] getState() {
        return state;
    }

    public void addChild( Node child ) {
        this.children.add( child );
    }

    @Override
    public boolean equals( Object o ) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        Node node = (Node) o;
        return state[0][0] == node.state[0][0]
                && state[0][1] == node.state[0][1]
                && state[1][0] == node.state[1][0]
                && state[1][1] == node.state[1][1];
    }

    @Override
    public int hashCode() {
        int value = state[0][0];
        value = value * 29 + state[0][1];
        value = value * 31 + state[1][0];
        value = value * 37 + state[1][1];
        return value;
    }

    @Override
    public String toString() {
        return "" + state[0][0] + "" + state[0][1] + "-" + state[1][0] + "" + state[1][1];
    }
}
