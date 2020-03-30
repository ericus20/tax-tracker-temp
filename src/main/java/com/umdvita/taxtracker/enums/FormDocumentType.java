package com.umdvita.taxtracker.enums;

import lombok.Getter;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class holds form document types enumerators specified to be supported.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Getter
public enum FormDocumentType {

  CONSENT("Virtual VITA/TCE Taxpayer Consent", "https://www.irs.gov/pub/irs-pdf/f14446.pdf",
          Paths.get("pdf/consent-form.pdf"), "consent-form.pdf"),
  TEST("test.pdf", "http://foersom.com/net/HowTo/data/OoPdfFormExample.pdf", Paths.get("pdf/test.pdf"), "test.pdf"),
  INTAKE("Intake/Interview & Quality Review Sheet", "https://www.irs.gov/pub/irs-pdf/f13614c.pdf",
          Paths.get("pdf/resident-intake.pdf"), "resident-intake.pdf"),
  NON_RESIDENT_INTAKE("Non Resident Alien Intake and Interview Sheet", "https://www.irs.gov/pub/irs-pdf/f13614nr.pdf",
          Paths.get("pdf/non-resident-intake.pdf"), "non-resident-intake.pdf");

  private final String name;
  private final String url;
  private final Path path;
  private final String actualName;

  FormDocumentType(String name, String url, Path path, String actualName) {
    this.name = name;
    this.url = url;
    this.path = path;
    this.actualName = actualName;
  }
}
