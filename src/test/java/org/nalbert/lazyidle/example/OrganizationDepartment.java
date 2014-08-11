package org.nalbert.lazyidle.example;

import com.googlecode.lazyrecords.Grammar;
import com.googlecode.lazyrecords.Keyword;
import com.googlecode.lazyrecords.Record;
import com.googlecode.lazyrecords.Records;
import com.googlecode.totallylazy.Function1;
import com.googlecode.totallylazy.Sequence;
import org.nalbert.lazyidle.example.lazyrecord.OrganizationDef;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

import org.nalbert.lazyidle.example.h2.DataSource;

import static com.googlecode.lazyrecords.Grammar.all;
import static com.googlecode.lazyrecords.Grammar.keyword;
import static com.googlecode.lazyrecords.Grammar.where;
import static com.googlecode.totallylazy.matchers.NumberMatcher.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author nader albert
 * @since 11/08/2014.
 */
@Test
public class OrganizationDepartment {

  protected Records records;
  protected Connection connection;

  @Test
  /**
   * Tests the eager addition of a list of Organization records. The term eager means that the sequence of records is
   * being added to the Records data-structure as soon as the Records.add function is being invoked.
   * @see {@link testLazyOrganizationsAdd} for a different implementation.
   * */
  public void testEagerOrganizationsAdd() {
    this.connection = DataSource.getH2Connection();
    try {
       records = DataSource.createRecords(connection);
    } catch (SQLException e) {
      e.printStackTrace();
    }

   Sequence<Record> recordsSequence = records.get(OrganizationDef.organizationDef);

    URI organization1URI = null;
    URI organization2URI = null;
    URI organization3URI = null;
    URI organization4URI = null;

    try {
      organization1URI = new URI("http://www.pwc.au.com");
      organization2URI = new URI("http://www.deloite.au.com");
      organization3URI = new URI("http://www.ernest.au.com");
      organization4URI = new URI("http://www.xyz.au.com");

    } catch (URISyntaxException e) {
      e.printStackTrace();
    }

    Record organizationRecord1 = generateOrganizationRecord(UUID.randomUUID(),organization1URI,"PwC",true,new BigDecimal(157));
    Record organizationRecord2 = generateOrganizationRecord(UUID.randomUUID(),organization2URI,"Ernest",true,new BigDecimal(158));
    Record organizationRecord3 = generateOrganizationRecord(UUID.randomUUID(),organization3URI,"Deloite",true,new BigDecimal(156));
    Record organizationRecord4 = generateOrganizationRecord(UUID.randomUUID(),organization4URI,"XYZ",true,new BigDecimal(155));

    assertThat(records.get(OrganizationDef.organizationDef).filter(all()).size(), is(0));

    // sequence.add is deprecated to avoid mutability, and that's why cons is used instead. note that another sequence
    // object has been created rather than modifying the existing one.
    recordsSequence = recordsSequence.cons(organizationRecord1);
    recordsSequence = recordsSequence.cons(organizationRecord2);
    recordsSequence = recordsSequence.cons(organizationRecord3);
    recordsSequence = recordsSequence.cons(organizationRecord4);

    records.add(OrganizationDef.organizationDef,recordsSequence);
    assertThat(records.get(OrganizationDef.organizationDef).filter(all()).size(), is(4));
  }

  @Test
  /**
   * as opposed to the previous eager implementation, this function tests the lazy way of adding sequence of new records
   * to the Records data-structure. Instead of directly adding the modified sequence, a function add, is being pulled back
   * which can be invoked at a later time, when the addition is really required. You are kind of describing what you need
   * first and then at a later time, you are actually invoking it, and from here stems the term lazy.
   * */
  public void testLazyOrganizationsAdd() {
    this.connection = DataSource.getH2Connection();
    try {
      records = DataSource.createRecords(connection);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    Sequence<Record> recordsSequence = records.get(OrganizationDef.organizationDef);

    URI organization1URI = null;
    URI organization2URI = null;
    URI organization3URI = null;
    URI organization4URI = null;

    try {
      organization1URI = new URI("http://www.pwc.au.com");
      organization2URI = new URI("http://www.deloite.au.com");
      organization3URI = new URI("http://www.ernest.au.com");
      organization4URI = new URI("http://www.xyz.au.com");

    } catch (URISyntaxException e) {
      e.printStackTrace();
    }

    Record organizationRecord1 = generateOrganizationRecord(UUID.randomUUID(),organization1URI,"PwC",true,new BigDecimal(157));
    Record organizationRecord2 = generateOrganizationRecord(UUID.randomUUID(),organization2URI,"Ernest",true,new BigDecimal(158));
    Record organizationRecord3 = generateOrganizationRecord(UUID.randomUUID(),organization3URI,"Deloite",true,new BigDecimal(156));
    Record organizationRecord4 = generateOrganizationRecord(UUID.randomUUID(),organization4URI,"XYZ",true,new BigDecimal(155));

    assertThat(records.get(OrganizationDef.organizationDef).filter(all()).size(), is(0));

    // sequence.add is deprecated to avoid mutability, and that's why cons is used instead. note that another sequence
    // object has been created rather than modifying the existing one.
    recordsSequence = recordsSequence.cons(organizationRecord1);
    recordsSequence = recordsSequence.cons(organizationRecord2);
    recordsSequence = recordsSequence.cons(organizationRecord3);
    recordsSequence = recordsSequence.cons(organizationRecord4);

    Function1<Records,Number> addFunc = Records.functions.add(OrganizationDef.organizationDef, recordsSequence);

    // ensure that no records have been added yet
    assertThat(records.get(OrganizationDef.organizationDef).filter(all()).size(), is(0));

    Number numberOfAddedRecords = 0;
    try {
      numberOfAddedRecords = addFunc.call(records);
    } catch (Exception e) {
      e.printStackTrace();
    }
    // now 4 new records should have been added..
    assertThat(numberOfAddedRecords, is(4));
    assertThat(records.get(OrganizationDef.organizationDef).filter(all()).size(), is(4));
  }


  @Test
  /**
   * .
   * */
  public void testOrganizationRecordsFilter() {
    this.connection = DataSource.getH2Connection();
    try {
      records = DataSource.createRecords(connection);
    } catch (SQLException e) {
      e.printStackTrace();
    }

    Sequence<Record> recordsSequence = records.get(OrganizationDef.organizationDef);

    URI organization1URI = null;
    URI organization2URI = null;
    URI organization3URI = null;
    URI organization4URI = null;

    try {
      organization1URI = new URI("http://www.pwc.au.com");
      organization2URI = new URI("http://www.deloite.au.com");
      organization3URI = new URI("http://www.ernest.au.com");
      organization4URI = new URI("http://www.xyz.au.com");

    } catch (URISyntaxException e) {
      e.printStackTrace();
    }

    Record organizationRecord1 = generateOrganizationRecord(UUID.randomUUID(),organization1URI,"PwC",true,new BigDecimal(157));
    Record organizationRecord2 = generateOrganizationRecord(UUID.randomUUID(),organization2URI,"Ernest",true,new BigDecimal(158));
    Record organizationRecord3 = generateOrganizationRecord(UUID.randomUUID(),organization3URI,"Deloite",true,new BigDecimal(156));
    Record organizationRecord4 = generateOrganizationRecord(UUID.randomUUID(),organization4URI,"XYZ",true,new BigDecimal(155));

    assertThat(records.get(OrganizationDef.organizationDef).filter(all()).size(), is(0));

    // sequence.add is deprecated to avoid mutability, and that's why cons is used instead. note that another sequence
    // object has been created rather than modifying the existing one.
    recordsSequence = recordsSequence.cons(organizationRecord1);
    recordsSequence = recordsSequence.cons(organizationRecord2);
    recordsSequence = recordsSequence.cons(organizationRecord3);
    recordsSequence = recordsSequence.cons(organizationRecord4);

    Function1<Records,Number> addFunc = Records.functions.add(OrganizationDef.organizationDef, recordsSequence);

    try {
      addFunc.call(records); // the number of updated records is not relevant in this test case and hence it is intentionally ignored
    } catch (Exception e) {
      e.printStackTrace();
    }

    // ensure that no records have been added yet
    assertThat(records.get(OrganizationDef.organizationDef).filter(all()).size(), is(4));

    // ensure that no records have been added yet
    assertThat(records.get(OrganizationDef.organizationDef).filter(where(OrganizationDef.mutliNational, Grammar.is(true))).size(), is(4));

    // ensure that no records have been added yet
    assertThat(records.get(OrganizationDef.organizationDef).filter(where(OrganizationDef.url, Grammar.is(organization1URI))).size(), is(1));

    // ensure that only one organization is called PwC
    assertThat(records.get(OrganizationDef.organizationDef).filter(where(OrganizationDef.name, Grammar.eq("PwC"))).size(), is(1));

    // ensure that no organization is called pwc with small letters
    assertThat(records.get(OrganizationDef.organizationDef).filter(where(OrganizationDef.name, Grammar.is("pwc"))).size(), is(0));

    // ensure that no records have been added yet
    assertThat(records.get(OrganizationDef.organizationDef).filter(where(OrganizationDef.numberOfBranches, Grammar.between(new BigDecimal(155),new BigDecimal(157)))).size(), is(3));
  }

  /**
   * */
  public void testOrganizationRecordValueRetrieval(){

  }

  /**
   * helper method that just creates a new organization record, with the list of specified parameters.
   * */
  private Record generateOrganizationRecord(UUID organizationUUID, URI organizationURI, String organizationName,
                                            boolean isMultiNational, BigDecimal branches) {

    return Grammar.record(OrganizationDef.uuid,organizationUUID,OrganizationDef.url,organizationURI,
            OrganizationDef.name, organizationName, OrganizationDef.mutliNational, isMultiNational,
            OrganizationDef.numberOfBranches,branches);
  }

}