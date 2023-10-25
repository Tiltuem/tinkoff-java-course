package edu.project1.guessResult;

public enum StagesOfHangman {
    ZERO {
        @Override
        public String toString() {
            return """

                        +---+
                        |   |
                        |
                        |
                        |
                        |
                        =========\
                    """;
        }
    },
    ONE {
        @Override
        public String toString() {
            return """

                        +---+
                        |   |
                        |   O
                        |
                        |
                        |
                        =========\
                    """;
        }
    },
    TWO {
        @Override
        public String toString() {
            return """

                        +---+
                        |   |
                        |   O
                        |   |
                        |
                        |
                        =========\
                    """;
        }
    },
    THREE {
        @Override
        public String toString() {
            return """

                        +---+
                        |   |
                        |   O
                        |  /|
                        |
                        |
                        =========\
                    """;
        }
    },
    FOUR {
        @Override
        public String toString() {
            return """

                        +---+
                        |   |
                        |   O
                        |  /|\\
                        |
                        |
                        =========\
                    """;
        }
    },
    FIVE {
        @Override
        public String toString() {
            return """

                        +---+
                        |   |
                        |   O
                        |  /|\\
                        |  /
                        |
                        =========\
                    """;
        }
    },
    SIX {
        @Override
        public String toString() {
            return """

                        +---+
                        |   |
                        |   O
                        |  /|\\
                        |  / \\
                        |
                        =========\
                    """;
        }
    }
}
