package cn.yulan.user.module.dao;

import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.springframework.stereotype.Repository;

/**
 * 图片 key - value 持久层
 *
 * @author Yulan Zhou
 * @date 2021/03/15
 */

@Repository
public class ImageKVDAO {

    static {
        RocksDB.loadLibrary();
    }

    private RocksDB db;
    private static String dbPath = "E:\\testdata";

    //
    public boolean keyMayExist(byte[] bytes) {
        Options options = new Options();
        options.setCreateIfMissing(true);
        if (db != null) {
            db.close();
        }
        try {
            db = RocksDB.open(options, dbPath);
            return db.keyMayExist(bytes, new StringBuffer());
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
        return false;
    }

    public byte[] get(byte[] key) {
        Options options = new Options();
        options.setCreateIfMissing(true);
        if (db != null) {
            db.close();
        }
        try {
            db = RocksDB.open(options, dbPath);
            return db.get(key);
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void put(byte[] key, byte[] value) {
        Options options = new Options();
        options.setCreateIfMissing(true);
        if (db != null) {
            db.close();
        }
        try {
            db = RocksDB.open(options, dbPath);
            db.put(key,value);
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
    }



}