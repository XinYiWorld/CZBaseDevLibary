package com.xinyi.czbasedevtool.base.view.flag;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

/**
 * Created by 陈章 on 2017/1/18 0018.
 * func:
 */

public class BadgeViewFactory {
    public static BadgeView create (Context context, View target){
        BadgeView badgeView = new BadgeView(context, target,7,7);
        badgeView.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
        badgeView.setBadgeMargin(1, 1);
        badgeView.setBadgeBackgroundColor(Color.RED);
        return badgeView;
    }
}
