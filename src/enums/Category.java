package enums;

import java.util.HashMap;
import java.util.Map;

public enum Category {

    EMPLOYEE(1, "Empregado"),
    EMPLOYER(2, "Empregador"),
    UNEMPLOYED(3, "Desempregado"),
    NONE(4, "Nenhuma");

    private static Map<String, Category> categoryMap = new HashMap<>();
    private int id;
    private String description;
    Category(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public static Category getCategoryById(int id) {
        return categoryMap.get(String.valueOf(id));
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    static {
        for (Category c: Category.values()) {
            categoryMap.put(String.valueOf(c.getId()), c);
        }
    }

}
