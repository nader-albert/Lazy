package org.nalbert.lazyidle.example.lazyrecord;

import com.googlecode.lazyrecords.Definition;
import com.googlecode.lazyrecords.Keyword;
import org.nalbert.lazyidle.example.model.Organization;

import static com.googlecode.lazyrecords.Grammar.definition;
import static com.googlecode.lazyrecords.Grammar.keyword;

import java.math.BigDecimal;
import java.net.URI;
import java.util.UUID;

/**
 * @author nader albert
 * @since 11/08/2014.
 */
public interface OrganizationDef extends Definition {

  OrganizationDef organizationDef = definition(OrganizationDef.class);

  Keyword<UUID> uuid = keyword("uuid", UUID.class);
  Keyword<URI> url = keyword("url", URI.class);
  Keyword<String> name = keyword("name", String.class);
  Keyword<Boolean> mutliNational = keyword("isMultiNational", Boolean.class);
  Keyword<BigDecimal> numberOfBranches = keyword("branches", BigDecimal.class);
}
