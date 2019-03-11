package com.example.annuairegsh.Manager;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.annuairegsh.Activity.CityActivity;
import com.example.annuairegsh.Activity.DepartmentActivity;
import com.example.annuairegsh.Activity.HomeActivity;
import com.example.annuairegsh.Model.City;
import com.example.annuairegsh.Model.Company;
import com.example.annuairegsh.Model.Constant;
import com.example.annuairegsh.Model.Contact;
import com.example.annuairegsh.Model.Department;
import com.example.annuairegsh.Model.ListCity;
import com.example.annuairegsh.Model.ListDepartment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class RealmManager {
    public static Realm realM;
    private  HandlerRealm handlerRealm;

    public static boolean areContactsInCache() {
        Realm realm = Realm.getDefaultInstance();
        Log.d("POPP", "areContactsInCache: " +  realm.where(Contact.class).count());
        return realm.where(Contact.class).count() > 1000;
    }
    //private ArrayList<Contact> cts;


    public void initHandler(){
         handlerRealm = new HandlerRealm();
    }
    public  void saveContacts(final ArrayList<Contact> contacts){
        //cts = contacts;
        initHandler();
        Message message = new Message();
        message.obj = contacts;
        message.what = Constant.CONTACT;
        handlerRealm.sendMessage(message);
       /* realM = Realm.getDefaultInstance();
        realM.beginTransaction();
        realM.insertOrUpdate(contacts);
        realM.commitTransaction();
        realM.close();*/



        /*realM.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                bgRealm.insertOrUpdate(contacts);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Original queries and Realm objects are automatically updated.

            }
        });*/

    }

    public static void saveCompany(ArrayList<Company> companies){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(companies);
        realm.commitTransaction();
        realm.close();
    }

    public  void saveDepartment(final ArrayList<Department> departments,   String cp, City city) {
      /*  Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        // Persist unmanaged objects
//        Company company = realm.createObject(Company.class); // Create managed objects directly
        City c = realm.where(City.class).equalTo("id", city.getId()).findFirst();
        Log.d("SIZEE", "saveCity: " + departments.size());


        for(Department d: departments) {
            final Department departmentR = realm.copyToRealmOrUpdate(d);
            c.getDepartments().add(departmentR);


        }


        // realm.insertOrUpdate(cities);
        realm.commitTransaction();
        realm.close();*/


        initHandler();
        Message message = new Message();
        Object[] objects = new Object[3];
        objects[0] = new ListDepartment(departments);
        objects[1] = cp;
        objects[2] = city.getId();
        message.obj = objects;
        message.what = Constant.DEPARTMENT;
        handlerRealm.sendMessage(message);
    }

    public static void savePic( Contact contact,   String pic ) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        // Persist unmanaged objects
//        Company company = realm.createObject(Company.class); // Create managed objects directly
        contact.setPictureC(pic);
        // realm.insertOrUpdate(cities);
        realm.commitTransaction();
        realm.close();
    }

    public static void savePicById( String id,   String pic ) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        // Persist unmanaged objects
//        Company company = realm.createObject(Company.class); // Create managed objects directly
        getContactbyId(id).setPictureC(pic);
        // realm.insertOrUpdate(cities);
        realm.commitTransaction();
        realm.close();
    }

    public  void saveCity(final ArrayList<City> cities, final Company cp){

        /*Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        // Persist unmanaged objects
//        Company company = realm.createObject(Company.class); // Create managed objects directly
        Company c = realm.where(Company.class).equalTo("nameAD", cp.getNameAD()).findFirst();
        Log.d("SIZEE", "saveCity: " + cities.size());
        for(City city: cities) {
            final City cityR = realm.copyToRealmOrUpdate(city);
            c.getCities().add(cityR);
        }


      // realm.insertOrUpdate(cities);
       realm.commitTransaction();
        realm.close();
*/

        initHandler();
        Message message = new Message();
        Object[] objects = new Object[2];
        objects[0] = new ListCity(cities);
        objects[1] = cp.getNameAD();
        message.obj = objects;
        message.what = Constant.CITY;
        handlerRealm.sendMessage(message);


    }

    public static void showCity(){
        Realm realm = Realm.getDefaultInstance();
        final RealmResults<City> puppies = realm.where(City.class).findAll();
        Log.d("POP", "showCity: " + puppies.toString());
        realm.close();
    }
   public static void test(){
        Realm realm = Realm.getDefaultInstance();
        City city = new City("SBA2", "SBA", "SBAG");
        City city2 = new City("ORAN", "ORAN2", "IORAN");
    realm.beginTransaction();
    // Persist unmanaged objects
    Company company = realm.createObject(Company.class); // Create managed objects directly
    company.getCities().add(city);
    company.setNameAD("GSHA");

    Company company1 = realm.createObject(Company.class);;
    company1.setNameAD("HL");
    company1.getCities().add(city2);

    realm.commitTransaction();
       realm.close();
}

    public static void showTest(){
        Realm realm = Realm.getDefaultInstance();
        final RealmResults<Company> companies = realm.where(Company.class).findAll();
        Log.d("POP1", "showCity: " + companies.toString());
        for(Company c: companies){
            Log.d("POP5", "showCity: " + c.getNameAD() + "PPP " + c.getCities());
        }

        final RealmResults<City> cities = realm.where(City.class).findAll();
        Log.d("POP2", "showCity: " + cities.toString());

        final RealmResults<Department> departments = realm.where(Department.class).findAll();
        Log.d("POP3", "showCity: " + departments.toString());

        final RealmResults<Contact> contacts = realm.where(Contact.class).findAll();
        Log.d("POP4", "showCity: " + departments.size());


        final RealmResults<Contact> contacts2 = realm.where(Contact.class).equalTo("company", "PHAR").findAll();
        Log.d("POP6", "showCity: " + contacts2);
       // realm.close();
    }


    public static ArrayList<Contact> getContactsByName(String search) {
        Realm realm = Realm.getDefaultInstance();

        ArrayList<Contact> contactArray = new ArrayList<>();
        RealmResults<Contact> conta = realm.where(Contact.class).beginsWith("name", search, Case.INSENSITIVE).sort("name").limit(10).findAll();

        for(Contact c : conta){
            contactArray.add(c);
        }
        //Object[] contacts = conta.toArray();
        if(conta.size() < 10) {
          conta = realm.where(Contact.class).contains("name", search, Case.INSENSITIVE).not().beginGroup()
            .beginsWith("name", search, Case.INSENSITIVE)
                    .endGroup().sort("name").limit(10 - conta.size()).findAll();

            for(Contact c : conta){
                contactArray.add(c);
            }


        }

        if(contactArray.size() < 10){
            conta = realm.where(Contact.class).contains("description", search, Case.INSENSITIVE).not().beginGroup()
                    .beginsWith("name", search, Case.INSENSITIVE).or()
                    .contains("name", search, Case.INSENSITIVE)
                    .endGroup().sort("name").limit(10 - conta.size()).findAll();
            for(Contact c : conta){
                contactArray.add(c);
            }
        }

        if(contactArray.size() < 10){
            conta = realm.where(Contact.class).beginsWith("number", search, Case.INSENSITIVE).sort("name").limit(10 - conta.size()).findAll();
            for(Contact c : conta){
                contactArray.add(c);
            }
        }

        return  contactArray;


    }

    public static RealmResults<Contact> getContactByDeparmtment(String department, String company, String city){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Contact> conta;

             conta = realm.where(Contact.class).equalTo("company", company).equalTo("city", city).equalTo("department", department).sort("name").findAll();

        //  realm.close();
        return conta;

    }

    public static Contact getContactByNumber(String number){
     //   Realm.init(getApplicationContext());
        Realm realm = Realm.getDefaultInstance();

        return realm.where(Contact.class).equalTo("number", number).findFirst();

    }

    public static Contact getContactbyId(String id) {
        Realm realm = Realm.getDefaultInstance();
        Contact contact = realm.where(Contact.class).equalTo("id", id).findFirst();
            // do something with the person ...
        realm.close();
        return contact;
    }

    public static void DeleteById(String id){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
         RealmObject c = realm.where(Contact.class).equalTo("id", id).findFirst();

         if(c != null)
             c.deleteFromRealm();

        realm.commitTransaction();
        realm.close();

    }

    public static RealmResults<Company> getCompanies() {
        Realm realm = Realm.getDefaultInstance();
        //CHABOOX is too avoid company with no contact
       // RealmResults<Company> companies = realm.where(Company.class).notEqualTo("cities.code", "CHABOOX").findAll();
       RealmResults<Company> companies = realm.where(Company.class).findAll();
        realm.close();
        return companies;
    }

    public static RealmResults<Company> getCompanies(String pole) {
        Realm realm = Realm.getDefaultInstance();
        //CHABOOX is too avoid company with no contact
        // RealmResults<Company> companies = realm.where(Company.class).notEqualTo("cities.code", "CHABOOX").findAll();
        RealmResults<Company> companies = realm.where(Company.class).equalTo("pole", pole).findAll();
        realm.close();
        return companies;
    }

    public static Company getCompanyByCode(String code) {
        Realm realm = Realm.getDefaultInstance();
        Company company = realm.where(Company.class).equalTo("nameAD", code).findFirst();
        // do something with the person ...
        realm.close();
        return company;
    }

    public static City getCityById(String idCity) {
        Realm realm = Realm.getDefaultInstance();
        City city = realm.where(City.class).equalTo("id", idCity).findFirst();
        // do something with the person ...
        realm.close();
        return city;
    }

    public ArrayList<City> getCityByCompany(String company){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Contact> contacts = realm.where(Contact.class).equalTo("company", company).distinct("city").findAll();
        ArrayList<City> cities = new ArrayList<>();
        HashMap<String, String> cityDesc = getCityCompleteName();
        for(Contact c : contacts){
            cities.add(new City(cityDesc.get(c.getCity()), c.getCity(), company + "_" + c.getCity()));
        }
      //  realm.close();

        return  cities;
    }

    public ArrayList<Department> getDepartmentByCity(City city){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Contact> contacts = realm.where(Contact.class).equalTo("company", city.getId().split("_")[0]).equalTo("city", city.getCode()).distinct("department").findAll();
        ArrayList<Department> departments = new ArrayList<>();
        Log.d("OUO", "getDepartmentByCity: " + contacts.size());

        HashMap<String, String> departmentDescription = getDescriptionDepartment();
        for(Contact c : contacts){
            departments.add(new Department(c.getDepartment(),departmentDescription.get(c.getDepartment())));
        }
        //  realm.close();

        return  departments;
    }

    private HashMap<String, String> getDescriptionDepartment() {
        HashMap<String, String> directionDescription = new HashMap<>();
        directionDescription.put("APP", "Approvisionnements");
        directionDescription.put("CDF", "Centre De Formation");
        directionDescription.put("CDG", "Contrôle De Gestion");
        directionDescription.put("DAA", "Direction/Département Achats Approvisionnements");
        directionDescription.put("DAC", "Direction Assistance Clientèle");
        directionDescription.put("DAG", "Direction/Département Administration Générale");
        directionDescription.put("DCC", "Direction Commerciale Centre");//
        directionDescription.put("DCE", "Direction/Département Commerce Externe");//
        directionDescription.put("DCG", "Direction/Département Contrôle de gestion");//
        directionDescription.put("DCO", "Direction Commerciale");//
        directionDescription.put("DFC", "Direction/Département Finance Comptabilité");//
        directionDescription.put("DGR", "Direction Générale");//
        directionDescription.put("DIM", "Direction/Département Importations");//
        directionDescription.put("DMC", "Direction/Département Marketing et Commerciale");//
        directionDescription.put("DQH", "Direction Qualité");//
        directionDescription.put("DQC", "Direction/Département Contrôle Qualité");//
        directionDescription.put("DRE", "Direction/Département Réalisation");//
        directionDescription.put("DRH", "Direction Ressources Humaines");//
        directionDescription.put("DSD", "Direction Stratégie et Développement");//
        directionDescription.put("DSI", "Direction/Département Systèmes d'Information");//
        directionDescription.put("Dsi", "Direction/Département Systèmes d'Information");//
        directionDescription.put("DTQ", "Direction/Département Technique");//
        directionDescription.put("GDS", "Gestion des Stocks");//
        directionDescription.put("GRH", "Gestion des Ressources Humaines");//
        directionDescription.put("HSE", "Hygiène Securité Environnement");//
        directionDescription.put("LAB", "Laboratoire");//
        directionDescription.put("LOG", "Logistique");//
        directionDescription.put("MNT", "Maintenance");//
        directionDescription.put("PRM", "Production et Maintenance");//
        directionDescription.put("PRO", "Production");//
        directionDescription.put("SMQ", "Systèmes Management Qualité");//
        directionDescription.put("CIM", "Cimenterie");//
        directionDescription.put("DPR", "Directeur de Projet");//
        directionDescription.put("TRS", "Trésorerie");
        directionDescription.put("DAF", "Direction/Département Administration & Finances");
        directionDescription.put("SEC", "Sécurité");
        directionDescription.put("SECU", "Sécurité");
        directionDescription.put("ARBO", "Département Arboricole");
        directionDescription.put("DMK", "Direction Marketing");
        directionDescription.put("NR", "Non renseigné");

        return directionDescription;
    }


    private HashMap<String, String> getCityCompleteName() {
        HashMap<String, String> cityDesc = new HashMap<>();
        cityDesc.put("SBA", "Sidi Bel Abbes");
        cityDesc.put("ORN", "Oran");
        cityDesc.put("MOS", "Mostaganem");
        cityDesc.put("TMR", "Tamanrasset");
        cityDesc.put("TLM", "Tlemcen");
        cityDesc.put("STF", "Sétif");
        cityDesc.put("CST", "Constantine");//
        cityDesc.put("ALG", "Alger");//


        return cityDesc;
    }

    public void PopulateCityIntoCompany(Handler handler, int what){
        Realm realm = Realm.getDefaultInstance();

        RealmResults<Company> companies = realm.where(Company.class).findAll();
        realm.beginTransaction();
        for(Company company: companies){
        ArrayList<City> cities= getCityByCompany(company.getNameAD());

           /* initHandler();
            Message message = new Message();
            Object[] objects = new Object[2];
            objects[0] = new ListCity(cities);
            objects[1] = company.getNameAD();
            message.obj = objects;
            message.what = Constant.CITY;
            handlerRealm.sendMessage(message);*/

            for(City city: cities) {
                final City cityR = realm.copyToRealmOrUpdate(city);
                company.getCities().add(cityR);
            }

        }
        realm.commitTransaction();
        realm.close();
        handler.sendEmptyMessage(what);
    }

    public void populateDepartmentIntoCity(Handler handler, int what){
        Realm realm = Realm.getDefaultInstance();

        RealmResults<City> cities = realm.where(City.class).findAll();
        realm.beginTransaction();
        for(City city: cities){
            ArrayList<Department> departments= getDepartmentByCity(city);

           /* initHandler();
            Message message = new Message();
            Object[] objects = new Object[2];
            objects[0] = new ListCity(cities);
            objects[1] = company.getNameAD();
            message.obj = objects;
            message.what = Constant.CITY;
            handlerRealm.sendMessage(message);*/

            for(Department d: departments) {
                final Department departmentR = realm.copyToRealmOrUpdate(d);
                city.getDepartments().add(departmentR);

            }

        }
        realm.commitTransaction();
        realm.close();
       handler.sendEmptyMessage(what);
    }


    public class HandlerRealm extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Constant.CONTACT:
                    realM = Realm.getDefaultInstance();
                    realM.beginTransaction();
                    realM.insertOrUpdate((ArrayList<Contact>) msg.obj);
                    realM.commitTransaction();
                    realM.close();
                    break;
                case Constant.CITY:
                    realM = Realm.getDefaultInstance();
                    realM.beginTransaction();
                    // Persist unmanaged objects
                    //Company company = realm.createObject(Company.class); // Create managed objects directly
                    Company c = realM.where(Company.class).equalTo("nameAD", ((Object[])( msg.obj))[1].toString()).findFirst();
                    ArrayList<City> cities = ((ListCity) (((Object[])( msg.obj))[0])).getCities();
                    //Log.d("SIZEE", "saveCity: " + cities.size());
                    for(City city: cities) {
                        final City cityR = realM.copyToRealmOrUpdate(city);
                        c.getCities().add(cityR);
                    }


                    // realm.insertOrUpdate(cities);
                    realM.commitTransaction();
                    realM.close();

                    break;
                case Constant.DEPARTMENT:
                    Realm realm = Realm.getDefaultInstance();
                    realm.beginTransaction();
                    // Persist unmanaged objects
//        Company company = realm.createObject(Company.class); // Create managed objects directly
                    City c2 = realm.where(City.class).equalTo("id", ((Object[])( msg.obj))[2].toString()).findFirst();
                  //  Log.d("SIZEE", "saveCity: " + departments.size());


                    for(Department d: ((ListDepartment) (((Object[])( msg.obj))[0])).getDepartments()) {
                        final Department departmentR = realm.copyToRealmOrUpdate(d);
                        c2.getDepartments().add(departmentR);


                    }


                    // realm.insertOrUpdate(cities);
                    realm.commitTransaction();
                    realm.close();
                    break;
            }
        }
    }

}
