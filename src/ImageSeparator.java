

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;


import javax.imageio.ImageIO;

public class ImageSeparator {

    private BufferedImage image;

    private boolean visited[][];

    private boolean pattern_store = Boolean.FALSE;

    private Color pattern_color = Color.WHITE;

    private int delta_x[] = {-1, 0, 1, 1, 1, 0, -1, -1};
    private int delta_y[] = {-1, -1, -1, 0, 1, 1, 1, 0};

    private Set<Tuple> shapePoints;

    public ImageSeparator(){
        pattern_store = false;
    }

    public ImageSeparator(boolean isStorable){
        this.pattern_store = isStorable;
    }

    public ImageSeparator(BufferedImage image) {
        this.pattern_store = false;
        this.image = image;
    }

    public ImageSeparator(BufferedImage image, boolean isStorable) {
        this.image = image;
        this.pattern_store = isStorable;
    }

    public ImageSeparator(String path) {
        pattern_store = false;
        try {
            this.image = ImageIO.read(new File(ImageSeparator.class.getResource(path).getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ImageSeparator(String path, boolean isStorable) {
        this.pattern_store = isStorable;
        try {
            this.image = ImageIO.read(new File(ImageSeparator.class.getResource(path).getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //not recommended to change
    public void setPattern_color(Color color) {
        this.pattern_color = color;
    }

    void reloadImage(String path){
        try {
            this.image = ImageIO.read(new File(ImageSeparator.class.getResource(path).getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isSameShape(Shape shape1, Shape shape2) throws Exception{
        return Helper.getHash(shape1) == Helper.getHash(shape2);
    }

    private void printShape(Shape origin){
        Shape<Integer> shape = (Shape<Integer>) origin;
        for(int i = 0; i < shape.getN(); i++){
            for(int j = 0; j < shape.getM(); j++){
                System.out.print(shape.getShapeMatrix()[i][j] == 1 ? "1" : " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private boolean isNewShape(Shape<Integer> currentShape, String path) {
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            try {
                Shape testingShape = Serializer.deserialize("resources/semi_patterns/"+file.getName());
                if(isSameShape(currentShape, testingShape)){
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public LinkedList<Shape<Integer>> run() {
        LinkedList<Shape<Integer>> ans = new LinkedList<>();
        shapePoints = new HashSet<>();
        int lines = image.getHeight();
        int columns = image.getWidth();
        visited = new boolean[columns][lines];

        for (int i = 0; i < lines; i++) {
            for (int j = 0; j < columns; j++) {
                Color c = new Color(image.getRGB(j, i));
                if (c.equals(Color.WHITE) && !visited[j][i]) {
                    shapePoints.clear();
                    dfs(j, i, columns, lines);
                    Shape<Integer> currentShape = getShape(shapePoints);
                    ans.add(currentShape);
                    if(pattern_store){
                        if(isNewShape(currentShape, "resources/semi_patterns/")){
                            try {
                                Serializer.serialize(currentShape, "resources/semi_patterns/" +
                                        Helper.getFirstAvailableShapeName("resources/semi_patterns"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    shapePoints.clear();
                }
            }
        }
        return ans;
    }

    private void dfs(int x, int y, int maxX, int maxY) {
        shapePoints.add(new Tuple(x, y));
        visited[x][y] = true;
        for (int i = 0; i < 8; i++) {
            int next_x = x + delta_x[i];
            int next_y = y + delta_y[i];
            if (next_x >= 0 && next_y >= 0 && next_x < maxX && next_y < maxY && !visited[next_x][next_y]) {
                java.awt.Color color = new Color(image.getRGB(next_x, next_y));
                if (color.equals(pattern_color)) {
                    dfs(next_x, next_y, maxX, maxY);
                }
            }
        }
    }

    public Shape<Integer> getShape(Set<Tuple> points) {

        int maxx = 0, maxy = 0;
        int minx = Integer.MAX_VALUE, miny = Integer.MAX_VALUE;

        for (Tuple point : points) {
            maxx = Math.max(maxx, (int) point.x);
            maxy = Math.max(maxy, (int) point.y);

            minx = Math.min(minx, (int) point.x);
            miny = Math.min(miny, (int) point.y);
        }

        int n = maxy - miny + 1, m = maxx - minx + 1;


        Integer matrix[][] = new Integer[n][m];
        for(int i = 0; i < n; i++){
            for(int j = 0; j < m; j++){
                matrix[i][j] = 0;
            }
        }
        for (Tuple point : points) {
            matrix[(int) point.y - miny][(int) point.x - minx] = 1;
        }
        Shape<Integer> shape = ShapeFactory.getComparableShape();
        shape.setN(n);
        shape.setM(m);
        shape.setShapeMatrix(matrix);

        return shape;
    }


}