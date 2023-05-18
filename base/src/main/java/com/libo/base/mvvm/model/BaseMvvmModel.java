package com.libo.base.mvvm.model;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.libo.base.preference.BasicDataPreferenceUtil;
import com.libo.base.util.GenericUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * /**
 *
 * @param <NETWORK_DATA>  网络数据
 * @param <RESULT_DATA>   网络返回数据
 */
public abstract class BaseMvvmModel<NETWORK_DATA, RESULT_DATA> implements MvvmDataObserver<NETWORK_DATA> {
    private CompositeDisposable compositeDisposable;
    protected int mPage = 1;
    protected WeakReference<IBaseModelListener> mReferenceIBaseModelListener;
    private boolean mIsPaging;
    private final int INIT_PAGE_NUMBER;
    private boolean mIsLoading;
    private String mCachedPreferenceKey;
    private String mApkPredefinedData;

    public BaseMvvmModel(boolean isPaging, String cachedPreferenceKey, String apkPredefinedData, int initPageNumber) {
        this.mIsPaging = isPaging;
        if (isPaging ) {
            INIT_PAGE_NUMBER = initPageNumber;
        } else {
            INIT_PAGE_NUMBER = -1;
        }
        this.mCachedPreferenceKey = cachedPreferenceKey;
        this.mApkPredefinedData = apkPredefinedData;
    }

    public void register(IBaseModelListener<RESULT_DATA> listener) {
        if (listener != null) {
            mReferenceIBaseModelListener = new WeakReference<>(listener);
        }
    }

    public void refresh() {
        // Need to throw exception if register is not called;
        if (!mIsLoading) {
            if (mIsPaging) {
                mPage = INIT_PAGE_NUMBER;
            }
            mIsLoading = true;
            load();
        }
    }

    public void loadNextPage() {
        // Need to throw exception if register is not called;
        if (!mIsLoading) {
            mIsLoading = true;
            load();
        }
    }

    protected boolean isNeedToUpdate(long cachedTimeSlot) {
        return true;
    }

    public void getCachedDataAndLoad(){
        if(!mIsLoading) {
            mIsLoading = true;
            if(mCachedPreferenceKey != null){
                String saveDataString = BasicDataPreferenceUtil.getInstance().getString(mCachedPreferenceKey);
                if(!TextUtils.isEmpty(saveDataString)) {
                    try {
                        NETWORK_DATA savedData = new Gson().fromJson(new JSONObject(saveDataString).getString("data"), (Class<NETWORK_DATA>) GenericUtils.getGenericType(this));
                        if(savedData != null) {
                            onSuccess(savedData, true);
                        }
                        long timeSlot = Long.parseLong(new JSONObject(saveDataString).getString("updateTimeInMillis"));
                        if(isNeedToUpdate(timeSlot)) {
                            load();
                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if(mApkPredefinedData != null) {
                    NETWORK_DATA savedData = new Gson().fromJson(mApkPredefinedData, (Class<NETWORK_DATA>) GenericUtils.getGenericType(this));
                    if(savedData != null) {
                        onSuccess(savedData, true);
                    }
                }
            }
            load();
        }
    }


    public abstract void load();

    protected void notifyResultToListener(NETWORK_DATA networkData, RESULT_DATA resultData, boolean isFromCache) {
        IBaseModelListener listener = mReferenceIBaseModelListener.get();
        if (listener != null) {
            // notify
            if (mIsPaging) {
                listener.onLoadSuccess( resultData, new PagingResult(mPage == INIT_PAGE_NUMBER, resultData == null ? true : ((List) resultData).isEmpty(), ((List) resultData).size() > 0));
            } else {
                listener.onLoadSuccess( resultData,null);
            }

            // save resultData to preference
            if(mIsPaging) {
                if(mCachedPreferenceKey != null && mPage == INIT_PAGE_NUMBER && !isFromCache){
                    saveDataToPreference(networkData);
                }
            } else {
                if(mCachedPreferenceKey != null && !isFromCache){
                    saveDataToPreference(networkData);
                }
            }

            // update page number
            if (mIsPaging && !isFromCache) {
                if (resultData != null && ((List) resultData).size() > 0) {
                    mPage++;
                }
            }
        }
        if(!isFromCache) {
            mIsLoading = false;
        }
    }

    protected void loadFail(final String errorMessage) {
        IBaseModelListener listener = mReferenceIBaseModelListener.get();
        if (listener != null) {
            if (mIsPaging) {
                listener.onLoadFail( errorMessage, new PagingResult(mPage == INIT_PAGE_NUMBER, true, false));
            } else {
                listener.onLoadFail(errorMessage,null);
            }
        }
        mIsLoading = false;
    }

    protected void saveDataToPreference(NETWORK_DATA data) {
        if(data != null) {
            BaseCachedData<NETWORK_DATA> cachedData = new BaseCachedData<>();
            cachedData.data = data;
            cachedData.updateTimeInMillis = System.currentTimeMillis();
            BasicDataPreferenceUtil.getInstance().setString(mCachedPreferenceKey, new Gson().toJson(cachedData));
        }
    }

    public void cancel() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }


    public void addDisposable(Disposable d) {
        if (d == null) {
            return;
        }

        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }

        compositeDisposable.add(d);
    }
}
