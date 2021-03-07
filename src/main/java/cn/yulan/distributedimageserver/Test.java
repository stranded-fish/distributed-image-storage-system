package cn.yulan.distributedimageserver;

import org.rocksdb.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * test
 *
 * @author Yulan Zhou
 * @date 2021/02/27
 */
class Test {

    private static final String dbPath = "C:\\Users\\54738\\Desktop\\rockddd";

    static {
        RocksDB.loadLibrary();
    }

    RocksDB rocksDB;

    //  RocksDB.DEFAULT_COLUMN_FAMILY
    public void testDefaultColumnFamily() throws RocksDBException, IOException {
        Options options = new Options();
        options.setCreateIfMissing(true);

        // 文件不存在，则先创建文件
        rocksDB = RocksDB.open(options, dbPath);// 这个方法如果目标文件夹不存在，会自动创建，但只能创建一层文件夹，不能递归创建多层

        /**
         * 简单key-value
         */
        byte[] key = "Hello".getBytes();
        byte[] value = "World".getBytes();
        rocksDB.put(key, value);

        byte[] keys = "Hellos".getBytes();
        byte[] values = "Worlds".getBytes();
        rocksDB.put(keys, values);

        byte[] getValue = rocksDB.get(key);
        System.out.println(new String(getValue));

        byte[] getValues = rocksDB.get(keys);
        System.out.println(new String(getValues));

//        for (int i = 0; i < 99999; i++) {
//            byte[] keys = ("Hello" + i).getBytes();
//            byte[] values = ("World" + i).getBytes();
//            rocksDB.put(keys, values);
//        }

        /**
         * 通过List做主键查询
         */
        /*
        rocksDB.put("SecondKey".getBytes(), "SecondValue".getBytes());

        List<byte[]> keys = new ArrayList<>();
        keys.add(key);
        keys.add("SecondKey".getBytes());

        Map<byte[], byte[]> valueMap = rocksDB.multiGet(keys);
        for (Map.Entry<byte[], byte[]> entry : valueMap.entrySet()) {
            System.out.println(new String(entry.getKey()) + ":" + new String(entry.getValue()));
        }

        /**
         *  打印全部[key - value]
         */
//        RocksIterator iter = rocksDB.newIterator();
//        for (iter.seekToFirst(); iter.isValid(); iter.next()) {
//            System.out.println("iter key:" + new String(iter.key()) + ", iter value:" + new String(iter.value()));
//        }

        /**
         *  删除一个key
         */
//        rocksDB.delete(key);
//        System.out.println("after remove key:" + new String(key));
//
//        iter = rocksDB.newIterator();
//        for (iter.seekToFirst(); iter.isValid(); iter.next()) {
//            System.out.println("iter key:" + new String(iter.key()) + ", iter value:" + new String(iter.value()));
//        }
    }

    public void testCertainColumnFamily() throws RocksDBException {
        String table = "CertainColumnFamilyTest";
        String key = "certainKey";
        String value = "certainValue";

        List<ColumnFamilyDescriptor> columnFamilyDescriptors = new ArrayList<>();
        Options options = new Options();
        options.setCreateIfMissing(true);

        List<byte[]> cfs = RocksDB.listColumnFamilies(options, dbPath);
        if (cfs.size() > 0) {
            for (byte[] cf : cfs) {
                columnFamilyDescriptors.add(new ColumnFamilyDescriptor(cf, new ColumnFamilyOptions()));
            }
        } else {
            columnFamilyDescriptors.add(new ColumnFamilyDescriptor(RocksDB.DEFAULT_COLUMN_FAMILY, new ColumnFamilyOptions()));
        }

        List<ColumnFamilyHandle> columnFamilyHandles = new ArrayList<>();
        DBOptions dbOptions = new DBOptions();
        dbOptions.setCreateIfMissing(true);

        rocksDB = RocksDB.open(dbOptions, dbPath, columnFamilyDescriptors, columnFamilyHandles);
        for (int i = 0; i < columnFamilyDescriptors.size(); i++) {
            if (new String(columnFamilyDescriptors.get(i).columnFamilyName()).equals(table)) {
                rocksDB.dropColumnFamily(columnFamilyHandles.get(i));
            }
        }

        ColumnFamilyHandle columnFamilyHandle = rocksDB.createColumnFamily(new ColumnFamilyDescriptor(table.getBytes(), new ColumnFamilyOptions()));
        rocksDB.put(columnFamilyHandle, key.getBytes(), value.getBytes());

        byte[] getValue = rocksDB.get(columnFamilyHandle, key.getBytes());
        System.out.println("get Value : " + new String(getValue));

        rocksDB.put(columnFamilyHandle, "SecondKey".getBytes(), "SecondValue".getBytes());

        List<byte[]> keys = new ArrayList<byte[]>();
        keys.add(key.getBytes());
        keys.add("SecondKey".getBytes());

        List<ColumnFamilyHandle> handleList = new ArrayList<>();
        handleList.add(columnFamilyHandle);
        handleList.add(columnFamilyHandle);

        Map<byte[], byte[]> multiGet = rocksDB.multiGet(handleList, keys);
        for (Map.Entry<byte[], byte[]> entry : multiGet.entrySet()) {
            System.out.println(new String(entry.getKey()) + "--" + new String(entry.getValue()));
        }

        rocksDB.delete(columnFamilyHandle, key.getBytes());

        RocksIterator iter = rocksDB.newIterator(columnFamilyHandle);
        for (iter.seekToFirst(); iter.isValid(); iter.next()) {
            System.out.println(new String(iter.key()) + ":" + new String(iter.value()));
        }
    }

    public static void main(String[] args) throws Exception {
        Test test = new Test();
        test.testDefaultColumnFamily();
//        test.testCertainColumnFamily();
    }

}