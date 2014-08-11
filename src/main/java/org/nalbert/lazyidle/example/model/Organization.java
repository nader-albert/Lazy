package org.nalbert.lazyidle.example.model;

import com.googlecode.lazyrecords.Definition;

import java.math.BigDecimal;
import java.net.URI;

/**
 * @author nader albert
 * @sine 11/08/2014.
 */
public abstract class Organization implements Definition {
  URI uid;
  String name;
  String url;
  Boolean multinational;
  BigDecimal numberOfBranches;
}
