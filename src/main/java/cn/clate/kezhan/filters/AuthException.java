package cn.clate.kezhan.filters;

import org.nutz.mvc.View;

public class AuthException extends Exception {
    private View mView;

    public AuthException(View view){
        mView = view;
    }

    public View getView(){
        return mView;
    }
}
