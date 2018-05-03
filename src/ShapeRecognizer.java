import Exceptions.RecognizerException;

public interface ShapeRecognizer<T> {
    public T getShape(Shape<T> shape) throws RecognizerException;
}
