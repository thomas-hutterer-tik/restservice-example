package restservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.util.Base64;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/*
 * Test class that remotely invokes all the REST APis for the User entity
 * 
 * based on: http://websystique.com/spring-boot/spring-boot-rest-api-example/
 */
public class ImageRecognitionTest {
  
    private static final String REST_SERVICE_URI = "http://image-recognition-aml.apps.tools.adp.allianz";
	private static final RestTemplate restTemplate = getRestTemplateBypassingHostNameVerifcation();

	public static RestTemplate getRestTemplateBypassingHostNameVerifcation() {
		//Create SSL Client
		CloseableHttpClient httpClient = null;
		// HttpHost target = new HttpHost('www.mysite.com', 443, "https");

/*		SSLContext sslcontext = SSLContexts.createSystemDefault();
		SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
		sslcontext, new String[] { "TLSv1", "SSLv3" }, null,
		SSLConnectionSocketFactory.getDefaultHostnameVerifier());

		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
		.register("http", PlainConnectionSocketFactory.INSTANCE)
		.register("https", sslConnectionSocketFactory)
		.build();
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

		httpClient = HttpClients.custom()
		.setSSLSocketFactory(sslConnectionSocketFactory)
		.setConnectionManager(cm)
		.build();
*/
		
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = new TrustManager[] { 
		    new X509TrustManager()  {     
		        public java.security.cert.X509Certificate[] getAcceptedIssuers() { 
		            return new X509Certificate[0];
		        } 
		        public void checkClientTrusted( 
		            java.security.cert.X509Certificate[] certs, String authType) {
		            } 
		        public void checkServerTrusted( 
		            java.security.cert.X509Certificate[] certs, String authType) {
		        }
		    } 
		}; 

		// Install the all-trusting trust manager
		try {
		    SSLContext sc = SSLContext.getInstance("SSL"); 
		    sc.init(null, trustAllCerts, new java.security.SecureRandom()); 
			httpClient = HttpClients.custom().setSSLContext(sc)
					// .setSSLSocketFactory((LayeredConnectionSocketFactory) sc.getSocketFactory())
					.build();
		} catch (GeneralSecurityException e) {
		} 
		
		try {
		    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		    requestFactory.setHttpClient(httpClient);
		    return new RestTemplate(requestFactory);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}	
	
    private static byte[] encodeFileToBase64Binary(File file){
    	byte[] encodedfile = null;
        FileInputStream fileInputStreamReader = null;
        try {
            fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int)file.length()];
            fileInputStreamReader.read(bytes);
            encodedfile = Base64.getEncoder().encode(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        	if (fileInputStreamReader != null)
				try {
					fileInputStreamReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        }

        return encodedfile;
    }
    
    public class Image
    {
    	public byte[] ImageData;

		public Image(File file) {
			ImageData = encodeFileToBase64Binary(file);
		}
    }    
    
    // helper for other tests
    private URI imageRecognition() {
		Image image = new Image(new File("Sivota2012.JPG"));
		return restTemplate.postForLocation(REST_SERVICE_URI, image, Image.class);
    }
        
    public static void main(String args[]){
    	ImageRecognitionTest test = new ImageRecognitionTest();
        URI uri = test.imageRecognition();
        System.out.println("Result = " + uri);
    }

 }