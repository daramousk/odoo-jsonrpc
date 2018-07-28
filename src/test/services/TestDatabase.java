package test.services;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import javax.json.JsonObject;

import org.junit.Test;

import main.Odoo;
import services.Database;
import services.Database.FORMAT_DUMP;

public class TestDatabase {

    @Test
    public void testWorkflow() throws main.Error, IOException {
        Odoo instance = new Odoo("localhost", "http", 8069, 100, 8.0F, null);
        Database db = instance.getServiceDatabase();
        db.create("admin", "demo", true, "en_US", "admin");
        assertEquals("admin", db.list().get("name"));
        JsonObject dump = db.dump("admin", "admin", FORMAT_DUMP.ZIP);
        db.restore("admin", "admin2", new File(""), false);
        db.duplicate("admin", "admin2", "admin3");
        db.drop("admin", "admin2");
        // TODO add assertions
    }

}
