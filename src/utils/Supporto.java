package utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aless
 */
public class Supporto {
    
    private long inizio = 0;
    private long tempo = 0;
    private String motore = "";
    
    public Supporto(String motore){
        this.motore = motore;
    }    
    
    public void timerStart(){
        inizio = System.currentTimeMillis();
    }
    
    public long timerStop(){
        long fine = System.currentTimeMillis();
        tempo = (long) ((fine - inizio) * 0.001);
        return tempo;
    }
    
    public String stringaOut(String path, String result){
        return "File: "+path+"\nMotore: "+motore+"\nTempo: "+tempo+" secondi\nParole totali: "+paroleTotali(result)+"\n\n"+result;
    }
    
    public void fileOut(String path, String result){
        
        PrintStream scrivi = null;
        
        try {
            path = path+"_"+motore+".txt";
            scrivi = new PrintStream(new FileOutputStream(path));
            scrivi.println(stringaOut(path, result));
            scrivi.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Supporto.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            scrivi.close();
        }
               
    }
    
    public int paroleTotali(String testo){
    
        int totale = 0;
        StringTokenizer st = new StringTokenizer(testo);
        while(st.hasMoreTokens()) {
            st.nextToken();
            totale++;
        }
        return totale;    
    }
    
}
