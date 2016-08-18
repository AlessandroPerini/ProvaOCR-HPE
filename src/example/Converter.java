package example;

import hodclient.HODApps;
import hodclient.HODClient;
import hodclient.IHODClientCallback;
import hodresponseparser.HODErrorCode;
import hodresponseparser.HODErrorObject;
import hodresponseparser.HODResponseParser;
import hodresponseparser.OCRDocumentResponse;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author aless
 */
class Converter implements IHODClientCallback {
    
    HODClient client = new HODClient("556fdd83-62ed-4851-95ef-f77d14f59775", this);
    HODResponseParser parser = new HODResponseParser();
    String hodApp = "";
    String filePath;
    String finalResult = "";
    
    public String doOCR(String filePath) {
        
        this.filePath = filePath;
        hodApp = HODApps.OCR_DOCUMENT;
        Map<String, Object> params = new HashMap<>();
        
        // pass a single file as File object
        File mediaFile = new File(filePath);
        
        // pass a single file as filename
        // String mediaFile = fileName;
        
        params.put("file", mediaFile);
        params.put("language", "en-US");
        
        client.PostRequest(params, hodApp, HODClient.REQ_MODE.ASYNC);
        return resultOCR("");
    }
    
    public String resultOCR(String response){
        requestCompletedWithContent(response);
        return finalResult;
    }
    
    @Override
    public void requestCompletedWithContent(String response) {
        
        OCRDocumentResponse resp = parser.ParseOCRDocumentResponse(response);
        if (resp != null) {
            for (OCRDocumentResponse.TextBlock doc : resp.text_block)
            {
                finalResult += doc.text;
            }
            //System.out.println(result);
        } else {
            List<HODErrorObject> errors = parser.GetLastError();
            for (HODErrorObject err : errors) {
                if (err.error == HODErrorCode.QUEUED) {
                    try {
                        Thread.sleep(3000);
                        client.GetJobStatus(err.jobID);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                } else if (err.error == HODErrorCode.IN_PROGRESS) {
                    try {
                        Thread.sleep(10000);
                        client.GetJobStatus(err.jobID);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                } else {
                    String result = "\n\nError:\r\n";
                    result += "Code: " + String.format("%d\r\n", err.error);
                    result += "Reason: " + err.reason + "\r\n";
                    result += "Detail: " + err.detail + "\r\n";
                    //System.out.println(result);
                }
            }
        }
    }
    @Override
    public void requestCompletedWithJobID(String response) {
        // TODO Auto-generated method stub
        String jobID = parser.ParseJobID(response);
        if (jobID.length() > 0)
            client.GetJobStatus(jobID);
    }
    @Override
    public void onErrorOccurred(String errorMessage) {
        // TODO Auto-generated method stub
        System.out.println("Error: " + errorMessage);
    }
    
}