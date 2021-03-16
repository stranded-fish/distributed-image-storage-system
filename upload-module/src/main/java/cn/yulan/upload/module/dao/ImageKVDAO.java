package cn.yulan.upload.module.dao;

import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

/**
 * 图片 key - value 持久层
 *
 * @author Yulan Zhou
 * @date 2021/03/15
 */

@Repository
public class ImageKVDAO {

    @Value("${rocksdb-save-path}")
    private String dbPath;

    private RocksDB db;

    @PostConstruct
    void initialize() {
        RocksDB.loadLibrary();
        final Options options = new Options();
        options.setCreateIfMissing(true);
        File file = new File(dbPath);
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            db = RocksDB.open(options, dbPath);
        } catch (RocksDBException e) {
//            log.error("Error initializng RocksDB, check configurations and permissions, exception: {}, message: {}, stackTrace: {}",
//                    ex.getCause(), ex.getMessage(), ex.getStackTrace());
            e.printStackTrace();
        }
        System.out.println("RocksDB initialized and ready to use");
        //        log.info("RocksDB initialized and ready to use");
    }


    //
    public boolean keyMayExist(byte[] bytes) {
        return db.keyMayExist(bytes, new StringBuffer());
    }

    public byte[] get(byte[] key) {
        try {
            return db.get(key);
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void put(byte[] key, byte[] value) {
        try {
            db.put(key, value);
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
    }

}