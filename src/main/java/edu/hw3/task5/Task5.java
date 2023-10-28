package edu.hw3.task5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Task5 {

    private Task5() {
    }

    public static List<Contact> parseContact(String[] contacts, String order) {
        if (Objects.isNull(contacts) || contacts.length == 0) {
            return new ArrayList<>();
        }

        List<Contact> contactList = new ArrayList<>();
        String[] parseContact;

        for (String contact : contacts) {
            parseContact = contact.split(" ");

            if (parseContact.length == 2) {
                contactList.add(new Contact(parseContact[0], parseContact[1]));
            } else {
                contactList.add(new Contact(parseContact[0]));
            }
        }

        Collections.sort(contactList);

        return order(contactList, order);
    }

    private static List<Contact> order(List<Contact> contactList, String order) {
        switch (order) {
            case "ASC" -> {
                return contactList;
            }
            case "DESC" -> {
                Collections.reverse(contactList);
                return contactList;
            }
            default -> throw new IllegalArgumentException("Incorrect order");
        }
    }
}
