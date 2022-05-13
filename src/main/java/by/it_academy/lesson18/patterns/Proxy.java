package by.it_academy.lesson18.patterns;

/**
 * @author Maxim Tereshchenko
 */
interface Database {

    Object fetch(String sql);
}

class RealDatabase implements Database {
    @Override
    public Object fetch(String sql) {
        return null;
    }
}

class Proxy implements Database {

    private Database database;

    @Override
    public Object fetch(String sql) {
        if (database == null) {
            database = new RealDatabase();
        }

        return database.fetch(sql);
    }
}
