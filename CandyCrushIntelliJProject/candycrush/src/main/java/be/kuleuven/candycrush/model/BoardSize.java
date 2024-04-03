package be.kuleuven.candycrush.model;

// Definieer het record BoardSize
record BoardSize(int rows, int columns) {
    // Controleer of de opgegeven rijen en kolommen geldig zijn
    public BoardSize {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException("Aantal rijen en kolommen moeten groter zijn dan 0.");
        }
    }
}

