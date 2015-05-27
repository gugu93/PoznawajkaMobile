package poznawajkamobile.pz2.aplicationandroid.utils.requests;

import android.os.AsyncTask;

import com.google.common.base.Strings;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import poznawajkamobile.pz2.aplicationandroid.utils.listners.GaleryListner;

public class DownloadGalleryTask extends AsyncTask<String, Void, String> {

    private static final String TAG = DownloadGalleryTask.class.getSimpleName();
    private static OkHttpClient client = new OkHttpClient();
    private GaleryListner mListener;

    public DownloadGalleryTask(GaleryListner listener) {
        mListener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            String url = "https://127.0.0.1:8080?";
            url += URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(params[0], "UTF-8") + "&";
            url += URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(params[1], "UTF-8");
            Headers.Builder headersBuilder = new Headers.Builder();
            headersBuilder.add("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:11.0) Gecko/20100101 Firefox/11.0");
            headersBuilder.add("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
            headersBuilder.add("Accept-Encoding", "identity");
            headersBuilder.add("Accept-Language", "pl,en-us;q=0.7,en;q=0.3");
            headersBuilder.add("Host", "aaa.pl");
            Request request = new Request.Builder()
                    .url(url)
                    .headers(headersBuilder.build())
                    .build();
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        if (!Strings.isNullOrEmpty(result)) {
            mListener.onFinish(result);
        } else {
            mListener.onFinish(result);
        }
    }

    public Document getDomElement(String xml) {
        Document doc;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(false);
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            doc = db.parse(is);
        } catch (ParserConfigurationException e) {
            return null;
        } catch (SAXException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
        return doc;
    }

    public String getValue(Element item, String str) {
        NodeList n = item.getElementsByTagName(str);
        return this.getElementValue(n.item(0));
    }

    public final String getElementValue(Node elem) {
        Node child;
        if (elem != null) {
            if (elem.hasChildNodes()) {
                for (child = elem.getFirstChild(); child != null; child = child.getNextSibling()) {
                    if (child.getNodeType() == Node.TEXT_NODE) {
                        return child.getNodeValue();
                    }
                }
            }
        }
        return "";
    }
}
