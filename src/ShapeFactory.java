public class ShapeFactory {
    public static Shape getComparableShape(){
        Shape<Integer> shape = new Shape() {
            @Override
            public int compareTo(Object o) {
                Shape<Integer> other = (Shape<Integer>) o;
                if(getN() != other.getN() || getM() != other.getM()) return 1;
                for(int i = 0; i < other.getN(); i++){
                    for(int j = 0; j < other.getM(); j++){
                        if((int) getShapeMatrix()[i][j] != (int) other.getShapeMatrix()[i][j]){
                            return 1;
                        }
                    }
                }
                return 0;
            }
        };
        return shape;
    }

}
