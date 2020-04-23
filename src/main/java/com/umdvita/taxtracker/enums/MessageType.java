package com.umdvita.taxtracker.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * This class holds message type enumerators specified to be supported.
 *
 * @author Eric Opoku
 * @version 1.0
 * @since 1.0
 */
@Getter
@ToString
@AllArgsConstructor
public enum MessageType {
  COMPLETE_STAGE("Complete tax stage", "Tax stage successfully completed"),
  SEND_FOR_CLIENT_REVIEW("Send for client review", "Tax successfully submitted for review"),
  TAKE_OVER("Tax case take over", "Take over successful"),
  ASSIGN_CASE("Tax case assignment", "Tax case successfully assigned"),
  CLOSE_CASE("Tax case closing", "Tax case successfully closed"),
  SEND_MESSAGE("Send message", "Message successfully delivered"),
  ATTACH_NOTE("Attach note", "Note successfully attached"),
  INCOMPLETE_PROFILE("Incomplete Profile", "Tax case successfully marked incomplete"),
  NO_GROUP_ASSIGNED("No Group Assigned", "No group assigned, contact your leader"),
  DOCUMENT_UPLOADED("Document upload", "Document successfully uploaded"),

  APPOINTMENT_DELETED("Appointment deleted", "Appointment successfully deleted");

  private final String header;
  private final String body;
}
