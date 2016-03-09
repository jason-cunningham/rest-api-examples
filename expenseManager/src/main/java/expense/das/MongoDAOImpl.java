package expense.das;

import com.mongodb.*;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jcunningham on 3/6/16.
 */
public class MongoDAOImpl implements DAO<DBObject> {
    private static MongoClient mongo = null;
    private static DB expenseDB = null;
    private static MongoDAOImpl dao = null;

    private static final String EXPENSE_DB_NAME = "expense_db";
    private static final String MERCHANT_TABLE_NAME = "merchant";
    public static final String QUERY_ID = "_id";

    static {
        try {
            dao = new MongoDAOImpl();
            mongo = new MongoClient( "localhost" , 27017 );
            expenseDB = mongo.getDB(EXPENSE_DB_NAME);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private MongoDAOImpl(){}

    public static MongoDAOImpl getMongoInstance() {
        return dao;
    }

    @Override
    public String create(DBObject data) throws Exception {
        DBCollection merchantTable = expenseDB.getCollection(MERCHANT_TABLE_NAME);
        merchantTable.insert(data);
        return data.get("_id").toString();
    }

    @Override
    public String update(String id, DBObject data) throws Exception {
        DBCollection merchantTable = expenseDB.getCollection(MERCHANT_TABLE_NAME);
        BasicDBObject query = new BasicDBObject();
        query.put(QUERY_ID, new ObjectId(id));
        merchantTable.update(query, data);
        return id;
    }

    @Override
    public List<DBObject> findAll() throws Exception {
        DBCollection merchantTable = expenseDB.getCollection(MERCHANT_TABLE_NAME);
        DBCursor cursor = merchantTable.find();
        List<DBObject> records = new ArrayList<DBObject>();

        while (cursor.hasNext()) {
            records.add(cursor.next());
        }

        return records;
    }

    @Override
    public List<DBObject> find(String id) throws Exception {
        DBCollection merchantTable = expenseDB.getCollection(MERCHANT_TABLE_NAME);

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put(QUERY_ID, new ObjectId(id));

        DBCursor cursor = merchantTable.find(searchQuery);
        List<DBObject> records = new ArrayList<DBObject>();

        while (cursor.hasNext()) {
            records.add(cursor.next());
        }

        return records;
    }

    @Override
    public void delete(String id) throws Exception {
        DBCollection merchantTable = expenseDB.getCollection(MERCHANT_TABLE_NAME);
        BasicDBObject query = new BasicDBObject();
        query.put(QUERY_ID, new ObjectId(id));
        WriteResult result = merchantTable.remove(query);
    }
}
