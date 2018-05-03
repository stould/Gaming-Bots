import Exceptions.RecognizerException;

import java.io.File;

public class DigitRecognizer implements ShapeRecognizer<Integer> {

    private Shape<Integer> patterns[];

    public DigitRecognizer(String path) {
        patterns = new Shape[10];
        for (int i = 0; i < 10; i++) {
            try {
                patterns[i] = Serializer.deserialize(path+"/"+i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Integer getShape(Shape<Integer> shape) throws RecognizerException {
        for (int i = 0; i < 10; i++) {
            if (shape.compareTo(patterns[i]) == 0) {//same
                return i;
            }
        }
        throw new RecognizerException("No pattern matches current shape.");
    }
}
