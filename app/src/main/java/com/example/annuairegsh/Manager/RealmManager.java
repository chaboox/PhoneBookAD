package com.example.annuairegsh.Manager;

import android.util.Log;

import com.example.annuairegsh.Model.City;
import com.example.annuairegsh.Model.Company;
import com.example.annuairegsh.Model.Contact;
import com.example.annuairegsh.Model.Department;

import java.util.ArrayList;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmResults;

public class RealmManager {
    public static void saveContacts(ArrayList<Contact> contacts){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(contacts);
        realm.commitTransaction();
        realm.close();

    }

    public static void saveCompany(ArrayList<Company> companies){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(companies);
        realm.commitTransaction();
        realm.close();
    }

    public static void saveDepartment(final ArrayList<Department> departments, final  String cp, City city) {
        Realm realm = Realm.getDefaultInstance();
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
        realm.close();
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

    public static void saveCity(final ArrayList<City> cities, final Company cp){

        Realm realm = Realm.getDefaultInstance();
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


    }

    public static void showCity(){
        Realm realm = Realm.getDefaultInstance();
        final RealmResults<City> puppies = realm.where(City.class).findAll();
        Log.d("POP", "showCity: " + puppies.toString());
        realm.close();
    }
   public static void test(){
        Realm realm = Realm.getDefaultInstance();
        City city = new City("SBA2", "SBA");
        City city2 = new City("ORAN", "ORAN2");
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
        Log.d("POP4", "showCity: " + contacts.size());
        realm.close();
    }


    public static RealmResults<Contact> getContactsByName(String search) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Contact> conta =realm.where(Contact.class).contains("name", search, Case.INSENSITIVE).limit(20).findAll();
      //  realm.close();
        return conta;


    }

    public static Contact getContactbyId(String id) {
        Realm realm = Realm.getDefaultInstance();
        Contact contact = realm.where(Contact.class).equalTo("id", id).findFirst();
            // do something with the person ...
        realm.close();
        return contact;
    }

    public static RealmResults<Company> getCompanies() {
        Realm realm = Realm.getDefaultInstance();
        //CHABOOX is too avoid company with no contact
        RealmResults<Company> companies = realm.where(Company.class).notEqualTo("cities.departments.code", "CHABOOX").findAll();
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
}
