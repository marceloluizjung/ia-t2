package com.algorithm;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Hello world!
 */
public class App {
    public static List<Position> positions = new ArrayList();
    public static HashMap<Position, Knot> map = new HashMap<>();
    public static File file;
    public static BufferedReader bufferedReader;

    public static void actionPerformed() throws IOException {
        JFileChooser f = new JFileChooser();
        f.setFileSelectionMode(JFileChooser.FILES_ONLY);
        f.showSaveDialog(null);
        file = f.getSelectedFile();
        bufferedReader = new BufferedReader(new FileReader(file));
        String linha = bufferedReader.readLine();
        linha = linha.replaceAll("\"", "");
        while (linha != null) {
            String[] mapLine = linha.split(";");
            //processar linha
            for (int count = 0; count < mapLine.length; count++) {
                String[] mapDefined = mapLine[count].split(",");

                Position position = new Position(Integer.parseInt(mapDefined[0].length() > 1 ? String.valueOf(mapDefined[0].toCharArray()[1]) : mapDefined[0]), Integer.parseInt(String.valueOf(mapDefined[1])));
                Knot knot = new Knot(mapDefined[2].equals("true") ? true : false);
                positions.add(position);
                map.put(position, knot);
            }
            linha = bufferedReader.readLine();
        }
        bufferedReader.close();
    }


    public static void main(String[] args) throws IOException {
        //Testar com um cenário trancado
        actionPerformed();

        //Map
        /*
        Knot knot11 = new Knot(false);
        Knot knot12 = new Knot(false);
        Knot knot13 = new Knot(true);
        Knot knot14 = new Knot(false);
        Knot knot15 = new Knot(false);

        Knot knot21 = new Knot(false);
        Knot knot22 = new Knot(false);
        Knot knot23 = new Knot(true);
        Knot knot24 = new Knot(false);
        Knot knot25 = new Knot(false);

        Knot knot31 = new Knot(false);
        Knot knot32 = new Knot(true);
        Knot knot33 = new Knot(true);
        Knot knot34 = new Knot(false);
        Knot knot35 = new Knot(false);

        Knot knot41 = new Knot(false);
        Knot knot42 = new Knot(false);
        Knot knot43 = new Knot(false);
        Knot knot44 = new Knot(false);
        Knot knot45 = new Knot(false);

        //Positions
        Position position11 = new Position(1, 1);
        positions.add(position11);
        Position position12 = new Position(1, 2);
        positions.add(position12);
        Position position13 = new Position(1, 3);
        positions.add(position13);
        Position position14 = new Position(1, 4);
        positions.add(position14);
        Position position15 = new Position(1, 5);
        positions.add(position15);

        Position position21 = new Position(2, 1);
        positions.add(position21);
        Position position22 = new Position(2, 2);
        positions.add(position22);
        Position position23 = new Position(2, 3);
        positions.add(position23);
        Position position24 = new Position(2, 4);
        positions.add(position24);
        Position position25 = new Position(2, 5);
        positions.add(position25);

        Position position31 = new Position(3, 1);
        positions.add(position31);
        Position position32 = new Position(3, 2);
        positions.add(position32);
        Position position33 = new Position(3, 3);
        positions.add(position33);
        Position position34 = new Position(3, 4);
        positions.add(position34);
        Position position35 = new Position(3, 5);
        positions.add(position35);

        Position position41 = new Position(4, 1);
        positions.add(position41);
        Position position42 = new Position(4, 2);
        positions.add(position42);
        Position position43 = new Position(4, 3);
        positions.add(position43);
        Position position44 = new Position(4, 4);
        positions.add(position44);
        Position position45 = new Position(4, 5);
        positions.add(position45);

        //Constrution map
        map.put(position11, knot11);
        map.put(position12, knot12);
        map.put(position13, knot13);
        map.put(position14, knot14);
        map.put(position15, knot15);

        map.put(position21, knot21);
        map.put(position22, knot22);
        map.put(position23, knot23);
        map.put(position24, knot24);
        map.put(position25, knot25);

        map.put(position31, knot31);
        map.put(position32, knot32);
        map.put(position33, knot33);
        map.put(position34, knot34);
        map.put(position35, knot35);

        map.put(position41, knot41);
        map.put(position42, knot42);
        map.put(position43, knot43);
        map.put(position44, knot44);
        map.put(position45, knot45);
*/
        //A*
        AtomicReference<Position> positionStart = new AtomicReference<>();
        positions.forEach(position -> {
            if (position.getLine() == 1 && position.getColumn() == 1) positionStart.set(position);
        });

        AtomicReference<Position> positionEnd = new AtomicReference<>();
        positions.forEach(position -> {
            if (position.getLine() == 1 && position.getColumn() == 5) positionEnd.set(position);
        });

        AStar aStar = new AStar(positions, map);
        List<Position> list = aStar.aStarAlgorithm(positionStart.get(), positionEnd.get());
        System.out.println("Caminho de menor custo (Algoritmo A*)");
        list.forEach(position -> {
            System.out.println("Linha:" + position.getLine() + " | Coluna:" + position.getColumn());
        });
    }
}
