package org.xutils.my_define;

import android.app.Application;

import org.xutils.DbManager;
import org.xutils.x;

import java.io.File;

/**
 * Created by 陈章 on 2017/6/21 0021.
 * func:
 * 自定义的dbutil的封装类
 * 1)客户端需要在
 */

public class CZDbManager {


    public static class Builder {
        boolean isOnline;           //是否是线上环境
        Application app;            //项目的Application
        String dbName;              //数据库名
        String dbDir;               //数据库存放路径
        int dbVersion;              //数据库版本
        DbManager.DbOpenListener dbOpenListener;
        DbManager.DbUpgradeListener dbUpgradeListener;

        public Builder() {
            isOnline = false;
            app = null;
            dbName = null;
            dbDir = null;
            dbVersion = 1;
            dbOpenListener = new DbManager.DbOpenListener() {
                @Override
                public void onDbOpened(DbManager db) {
                    // 开启WAL, 对写入加速提升巨大
                    db.getDatabase().enableWriteAheadLogging();
                    //不加这个创建不了表
                    db.getDatabase().execSQL("VACUUM;");
                }
            };
            dbUpgradeListener = null;
        }

        public Builder isOnline(boolean isOnline) {
            this.isOnline = isOnline;
            return this;
        }


        public Builder application(Application app) {
            this.app = app;
            return this;
        }

        public Builder dbName(String dbName) {
            if (!dbName.endsWith(".db")) {
                this.dbName = dbName + ".db";
            } else {
                this.dbName = dbName;
            }
            return this;
        }

        public Builder dbDir(String dbDir) {
            this.dbDir = dbDir;
            return this;
        }

        public Builder dbVersion(int dbVersion) {
            this.dbVersion = dbVersion;
            return this;
        }

        public Builder dbOpenListener(DbManager.DbOpenListener dbOpenListener) {
            this.dbOpenListener = dbOpenListener;
            return this;
        }

        public Builder dbUpgradeListener(DbManager.DbUpgradeListener dbUpgradeListener) {
            this.dbUpgradeListener = dbUpgradeListener;
            return this;
        }

        public void build() {
            x.Ext.init(app);//Xutils初始化
            x.Ext.setDebug(isOnline); // 是否输出debug日志
            DbManager.DaoConfig defaultDaoConfig;
            defaultDaoConfig = new DbManager.DaoConfig()
                    .setDbName(dbName)
                    // 不设置dbDir时, 默认存储在app的私有目录.
                    .setDbDir(new File(dbDir))
                    .setDbVersion(dbVersion)
                    .setDbOpenListener(dbOpenListener)
                    .setDbUpgradeListener(dbUpgradeListener);
            x.setDefaultDaoConfig(defaultDaoConfig);
        }
    }

    public static DbManager getDefaultDb() {
        return x.getDefaultDb();
    }

}
