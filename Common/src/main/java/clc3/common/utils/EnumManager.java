package clc3.common.utils;

import java.util.HashSet;
import java.util.Set;

public class EnumManager {

    public class IdDescriptionEntry {
        Integer id;
        String description;

        public IdDescriptionEntry(Integer id, String description) {
            this.id = id;
            this.description = description;
        }

        @Override
        public int hashCode() {
            return this.id;
        }
    }

    private Set<IdDescriptionEntry> set = new HashSet<>();

    public Set<IdDescriptionEntry> getAll() {
        return set;
    }

    public boolean add(Enum<?> e) {
        return set.add(new IdDescriptionEntry(e.ordinal(), e.name()));
    }
}
