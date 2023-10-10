package project2;

public enum Campus {
    NEW_BRUNSWICK(0),
    NEWARK(1),
    CAMDEN(2);

    private final int code;

    private Campus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static Campus fromCode(int code) {
        for (Campus campus : Campus.values()) {
            if (campus.getCode() == code) {
                return campus;
            }
        }
        return null;  // or you can throw an IllegalArgumentException
    }

}
