package com.mstefanwalker.core;

import com.mstefanwalker.model.Node;
import com.mstefanwalker.model.Tree;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Test {

    private static float lowestCoverage = 100;

    private static float highestCoverage = 0;

    private static File file = new File("out");

    public static void main( String... args ) throws IOException {

        List<Node> exclude = new ArrayList<>();

        file.delete();
        file.createNewFile();

        for ( int h10 = 0; h10 < 5; h10++ )
            for ( int h11 = 0; h11 < 5; h11++ ) {
                Node candidate = new Node( 0,0, h10, h11 );
                if ( !exclude.contains( candidate ) ) exclude.add( candidate );
            }

        for ( int h00 = 0; h00 < 5; h00++ )
            for ( int h01 = 0; h01 < 5; h01++ ) {
                Node candidate = new Node( h00, h01, 0, 0 );
                if ( !exclude.contains( candidate ) ) exclude.add( candidate );
            }

        exclude.add( new Node( 0, 0, 0, 2 ) );
        exclude.add( new Node( 0, 0, 0, 4 ) );
        exclude.add( new Node( 0, 1, 4, 4 ) );
        exclude.add( new Node( 0, 2, 0, 3 ) );
        exclude.add( new Node( 0, 3, 2, 2 ) );
        exclude.add( new Node( 2, 0, 3, 3 ) );
        exclude.add( new Node( 0, 3, 0, 2 ) );
        exclude.add( new Node( 0, 4, 0, 1 ) );
        exclude.add( new Node( 0, 4, 1, 1 ) );

        for ( int i = 1; i < 5; i++ )
            test( new Node( i, i, i, i ) );

        List<Node> allNodes = new LinkedList<>();
        for ( int h00 = 0; h00 < 5; h00++ )
            for ( int h01 = 0; h01 < 5; h01++ )
                for ( int h10 = 0; h10 < 5; h10++ )
                    for ( int h11 = 0; h11 < 5; h11++ ) {
                        Node candidate = new Node( h00, h01, h10, h11 );
                        if ( !allNodes.contains( candidate ) && !exclude.contains( candidate ) )
                            allNodes.add( candidate );
                    }

        for ( Node node : allNodes )
            test( node );

        outputln( lowestCoverage + "% " + highestCoverage + "%" );
    }

    private static void test( Node root ) throws IOException {

        outputln( "\ntesting root " + root + "\n");

        Tree t = new Tree( root );

        t.grow();

        outputln( "" + t.getAllNodes() );

        float coverage = ((t.getAllNodes().size() * 100f) / (15*15));

        if ( coverage < lowestCoverage ) lowestCoverage = coverage;
        if ( coverage > highestCoverage ) highestCoverage = coverage;

        outputln( coverage + "% coverage" );
    }

    private static void outputln( String string ) throws IOException {
        Files.writeString( file.toPath(), string + "\n", StandardOpenOption.APPEND);
        System.out.println( string );
    }
}
