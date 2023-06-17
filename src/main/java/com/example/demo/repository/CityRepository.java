package com.example.demo.repository;

import com.example.demo.entity.City;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.File;

@Repository
public class CityRepository {
    private static final String FILE_NAME = "src/main/resources/static/xml.xml";

    public Long getLastId() {
        List<City> cityList = getAllCity();
        if (!cityList.isEmpty()) {
            City lastCity = cityList.get(cityList.size() - 1);
            return lastCity.getId();
        }
        return Long.parseLong("0");
    }

    public City saveCity(City city) {
        List<City> cityList = getAllCity();
        if(cityList.isEmpty()){
            city.setId(Long.valueOf(1));
        }
        else{
            city.setId(cityList.get(cityList.size()-1).getId()+1);
        }
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

        File file = new File(FILE_NAME);
        Document document;
        Element root;
        if (file.exists()) {
            try {
                document = documentBuilder.parse(file);
                root = document.getDocumentElement();
            } catch (SAXException | IOException e) {
                throw new RuntimeException(e);
            }
        } else {            // If the file does not exist, create a new document
            document = documentBuilder.newDocument();
            root = document.createElement("cities");
            document.appendChild(root);
        }

        Element element = document.createElement("city");
        root.appendChild(element);

        Long idField = city.getId();
        Element idElement = document.createElement("id");
        idElement.appendChild(document.createTextNode(String.valueOf(idField)));
        element.appendChild(idElement);

        String cityName = city.getCityName();
        Element cityNameElement = document.createElement("cityName");
        cityNameElement.appendChild(document.createTextNode(cityName));
        element.appendChild(cityNameElement);

        String countryName = city.getCountryName();
        Element titleElement = document.createElement("countryName");
        titleElement.appendChild(document.createTextNode(countryName));
        element.appendChild(titleElement);

        String phoneCode = city.getPhoneCode();
        Element phoneCodeElement = document.createElement("phoneCode");
        phoneCodeElement.appendChild(document.createTextNode(phoneCode));
        element.appendChild(phoneCodeElement);

        String signOfTheCapital = city.getSignOfTheCapital();
        Element signOfTheCapitalElement = document.createElement("signOfTheCapital");
        signOfTheCapitalElement.appendChild(document.createTextNode(signOfTheCapital));
        element.appendChild(signOfTheCapitalElement);

        Integer countPerson = city.getCountPerson();
        Element countPersonElement = document.createElement("countPerson");
        countPersonElement.appendChild(document.createTextNode(String.valueOf(countPerson)));
        element.appendChild(countPersonElement);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        }
        DOMSource source = new DOMSource(document);

        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        StreamResult result = new StreamResult(outputStream);

        try {
            transformer.transform(source, result);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
        return city;
    }

    public void deleteCity(Long id) {
        if (!new File(FILE_NAME).exists()) {
            throw new RuntimeException("File not found: " + FILE_NAME);
        }

        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(FILE_NAME);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        Document document;
        try {
            document = builder.parse(fileInputStream);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        NodeList nodeList = document.getElementsByTagName("city");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            Element element = (Element) node;
            Long idField = Long.parseLong(element.getElementsByTagName("id").item(0).getTextContent());

            if (idField == id) {
                node.getParentNode().removeChild(node);
                break;
            }
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        }
        DOMSource source = new DOMSource(document);

        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(new File(FILE_NAME));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        StreamResult result = new StreamResult(outputStream);

        try {
            transformer.transform(source, result);
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    public List<City> getAllCity() {

        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(new File(FILE_NAME));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        Document document;
        try {
            document = builder.parse(fileInputStream);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        NodeList nodeList = document.getElementsByTagName("cities");
        List<City> cityList = new ArrayList<>();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node imagesNode = nodeList.item(i);
            NodeList imageNodes = imagesNode.getChildNodes();

            for (int j = 0; j < imageNodes.getLength(); j++) {
                Node imageNode = imageNodes.item(j);

                if (imageNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) imageNode;

                    Node idNode = element.getElementsByTagName("id").item(0);
                    Node cityNameNode = element.getElementsByTagName("cityName").item(0);
                    Node countryNameNode = element.getElementsByTagName("countryName").item(0);
                    Node phoneCodeNode = element.getElementsByTagName("phoneCode").item(0);
                    Node signOfTheCapitalNode = element.getElementsByTagName("signOfTheCapital").item(0);
                    Node countPersonNode = element.getElementsByTagName("countPerson").item(0);

                    if (idNode != null && cityNameNode != null && countryNameNode != null && phoneCodeNode != null
                            && signOfTheCapitalNode != null && countPersonNode != null) {
                        Long idField = Long.parseLong(idNode.getTextContent());
                        String cityName = cityNameNode.getTextContent();
                        String countryName = countryNameNode.getTextContent();
                        String phoneCode = phoneCodeNode.getTextContent();
                        String signOfTheCapital = signOfTheCapitalNode.getTextContent();
                        Integer countPerson = Integer.parseInt(countPersonNode.getTextContent());
                        City city = new City(idField, cityName, countryName, phoneCode, signOfTheCapital, countPerson);
                        cityList.add(city);
                    }
                }
            }
        }
        return cityList;
    }

    public City getCityById(Long id) {

        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(new File(FILE_NAME));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        Document document;
        try {
            document = builder.parse(fileInputStream);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        NodeList nodeList = document.getElementsByTagName("city");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            Element element = (Element) node;
            Long idField = Long.parseLong((element.getElementsByTagName("id").item(0).getTextContent()));

            if (idField == id) {
                Long id1 = Long.parseLong(element.getElementsByTagName("id").item(0).getTextContent());
                String cityName = element.getElementsByTagName("cityName").item(0).getTextContent();
                String countryName = element.getElementsByTagName("countryName").item(0).getTextContent();
                String phoneCode = element.getElementsByTagName("phoneCode").item(0).getTextContent();
                String signOfTheCapital = element.getElementsByTagName("signOfTheCapital").item(0).getTextContent();
                Integer countPerson = Integer.parseInt(element.getElementsByTagName("countPerson").item(0).getTextContent());
                return new City(id1, cityName, countryName, phoneCode, signOfTheCapital,countPerson);
            }
        }
        return null;
    }

    public City updateCity(City city) {
        Long id = city.getId();

        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(new File(FILE_NAME));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        Document document;
        try {
            document = builder.parse(fileInputStream);
        } catch (SAXException | IOException e) {
            throw new RuntimeException(e);
        }

        NodeList nodeList = document.getElementsByTagName("city");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            Element element = (Element) node;
            Long idField = Long.parseLong((element.getElementsByTagName("id").item(0).getTextContent()));

            if (idField.equals(id)) {
                Element idElement = (Element) element.getElementsByTagName("id").item(0);
                idElement.setTextContent(Long.toString(city.getId()));

                Element cityNameElement = (Element) element.getElementsByTagName("cityName").item(0);
                cityNameElement.setTextContent(city.getCityName());

                Element countryNameElement = (Element) element.getElementsByTagName("countryName").item(0);
                countryNameElement.setTextContent(city.getCountryName());

                Element phoneCodeElement = (Element) element.getElementsByTagName("phoneCode").item(0);
                phoneCodeElement.setTextContent(city.getPhoneCode());

                Element signOfTheCapitalElement = (Element) element.getElementsByTagName("signOfTheCapital").item(0);
                signOfTheCapitalElement.setTextContent(city.getSignOfTheCapital());

                Element countPersonElement = (Element) element.getElementsByTagName("countPerson").item(0);
                countPersonElement.setTextContent(city.getCountPerson()+"");

                try {
                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource source = new DOMSource(document);
                    StreamResult result = new StreamResult(new File(FILE_NAME));
                    transformer.transform(source, result);
                } catch (TransformerException e) {
                    throw new RuntimeException(e);
                }
                return city;
            }
        }
        return null;
    }

}
