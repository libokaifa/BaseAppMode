package com.libo.testmodel.newslist;



import com.chad.library.adapter.base.BaseBinderAdapter;
import com.chad.library.adapter.base.BaseProviderMultiAdapter;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.libo.base.customview.BaseCustomViewModel;
import com.libo.common.databinding.PictureTitleViewBinding;
import com.libo.common.databinding.TitleViewBinding;
import com.libo.common.picturetitleview.PictureTitleViewModel;
import com.libo.common.titleview.TitleViewModel;
import com.libo.testmodel.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.databinding.DataBindingUtil;

/**
 * /**
 *
 * @Author libo
 * @create 2021/6/15 9:41 上午
 * @describe:
 */
public class NewsListRecyclerViewAdapter  extends BaseProviderMultiAdapter<BaseCustomViewModel> {
    private final int VIEW_TYPE_PICTURE_TITLE = 1;
    private final int VIEW_TYPE_TITLE = 2;

    public NewsListRecyclerViewAdapter() {
        super();
        addItemProvider(new PicVH());
        addItemProvider(new TItleVH());
    }

    @Override
    protected int getItemType(@NotNull List<? extends BaseCustomViewModel> list, int i) {
        if (list.get(i) instanceof PictureTitleViewModel){
            return  VIEW_TYPE_PICTURE_TITLE;
        }
        return VIEW_TYPE_TITLE;
    }
    class  TItleVH extends BaseItemProvider<BaseCustomViewModel>{

        @Override
        public int getItemViewType() {
            return VIEW_TYPE_TITLE;
        }

        @Override
        public int getLayoutId() {
            return R.layout.title_view;
        }

        @Override
        public void convert(@NotNull BaseViewHolder baseViewHolder, BaseCustomViewModel baseCustomViewModel) {
            TitleViewBinding titleViewBinding=DataBindingUtil.bind(baseViewHolder.itemView);
            titleViewBinding.setViewModel((TitleViewModel) baseCustomViewModel);
            titleViewBinding.executePendingBindings();
        }
    }

    class  PicVH extends BaseItemProvider<BaseCustomViewModel>{

        @Override
        public int getItemViewType() {
            return VIEW_TYPE_PICTURE_TITLE;
        }

        @Override
        public int getLayoutId() {
            return  R.layout.picture_title_view;
        }

        @Override
        public void convert(@NotNull BaseViewHolder baseViewHolder, BaseCustomViewModel baseCustomViewModel) {
          PictureTitleViewBinding pictureTitleViewBinding= DataBindingUtil.bind(baseViewHolder.itemView);
          pictureTitleViewBinding.setViewModel((PictureTitleViewModel) baseCustomViewModel);
          pictureTitleViewBinding.executePendingBindings();
        }
    }
}
