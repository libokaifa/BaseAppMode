package com.libo.baseappmodel;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.libo.baseappmodel.databinding.ItemStuBinding;
import com.libo.room.Student;
import com.libo.room.StudentDao;

/**
 * author : LiBo
 * date : 2023/5/18 15:13
 * description :
 */
public class ItemStuAdapter  extends BaseQuickAdapter<Student, BaseDataBindingHolder<ItemStuBinding>> {
    public ItemStuAdapter() {
        super(R.layout.item_stu);
    }

    @Override
    protected void convert(@NonNull BaseDataBindingHolder<ItemStuBinding> itemStuBindingBaseDataBindingHolder, Student student) {
        itemStuBindingBaseDataBindingHolder.getDataBinding().tvEmpty.setText(student.getName()+" ::: "+student.getPassword());
    }
}
