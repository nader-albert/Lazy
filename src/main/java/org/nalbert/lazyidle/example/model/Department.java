package org.nalbert.lazyidle.example.model;

import com.googlecode.lazyrecords.Definition;

import java.net.URI;

/**
 * @author nader albert
 * @since 11/08/2014.
 */
public abstract class Department implements Definition {
  URI uuid;
  String name;
  String description;
  String numberOfPeople;

}
