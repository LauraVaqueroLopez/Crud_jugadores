package MiPaquete;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class app {
    public static void main(String[] args) {
        // Crear EntityManagerFactory y EntityManager
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("unidad-equipos");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
//            // INSERTAR UN EQUIPO
//            tx.begin();
//            Equipo nuevoEquipo = new Equipo();
//            nuevoEquipo.setNombre("Equipo Nuevo");
//            nuevoEquipo.setEstadio("Estadio Nuevo");
//            em.persist(nuevoEquipo);
//            tx.commit();
//            System.out.println("Equipo insertado: " + nuevoEquipo.getNombre());

            // INSERTAR UN JUGADOR SIN CREAR UN NUEVO EQUIPO
            String nombreEquipo = "RM"; // Nombre del equipo al que se desea asignar el jugador
            Equipo equipoExistente = em.createQuery("SELECT e FROM Equipo e WHERE e.nombre = :nombre", Equipo.class)
                    .setParameter("nombre", nombreEquipo)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

            if (equipoExistente != null) {
                tx.begin();
                Jugador nuevoJugador = new Jugador();
                nuevoJugador.setNombre("Vinicius");
                nuevoJugador.setEstatura(1.55f);
                nuevoJugador.setPeso(44.4f);
                nuevoJugador.setIdEquipo(equipoExistente);
                em.persist(nuevoJugador);
                tx.commit();
                System.out.println("Jugador insertado: " + nuevoJugador.getNombre() + " en " + equipoExistente.getNombre());
            } else {
                System.out.println("El equipo especificado no existe.");
            }
            // SELECT PARA UN JUGADOR POR ID
            Jugador jugadorEncontrado = em.find(Jugador.class, 7);
            if (jugadorEncontrado != null) {
                System.out.println("Jugador encontrado: \n{\nNombre: " + jugadorEncontrado.getNombre() + "\nEquipo: " + jugadorEncontrado.getIdEquipo().getNombre()+ "\n}");
            } else {
                System.out.println("No se encontró un jugador con el ID proporcionado.");
            }

            // SELECT PARA TODOS LOS JUGADORES
            List<Jugador> listaJugadores = em.createQuery("SELECT j FROM Jugador j", Jugador.class).getResultList();
            System.out.println("Lista de jugadores:");
            for (Jugador jugador : listaJugadores) {
                System.out.println("- " + jugador.getNombre());
            }

            // SELECT PARA TODOS LOS EQUIPOS
            List<Equipo> listaEquipos = em.createQuery("SELECT e FROM Equipo e", Equipo.class).getResultList();
            System.out.println("Lista de equipos:");
            for (Equipo equipo : listaEquipos) {
                System.out.println("- " + equipo.getNombre());
            }

            // UPDATE DE UN JUGADOR
//            tx.begin();
//            Jugador jugadorAActualizar = em.find(Jugador.class, 3);
//            if (jugadorAActualizar != null) {
//                jugadorAActualizar.setNombre("Jugador Actualizado");
//                jugadorAActualizar.setPeso(80.0f);
//                em.merge(jugadorAActualizar);
//                System.out.println("Jugador actualizado: " + jugadorAActualizar.getNombre());
//            } else {
//                System.out.println("No se encontró un jugador con el ID para actualizar.");
//            }
//            tx.commit();
//
//            // UPDATE DE UN EQUIPO
//            tx.begin();
//            Equipo equipoAActualizar = em.find(Equipo.class, 1);
//            if (equipoAActualizar != null) {
//                equipoAActualizar.setNombre("Equipo Actualizado");
//                equipoAActualizar.setEstadio("Estadio Actualizado");
//                em.merge(equipoAActualizar);
//                System.out.println("Equipo actualizado: " + equipoAActualizar.getNombre());
//            } else {
//                System.out.println("No se encontró un equipo con el ID para actualizar.");
//            }
//            tx.commit();

            // DELETE DE UN JUGADOR
            tx.begin();
            Jugador jugadorAEliminar = em.find(Jugador.class, 3);
            Jugador jugadorAElimina2 = em.find(Jugador.class, 4);
            Jugador jugadorAEliminar3 = em.find(Jugador.class, 6);
            if (jugadorAEliminar != null) {
                em.remove(jugadorAEliminar);
                em.remove(jugadorAElimina2);
                em.remove(jugadorAEliminar3);
                System.out.println("Jugador eliminado con éxito.");
            } else {
                System.out.println("No se encontró un jugador con el ID para eliminar.");
            }
            tx.commit();

            // DELETE DE UN EQUIPO
            tx.begin();
            Equipo equipoAEliminar = em.find(Equipo.class, 3);
            Equipo equipoAEliminar2 = em.find(Equipo.class, 6);
            if (equipoAEliminar != null) {
                em.remove(equipoAEliminar);
                em.remove(equipoAEliminar2);
                System.out.println("Equipo eliminado con éxito.");
            } else {
                System.out.println("No se encontró un equipo con el ID para eliminar.");
            }
            tx.commit();

        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback(); // Revertir la transacción en caso de error
            }
            e.printStackTrace();
        } finally {
            em.close(); // Cerrar el EntityManager
            emf.close(); // Cerrar el EntityManagerFactory
        }
    }
}
