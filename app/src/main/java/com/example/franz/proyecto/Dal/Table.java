package com.example.franz.proyecto.Dal;

public enum Table {
    tbl_historial("tbl_historial");


    private final String text;

    /**
     * @param text
     */
    private Table(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }
}
