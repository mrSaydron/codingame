package ru.mrak.codingame.easy.orderOfSuccession;

import java.util.*;

class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        List<Person> personList = new ArrayList<>(n);
        Map<String, List<Person>> personMap = new HashMap<>();
        for (int i = 0; i < n; i++) {
            Person person = new Person(in.next(), in.next(), in.nextInt(), in.next(), in.next(), in.next());
            if(!personMap.containsKey(person.parent))
                personMap.put(person.parent, new ArrayList<>());
            personMap.get(person.parent).add(person);
            System.err.println(person);
        }
        find(personMap, personList, null);

        for (Person person : personList) {
            System.out.println(person.name);
        }
    }

    private static void find(Map<String, List<Person>> personMap, List<Person> personList, String parent) {
        List<Person> people = personMap.get(parent);
        if(people == null) return;
        people.sort(Comparator.comparing(Person::getGender).reversed().thenComparing(Person::getBirth));
        for (Person person : people) {
            if(!person.death && !person.religion.equals("Catholic")) {
                personList.add(person);
            }
            find(personMap, personList, person.name);
        }
    }

    private static class Person {
        String name;
        String parent;
        int birth;
        boolean death;
        String religion;
        String gender;

        public Person(String name, String parent, int birth, String death, String religion, String gender) {
            this.name = name;
            this.parent = parent.equals("-") ? null : parent;
            this.birth = birth;
            this.death = !death.equals("-");
            this.religion = religion;
            this.gender = gender;
        }

        @Override
        public String toString() {
            return name + " "
                    + (parent != null ? parent : "-") + " "
                    + birth + " "
                    + (death ? "yes" : "-") + " "
                    + religion + " "
                    + gender;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getParent() {
            return parent;
        }

        public void setParent(String parent) {
            this.parent = parent;
        }

        public int getBirth() {
            return birth;
        }

        public void setBirth(int birth) {
            this.birth = birth;
        }

        public boolean isDeath() {
            return death;
        }

        public void setDeath(boolean death) {
            this.death = death;
        }

        public String getReligion() {
            return religion;
        }

        public void setReligion(String religion) {
            this.religion = religion;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    }
}
