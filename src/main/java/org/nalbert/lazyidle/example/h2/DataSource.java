package org.nalbert.lazyidle.example.h2;

import com.googlecode.lazyrecords.MemoryLogger;
import com.googlecode.lazyrecords.PrintStreamLogger;
import com.googlecode.lazyrecords.Records;
import com.googlecode.lazyrecords.SchemaGeneratingRecords;
import com.googlecode.lazyrecords.sql.SqlRecords;
import com.googlecode.lazyrecords.sql.SqlSchema;
import com.googlecode.lazyrecords.sql.grammars.AnsiSqlGrammar;
import com.googlecode.lazyrecords.sql.grammars.SqlGrammar;
import com.googlecode.lazyrecords.sql.mappings.SqlMappings;
import org.h2.jdbcx.JdbcDataSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.SQLException;

import static com.googlecode.lazyrecords.Loggers.loggers;
import static com.googlecode.totallylazy.Streams.streams;


/**
 * @author nader albert
 * @since 11/08/2014.
 */
public class DataSource {

    private DataSource() {
    }

    public static Connection getH2Connection() {
      try {
        return connectToH2();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }

    private static Connection connectToH2() throws SQLException {
      JdbcDataSource ds = new JdbcDataSource();
      ds.setURL("jdbc:h2:mem:lazy_records_db");

      ds.setPassword("");ds.setUser("sa");
      return ds.getConnection();
    }

    public static Records createRecords(Connection connection) throws SQLException {
      SqlGrammar grammar = new AnsiSqlGrammar();
      SqlRecords sqlRecords = sqlRecords(connection, grammar);
      Records generatedRecords = new SchemaGeneratingRecords(sqlRecords, new SqlSchema(sqlRecords, grammar));
      return generatedRecords;
    }

    private static SqlRecords sqlRecords(Connection connection, SqlGrammar grammar) {
      return new SqlRecords(
              connection, new SqlMappings(), grammar,
              loggers(new MemoryLogger(), new PrintStreamLogger(new PrintStream(streams(System.out, new ByteArrayOutputStream()))))
      );
    }
  }
