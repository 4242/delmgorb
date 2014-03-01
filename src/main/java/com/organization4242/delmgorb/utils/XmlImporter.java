package com.organization4242.delmgorb.utils;

import com.organization4242.delmgorb.view.MainWindowView;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Created by ilya-murzinov on 02.03.14.
 */
public class XmlImporter {
    private static Document doc;

    private static void init(File newFile) {
        File file = newFile;
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setValidating(false);
            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            try {
                doc = builder.parse(file);
            } catch (IllegalArgumentException ex) {
                System.out.println(ex);
            }
        } catch (SAXException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        } catch (ParserConfigurationException ex) {
            System.out.println(ex);
        }
    }

    public static void importData(MainWindowView view) {
        init(OpenFileHelper.open(view));
        System.out.println(doc.getDomConfig());
    }

    public static void importConfig(MainWindowView view) {
        init(OpenFileHelper.open(view));
        System.out.println(doc.getDomConfig());
    }
}
