package example;

/**
 *
 * @author aless
 */
public class ExampleHPE {
    
    public static void main(String[] args) {
        
        String fileToConvert = "src/file/image2.jpg";
        
        Converter converter = new Converter();
        converter.doOCR(fileToConvert);
        
    }
    
}
