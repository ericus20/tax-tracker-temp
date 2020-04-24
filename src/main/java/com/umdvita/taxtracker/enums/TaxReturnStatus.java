package com.umdvita.taxtracker.enums;

import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class holds TAX RETURN status enumerators specified to be supported.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Getter
@ToString
public enum TaxReturnStatus {

  PREPARER_ASSIGNED(1, 7, "PREPARER_ASSIGNED", "Preparer assigned"),
  LEADER_ASSIGNED(4, 7, "LEADER_ASSIGNED", "Leader assigned"),

  CREATED(2, 0, "CREATED", "Tax case created"),
  PREPARATION(3, 1, "PREPARATION", "Tax case in preparation"),
  IN_REVIEW(5, 2, "IN REVIEW", "Tax case in review"),
  CLIENT_REVIEW(6, 3, "CLIENT REVIEW", "Ready for Client's Review"),
  READY_FOR_FILING(7, 4, "READY FOR FILING", "Tax case ready for filing"),
  FILED(8, 5, "FILED", "E-file/Paper file completed"),

  INCOMPLETE(9, -1, "INCOMPLETE", "Document or Information needed");

  private final int id;
  private final int index;
  private final String name;
  private final String content;

  TaxReturnStatus(final int id, int index, final String roleName, String content) {
    this.id = id;
    this.index = index;
    this.name = roleName;
    this.content = content;
  }

  public static List<TaxReturnStatus> getSteps() {
    return Arrays.stream(values())
            .filter(status ->
                    !status.equals(LEADER_ASSIGNED) && !status.equals(PREPARER_ASSIGNED) && !status.equals(INCOMPLETE))
            .collect(Collectors.toList());
  }
}
