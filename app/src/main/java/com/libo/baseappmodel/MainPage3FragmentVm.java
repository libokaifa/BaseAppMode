package com.libo.baseappmodel;

import androidx.lifecycle.LiveData;

import com.libo.base.baseapp.BaseApplication;
import com.libo.base.mvvm.viewmodel.BaseMvvmViewModel;
import com.libo.room.Student;
import com.libo.room.StudentRepository;

import java.util.List;

/**
 * author : LiBo
 * date : 2023/5/15 17:09
 * description :
 */
public class MainPage3FragmentVm extends BaseMvvmViewModel {
    private StudentRepository studentRepository;
    @Override
    protected void createDataModel() {
        studentRepository=new StudentRepository(BaseApplication.mApplication);
    }

    @Override
    protected void registerDataChangeListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onVMCleared() {

    }

    void insert(Student... students){
        studentRepository.insert(students);
    }
    void delete(Student student){
        studentRepository.delete(student);
    }
    void update(Student student){
        studentRepository.update(student);
    }
    List<Student> getAll(){
        return studentRepository.getAll();
    }

    LiveData<List<Student>> getAllLiveDataStudent(){
        return studentRepository.getAllLiveDataStudent();
    }
}
