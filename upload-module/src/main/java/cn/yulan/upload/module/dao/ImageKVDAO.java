package cn.yulan.upload.module.dao;

import lombok.extern.slf4j.Slf4j;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.File;

/**
 * 图片 key - value 持久层
 *
 * @author Yulan Zhou
 */
@Repository
@Slf4j
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
            log.error("Error initializing RocksDB, check configurations and permissions, exception: {}, message: {}, stackTrace: {}",
                    e.getCause(), e.getMessage(), e.getStackTrace());
        }
        log.info("RocksDB initialized and ready to use");
    }

    public boolean keyMayExist(byte[] bytes) {
        return db.keyMayExist(bytes, new StringBuffer());
    }

    public byte[] get(byte[] key) {
        try {
            return db.get(key);
        } catch (RocksDBException e) {
            log.error("Error executing GET operation, check configurations and permissions, exception: {}, message: {}, stackTrace: {}",
                    e.getCause(), e.getMessage(), e.getStackTrace());
        }
        return null;
    }

    public void put(byte[] key, byte[] value) {
        try {
            db.put(key, value);
        } catch (RocksDBException e) {
            log.error("Error executing PUT operation, check configurations and permissions, exception: {}, message: {}, stackTrace: {}",
                    e.getCause(), e.getMessage(), e.getStackTrace());
        }
    }
}