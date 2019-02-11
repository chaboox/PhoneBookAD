package com.example.annuairegsh.Manager;

import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import com.example.annuairegsh.Model.Company;
import com.example.annuairegsh.Model.Constant;
import com.example.annuairegsh.Model.Contact;
import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class  CSVManager {
        CSVReader csvReader;

    public CSVManager() {
        }



        public Boolean isAgencyValidAndGetLabel(String nameAgency, TextView label) throws IOException{
          /*  csvReader = new CSVReader(new FileReader(Environment.getExternalStorageDirectory() + "/"
                    + Constant.DOWNLOAD_DIRECTORY  + "/" + Constant.AGENCE_NAME_FILE_CSV), ';');
            String [] nextLine;
            while ((nextLine = csvReader.readNext()) != null) {
                if(nextLine[2].equals(nameAgency)) {
                    FormActivity.agence = new Agence(nextLine[2], nextLine[0]);
                    label.setText(nextLine[3]);
                    return true;
                }
            }*/
            return false;

        }



        public void TestCSV() throws IOException{
//            csvReader = new CSVReader(new FileReader(Environment.getExternalStorageDirectory() + "/"
//                    + Constant.DOWNLOAD_DIRECTORY  + "/" + Constant.AGENCE_NAME_FILE_CSV), ';');
//            String [] nextLine;
//            nextLine = csvReader.readNext();
//            Log.d("TESTCSV", "TestCSV: #" + nextLine[2] + "#");
//            nextLine = csvReader.readNext();
//            Log.d("TESTCSV", "TestCSV: #" + nextLine[2] + "#");


        }

      /*  public Produit getMachineByName(String name) throws IOException{
            csvReader = new CSVReader(new FileReader(Environment.getExternalStorageDirectory() + "/"
                    + Constant.DOWNLOAD_DIRECTORY  + "/" + Constant.RACINE_NAME_FILE_CSV), ';');
            String [] nextLine;
            while ((nextLine = csvReader.readNext()) != null) {
                if(nextLine[0].equals(name)) return new Produit(nextLine[0], nextLine[1], nextLine[2]);
            }

            return new Produit(null, null, null);
        }*/
    public static void saveInCSV(ArrayList<Company> companies){
        String combinedString =   "\"nameAD\";\"name\";\"description\";\"pole\"";
        for( Company c :companies){
            combinedString = combinedString +"\n"+"\"" + c.getNameAD() +"\";\"" + c.getName() + "\";\"" + c.getDescription() + "\";\"" + c.getDescription() +"\"";
        }


        File file   = null;
        File root   = Environment.getExternalStorageDirectory();
        if (root.canWrite()){
            File dir    =   new File (root.getAbsolutePath() + "/" + Constant.DOWNLOAD_DIRECTORY);
            dir.mkdirs();
            file   =   new File(dir, "Companies.csv");
            FileOutputStream out   =   null;
            try {
                out = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                out.write(combinedString.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveContactsInCSV(ArrayList<Contact> contacts){
        String combinedString =   "\"name\";\"number\";\"job\";\"company\";\"city\";\"voip\";\"mail\";\"department\"";
        for( Contact c : contacts){
            combinedString = combinedString +"\n"+"\"" + c.getName() +"\";\"" + c.getNumber() + "\";\"" + c.getDescription() + "\";\"" + c.getCompany()  + "\";\"" + c.getCity()  +
                    "\";\"" + c.getVoip() + "\";\"" + c.getMail() + "\";\"" + c.getDepartment()  + "\"";
        }


        File file   = null;
        File root   = Environment.getExternalStorageDirectory();
        if (root.canWrite()){
            File dir    =   new File (root.getAbsolutePath() + "/" + Constant.DOWNLOAD_DIRECTORY);
            dir.mkdirs();
            file   =   new File(dir, "Contacts.csv");
            FileOutputStream out   =   null;
            try {
                out = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            try {
                out.write(combinedString.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void CreateRootFolder(){
        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + Constant.DOWNLOAD_DIRECTORY);
        Log.d(TAG, "CreateKiloutouFolder:  FOLDER EXISTE" );
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
            Log.d(TAG, "CreateKiloutouFolder:  CREATED FOLDER" + success );
        }
    }

}
