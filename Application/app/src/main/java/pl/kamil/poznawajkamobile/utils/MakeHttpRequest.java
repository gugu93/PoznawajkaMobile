package pl.kamil.poznawajkamobile.utils;

import android.os.AsyncTask;

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

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import pl.kamil.poznawajkamobile.fragment.DownloadListener;

public class MakeHttpRequest extends AsyncTask<Boolean, Void, String> {

    private static final String TAG = MakeHttpRequest.class.getSimpleName();
    private static OkHttpClient client = new OkHttpClient();
    private DownloadListener mListener;

    public MakeHttpRequest(DownloadListener listener) {
        mListener = listener;
    }

    @Override
    protected String doInBackground(Boolean... params) {
        try {
            String url = "url";
//            for (Map.Entry<String, String> entry : UpdateUtils.getStatisticParam(mListener.getContext(), params[0]).entrySet()) {
//                url += entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "UTF-8") + "&";
//            } robienie zapytania url z integracja url
            url = url.substring(0, url.length() - 1);
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
//        UpdateInfo info = null;
//        if (!Strings.isNullOrEmpty(result)) {
//            Document doc = getDomElement(result);
//            NodeList nl = doc.getElementsByTagName(Constant.UPDATE_XML_ROOT_TAG);
//            if (nl.getLength() > 0) {
//                Element e = (Element) nl.item(0);
//                if (e != null) {
//                    String path = getValue(e, Constant.UPDATE_XML_PATH_TAG);
//                    String size = getValue(e, Constant.UPDATE_XML_SIZE_TAG);
//                    String version = getValue(e, Constant.UPDATE_XML_VERSION_TAG);
//                    String date = getValue(e, Constant.UPDATE_XML_DATE_TAG);
//
//                    info = new UpdateInfo(path, size, version, date);
//                    mListener.setInfo(info);
//                }
//            }
//        }
        mListener.onCheckFinished("Sdsadsa");
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
