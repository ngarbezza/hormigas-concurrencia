package ar.edu.unq.pconc.hormigas.model.herramientas;

import java.util.Random;

public enum Direccion {
    NORTE {
        public Par getMovimiento() {
            return new Par(1, 0);
        }

        public String toString() {
            return "^";
        }
    },
    SUR {
        public Par getMovimiento() {
            return new Par(-1, 0);
        }

        public String toString() {
            return "v";
        }
    },
    ESTE {
        public Par getMovimiento() {
            return new Par(0, 1);
        }

        public String toString() {
            return ">";
        }
    },
    OESTE {
        public Par getMovimiento() {
            return new Par(0, -1);
        }

        public String toString() {
            return "<";
        }
    };

    public abstract Par getMovimiento();

    public static Direccion getDireccionAleatoria() {
        Random rand = new Random();
        return values()[rand.nextInt(4)];
    }
}
