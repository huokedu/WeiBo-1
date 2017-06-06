package com.wenming.weiswift.app.home.presenter;

import com.wenming.weiswift.app.common.oauth.AccessTokenManager;
import com.wenming.weiswift.app.home.contract.HomeContract;
import com.wenming.weiswift.app.home.data.HomeDataSource;
import com.wenming.weiswift.app.home.entity.Group;

import java.util.List;

/**
 * Created by wenmingvs on 2017/6/5.
 */

public class HomePresenter implements HomeContract.Presenter {
    private HomeDataSource mDataModel;
    private HomeContract.View mView;

    public HomePresenter(HomeDataSource dataModel, HomeContract.View view) {
        this.mDataModel = dataModel;
        this.mView = view;
        this.mView.setPresenter(this);
    }

    @Override
    public void start() {
        requestGroups();
    }

    @Override
    public void requestGroups() {
        mView.showLoading();
        mDataModel.requestGroups(AccessTokenManager.getInstance().getOAuthToken().getToken(), new HomeDataSource.RequestCallBack() {
            @Override
            public void onSuccess(List<Group> groups) {
                mView.hideRetryBg();
                mView.();
            }

            @Override
            public void onFail(String error) {
                mView.showServerMessage(error);
                mView.showRetryBg();
            }

            @Override
            public void onNetWorkNotConnected() {
                mView.showNoneNetWork();
                mView.showRetryBg();
            }

            @Override
            public void onTimeOut() {
                mView.showRetryBg();
            }
        });
    }
}