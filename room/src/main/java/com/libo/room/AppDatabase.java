package com.libo.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Student.class}, version = 3,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "libo_db";

    private static AppDatabase instance;
    public static synchronized AppDatabase getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class
                                    ,DATABASE_NAME)
                    //可以强制在主线程运行数据库操作
                    .allowMainThreadQueries()
                    //强制升级
//                    .fallbackToDestructiveMigration()
                    .addMigrations(MIGRATION_2_3)// 升级策略
                    .build();

        }
        return instance;
    }
    public abstract  StudentDao studentDao();

    //进行数据库升级
    static final Migration MIGRATION_1_2=new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            //在这里用sql脚本完成数据变化
            database.execSQL("alter table student add column flag integer not null default 1");
        }
    };
    //进行数据库升级
    static final Migration MIGRATION_2_3=new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("create table student_temp (uid integer primary key not null,name text,pwd text,addressId)");
//            database.execSQL("insert into student (uid,name,pwd,addressid)" + " select uid,name,pwd,addressid from student");
//            database.execSQL("drop table student");
//            database.execSQL("alter table student_temp rename to student");
        }
    };


}






