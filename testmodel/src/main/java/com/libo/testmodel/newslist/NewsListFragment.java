package com.libo.testmodel.newslist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.libo.base.customview.BaseCustomViewModel;
import com.libo.base.loadsir.EmptyCallback;
import com.libo.base.loadsir.ErrorCallback;
import com.libo.base.loadsir.LoadingCallback;
import com.libo.base.mvvm.viewmodel.ViewStatus;
import com.libo.testmodel.R;
import com.libo.testmodel.databinding.FragmentNewsBinding;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * /**
 *
 * @Author libo
 * @create 2021/6/15 9:34 上午
 * @describe:
 */
public class NewsListFragment  extends Fragment implements Observer {
    private NewsListRecyclerViewAdapter mAdapter;
    private FragmentNewsBinding viewDataBinding;
    private NewsListViewModel mNewsListViewModel;
    private LoadService mLoadService;

    protected final static String BUNDLE_KEY_PARAM_CHANNEL_ID = "bundle_key_param_channel_id";
    protected final static String BUNDLE_KEY_PARAM_CHANNEL_NAME = "bundle_key_param_channel_name";

    public static NewsListFragment newInstance(String channelId, String channelName) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY_PARAM_CHANNEL_ID, channelId);
        bundle.putString(BUNDLE_KEY_PARAM_CHANNEL_NAME, channelName);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mNewsListViewModel = new NewsListViewModel(getArguments().getString(BUNDLE_KEY_PARAM_CHANNEL_ID),getArguments().getString(BUNDLE_KEY_PARAM_CHANNEL_NAME));
        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false);
        mAdapter = new NewsListRecyclerViewAdapter();
        viewDataBinding.listview.setHasFixedSize(true);
        viewDataBinding.listview.setLayoutManager(new LinearLayoutManager(getContext()));
        viewDataBinding.listview.setAdapter(mAdapter);
        viewDataBinding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mNewsListViewModel.refresh();
            }
        });
        viewDataBinding.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mNewsListViewModel.loadNextPage();
            }
        });
        mNewsListViewModel.dataList.observe(this, new Observer<List<BaseCustomViewModel>>() {
            @Override
            public void onChanged(List<BaseCustomViewModel> baseCustomViewModels) {
                viewDataBinding.refreshLayout.finishRefresh();
                viewDataBinding.refreshLayout.finishLoadMore();
                mAdapter.setList(baseCustomViewModels);

            }
        });
        mNewsListViewModel.viewStatusLiveData.observe(this, this);
        mLoadService = LoadSir.getDefault().register(viewDataBinding.refreshLayout, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                mNewsListViewModel.refresh();
            }
        });
        return mLoadService.getLoadLayout();
    }

    @Override
    public void onChanged(Object o) {
        if (o instanceof ViewStatus && mLoadService != null) {
            switch ((ViewStatus) o) {
                case LOADING:
                    mLoadService.showCallback(LoadingCallback.class);
                    break;
                case EMPTY:
                    mLoadService.showCallback(EmptyCallback.class);
                    break;
                case SHOW_CONTENT:
                    mLoadService.showSuccess();
                    break;
                case NO_MORE_DATA:

                    break;
                case REFRESH_ERROR:
                    if (mNewsListViewModel.dataList.getValue().size() == 0) {
                        mLoadService.showCallback(ErrorCallback.class);
                    } else {

                    }
                    break;
                case LOAD_MORE_FAILED:

                    break;
            }
        }
    }
}
