package edu.hw7.task3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PersonDatabaseImpl implements PersonDatabase {
    private final Map<Integer, Person> personMap = new HashMap<>();
    private final Map<String, List<Integer>> nameIndex = new HashMap<>();
    private final Map<String, List<Integer>> addressIndex = new HashMap<>();
    private final Map<String, List<Integer>> phoneIndex = new HashMap<>();

    @Override
    public synchronized void add(Person person) {
        int id = person.id();
        personMap.put(id, person);

        addToIndex(nameIndex, person.name(), id);
        addToIndex(addressIndex, person.address(), id);
        addToIndex(phoneIndex, person.phoneNumber(), id);
    }

    @Override
    public synchronized void delete(int id) {
        Person person = personMap.remove(id);

        removeFromIndex(nameIndex, person.name(), id);
        removeFromIndex(addressIndex, person.address(), id);
        removeFromIndex(phoneIndex, person.phoneNumber(), id);
    }

    @Override
    public synchronized List<Person> findByName(String name) {
        return findByIndex(nameIndex, name);
    }

    @Override
    public synchronized List<Person> findByAddress(String address) {
        return findByIndex(addressIndex, address);
    }

    @Override
    public synchronized List<Person> findByPhone(String phone) {
        return findByIndex(phoneIndex, phone);
    }

    private void addToIndex(Map<String, List<Integer>> index, String key, int id) {
        index.computeIfAbsent(key, k -> new ArrayList<>()).add(id);
    }

    private void removeFromIndex(Map<String, List<Integer>> index, String key, int id) {
        index.computeIfPresent(key, (k, ids) -> {
            ids.remove(Integer.valueOf(id));

            if (ids.isEmpty()) {
                return null;
            }

            return ids;
        });
    }

    private List<Person> findByIndex(Map<String, List<Integer>> index, String key) {
        List<Integer> ids = index.getOrDefault(key, new ArrayList<>());
        List<Person> result = new ArrayList<>();

        for (int id : ids) {
            Person person = personMap.get(id);
            if (Objects.nonNull(person)) {
                result.add(person);
            }
        }

        return result;
    }
}
