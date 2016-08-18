package example;

import utils.Supporto;

/**
 *
 * @author aless
 */
public class ExampleHPE {
    
    public static void main(String[] args) {
        
        Supporto prova = new Supporto("Prova");
        prova.timerStart();
        int i;
        String estensione[] = {"","png","jpg","tif","tif","tif","tif","png",
                                  "jpg","jpg","png","jpg","jpg","jpg","tif",
                                  "tif","png","gif","gif","png","png"};
        
        for (i = 1; i < 2; i++) {
   
            Supporto supporto = new Supporto("HPE");
            String fileToConvert = "C:\\Users\\aless\\Desktop\\scanned_doc\\dir"+i+"\\img"+i+"."+estensione[i];
            Converter converter = new Converter();
            supporto.timerStart();
            String result = converter.doOCR(fileToConvert);
            supporto.timerStop();
            System.out.println("\n - "+fileToConvert);
            supporto.fileOut(fileToConvert, result);
            
        }
        
        System.out.println("\nTempo totale: "+prova.timerStop());
    }
    
}
