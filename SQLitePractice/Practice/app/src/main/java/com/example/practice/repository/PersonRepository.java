package com.example.practice.repository;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.practice.database.DbHelper;
import com.example.practice.models.Person;
import com.example.practice.interfaces.iRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
public class PersonRepository implements iRepository<Person> {
    public static final String TABLE_NAME = "tblPerson";
    private Person person;
    private DbHelper dbHelper;
    public PersonRepository(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }
    public PersonRepository(Person person, DbHelper dbHelper) {
        this.person = person;
        this.dbHelper = dbHelper;
    }
    public static String initTable() {
        return "CREATE TABLE " + TABLE_NAME + "(id INTEGER PRIMARY KEY AUTOINCREMENT, firstname TEXT, middlename TEXT, lastname TEXT, employment_status TEXT, username TEXT, password TEXT, age INTEGER, salary REAL, created_date TEXT, created_time TEXT, updated_date TEXT, updated_time TEXT)";
    }
    public static String dropTable() {
        return "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
    @Override
    public boolean Delete() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE id=?";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindLong(1, person.getId());
        return statement.executeUpdateDelete() > 0;
    }

    @Override
    public boolean Create() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String datas = "(firstname, middlename, lastname, username, password, age, salary, created_date, created_time, updated_date, updated_time, employment_status)";

        String sql = "INSERT INTO " + TABLE_NAME + " " + datas + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, person.getFirstname());
        statement.bindString(2, person.getMiddlename());
        statement.bindString(3, person.getLastname());
        statement.bindString(4, person.getUsername());
        statement.bindString(5, person.getPassword());
        statement.bindLong(6, person.getAge());
        statement.bindDouble(7, person.getSalary());

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        person.setCreated_date(date);
        person.setCreated_time(time);

        person.setUpdated_date(date);
        person.setUpdated_time(time);

        statement.bindString(8, person.getCreated_date());
        statement.bindString(9, person.getCreated_time());
        statement.bindString(10, person.getUpdated_date());
        statement.bindString(11, person.getUpdated_time());
        statement.bindString(12, person.getEmploymentStatus());
        return statement.executeInsert() > 0;
    }

    @Override
    public boolean Update() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "UPDATE " + TABLE_NAME + " SET firstname=?,middlename=?,lastname=?,username=?,password=?,age=?, salary=?, updated_date=?, updated_time=?, employment_status=?  WHERE id=?";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();
        statement.bindString(1, person.getFirstname());
        statement.bindString(2, person.getMiddlename());
        statement.bindString(3, person.getLastname());
        statement.bindString(4, person.getUsername());
        statement.bindString(5, person.getPassword());
        statement.bindLong(6, person.getAge());
        statement.bindDouble(7, person.getSalary());

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        person.setCreated_date(date);
        person.setCreated_time(time);

        person.setUpdated_date(date);
        person.setUpdated_time(time);

        statement.bindString(10, person.getEmploymentStatus());
        statement.bindLong(11, person.getId());

        return statement.executeUpdateDelete() > 0;
    }

    public Person Login() {
        Person existPerson = null;
        String sql = "SELECT * FROM " + TABLE_NAME + " where username = ? AND password=?";
        Cursor c = dbHelper.queryReadable(sql, new String[]{
                person.getUsername(),
                person.getPassword(),
        });

        if (c.getCount() < 0) {
            return null;
        }

        if (c.moveToFirst()) {
            existPerson = new Person();
            existPerson.setId(c.getInt(c.getColumnIndex("id")));
            existPerson.setFirstname(c.getString(c.getColumnIndex("firstname")));
            existPerson.setMiddlename(c.getString(c.getColumnIndex("middlename")));
            existPerson.setLastname(c.getString(c.getColumnIndex("lastname")));

            existPerson.setAge(c.getInt(c.getColumnIndex("age")));
            existPerson.setSalary(c.getDouble(c.getColumnIndex("salary")));

            existPerson.setUsername(c.getString(c.getColumnIndex("username")));
            existPerson.setPassword(c.getString(c.getColumnIndex("password")));
            existPerson.setEmploymentStatus(c.getString(c.getColumnIndex("employment_status")));

            existPerson.setCreated_date(c.getString(c.getColumnIndex("created_date")));
            existPerson.setUpdated_date(c.getString(c.getColumnIndex("updated_date")));

            existPerson.setCreated_time(c.getString(c.getColumnIndex("created_time")));
            existPerson.setUpdated_time(c.getString(c.getColumnIndex("updated_time")));
        }

        return existPerson;

    }

    public Person GetByUsername() {
        Person existPerson = null;
        String sql = "SELECT * FROM " + TABLE_NAME + " where username =?";
        Cursor c = dbHelper.queryReadable(sql, new String[]{person.getUsername()});

        if (c.getCount() < 0) {
            return null;
        }

        if (c.moveToFirst()) {
            existPerson = new Person();
            existPerson.setId(c.getInt(c.getColumnIndex("id")));
            existPerson.setFirstname(c.getString(c.getColumnIndex("firstname")));
            existPerson.setMiddlename(c.getString(c.getColumnIndex("middlename")));
            existPerson.setLastname(c.getString(c.getColumnIndex("lastname")));

            existPerson.setAge(c.getInt(c.getColumnIndex("age")));
            existPerson.setSalary(c.getDouble(c.getColumnIndex("salary")));

            existPerson.setUsername(c.getString(c.getColumnIndex("username")));
            existPerson.setPassword(c.getString(c.getColumnIndex("password")));

            existPerson.setCreated_date(c.getString(c.getColumnIndex("created_date")));
            existPerson.setUpdated_date(c.getString(c.getColumnIndex("updated_date")));
            existPerson.setEmploymentStatus(c.getString(c.getColumnIndex("employment_status")));

            existPerson.setCreated_time(c.getString(c.getColumnIndex("created_time")));
            existPerson.setUpdated_time(c.getString(c.getColumnIndex("updated_time")));
        }

        return existPerson;
    }

    @Override
    public Person GetById() {
        Person existPerson = null;
        String sql = "SELECT * FROM " + TABLE_NAME + " where id =?";
        Cursor c = dbHelper.queryReadable(sql, new String[]{String.valueOf(person.getId())});

        if (c.getCount() < 0) {
            return null;
        }

        if (c.moveToFirst()) {
            existPerson = new Person();
            existPerson.setId(c.getInt(c.getColumnIndex("id")));
            existPerson.setFirstname(c.getString(c.getColumnIndex("firstname")));
            existPerson.setMiddlename(c.getString(c.getColumnIndex("middlename")));
            existPerson.setLastname(c.getString(c.getColumnIndex("lastname")));

            existPerson.setAge(c.getInt(c.getColumnIndex("age")));
            existPerson.setSalary(c.getDouble(c.getColumnIndex("salary")));
            existPerson.setEmploymentStatus(c.getString(c.getColumnIndex("employment_status")));

            existPerson.setUsername(c.getString(c.getColumnIndex("username")));
            existPerson.setPassword(c.getString(c.getColumnIndex("password")));

            existPerson.setCreated_date(c.getString(c.getColumnIndex("created_date")));
            existPerson.setUpdated_date(c.getString(c.getColumnIndex("updated_date")));

            existPerson.setCreated_time(c.getString(c.getColumnIndex("created_time")));
            existPerson.setUpdated_time(c.getString(c.getColumnIndex("updated_time")));
        }

        return existPerson;
    }
    @Override
    public List<Person> GetAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME;
        Cursor c = db.rawQuery(sql, null);
        List<Person> personList = new ArrayList<>();
        if (c.getCount() < 0) {
            return null;
        } else {
            while (c.moveToNext()) {
                Person existPerson = new Person();
                existPerson.setId(c.getInt(c.getColumnIndex("id")));
                existPerson.setFirstname(c.getString(c.getColumnIndex("firstname")));
                existPerson.setMiddlename(c.getString(c.getColumnIndex("middlename")));
                existPerson.setLastname(c.getString(c.getColumnIndex("lastname")));

                existPerson.setAge(c.getInt(c.getColumnIndex("age")));
                existPerson.setSalary(c.getDouble(c.getColumnIndex("salary")));
                existPerson.setEmploymentStatus(c.getString(c.getColumnIndex("employment_status")));

                existPerson.setUsername(c.getString(c.getColumnIndex("username")));
                existPerson.setPassword(c.getString(c.getColumnIndex("password")));

                existPerson.setCreated_date(c.getString(c.getColumnIndex("created_date")));
                existPerson.setUpdated_date(c.getString(c.getColumnIndex("updated_date")));

                existPerson.setCreated_time(c.getString(c.getColumnIndex("created_time")));
                existPerson.setUpdated_time(c.getString(c.getColumnIndex("updated_time")));

                personList.add(existPerson);
            }
            return personList;
        }
    }


}
