package org.nalbert.lazyidle.example.lazyrecord;

import com.googlecode.lazyrecords.Definition;
import com.googlecode.lazyrecords.Keyword;
import org.nalbert.lazyidle.example.model.Department;

import java.math.BigDecimal;
import java.net.URI;
import java.util.UUID;

import static com.googlecode.lazyrecords.Grammar.definition;
import static com.googlecode.lazyrecords.Grammar.keyword;

/**
 * @author nader albert
 * @since 11/08/2014.
 */
public interface DepartmentDef extends Definition {

  Department organizationDef = definition(Department.class);

  Keyword<URI> url = keyword("url", URI.class);
  Keyword<String> name = keyword("name", String.class);
  Keyword<String> description = keyword("desctiption", String.class);

  Keyword<UUID> uuid = keyword("uuid", UUID.class);
  Keyword<BigDecimal> numberOfBranches = keyword("branches", BigDecimal.class);
}
